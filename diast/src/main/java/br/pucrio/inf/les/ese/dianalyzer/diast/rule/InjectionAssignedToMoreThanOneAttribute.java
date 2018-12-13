package br.pucrio.inf.les.ese.dianalyzer.diast.rule;

import java.util.List;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.AbstractElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.AssignmentBusiness;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ElementResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ProducerAnnotation;

public class InjectionAssignedToMoreThanOneAttribute extends AbstractRule {
	
	/*
	 class ExampleBusiness extends GenericBusinessImpl {
	 
	    @Inject
	    Business anotherInjection;
	    
	    Business variable;
		
		public void anyMethod() {
			variable = anotherInjection;
		}
		
	 }
	*/
	
	private Integer attributeAssignment;
	
	private MethodDeclarationVisitor methodDeclarationVisitor;
	
	public InjectionAssignedToMoreThanOneAttribute() {
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
			
			NodeList<Statement> statements = methodDeclaration.getBody().get().getStatements();
			
			//search body for reassignment of element
			List<AssignExpr> list = AssignmentBusiness.getAssignmentsFromStatements(statements);
				
			for(AssignExpr expr : list){
				
				String value = expr.getValue().toString();
				if (value.contains("this.")){
					value = value.substring(value.indexOf("this."),value.length());
				}
				if(value.equals(element.getName())){
					attributeAssignment++;
				}
				
			}
			
	    }
		
	}

	@Override
	public ElementResult processRule(CompilationUnit cu, AbstractElement element) {
		
		attributeAssignment = 0;
		
		//iterate through method declarations
		methodDeclarationVisitor.visit(cu, element);		
		
		ElementResult result = new ElementResult();
		
		result.setElement(element);
		
		result.setResult(false);
		
		if (attributeAssignment > 1) result.setResult(true);
		
        return result;
		
	}

}
