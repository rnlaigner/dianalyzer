package br.pucrio.inf.les.ese.dianalyzer.diast.rule;

import java.util.List;
import java.util.NoSuchElementException;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import br.pucrio.inf.les.ese.dianalyzer.diast.logic.MethodCallBusiness;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.AbstractElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ElementResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.InjectionAnnotation;

public class AppearsInEveryMethod extends AbstractRule {

	private Integer numberOfAppearances;
	private Integer numberOfMethods;
	
	private MethodDeclarationVisitor methodDeclarationVisitor;
	
	public AppearsInEveryMethod(){
		super();
		methodDeclarationVisitor = new MethodDeclarationVisitor();
	}
	
	private class MethodDeclarationVisitor extends VoidVisitorAdapter<AbstractElement> {
		
		@Override
	    public void visit(MethodDeclaration methodDeclaration, AbstractElement element)
	    {
//			Boolean containsProducerAnnotation = 
//					methodDeclaration
//					.getAnnotations()
//					.stream()
//					.anyMatch(
//							 an -> an
//							.getName()
//							.getIdentifier()
//							.matches(ProducerAnnotation.getProducerAnnotationsRegex())
//							);
//			
//			if (containsProducerAnnotation) return;
			
//			NodeList<Parameter> parameters = methodDeclaration.getParameters();
			
			ClassOrInterfaceDeclaration classOrInterfaceDeclaration = (ClassOrInterfaceDeclaration) methodDeclaration.getParentNode().get();
			
			if(classOrInterfaceDeclaration.isInterface()){
				return;
			}
			
			NodeList<Statement> statements = null;
			
			try{
				statements = methodDeclaration.getBody().get().getStatements();
			}
			catch(NoSuchElementException e){
				log.error(e.getMessage());
				log.error(e.getStackTrace());
			}
			
			//check if it is an abstract method
			if(statements == null){
				return;
			}
			
			//se methodDeclaration is methodinjection, return
			Boolean containsInjectionAnnotation = methodDeclaration.getAnnotations().stream().anyMatch(
													 an -> an
													.getName()
													.getIdentifier()
													.matches(InjectionAnnotation.getInjectionAnnotationsRegex())
													);
			
			if (containsInjectionAnnotation) return;
			
			//search body for usage of element
			List<MethodCallExpr> elementMethodCalls = MethodCallBusiness.findElementMethodCall( statements, element );

			numberOfMethods++;
			
			if (elementMethodCalls.size() > 0){
				numberOfAppearances++;
			}
			
	    }
		
	}

	@Override
	public ElementResult processRule(CompilationUnit cu, AbstractElement element) {
		
		numberOfAppearances = 0;
		numberOfMethods = 0;
		
		methodDeclarationVisitor.visit(cu,element);
		
		ElementResult result = new ElementResult();
		
		result.setElement(element);
		
		result.setResult(false);
		
		if(numberOfAppearances == numberOfMethods){
			result.setResult(true);
		}
		
        return result;
	}
	
}
