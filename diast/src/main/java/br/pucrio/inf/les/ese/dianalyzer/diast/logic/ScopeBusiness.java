package br.pucrio.inf.les.ese.dianalyzer.diast.logic;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.NoSuchElementException;

public class ScopeBusiness {

    private static final Log log = LogFactory.getLog(ScopeBusiness.class);

    public static String extractScopeAnnotationValueAsString(CompilationUnit cu){

        AnnotationExpr annotation = null;

        try {
            annotation = cu.getType(0).getAnnotationByName("Scope").get();
        }
        catch(NoSuchElementException | IndexOutOfBoundsException e){
            return null;
        }

        String annotationAsString = null;

        if( annotation.isSingleMemberAnnotationExpr() ){
            annotationAsString = extractAnnotationAsString( annotation );
            return annotationAsString;
        } else if( annotation.isNormalAnnotationExpr() ){
            annotationAsString = extractAnnotationAsString( annotation );
            return annotationAsString;
        }

        log.info("It is not a single member annotation");
        return null;

    }

    private static String extractAnnotationAsString(AnnotationExpr annotation) {

        String annotationAsString = null;

        if( annotation.isSingleMemberAnnotationExpr() ) {
            SingleMemberAnnotationExpr singleMemberAnnotationExpr = annotation.asSingleMemberAnnotationExpr();

            Expression valueExpr = singleMemberAnnotationExpr.getMemberValue();

            if (valueExpr.isStringLiteralExpr()) {
                annotationAsString = valueExpr.asStringLiteralExpr().asString();
            } else if (valueExpr.isFieldAccessExpr()) {
                annotationAsString = valueExpr.asFieldAccessExpr().getNameAsString();
            } else {
                log.info("It is not recognized");
                return null;
            }
        } else { //if( annotation.isNormalAnnotationExpr() ){
            annotationAsString = annotation.asNormalAnnotationExpr()
                                    .getPairs()
                                    .get(0)
                                    .getValue()
                                    .asStringLiteralExpr()
                                    .asString();
        }

        // FIXME if SPRING project default is singleton...
        if(annotationAsString.equals("SCOPE_PROTOTYPE") || annotationAsString.equals("prototype") ){
            annotationAsString = "prototype";
        }

        return annotationAsString;
    }

}
