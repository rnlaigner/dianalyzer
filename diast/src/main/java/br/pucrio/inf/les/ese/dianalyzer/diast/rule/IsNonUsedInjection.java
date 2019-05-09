package br.pucrio.inf.les.ese.dianalyzer.diast.rule;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.AbstractElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ElementResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.InjectedElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ProducerAnnotation;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.List;

public class IsNonUsedInjection extends AbstractRuleWithElement {

	private Integer numberOfAppearances;

	private MethodCallExprVisitor visitor;

	public IsNonUsedInjection() {
		super();
		numberOfAppearances = 0;
		visitor = new MethodCallExprVisitor();
	}

	@Override
	public ElementResult processRule(CompilationUnit cu, AbstractElement element) {

		// tentar resolver com thread sleep de um segundo

		//Start a new search
		//countOccurrences(cu, element);
		visitor.visit(cu,element);

		//checkStatementsForOccurrence(cu,element);

		if (numberOfAppearances.compareTo(0) == 0) {
			numberOfAppearances = 0;
			final ElementResult result = new ElementResult(true, element);
			return result;
		}

		numberOfAppearances = 0;
		final ElementResult result = new ElementResult(false, element);
		return result;

	}

	private class MethodCallExprVisitor extends VoidVisitorAdapter<AbstractElement> {

		@Override
		public void visit(MethodCallExpr methodCallExpr, AbstractElement element) {

			if( doesItAppear ( methodCallExpr, element ) ){
				numberOfAppearances++;
				return;
			}

			for(Expression expr : methodCallExpr.getArguments()){
				if( doesItAppear ( expr, element ) ){
					numberOfAppearances++;
					return;
				}
			}

		}

	}

//	public void countOccurrences(CompilationUnit cu, AbstractElement element) {
//
//		List<MethodCallExpr> methodCalls = cu.findAll( MethodCallExpr.class );
//
//		for( MethodCallExpr methodCall : methodCalls ) {
//
//			if( doesItAppear ( methodCall, element ) ){
//				numberOfAppearances++;
//				break;
//			}
//
//			for(Expression expr : methodCall.getArguments()){
//				if( !(expr instanceof MethodCallExpr) ){
//					if( doesItAppear ( expr, element ) ){
//						numberOfAppearances++;
//						break;
//					}
//				}
//			}
//		}
//
//	}

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

	protected boolean doesItAppear(Expression expr, AbstractElement element)
	{
		final String nodeName = getNodeName(expr);

		return element.getName().equals(nodeName);
	}

//	private void checkStatementsForOccurrence(CompilationUnit cu, AbstractElement element){
//
//		List<ForStmt> forStmts = cu.findAll( ForStmt.class );
//
//		for(ForStmt forStmt : forStmts){
//
//			List<Expression> exprs = forStmt.getInitialization();
//
//			for(Expression expr : exprs){
//
//				if(expr.isNameExpr()){
//					log.info("Expr name: "+expr.asNameExpr().getNameAsString());
//				}
//
//			}
//
//		}
//
//	}

}
