package br.pucrio.inf.les.ese.dianalyzer.diast.rule;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.AbstractElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ElementResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.MethodCallExpr;

public abstract class AbstractRuleWithElement extends AbstractRule {
	
	protected abstract ElementResult processRule(CompilationUnit cu, AbstractElement element) throws Exception;

	protected String getMethodCall(MethodCallExpr methodCall) {
		return methodCall.getName().getIdentifier();
	}

}
