package br.pucrio.inf.les.ese.dianalyzer.diast.logic;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.stmt.*;

import java.util.TreeSet;

public class MetricBusiness {

    private static MetricBusiness instance;

    private TreeSet<String> elementsForCyclomaticComplexity = new TreeSet<String>();

    public static MetricBusiness getInstance(){

        if(instance == null){
            instance = new MetricBusiness();
            return instance;
        }
        return instance;
    }


    private MetricBusiness(){

        elementsForCyclomaticComplexity.add( IfStmt.class.getName() );
        elementsForCyclomaticComplexity.add(WhileStmt.class.getName() );
        elementsForCyclomaticComplexity.add(DoStmt.class.getName() );
        elementsForCyclomaticComplexity.add(ForStmt.class.getName() );
        elementsForCyclomaticComplexity.add(ForeachStmt.class.getName() );
        elementsForCyclomaticComplexity.add(CatchClause.class.getName() );
        elementsForCyclomaticComplexity.add(SwitchStmt.class.getName() );
        elementsForCyclomaticComplexity.add(SwitchEntryStmt.class.getName() );
        elementsForCyclomaticComplexity.add( ReturnStmt.class.getName() );
        elementsForCyclomaticComplexity.add(ThrowStmt.class.getName() );

    }

    public int getCyclomaticComplexity( Node node ){

        int childComplexityReturn = 0;

        if (!node.getChildNodes().isEmpty()){
            for( Node child : node.getChildNodes() ){
                childComplexityReturn = childComplexityReturn + getCyclomaticComplexity(child);
            }
        }

        if ( elementsForCyclomaticComplexity.contains( node.getClass().getCanonicalName() ) ){
            childComplexityReturn++;

            if(node instanceof IfStmt && ((IfStmt) node).asIfStmt().hasElseBlock()){
                childComplexityReturn++;
            }

        }

        return childComplexityReturn;

    }

}
