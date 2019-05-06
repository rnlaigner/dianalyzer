package br.pucrio.inf.les.ese.dianalyzer.diast.rule;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.AbstractElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ElementResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.SimpleName;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class IsNonUsedInjection extends AbstractRuleWithElement {
	
	private final AtomicInteger numberOfAppearances = new AtomicInteger(0);

	@Override
	public ElementResult processRule(CompilationUnit cu, AbstractElement element) {
		
		//Start a new search
		numberOfAppearances.set(0);
		
		//visitMethodCallImpl(cu, element);

		if (checkOccurrence(cu, element)){
			numberOfAppearances.incrementAndGet();
			log.info("Check occurrence got");
		}
		
		ElementResult result = new ElementResult();
		
		result.setElement(element);
		
		result.setResult(true);
		
		if(numberOfAppearances.intValue() > 0) {
			result.setResult(false);
			log.info("Entered on if...");
		}
		
		log.info("Number of appearances: "+ numberOfAppearances );
		
        return result;
	}

	private boolean checkOccurrence(CompilationUnit cu, AbstractElement element){

		return cu.findAll(SimpleName.class)
				.stream()
				.filter(p -> {
							if (p.getIdentifier().contentEquals(element.getName())) {
								if(p.getParentNode().isPresent()) {
									return !p.getParentNode()
											.get()
											.getClass()
											.getSimpleName()
											.contentEquals("VariableDeclarator");
								}
							}
							return false;
						}
				)
				.count() > 0;

	}

	protected void visitMethodCallImpl(CompilationUnit cu, AbstractElement element) {

		//recursive call if parameter is a methodcallexpr
		
		List<MethodCallExpr> methodCalls = cu.findAll( MethodCallExpr.class );
		
		for( MethodCallExpr methodCall : methodCalls ) {
		
			for(Expression expr : methodCall.getArguments()){
				if(expr instanceof MethodCallExpr){
					// visitMethodCallImpl((MethodCallExpr)expr,element);
				} else {
					increaseNumberOfAppearancesIfElementAppears( expr, element );
				}
			}
			
			increaseNumberOfAppearancesIfElementAppears( methodCall, element );
			
		}

		

	}

	private void increaseNumberOfAppearancesIfElementAppears( Expression expr, AbstractElement element ){

		String nodeName = getNodeName(expr);

		Boolean itDoesAppear = doesItAppear( nodeName, element );

		if(itDoesAppear) {
			numberOfAppearances.incrementAndGet();
		}

	}

}
