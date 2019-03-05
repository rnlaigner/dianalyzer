package br.pucrio.inf.les.ese.dianalyzer.diast.rule;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.AbstractElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ProducerMethodElement;
import com.github.javaparser.ast.CompilationUnit;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ElementResult;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.IfStmt;

public class ProducerMethodWithHighComplexity extends AbstractRuleWithElement {

    /* Metric for Bad Practice 3 */
    // TODO adjust WMC for producer method
    private static final int WMC = 46;

	@Override
	public ElementResult processRule(CompilationUnit cu, AbstractElement element) {

		ProducerMethodElement producerMethodElement = (ProducerMethodElement) element;

		BlockStmt codeBlock = producerMethodElement.getBody();

        /*
		 	Weighted Method Count (WMC(C)) is the sum of the
			cyclomatic complexity of all methods in C [3] [20].
		 */

        int complexity = 0;

        for ( IfStmt ifStmt : producerMethodElement.getBody().getChildNodesByType(IfStmt.class) ) {

            // We found an "if" - cool, add one.
            complexity++;
            if (ifStmt.getElseStmt().isPresent()) {
                // This "if" has an "else"
                if (ifStmt.getElseStmt().get() instanceof IfStmt) {
                    // it's an "else-if". We already count that by counting the "if" above.
                } else {
                    // it's an "else-something". Add it.
                    complexity++;
                }
            }

        }

        boolean WMCisApplied = complexity > WMC ? true : false;

		return null;
	}
}
