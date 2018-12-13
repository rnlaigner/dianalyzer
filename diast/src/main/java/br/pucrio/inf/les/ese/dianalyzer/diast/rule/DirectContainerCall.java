package br.pucrio.inf.les.ese.dianalyzer.diast.rule;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.stmt.ExpressionStmt;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.AbstractElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ContainerClassType;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.InjectedElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ElementResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.InjectionType;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.VariableDeclarationElement;

public class DirectContainerCall extends AbstractMethodCallVisitor {
	
	List<InjectedElement> containerCallElements = new ArrayList<InjectedElement>();
	
	private boolean isContainerCall(String methodCall) {
		return ContainerClassType.SPRING.getContainerCall().equals(methodCall) || 
				ContainerClassType.CDI.getContainerCall().equals(methodCall);
	}

	@Override
	protected void visitMethodCallImpl(MethodCallExpr methodCall, AbstractElement arg) {
		
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
			
			//iterate over parentNode in order to get ExpressionStmt
			InjectedElement element = getElementInjectedByContainerCall(methodCall);
			
			containerCallElements.add(element);	
		}
		
	}
	
	private InjectedElement getElementInjectedByContainerCall(Node node){
		
		Node expr = node.clone();
		//clone does not set parent node
		expr.setParentNode(node.getParentNode().get());
		
		do {
			expr = expr.getParentNode().get();
		} while( !( expr instanceof ExpressionStmt ) );
		
		AssignExpr assignExpr = (AssignExpr) ((ExpressionStmt) expr).getExpression();
		
		String targetName = assignExpr.getTarget().toString();
		
		InjectedElement element = new InjectedElement();
		//Not possible to get Type at this point
//		element.setClassType(expr);
//		element.setType(type);
		element.setInjectionType(InjectionType.CONTAINER);
		element.setName(targetName);
		return element;
		
	}

	@Override
	public ElementResult processRule(CompilationUnit cu, AbstractElement element) {
		
		visit(cu,element);
		
		ElementResult result = new ElementResult();
		
		result.setElement(element);
		
		result.setResult(false);
		
		if(containerCallElements.size() > 0) result.setResult(true);
		
        return result;
		
	}

}
