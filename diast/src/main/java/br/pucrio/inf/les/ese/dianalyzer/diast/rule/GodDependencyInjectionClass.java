package br.pucrio.inf.les.ese.dianalyzer.diast.rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import br.pucrio.inf.les.ese.dianalyzer.diast.identification.ConstructorInjectionIdentificator;
import br.pucrio.inf.les.ese.dianalyzer.diast.identification.ContainerCallIdentificator;
import br.pucrio.inf.les.ese.dianalyzer.diast.identification.FieldDeclarationInjectionIdentificator;
import br.pucrio.inf.les.ese.dianalyzer.diast.identification.SetMethodInjectionIdentificator;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.AbstractElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.AssignmentBusiness;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ElementResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.InjectedElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ProducerAnnotation;

public class GodDependencyInjectionClass extends AbstractRuleWithNoElement {
	
	/* Metric for God Class */
	private static final int WMC = 46;
	private static final int ATFD = 5;
	private static final double TCC = 0.3; 
	
	private MethodDeclarationVisitor methodDeclarationVisitor;
	
	private Map<MethodCallExpr,List<InjectedElement>> tightClassCohesionMetric = new HashMap<MethodCallExpr,List<InjectedElement>>();

	public GodDependencyInjectionClass() {
		super();
	}
	
	private class MethodDeclarationVisitor extends VoidVisitorAdapter<InjectedElement> {
		
		@Override
	    public void visit(MethodDeclaration methodDeclaration, InjectedElement element)
	    {
			Boolean containsProducerAnnotation = 
					methodDeclaration
					.getAnnotations()
					.stream()
					.anyMatch(
							 an -> an
							.getName()
							.getIdentifier()
							.matches(ProducerAnnotation.getProducerAnnotationsRegex())
							);
			
			//Pode ser metodo que retorna container call
			
			if (containsProducerAnnotation) return;
			
			NodeList<Statement> statements = methodDeclaration.getBody().get().getStatements();
			
			//search body for reassignment of element
			List<AssignExpr> list = AssignmentBusiness.getAssignmentsFromStatements(statements);
				
			for(AssignExpr expr : list){
				
				String value = expr.getValue().toString();
				if (value.contains("this.")){
					value = value.substring(value.indexOf("this."),value.length());
				}
				if(value.equals(element.getName())){
					//attributeAssignment++;
				}
				
			}
			
	    }
		
	}

	@Override
	public ElementResult processRule(CompilationUnit cu, AbstractElement element) throws Exception {
		throw new Exception("Not implemented");
	}
	
	@Override
	public List<ElementResult> processRule(CompilationUnit cu) {
		
		ElementResult result = new ElementResult();
		
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

	    they both access the same class-level variable, or
	    a calls b, or b calls a.

		 */
		
		
		
		methodDeclarationVisitor.visit(cu,null);
		
		//result.setElement(element);
		
		result.setResult(false);
		
		return null;
	}


	
	private void processTightClassCohesionMetric(MethodCallExpr methodCall, InjectedElement element) {
		
		List<InjectedElement> value;
		
		//verifica se existe
		if (tightClassCohesionMetric.containsKey(methodCall)) {
		    // Okay, there's a key but the value is null
			value = tightClassCohesionMetric.get(methodCall);		
		} else {
		   // Definitely no such key
		   value = new ArrayList<InjectedElement>();
		}
		
		value.add(element);
		tightClassCohesionMetric.put(methodCall, value);
		
	}

}
