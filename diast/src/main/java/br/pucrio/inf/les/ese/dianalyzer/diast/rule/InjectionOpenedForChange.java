package br.pucrio.inf.les.ese.dianalyzer.diast.rule;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.*;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class InjectionOpenedForChange extends AbstractRuleWithElement {
	
	Integer methodLikeSetter;
	
	/*
	 * 
		class H {
			@Inject IExample1 one;
			// other declarations
			public void setOne(IExample1 one) {
				this.one = one;
			}
		}
	 */
	
	private MethodDeclarationVisitor methodDeclarationVisitor;
	
	public InjectionOpenedForChange() {
		methodDeclarationVisitor = new MethodDeclarationVisitor();
	}
	
	private class MethodDeclarationVisitor extends VoidVisitorAdapter<AbstractElement> {
		
		@Override
	    public void visit(MethodDeclaration methodDeclaration, AbstractElement element)
	    {			
			//it does not matter if it is void or it returns something
			
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
			
			Boolean containsInjectionAnnotation = 
					methodDeclaration
					.getAnnotations()
					.stream()
					.anyMatch(
							 an -> an
							.getName()
							.getIdentifier()
							.matches(InjectionAnnotation.getInjectionAnnotationsRegex())
							);
			
			//Pode ser metodo que retorna container call
			
			if (containsProducerAnnotation || containsInjectionAnnotation) return;
			
			NodeList<Parameter> parameters = methodDeclaration.getParameters();
			
			if (parameters.size() == 0) return;
			
			InjectedElement element_ = (InjectedElement) element;
			
			//verifica se o tipo do elemento esta presente nos parametros
			if(!parameters.stream().anyMatch(p -> p.getTypeAsString().toString().equals(element_.getType()))			) 
			{
				return;
			}
			
			NodeList<Statement> statements = methodDeclaration.getBody().get().getStatements();
			
			//search body for reassignment of element
			for(Statement stmt : statements){
				
				if (stmt.isExpressionStmt() && stmt.asExpressionStmt().getExpression().isAssignExpr()) {
					AssignExpr expr = stmt.asExpressionStmt().getExpression().asAssignExpr();

					if (expr.getTarget() != null) {

						if (expr.getTarget() instanceof FieldAccessExpr) {
							FieldAccessExpr target = (FieldAccessExpr) expr.getTarget();

							if (target.getName().toString().equals(element.getName())) {
								methodLikeSetter++;
							}
						} else if (expr.getTarget() instanceof NameExpr) {
							NameExpr target = (NameExpr) expr.getTarget();

							if (target.getName().toString().equals(element.getName())) {
								methodLikeSetter++;
							}
						} else {
							System.out.println("ACHEI UMA COISA ESTRANHA...");
						}

					}
				}
			}
	    }
		
	}

	@Override
	public ElementResult processRule(CompilationUnit cu, AbstractElement element) {
		
		methodLikeSetter = 0;
		
		methodDeclarationVisitor.visit(cu,element);
		
		ElementResult result = new ElementResult();
		
		result.setElement(element);
		
		result.setResult(false);
		
		Boolean isPublic = false;
		
		try{
			isPublic = element.getModifiers().stream().anyMatch(m -> m.equals("public"));
		}
		catch(Exception e){
			log.error(e.getMessage());
		}
		
		if(methodLikeSetter > 0 || isPublic) result.setResult(true);
		
        return result;
	}



}
