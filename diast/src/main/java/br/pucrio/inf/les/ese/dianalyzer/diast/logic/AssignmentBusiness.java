package br.pucrio.inf.les.ese.dianalyzer.diast.logic;

import java.util.List;
import java.util.stream.Collectors;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.stmt.Statement;

public class AssignmentBusiness {
	
	public static List<AssignExpr> getAssignmentsFromStatements( NodeList<Statement> statements){
		
		return statements
				.stream()
				.filter(st -> st.isExpressionStmt() ? st
						.asExpressionStmt().getExpression().isAssignExpr() : false)
				.map(st -> st.asExpressionStmt().getExpression().asAssignExpr())
				.collect(Collectors.toList());
		
	}

}
