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

import br.pucrio.inf.les.ese.dianalyzer.diast.model.AbstractElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.AssignmentBusiness;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.InjectedElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ElementResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.InjectionType;
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

	private class MethodDeclarationVisitor extends VoidVisitorAdapter<AbstractElement> {
		
		@Override
	    public void visit(MethodDeclaration methodDeclaration, AbstractElement element)
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
					
					MethodElement methodElement = new MethodElement();
					
					InjectedElement methodParameter = new InjectedElement();
					
					//TODO aqui poderia ser set_method tambem
					methodParameter.setInjectionType(InjectionType.METHOD);
					
					//methodParameter.set
					methodParameter.setType( parameter.getTypeAsString() );
					
					methodParameter.setName( parameter.getNameAsString() );
					
					methodElement.addParameter( methodParameter );
					
					methodElement.setBody(methodDeclaration.getBody().toString());
					
					methodParameterAssignedToMoreThanOneAttributeList.add( methodElement );
					
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
		
		methodParameterAssignedToMoreThanOneAttributeList = new ArrayList<MethodElement>();
		
		//iterate through method declarations
		methodDeclarationVisitor.visit(cu, null);		
		
		List<ElementResult> result = new ArrayList<ElementResult>();

		for( MethodElement method : methodParameterAssignedToMoreThanOneAttributeList) {
			result.add( new ElementResult(true,method) );
		}
		
        return result;
	}

}
