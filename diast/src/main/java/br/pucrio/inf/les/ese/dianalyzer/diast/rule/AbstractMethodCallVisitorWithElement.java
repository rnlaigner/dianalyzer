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
	
}
