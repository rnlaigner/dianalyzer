package br.pucrio.inf.les.ese.dianalyzer.diast.rule;

import br.pucrio.inf.les.ese.dianalyzer.diast.logic.OccurrenceBusiness;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.AbstractElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ElementResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.ForStmt;

import java.util.List;

public class IsNonUsedInjection extends AbstractRuleWithElement {

	@Override
	public synchronized ElementResult processRule(CompilationUnit cu, AbstractElement element) {

		// tentar resolver com thread sleep de um segundo

		final List<MethodCallExpr> methodCalls = cu.findAll( MethodCallExpr.class );

		//Start a new search
		final Integer numberOfAppearances = OccurrenceBusiness.getInstance().countOccurrences(methodCalls, element);

		// checkStatementsForOccurrence(cu,element);
		
		final ElementResult result = new ElementResult();

		if(numberOfAppearances == 0) {
			result.setResult(true);
		} else {
			result.setResult(false);
		}

		result.setElement(element);
		
        return result;
	}

	private void checkStatementsForOccurrence(CompilationUnit cu, AbstractElement element){

		List<ForStmt> forStmts = cu.findAll( ForStmt.class );

		for(ForStmt forStmt : forStmts){

			List<Expression> exprs = forStmt.getInitialization();

			for(Expression expr : exprs){

				if(expr.isNameExpr()){
					log.info("Expr name: "+expr.asNameExpr().getNameAsString());
				}

			}

		}

	}

}