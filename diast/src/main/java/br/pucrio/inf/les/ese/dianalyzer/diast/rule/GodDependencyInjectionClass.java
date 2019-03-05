package br.pucrio.inf.les.ese.dianalyzer.diast.rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import br.pucrio.inf.les.ese.dianalyzer.diast.identification.ConstructorInjectionIdentificator;
import br.pucrio.inf.les.ese.dianalyzer.diast.identification.ContainerCallIdentificator;
import br.pucrio.inf.les.ese.dianalyzer.diast.identification.FieldDeclarationInjectionIdentificator;
import br.pucrio.inf.les.ese.dianalyzer.diast.identification.SetMethodInjectionIdentificator;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.AbstractElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ElementResult;

public class GodDependencyInjectionClass extends AbstractRuleWithNoElement {
	
	/* Metric for God Class */
	private static final int WMC = 46;
	private static final int ATFD = 5;
	private static final double TCC = 0.3; 
	
	private MethodCallExprVisitor methodDeclarationVisitor = new MethodCallExprVisitor();
	
	private Map<String,List<String>> tightClassCohesionMetric = 
			new HashMap<String,List<String>>();

	public GodDependencyInjectionClass() {
		super();
	}
	
	private class MethodCallExprVisitor extends VoidVisitorAdapter<AbstractElement> {
		
		@Override
	    public void visit(MethodCallExpr methodCallExpr, AbstractElement element)
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
				
			} /*else if (stmt instanceof IfStmt){
				
			} */ else {
				while( ! ( nodeParent instanceof MethodDeclaration ) ){
					nodeParent = nodeParent.getParentNode().get();
				}
			}
			
			MethodDeclaration methodDeclaration = (MethodDeclaration) nodeParent;
			
			if (tightClassCohesionMetric.get(methodDeclaration.getNameAsString()) != null){
				List<String> elements = tightClassCohesionMetric.get(methodDeclaration.getNameAsString());
				elements.add(methodCallExpr.getScope().toString());
				tightClassCohesionMetric.put(methodDeclaration.getNameAsString(), elements );
			} else {
				List<String> elements = new ArrayList<String>();
				elements.add(methodCallExpr.getScope().toString());
				tightClassCohesionMetric.put(methodDeclaration.getNameAsString(), elements );
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
				
				// We found an "if" - cool, add one.
	            complexity++;
	            if (ifStmt.getElseStmt().isPresent()) {
	                // This "if" has an "else"
	                if (ifStmt.getElseStmt().get() instanceof IfStmt) {
	                    // it's an "else-if". We already count that by counting the "if" above.
	                } else {
	                    // it's an "else-something". Add it.
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

        FieldDeclarationInjectionIdentificator fieldId = new FieldDeclarationInjectionIdentificator();
        ConstructorInjectionIdentificator constructorId = new ConstructorInjectionIdentificator();
        SetMethodInjectionIdentificator setMethodId = new SetMethodInjectionIdentificator();
        ContainerCallIdentificator contId = new ContainerCallIdentificator();
        
        List<AbstractElement> elements = fieldId.identify(cu);
        elements.addAll(constructorId.identify(cu));
        elements.addAll(setMethodId.identify(cu));
        elements.addAll(contId.identify(cu));
		
		boolean ATFDisApplied = elements.size() > ATFD ? true : false;
		
		/*
			Tight Class Cohesion (TCC(C)) is the relative number
			of directly connected methods in C. Two methods are
			directly connected if they access the same instance
			variables of C [2].
		 */
		//TODO count class cohesion
		//Referencia: https://www.aivosto.com/project/help/pm-oo-cohesion.html
		//A ideia aqui eh percorrer todo metodo e guardar todas as instancias utilizadas
		//Depois disso, par a par, veriicar se ha atributos utilizados por ambos
		
		/*
		 Which methods are related? Methods a and b are related if:
	     they both access the same class-level variable, or a calls b, or b calls a.
		 */
		methodDeclarationVisitor.visit(cu,null);
		
		//TODO agora basta verificar quais atributos aparecem em ambos metodos no Hash
		
		//result.setElement(element);
		
		ElementResult elementResult = new ElementResult();
		
		resultSet.add(elementResult);
		
		return resultSet;
	}

}
