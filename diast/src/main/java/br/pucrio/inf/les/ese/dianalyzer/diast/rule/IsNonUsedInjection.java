package br.pucrio.inf.les.ese.dianalyzer.diast.rule;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.Element;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ElementResult;

public class IsNonUsedInjection extends AbstractMethodCallVisitor {
	
	private Integer numberOfAppearances;

	@Override
	public ElementResult processRule(CompilationUnit cu, Element element) {
		
		//Start a new search
		numberOfAppearances = 0;
		
		visit(cu, element);
		
		ElementResult result = new ElementResult();
		
		result.setElement(element);
		
		result.setResult(true);
		
		if(numberOfAppearances > 0) { 
			result.setResult(false);
		}
		
        return result;
	}

	@Override
	protected void visitMethodCallImpl(MethodCallExpr methodCall, Element element) {
        
		String nodeName = getNodeName(methodCall);
		
		//recursive call if parameter is a methodcallexpr
		for(Expression expr : methodCall.getArguments()){
			if(expr instanceof MethodCallExpr){
				visitMethodCallImpl((MethodCallExpr)expr,element);
			}
		}
    	
		Boolean itDoesAppear = doesItAppear( nodeName, element );
		
		if(itDoesAppear) {
			numberOfAppearances++;
		}
    		
	}



}
