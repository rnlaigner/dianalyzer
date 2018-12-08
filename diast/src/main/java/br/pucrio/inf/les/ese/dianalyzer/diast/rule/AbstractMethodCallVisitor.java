package br.pucrio.inf.les.ese.dianalyzer.diast.rule;

import java.util.NoSuchElementException;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.Element;

public abstract class AbstractMethodCallVisitor extends AbstractRule {
	
	protected MethodCallVisitor methodCallVisitor;
	
	public AbstractMethodCallVisitor(){
		methodCallVisitor = new MethodCallVisitor();
	}
	
	protected void visit(CompilationUnit cu, Element element) {
		methodCallVisitor.visit(cu, element);
	}
	
	protected abstract void visitMethodCallImpl(MethodCallExpr methodCall, Element arg);
	
	private class MethodCallVisitor extends VoidVisitorAdapter<Element> {
		
		
		@Override
	    public void visit(MethodCallExpr methodCall, Element arg)
	    {
	        visitMethodCallImpl(methodCall, arg);
	    }
		
	}

	protected boolean doesItAppear(String nodeName, Element element)
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
