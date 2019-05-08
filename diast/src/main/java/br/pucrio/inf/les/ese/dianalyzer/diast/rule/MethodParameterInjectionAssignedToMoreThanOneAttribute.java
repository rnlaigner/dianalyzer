package br.pucrio.inf.les.ese.dianalyzer.diast.rule;

import br.pucrio.inf.les.ese.dianalyzer.diast.logic.AssignmentBusiness;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.*;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class MethodParameterInjectionAssignedToMoreThanOneAttribute extends AbstractRuleWithNoElementMultipleResults {
	
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
			
			NodeList<Statement> statements = null;
			
			try{
				statements = methodDeclaration.getBody().get().getStatements();
			}
			catch(NoSuchElementException e){
				log.info(e.getMessage());
				log.info("Interface being processed. This rule does not apply to it.");
				return;
			}
			
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
				
				if (assignmentsFromCurrentParameter.size() > 1) {
					
					MethodElement methodElement = new MethodElement();
					
					InjectedElement methodParameter = new InjectedElement();
					
					// TODO aqui poderia ser set_method tambem
					methodParameter.setInjectionType(InjectionType.METHOD);
					
					// TODO verificar se eh interface precisa do hashmap de todos os compilation unit
					methodParameter.setObjectType(ObjectType.CLASS);
					
					// TODO methodParameter recebe annotation caso methoddeclaration tenha
					
					methodParameter.setType(parameter.getTypeAsString());
					
					methodParameter.setModifiers(parameter.getModifiers().stream().map(p->p.asString()).collect(Collectors.toList()));
					
					methodParameter.setType( parameter.getTypeAsString() );
					
					methodParameter.setName( parameter.getNameAsString() );
					
					methodElement.addParameter( methodParameter );
					
					methodElement.setModifiers(methodDeclaration.getModifiers().stream().map(p->p.asString()).collect(Collectors.toList()));
					
					methodElement.setBody(methodDeclaration.getBody().get());

					methodElement.setModifiers( methodDeclaration.getModifiers().stream().map(p->p.asString()).collect(Collectors.toList()) );

					List<String> elems = assignmentsFromCurrentParameter
							.stream()
							.filter(p -> p.getTarget().isFieldAccessExpr())
							.map(p-> p.getTarget().asFieldAccessExpr().getNameAsString())
							.distinct()
							.collect(Collectors.toList());
					
					for( String elem : elems){
						MethodElement newMethodElement = methodElement.clone();
						newMethodElement.setName(elem);
						methodParameterAssignedToMoreThanOneAttributeList.add( newMethodElement );
					}

				}
				
			}
			
	    }
		
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
