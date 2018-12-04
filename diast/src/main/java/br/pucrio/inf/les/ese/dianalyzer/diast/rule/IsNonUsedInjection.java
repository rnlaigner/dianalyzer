package br.pucrio.inf.les.ese.dianalyzer.diast.rule;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.MethodCallExpr;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.Element;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ElementResult;

public class IsNonUsedInjection extends AbstractMethodCallVisitor {
	
	Integer numberOfAppearances = 0;

	@Override
	public ElementResult processRule(CompilationUnit cu, Element element) {
		
		visit(cu,element);
		
		ElementResult result = new ElementResult();
		
		result.setElement(element);
		
		result.setResult(false);
		
		if(numberOfAppearances > 0) result.setResult(true);
		
        return result;
	}

	@Override
	protected void visitMethodCallImpl(MethodCallExpr methodCall, Element element) {
        
		String nodeName = getNodeName(methodCall);
    	
		Boolean itDoesAppear = doesItAppear( nodeName, element );
		
		if(itDoesAppear) {
			numberOfAppearances++;
		}
    		
	}



}
