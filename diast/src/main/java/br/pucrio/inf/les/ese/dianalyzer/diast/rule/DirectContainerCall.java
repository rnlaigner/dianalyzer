package br.pucrio.inf.les.ese.dianalyzer.diast.rule;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.stmt.ExpressionStmt;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.ContainerClassType;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.Element;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ElementResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.InjectionType;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.VariableDeclarationElement;

public class DirectContainerCall extends AbstractMethodCallVisitor {
	
	List<Element> containerCallElements = new ArrayList<Element>();
	
	private boolean isContainerCall(String methodCall) {
		return ContainerClassType.SPRING.getContainerCall().equals(methodCall) || 
				ContainerClassType.CDI.getContainerCall().equals(methodCall);
	}

	@Override
	protected void visitMethodCallImpl(MethodCallExpr methodCall, Element arg) {
		
		VariableDeclarationElement arg_ = (VariableDeclarationElement) arg;
		
		NameExpr nameNode;
		
		try {
			nameNode = (NameExpr) methodCall.getScope().get();
		}
		catch(NoSuchElementException | ClassCastException e){
			return;
		}
		
		if(!nameNode.getName().getIdentifier().equals(arg_.getVariableName())) return;
		
		String methodCallStr = getMethodCall(methodCall);
    	
		Boolean isContainerCall = isContainerCall( methodCallStr );
		
		if ( isContainerCall ) {
			//TODO add element
			//Element element = new Element();
			
//			element.setClassType(classType);
//			element.setInjectionType(injectionType);
//			element.setName(name);
//			element.setType(type);
			
			//recursion over parentNode in order to get ExpressionStmt
			Element element = getElementInjectedByContainerCall(methodCall);
			
			containerCallElements.add(element);
			//containerCallCount++;		
		}
		
	}
	
	private Element getElementInjectedByContainerCall(Node node){
		
		Node expr = node.clone();
		expr.setParentNode(node.getParentNode().get());
		
		//if(expression instanceof ExpressionStmt)
		
		do {
			
			expr = expr.getParentNode().get();
			
			
			
		} while( !( expr instanceof ExpressionStmt ) );
		
		
		
		//expr = (ExpressionStmt) expr;
		
		AssignExpr assignExpr = (AssignExpr) ((ExpressionStmt) expr).getExpression();
		
		String targetName = assignExpr.getTarget().toString();
		
		Expression target = assignExpr.getTarget();
		
		//target.get
		
		Element element = new Element();
//		element.setClassType(expr);
		element.setInjectionType(InjectionType.CONTAINER);
		element.setName(targetName);
//		element.setType(type);
		return element;
		
	}

	@Override
	public ElementResult processRule(CompilationUnit cu, Element element) {
		
		visit(cu,element);
		
		ElementResult result = new ElementResult();
		
		result.setElement(element);
		
		result.setResult(false);
		
		if(containerCallElements.size() > 0) result.setResult(true);
		
        return result;
		
	}

}
