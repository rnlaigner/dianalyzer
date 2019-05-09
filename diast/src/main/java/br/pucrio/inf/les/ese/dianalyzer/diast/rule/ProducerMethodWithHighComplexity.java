package br.pucrio.inf.les.ese.dianalyzer.diast.rule;

import br.pucrio.inf.les.ese.dianalyzer.diast.logic.MetricBusiness;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.AbstractElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ElementResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ProducerMethodElement;
import com.github.javaparser.ast.CompilationUnit;

public class ProducerMethodWithHighComplexity extends AbstractRuleWithElement {

    private static final int WMC = 8;

	@Override
	public ElementResult processRule(CompilationUnit cu, AbstractElement element) {

		ProducerMethodElement producerMethodElement = (ProducerMethodElement) element;

        /*
		 	Weighted Method Count (WMC(C)) is the sum of the
			cyclomatic complexity of all methods in C [3] [20].
		 */

        final int complexity = MetricBusiness.getInstance().getCyclomaticComplexity(producerMethodElement.getBody());

        final boolean WMCisApplied = complexity >= WMC ? true : false;

        final ElementResult result = new ElementResult(WMCisApplied,element);
        return result;
	}
}
