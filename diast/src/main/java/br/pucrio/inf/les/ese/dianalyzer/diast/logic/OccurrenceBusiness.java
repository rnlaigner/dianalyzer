package br.pucrio.inf.les.ese.dianalyzer.diast.logic;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.AbstractElement;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

public class OccurrenceBusiness {

    private final Log log = LogFactory.getLog(OccurrenceBusiness.class);

    private static OccurrenceBusiness instance;

    public static OccurrenceBusiness getInstance(){

        if(instance == null){
            instance = new OccurrenceBusiness();
            return instance;
        }
        return instance;
    }

    public Integer countOccurrences(CompilationUnit cu, AbstractElement element) {

        Integer numberOfAppearances = 0;

        List<MethodCallExpr> methodCalls = cu.findAll( MethodCallExpr.class );

        for( MethodCallExpr methodCall : methodCalls ) {

            if( doesItAppear ( methodCall, element ) ){
                numberOfAppearances++;
                break;
            }

            for(Expression expr : methodCall.getArguments()){
                if( !(expr instanceof MethodCallExpr) ){
                    if( doesItAppear ( expr, element ) ){
                        numberOfAppearances++;
                        break;
                    }
                }
            }
        }

        return numberOfAppearances;

    }

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
        String nodeName = getNodeName(expr);

        return element.getName().equals(nodeName);
    }

}
