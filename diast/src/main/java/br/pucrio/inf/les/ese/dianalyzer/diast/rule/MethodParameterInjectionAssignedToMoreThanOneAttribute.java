package br.pucrio.inf.les.ese.dianalyzer.diast.rule;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.AssignmentBusiness;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.Element;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ElementResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.MethodElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ProducerAnnotation;

public class MethodParameterInjectionAssignedToMoreThanOneAttribute extends AbstractRuleWithNoElement {
	
	/*
	 class ExampleBusiness extends GenericBusinessImpl {
	 
		IDAO exampleDAO;
		
		@Inject
		public void setExampleDAO(ExampleDAO exampleDAO) {
			this.genericDAO = exampleDAO; //genericDAO is an attribute of GenericBusinessImpl
			this.exampleDAO = exampleDAO;
		}
		
	 }
	*/
	
	private List<MethodElement> methodParameterAssignedToMoreThanOneAttributeList;
	
	private MethodDeclarationVisitor methodDeclarationVisitor;
	
	public MethodParameterInjectionAssignedToMoreThanOneAttribute() {
		super();
		methodDeclarationVisitor = new MethodDeclarationVisitor();
	}

	private class MethodDeclarationVisitor extends VoidVisitorAdapter<Element> {
		
		@Override
	    public void visit(MethodDeclaration methodDeclaration, Element element)
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
			
			NodeList<Parameter> parameters = methodDeclaration.getParameters();
			
			NodeList<Statement> statements = methodDeclaration.getBody().get().getStatements();
			
			//search body for reassignment of element
			List<AssignExpr> assignments = AssignmentBusiness.getAssignmentsFromStatements(statements);
				
			for(Parameter parameter : parameters){
			
				//get assignments from current parameter
				List<AssignExpr> assignmentsFromCurrentParameter = assignments
											.stream()
											.filter(a -> a
													.getValue()
													.toString()
													.equals(parameter.getName().toString())
													)
											.collect(Collectors.toList());
				
				if (assignmentsFromCurrentParameter.size() > 0) {
					
					//bad practice
					//TODO finish
					
				}
				
			}
			
	    }
		
	}

	@Override
	public ElementResult processRule(CompilationUnit cu, Element element) throws Exception {
		throw new Exception("Not implemented");
	}

	@Override
	public List<ElementResult> processRule(CompilationUnit cu) {
		
		methodParameterAssignedToMoreThanOneAttributeList = new ArrayList<MethodElement>();
		
		//iterate through method declarations
		methodDeclarationVisitor.visit(cu, null);		
		
		List<ElementResult> result = new ArrayList<ElementResult>();
		
		//result.setElement(null);
		
		//result.setResult(false);
		
		if (!methodParameterAssignedToMoreThanOneAttributeList.isEmpty()){
			//result.setResult(true);
			//TODO finish 
		}
		
        return result;
	}

}
