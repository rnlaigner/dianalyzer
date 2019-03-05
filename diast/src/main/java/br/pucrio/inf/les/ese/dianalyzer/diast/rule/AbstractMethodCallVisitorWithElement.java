package br.pucrio.inf.les.ese.dianalyzer.diast.rule;

import java.util.NoSuchElementException;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.AbstractElement;

public abstract class AbstractMethodCallVisitorWithElement extends AbstractRuleWithElement {
	
	protected MethodCallVisitor methodCallVisitor;
	
	public AbstractMethodCallVisitorWithElement(){
		methodCallVisitor = new MethodCallVisitor();
	}
	
	protected void visit(CompilationUnit cu, AbstractElement element) {
		methodCallVisitor.visit(cu, element);
	}
	
	protected abstract void visitMethodCallImpl(MethodCallExpr methodCall, AbstractElement arg);
	
	private class MethodCallVisitor extends VoidVisitorAdapter<AbstractElement> {
		
		
		@Override
	    public void visit(MethodCallExpr methodCall, AbstractElement arg)
	    {
	        visitMethodCallImpl(methodCall, arg);
	    }
		
	}

	protected boolean doesItAppear(String nodeName, AbstractElement element)
    {
    	return element.getName().equals(nodeName);
    }
	
	protected String getNodeName(MethodCallExpr methodCall)
	{
		try{
			Expression expr = methodCall.getScope().get();
			if(expr instanceof FieldAccessExpr){
				return ((FieldAccessExpr) expr).getNameAsString();
			}
		}
		catch(NoSuchElementException e){
			//Nothing to do
		}
		
		return methodCall.getChildNodes().get(0).toString();
	}
	
	protected String getMethodCall(MethodCallExpr methodCall) {
		return methodCall.getName().getIdentifier();
	}
	
}