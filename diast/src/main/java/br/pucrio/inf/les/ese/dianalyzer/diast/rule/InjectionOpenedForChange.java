package br.pucrio.inf.les.ese.dianalyzer.diast.rule;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.Element;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ElementResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ProducerAnnotation;

public class InjectionOpenedForChange extends AbstractRule {
	
	Integer methodLikeSetter = 0;
	
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
	
	private class MethodDeclarationVisitor extends VoidVisitorAdapter<Element> {
		
		@Override
	    public void visit(MethodDeclaration methodDeclaration, Element element)
	    {			
			//it does not matter if it is void or it returns something
			//String methodType = methodDeclaration.getTypeAsString();
			
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
			
			if (parameters.size() == 0) return;
			
			//verifica se o tipo do elemento esta presente nos parametros
			if(!parameters.stream().anyMatch(p -> p.getTypeAsString().toString().equals(element.getType()))			) 
			{
				return;
			}
			
			NodeList<Statement> statements = methodDeclaration.getBody().get().getStatements();
			
			//search body for reassigment of element
			for(Statement stmt : statements){
				
				if (stmt.isExpressionStmt() && stmt.asExpressionStmt().getExpression().isAssignExpr()){
					AssignExpr expr = stmt.asExpressionStmt().getExpression().asAssignExpr();
					String target = expr.getTarget().toString();
					if (target.contains("this.")){
						target = target.substring(target.indexOf("this."),target.length());
					}
					if(target.equals(element.getName())){
						methodLikeSetter++;
					}
				}
				
			}
	    }
		
	}

	@Override
	public ElementResult processRule(CompilationUnit cu, Element element) {
		
		methodDeclarationVisitor.visit(cu,element);
		
		ElementResult result = new ElementResult();
		
		result.setElement(element);
		
		result.setResult(false);
		
		Boolean isPublic = element.getModifiers().stream().anyMatch(m -> m.equals("public"));
		
		if(methodLikeSetter > 0 || isPublic) result.setResult(true);
		
        return result;
	}



}
