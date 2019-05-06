package br.pucrio.inf.les.ese.dianalyzer.diast.rule;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.AbstractElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ElementResult;

public abstract class AbstractRuleWithElement extends AbstractRule {
	
	protected abstract ElementResult processRule(CompilationUnit cu, AbstractElement element) throws Exception;
	
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
