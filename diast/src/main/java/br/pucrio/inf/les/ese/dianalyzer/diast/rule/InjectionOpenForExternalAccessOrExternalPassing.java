package br.pucrio.inf.les.ese.dianalyzer.diast.rule;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.InjectedElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.AbstractElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ElementResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ProducerAnnotation;

public class InjectionOpenForExternalAccessOrExternalPassing extends AbstractMethodCallVisitor {
	
	private Integer methodOpening = 0;
	
	private Integer methodPassing = 0;
	
	private MethodDeclarationVisitor methodDeclarationVisitor;
	
	public InjectionOpenForExternalAccessOrExternalPassing() {
		super();
		methodDeclarationVisitor = new MethodDeclarationVisitor();
	}

	private class MethodDeclarationVisitor extends VoidVisitorAdapter<AbstractElement> {
		
		@Override
	    public void visit(MethodDeclaration methodDeclaration, AbstractElement element)
	    {
			InjectedElement element_ = (InjectedElement) element;
			
	        //identify methods that return the instance that was previously injected e.g. getMethods
			String elementType = element_.getType();
			
			String methodType = methodDeclaration.getTypeAsString();
			
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
			
			if(methodType.equals("void") || containsProducerAnnotation) return;
			
			//README o certo certo aqui seria verificar se o atributo sendo retornado eh igual ao 
			//elemento passado como parametro nesse metodo
			//entretanto, grande parte dos casos o trecho abaixo vai satisfazer o que buscamos
			
			if(elementType.equals(methodType)) methodOpening++;
	    }
		
	}

	@Override
	protected void visitMethodCallImpl(MethodCallExpr methodCall, AbstractElement arg) {
		
		//identify a method call that pass the element as parameter
			
		NodeList<Expression> arguments = methodCall.getArguments();
		
		//remove all expressions that are not nameexpr
		arguments.removeIf(a -> !a.isNameExpr());
		
		try {
			if (arguments
					.stream()
					.anyMatch(
							a -> a
							.asNameExpr()
							.getName()
							.getIdentifier()
							.equals(arg.getName())
							)
				)
				methodPassing++;		
		}
		catch(IllegalStateException e) {
			System.out.println("some argument is not a nameexpr");
		}
	}

	@Override
	public ElementResult processRule(CompilationUnit cu, AbstractElement element) {
		
		//iterate through method calls
		visit(cu,element);
		
		//iterate through method declarations
		methodDeclarationVisitor.visit(cu, element);
		
		ElementResult result = new ElementResult();
		
		result.setElement(element);
		
		result.setResult(false);
		
		if(methodPassing > 0 || methodOpening > 0) result.setResult(true);
		
        return result;
		
	}

}
