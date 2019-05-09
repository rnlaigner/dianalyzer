package br.pucrio.inf.les.ese.dianalyzer.diast.rule;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.*;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.ExpressionStmt;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class DirectContainerCall extends AbstractMethodCallVisitorWithElement {
	
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

			if(element != null) {
				containerCallElements.add(element);
			}
		}
		
	}
	
	private InjectedElement getElementInjectedByContainerCall(Node node){
		
		Node expr = node.clone();
		// clone does not set parent node
		expr.setParentNode(node.getParentNode().get());
		
		do {
			expr = expr.getParentNode().get();
		} while( !( expr instanceof ExpressionStmt ) & expr.getParentNode().isPresent() );

		if(expr == null){
			return null;
		}
		
		String targetName = null;
		InjectedElement element = new InjectedElement();
		
		AssignExpr assignExpr = null;
		
		try{
			assignExpr = (AssignExpr) ((ExpressionStmt) expr).getExpression();
			targetName = assignExpr.getTarget().toString();
		}
		catch(ClassCastException e){
			log.error(e.getMessage());
		}
		
		VariableDeclarationExpr varDeclExpr = null;
		
		try{
			varDeclExpr = (VariableDeclarationExpr) ((ExpressionStmt) expr).getExpression();

			VariableDeclarator varDecl = (VariableDeclarator) varDeclExpr.getVariable(0);

			element.setType(varDecl.getTypeAsString());
			
			targetName = varDecl.getNameAsString();
		}
		catch(ClassCastException e){
			log.error(e.getMessage());
		}

		// TODO need to figure out if it is an interface or class, however, it is not important in this bad practice
		element.setObjectType(ObjectType.CLASS);

		element.setInjectionType(InjectionType.CONTAINER);
		element.setName(targetName);
		return element;
		
	}

	@Override
	public ElementResult processRule(CompilationUnit cu, AbstractElement element) {
		
		visit(cu,element);
		
		if(containerCallElements.size() > 0) {

			final ElementResult result = new ElementResult(true,element);
			return result;
		} else {
			final ElementResult result = new ElementResult(false,element);
			return result;
		}
		
	}

}
