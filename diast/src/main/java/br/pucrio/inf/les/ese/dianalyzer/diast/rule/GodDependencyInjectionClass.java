package br.pucrio.inf.les.ese.dianalyzer.diast.rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.IfStmt;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.Element;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ElementResult;

public class GodDependencyInjectionClass extends AbstractRuleWithMultipleElements {
	
	private static final int WMC = 46;
	
	private static final int ATFD = 5;
	
	private Map<Element,Integer> accessToForeignDataMetric = new HashMap<Element,Integer>();
	
	private Map<MethodCallExpr,List<Element>> tightClassCohesionMetric = new HashMap<MethodCallExpr,List<Element>>();

	public GodDependencyInjectionClass() {
		super();
	}

	@Override
	public ElementResult processRule(CompilationUnit cu, Element element) throws Exception {
		throw new Exception("Not implemented");
	}
	
	@Override
	public ElementResult processRule(CompilationUnit cu, List<Element> elements) {
		
		ElementResult result = new ElementResult();
		
		Map<MethodDeclaration,Integer> methodCyclomaticComplexityMap = new HashMap<MethodDeclaration,Integer>();
		
		/*
		 	Weighted Method Count (WMC(C)) is the sum of the
			cyclomatic complexity of all methods in C [3] [20].
		 */
		//TODO define cyclomatic complexity
		
		for ( MethodDeclaration mtdDecl : cu.getChildNodesByType(MethodDeclaration.class) ) {
			
			int complexity = 0;
			
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
		
		/* DEFINITION 1
		   Access To Foreign Data (ATFD(C)) is the number of
		   attributes of foreign classes accessed directly by class
		   C or via accessor methods [17].
		   DEFINITION 2
		   Access to Foreign Data (ATFD) represents the number of external
		   classes from which a given class accesses attributes, directly or
           via accessor-methods.
		 */		
		
		for( Element element : elements ){
			visit(cu, element);
		}
		
		//TODO count the number of access to foreign data
		
		
		/*
			Tight Class Cohesion (TCC(C)) is the relative number
			of directly connected methods in C. Two methods are
			directly connected if they access the same instance
			variables of C [2].
		 */
		//TODO count class cohesion
		
		
		//result.setElement(element);
		
		result.setResult(false);
		
		return null;
	}

	@Override
	protected void visitMethodCallImpl(MethodCallExpr methodCall, Element element) {
		
		String nodeName = getNodeName(methodCall);
    	
		Boolean itDoesAppear = doesItAppear( nodeName, element );
		
		if(itDoesAppear) {
			
			Element key = element;
			
			processAccessToForeignDataMetric(key);
			processTightClassCohesionMetric(methodCall,element);
			
		}
		
	}

	private void processAccessToForeignDataMetric(Element key) {
		//verifica se existe
		if (accessToForeignDataMetric.containsKey(key)) {
		    // Okay, there's a key but the value is null
			Integer value = accessToForeignDataMetric.get(key);
			
			if(value == null){
				value = 1;
			} else {
				value++;	
			}
			
			accessToForeignDataMetric.put(key, value);
			
		} else {
		   // Definitely no such key
			accessToForeignDataMetric.put(key, 1);
		}
	}
	
	private void processTightClassCohesionMetric(MethodCallExpr methodCall, Element element) {
		
		List<Element> value;
		
		//verifica se existe
		if (tightClassCohesionMetric.containsKey(methodCall)) {
		    // Okay, there's a key but the value is null
			value = tightClassCohesionMetric.get(methodCall);		
		} else {
		   // Definitely no such key
		   value = new ArrayList<Element>();
		}
		
		value.add(element);
		tightClassCohesionMetric.put(methodCall, value);
		
	}

}
