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
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

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

	public GodDependencyInjectionClass() {
		super();
	}
	
	private class MethodCallExprVisitor extends VoidVisitorAdapter<List<AbstractElement>> {
		
		@Override
	    public void visit(MethodCallExpr methodCallExpr, List<AbstractElement> injectedElements)
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
                    && injectedElements.contains(variable) ) {
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

                        if(injectedElements.stream().filter(p->p.getName().contentEquals(var)).count() > 0){
                            elements.add(variable);
                            tightClassCohesionMetric.put(methodDeclaration.getNameAsString(), elements );
                        }

                    }

                }
            }

	    }
		
	}
	
	@Override
	public List<ElementResult> processRule(CompilationUnit cu) {
		
		List<ElementResult> resultSet = new ArrayList<ElementResult>();
		
		//Map<MethodDeclaration,Integer> methodCyclomaticComplexityMap = new HashMap<MethodDeclaration,Integer>();
		
		/*
		 	Weighted Method Count (WMC(C)) is the sum of the
			cyclomatic complexity of all methods in C [3] [20].
		 */
		
		int complexity = 0;
		
		for ( MethodDeclaration mtdDecl : cu.getChildNodesByType(MethodDeclaration.class) ) {
			for ( IfStmt ifStmt : mtdDecl.getChildNodesByType(IfStmt.class) ) {
	            complexity++;
	            if (ifStmt.getElseStmt().isPresent()) {
	                if (!(ifStmt.getElseStmt().get() instanceof IfStmt)) {
	                    complexity++;
	                }
	            }
			}
		}
		
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
		methodDeclarationVisitor.visit(cu,injectedElements);
		
		//TODO agora basta verificar quais atributos aparecem em ambos metodos no Hash
		
		//result.setElement(element);
		// calculateTCC(cu,elements);
		ElementResult elementResult = new ElementResult();
		
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
