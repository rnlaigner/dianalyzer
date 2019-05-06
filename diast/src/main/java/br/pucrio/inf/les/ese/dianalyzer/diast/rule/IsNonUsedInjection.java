package br.pucrio.inf.les.ese.dianalyzer.diast.rule;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.AbstractElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ElementResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;

public class IsNonUsedInjection extends AbstractMethodCallVisitorWithElement {
	
	private Integer numberOfAppearances;

	@Override
	public ElementResult processRule(CompilationUnit cu, AbstractElement element) {
		
		//Start a new search
		numberOfAppearances = 0;
		
		visit(cu, element);
		
		ElementResult result = new ElementResult();
		
		result.setElement(element);
		
		result.setResult(false);
		
		if(numberOfAppearances == 0) {
			result.setResult(true);
		}
		
        return result;
	}

	@Override
	protected void visitMethodCallImpl(MethodCallExpr methodCall, AbstractElement element) {

		//recursive call if parameter is a methodcallexpr
		for(Expression expr : methodCall.getArguments()){
			if(expr instanceof MethodCallExpr){
				visitMethodCallImpl((MethodCallExpr)expr,element);
			} else {
				increaseNumberOfAppearancesIfElementAppears( expr, element );
			}
		}

		increaseNumberOfAppearancesIfElementAppears( methodCall, element );

	}

	private void increaseNumberOfAppearancesIfElementAppears( Expression expr, AbstractElement element ){

		String nodeName = getNodeName(expr);

		Boolean itDoesAppear = doesItAppear( nodeName, element );

		if(itDoesAppear) {
			numberOfAppearances++;
		}

	}

}
