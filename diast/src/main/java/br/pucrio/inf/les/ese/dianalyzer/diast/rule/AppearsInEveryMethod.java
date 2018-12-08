package br.pucrio.inf.les.ese.dianalyzer.diast.rule;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.MethodCallExpr;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.Element;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ElementResult;

public class AppearsInEveryMethod extends AbstractMethodCallVisitor {

	private Integer numberOfAppearances;
	private Integer numberOfMethods;

	@Override
	public ElementResult processRule(CompilationUnit cu, Element element) {
		
		numberOfAppearances = 0;
		numberOfMethods = 0;
		
		visit(cu,element);
		
		ElementResult result = new ElementResult();
		
		result.setElement(element);
		
		result.setResult(false);
		
		if(numberOfAppearances == numberOfMethods) result.setResult(true);
		
        return result;
	}
	
	@Override
	protected void visitMethodCallImpl(MethodCallExpr methodCall, Element arg) {
		
    	String nodeName = getNodeName(methodCall);

    	numberOfMethods++;
    	
		Boolean itDoesAppear = doesItAppear( nodeName, arg );
		
		if (itDoesAppear) numberOfAppearances++;
		
	}
	
}
