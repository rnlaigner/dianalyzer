package br.pucrio.inf.les.ese.dianalyzer.diast.logic;

import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.Statement;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.AbstractElement;

public class MethodCallBusiness {
	
	public static List<MethodCallExpr> 
					findElementMethodCall( NodeList<Statement> statements, AbstractElement element ){
		
		List<MethodCallExpr> methodCallExprList = new ArrayList<MethodCallExpr>();
		
		for(Statement stmt : statements){
			
			List<MethodCallExpr> methodCalls = stmt.getNodesByType(MethodCallExpr.class);
			
			for(MethodCallExpr mce : methodCalls) {
				
				Expression expression;
				try{
					expression = mce.getScope().get();
					
					if(expression.toString().equals(element.getName())){
						methodCallExprList.add(mce);
					}
				}
				catch(Exception e){
					//do anything
				}
				
				//System.out.println(mce.getNameAsString());
				
			}
			
			//while()
			
		}
		
		return methodCallExprList;
		
	}

}
