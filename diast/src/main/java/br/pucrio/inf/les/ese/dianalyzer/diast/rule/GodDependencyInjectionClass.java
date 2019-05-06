package br.pucrio.inf.les.ese.dianalyzer.diast.rule;

import br.pucrio.inf.les.ese.dianalyzer.diast.logic.InjectionBusiness;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.AbstractElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ElementResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.utils.Pair;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class GodDependencyInjectionClass extends AbstractRuleWithNoElementMultipleResults {
	
	/* Metric for God Class */
	private static final int WMC = 46;
	private static final int ATFD = 5;
	private static final double TCC = 0.3; 
	
	private MethodCallExprVisitor methodDeclarationVisitor = new MethodCallExprVisitor();

	// nome do metodo eh a key, list eh a lista de atributos usados pelo metodo
	private Map<String,TreeSet<String>> tightClassCohesionMetric =
			new HashMap<String, TreeSet<String>>();

    private TreeSet<String> elementsForCyclomaticComplexity = new TreeSet<String>();

	public GodDependencyInjectionClass() {
		super();

        elementsForCyclomaticComplexity.add( IfStmt.class.getName() );
        elementsForCyclomaticComplexity.add(WhileStmt.class.getName() );
        elementsForCyclomaticComplexity.add(DoStmt.class.getName() );
        elementsForCyclomaticComplexity.add(ForStmt.class.getName() );
        elementsForCyclomaticComplexity.add(ForeachStmt.class.getName() );
        elementsForCyclomaticComplexity.add(CatchClause.class.getName() );
        elementsForCyclomaticComplexity.add(SwitchStmt.class.getName() );
        elementsForCyclomaticComplexity.add(SwitchEntryStmt.class.getName() );
        elementsForCyclomaticComplexity.add( ReturnStmt.class.getName() );
        elementsForCyclomaticComplexity.add(ThrowStmt.class.getName() );

	}
	
	private class MethodCallExprVisitor extends VoidVisitorAdapter<Pair<List<AbstractElement>,List<String>>> {
		
		@Override
	    public void visit(MethodCallExpr methodCallExpr, Pair<List<AbstractElement>,List<String>> injectedAndMethodElements)
	    {
			//get method the call belongs to
			Node node = methodCallExpr.getParentNode().get();
			Node nodeParent = node.getParentNode().get();
			if (node instanceof VariableDeclarator){
				//pega mais um parent
				
				if (nodeParent instanceof FieldDeclaration){
					//Nao eh chamado dentro de metodo algum
					return;
				}
				
				//itera ate achar methodDeclaration
				while( ! ( nodeParent instanceof MethodDeclaration ) ){
					nodeParent = nodeParent.getParentNode().get();
				}
				
			} else {
				while( ! ( nodeParent instanceof MethodDeclaration ) ){
					nodeParent = nodeParent.getParentNode().get();
				}
			}
			
			MethodDeclaration methodDeclaration = (MethodDeclaration) nodeParent;

            TreeSet<String> elements = null;

			// ideal eh, antes de inserir no map, verificar se sao injected elements ou metodos da classe

			if (tightClassCohesionMetric.get(methodDeclaration.getNameAsString()) != null) {
                elements = tightClassCohesionMetric.get(methodDeclaration.getNameAsString());
            } else {
			    elements = new TreeSet<String>();
            }

            // se eh variabledeclaration
            boolean isVariableDeclaration = false;
            try{
                isVariableDeclaration = methodCallExpr
                        .getParentNode()
                        .get()
                        .getParentNode()
                        .get() instanceof VariableDeclarationExpr ? true : false;
            } catch( Exception e){

            }

            // se esta nos parametros
            String variable = null;

            try {

                if (methodCallExpr.getScope().isPresent() && methodCallExpr.getScope().get().isMethodCallExpr()){

                    // preciso pegar o name do objeto
                    variable = methodCallExpr
                            .getScope()
                            .get()
                            .asMethodCallExpr()
                            .getScope()
                            .get()
                            .asNameExpr()
                            .getNameAsString();

                } else {
                    if(methodCallExpr.getScope().get().isFieldAccessExpr()){
                        variable = methodCallExpr.getScope().get().asFieldAccessExpr().getScope().asNameExpr().getNameAsString().replace("this.", "");
                    } else{
                        variable = methodCallExpr.getScope().get().toString().replace("this.", "");
                    }

                }
            }
            catch(Exception e){

            }

            if( methodDeclaration.getParameterByName( variable ).isPresent() ){
                // esta nos parametros
            } else if ( ! methodDeclaration.getNameAsString().contentEquals( methodCallExpr.getNameAsString() )
                    && injectedAndMethodElements.a.contains(variable) ) {
                // nao eh chamada recursiva
                //e pertence a variaveis da classe, adiciono

                elements.add(variable);
                tightClassCohesionMetric.put(methodDeclaration.getNameAsString(), elements );

            } else {

                // ver childen para ver se parametros sao chamadas de metodos das variaveis da classe
                log.info("Diferente de tudo..");
                for(  Node argument : methodCallExpr.getArguments()  ){

                    if(argument instanceof MethodCallExpr){

                        MethodCallExpr mce = ((MethodCallExpr) argument).asMethodCallExpr();

                        variable = mce.getScope().get().asFieldAccessExpr().getNameAsString().replace("this.", "");;

                        final String var = variable;

                        if(injectedAndMethodElements.a.stream().filter(p->p.getName().contentEquals(var)).count() > 0){
                            elements.add(variable);
                            tightClassCohesionMetric.put(methodDeclaration.getNameAsString(), elements );
                        }

                    } else if (  argument instanceof NameExpr) {

                        // TODO pode ser passagem de atributo injetado, dessa forma, conto tambem
                        log.info("passagem de atributo");
                        variable = ((NameExpr) argument).asNameExpr().getNameAsString();

                        final String var = variable;

                        if(injectedAndMethodElements.a.stream().filter(p->p.getName().contentEquals(var)).count() > 0){
                            elements.add(variable);
                            tightClassCohesionMetric.put(methodDeclaration.getNameAsString(), elements );
                        }

                    } else{
                        log.info("Dont know");
                    }

                }
            }

	    }
		
	}

	private int getCyclomaticComplexity( Node node ){

        int childComplexityReturn = 0;

	    if (!node.getChildNodes().isEmpty()){
	        for( Node child : node.getChildNodes() ){
                childComplexityReturn = childComplexityReturn + getCyclomaticComplexity(child);
            }
        }

	    if ( elementsForCyclomaticComplexity.contains( node.getClass().getCanonicalName() ) ){
            childComplexityReturn++;

            if(node instanceof IfStmt && ((IfStmt) node).asIfStmt().hasElseBlock()){
                childComplexityReturn++;
            }

        }

	    return childComplexityReturn;

    }
	
	@Override
	public List<ElementResult> processRule(CompilationUnit cu) {
		
		List<ElementResult> resultSet = new ArrayList<ElementResult>();
		
		//Map<MethodDeclaration,Integer> methodCyclomaticComplexityMap = new HashMap<MethodDeclaration,Integer>();
		
		/*
		 	Weighted Method Count (WMC(C)) is the sum of the
			cyclomatic complexity of all methods in C [3] [20].
		 */

		// https://sbforge.org/sonar/rules/show/squid:MethodCyclomaticComplexity?modal=true&layout=false
        // The Cyclomatic Complexity is measured by the number of (&&, ||) operators and (if, while, do, for, ?:, catch, switch, case, return, throw)

//        for ( MethodDeclaration mtdDecl : cu.getChildNodesByType(MethodDeclaration.class) ) {
//			for ( IfStmt ifStmt : mtdDecl.getChildNodesByType(IfStmt.class) ) {
//	            complexity++;
//	            if (ifStmt.getElseStmt().isPresent()) {
//	                //if (!(ifStmt.getElseStmt().get() instanceof IfStmt)) {
//	                    complexity++;
//	                //}
//	            }
//			}
//            for ( WhileStmt whileStmt : mtdDecl.getChildNodesByType(WhileStmt.class) ) {
//                complexity++;
//            }
//            for (DoStmt doStmt : mtdDecl.getChildNodesByType(DoStmt.class) ) {
//                complexity++;
//            }
//            for (ForStmt forStmt : mtdDecl.getChildNodesByType(ForStmt.class) ) {
//                complexity++;
//            }
//            for (CatchClause catchStmt : mtdDecl.getChildNodesByType(CatchClause.class) ) {
//                complexity++;
//            }
//            for (SwitchStmt switchStmt : mtdDecl.getChildNodesByType(SwitchStmt.class) ) {
//                complexity++;
//                Long entriesCount = switchStmt.getEntries().stream().count();
//                complexity = complexity + entriesCount.intValue();
//            }
//            for (ReturnStmt returnStmt : mtdDecl.getChildNodesByType(ReturnStmt.class) ) {
//                complexity++;
//            }
//            for (ThrowStmt throwStmt : mtdDecl.getChildNodesByType(ThrowStmt.class) ) {
//                complexity++;
//            }
//		}

        int complexity = getCyclomaticComplexity(cu);

		boolean WMCisApplied = complexity > WMC ? true : false;
		
		/* DEFINITION 1
		   Access To Foreign Data (ATFD(C)) is the number of
		   attributes of foreign classes accessed directly by class
		   C or via accessor methods [17].
		   DEFINITION 2
		   Access to Foreign Data (ATFD) represents the number of external
		   classes from which a given class accesses attributes, directly or
           via accessor-methods.
		 */		

		List<AbstractElement> injectedElements = InjectionBusiness.getInjectedElementsFromClass(cu);
		
		boolean ATFDisApplied = injectedElements.size() > ATFD ? true : false;
		
		/*
			Tight Class Cohesion (TCC(C)) is the relative number
			of directly connected methods in C. Two methods are
			directly connected if they access the same instance
			variables of C [2].
		 */
		/*
		 Which methods are related? Methods a and b are related if:
	     they both access the same class-level variable, or a calls b, or b calls a.
		 */
        // Map<String,String> methods = getClassMethods(cu);

        // Pair<List<AbstractElement>,List<String>> pair = new Pair(injectedElements,methods);

        // Nao esta pegando os elementos abaixo:
        /*
        while (this.userBusiness.retrieveByLoginName(user.getLoginName())!=null) {
				user.setLoginName(user.getLoginName() + generateDuplicateIdentifier());
			}
         */
		// methodDeclarationVisitor.visit(cu,pair);
		
		//result.setElement(element);
		// calculateTCC(cu,elements);

		ElementResult elementResult = new ElementResult();

		elementResult.setResult( WMCisApplied && ATFDisApplied );

		resultSet.add(elementResult);
		
		return resultSet;
	}

	private Map getClassMethods( CompilationUnit cu){

        Map<String,String> methods = new HashMap<String,String>();

        cu.findAll( MethodDeclaration.class ).stream().forEach( mD -> methods.put(mD.getNameAsString(),"") );

        return methods;

    }

	private Integer calculateTCC( CompilationUnit cu, List<AbstractElement> elements ){

		final AtomicInteger count = new AtomicInteger(0);

		Map<String,String> methods = getClassMethods(cu);

		Map<String,TreeSet<String>> methodCallToOtherMethods = new HashMap<String,TreeSet<String>>();

        Map<String,TreeSet<String>> classVariablesInMethods = new HashMap<String,TreeSet<String>>();

		cu.findAll( MethodDeclaration.class ).stream().forEach(
				mD -> {

					mD.getBody().get().asBlockStmt().getStatements().stream().forEach(
						stmt -> {

							if( stmt.isExpressionStmt() ){
								log.info("Heere");
								Expression exprStmt = ((ExpressionStmt) stmt).getExpression();
								if(exprStmt.isVariableDeclarationExpr()){
									// skip
                                    // TODO caso extremo: variavel declarada no metodo tem mesmo nome da variavel de classe
								} else if( exprStmt.isMethodCallExpr() ){
									log.info("pego info do metodo e vejo se eh chamada a metodo da mesma classe");
									MethodCallExpr mce = exprStmt.asMethodCallExpr();
									String methodName = mce.getName().asString();
									if(methods.get(methodName) != null){
                                        TreeSet<String> tree = methodCallToOtherMethods.get( mD.getNameAsString() );
                                        if(tree != null){
                                            tree.add( methodName );
                                        } else{
                                            tree = new TreeSet<String>();
                                            tree.add( methodName );
                                            methodCallToOtherMethods.put( mD.getNameAsString(), tree );
                                        }

									} else if( mce.getScope().isPresent() && mce.getScope().get().isNameExpr() ){

									    String attr = mce.getScope().get().asNameExpr().getName().asString();

									    // vejo se eh chamada de metodo dos atributos da classe
                                        Long isVariableFromClass = elements
                                                .stream()
                                                .filter( p -> p.getName().contentEquals( attr ) )
                                                .count();

                                        if( isVariableFromClass > 0 ){

                                            TreeSet<String> tree = classVariablesInMethods.get( mD.getNameAsString() );
                                            if(tree != null){
                                                tree.add( attr );
                                            } else{
                                                tree = new TreeSet<String>();
                                                tree.add( attr );
                                                classVariablesInMethods.put( mD.getNameAsString(), tree );
                                            }

                                        }
                                    }

								}
							}
						}
					);
				}
		);

		return null;

	}

}
