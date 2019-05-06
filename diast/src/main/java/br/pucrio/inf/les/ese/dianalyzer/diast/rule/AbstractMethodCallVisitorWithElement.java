package br.pucrio.inf.les.ese.dianalyzer.diast.rule;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.AbstractElement;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

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
	
	protected String getNodeName(Expression expression)
	{
		Expression definitiveExpr = expression.clone();

		// enquanto for methodcallexpr eh pq nao chegou ao elemento raiz
		while (definitiveExpr.isMethodCallExpr()
				&& ((MethodCallExpr) definitiveExpr).getScope().isPresent()
			){
			definitiveExpr = ((MethodCallExpr) definitiveExpr).getScope().get();
		}

		if(definitiveExpr.isFieldAccessExpr()){
			return ((FieldAccessExpr) definitiveExpr).getNameAsString();
		}

		if(definitiveExpr.isNameExpr()){
			return definitiveExpr.asNameExpr().getNameAsString();
		}

		String toReturn = null;
		try{
			toReturn = expression.getChildNodes().get(0).toString();
		} catch(Exception e){
			toReturn = "";
			log.info("Type of element: "+expression.getClass().getCanonicalName());
		}

		return toReturn;
	}
	
	protected String getMethodCall(MethodCallExpr methodCall) {
		return methodCall.getName().getIdentifier();
	}
	
}
