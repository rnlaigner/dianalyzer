package br.pucrio.inf.les.ese.dianalyzer.diast.analysis;

import java.util.Arrays;
import java.util.List;

import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

//https://stackoverflow.com/questions/32622879/extract-methods-calls-from-java-code
@SuppressWarnings("rawtypes")
public class MethodVisitor extends VoidVisitorAdapter
{
    @SuppressWarnings("unchecked")
	@Override
    public void visit(MethodCallExpr methodCall, Object arg)
    {
        //System.out.print("Method call: " + methodCall.getName() + "\n");
        List<Expression> args = methodCall.getArguments();
        if (args != null)
        {
        	if (args instanceof List)
        	{
        		int numberOfAppearances = 
        				handleInjectedVariables( methodCall, (List<String>) arg );
        		System.out.println( methodCall.getName() + " \n" );
        		System.out.println(numberOfAppearances);
        	}
        	else
        	{
        		handleExpressions(args);
        	}
        	
        }
            
    }
    
    private int handleInjectedVariables(MethodCallExpr methodCall, List<String> variables)
    {
    	int i = 0;
    	//Optional<Node> node = methodCall.getParentNode();
    	String nodeName = methodCall.getChildNodes().get(0).toString();
    	
    	//System.out.println(node.get().getParentNode().get().toString());
    	
    	/*
    	System.out.println( methodCall.getName() + " \n" 
    						//+ methodCall.getNameAsString() + " \n"
    						//+ methodCall.findRootNode().toString() + " "
    						+ methodCall.getParentNodeForChildren().toString() + " \n"
    						//+ methodCall.get
    					   );
    	*/
    	
    	//System.out.println(nodeName);
    	
        for (String variable : variables)
        {
        	if(variable.equals(nodeName))
        	{
        		i++;
        	}
            
        }
        return i;
    }

    private void handleExpressions(List<Expression> expressions)
    {
        for (Expression expr : expressions)
        {
            if (expr instanceof MethodCallExpr)
                visit((MethodCallExpr) expr, null);
            else if (expr instanceof BinaryExpr)
            {
                BinaryExpr binExpr = (BinaryExpr)expr;
                handleExpressions(Arrays.asList(binExpr.getLeft(), binExpr.getRight()));
            }
        }
    }
}
