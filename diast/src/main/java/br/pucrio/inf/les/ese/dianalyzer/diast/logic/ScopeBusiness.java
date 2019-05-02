package br.pucrio.inf.les.ese.dianalyzer.diast.logic;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;

import java.util.NoSuchElementException;

public class ScopeBusiness {

    public static String extractScopeAnnotationValueAsString(CompilationUnit cu){

        AnnotationExpr annotation = null;

        try {
            annotation = cu.getType(0).getAnnotationByName("Scope").get();
        }
        catch(NoSuchElementException | IndexOutOfBoundsException e){
            return null;
        }

        String annotationAsString = extractAnnotationAsString( (SingleMemberAnnotationExpr) annotation );

        return annotationAsString;

    }

    private static String extractAnnotationAsString(SingleMemberAnnotationExpr annotation) {

        String annotationAsString = null;
        try {
            annotationAsString = ((StringLiteralExpr) annotation.getMemberValue()).asString();
        } catch (ClassCastException e) {
            // log.info("It is not a string literal");
            return null;
        }

        try{
            annotationAsString = ((FieldAccessExpr) annotation.getMemberValue()).getNameAsString();
        } catch (Exception e) {
            // log.info("It is not a field access expr");
            return null;
        }

        if(annotationAsString.equals("SCOPE_PROTOTYPE")){
            annotationAsString = "prototype";
        }

        return annotationAsString;
    }

}
