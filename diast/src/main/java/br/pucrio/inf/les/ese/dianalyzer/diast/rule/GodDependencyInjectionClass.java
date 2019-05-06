package br.pucrio.inf.les.ese.dianalyzer.diast.rule;

import br.pucrio.inf.les.ese.dianalyzer.diast.logic.InjectionBusiness;
import br.pucrio.inf.les.ese.dianalyzer.diast.logic.MetricBusiness;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.AbstractElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ElementResult;
import com.github.javaparser.ast.CompilationUnit;

import java.util.List;

public class GodDependencyInjectionClass extends AbstractRuleWithNoElement {
	
	/* Metric for God Class */
	private static final int WMC = 46;
	private static final int ATFD = 5;
	private static final double TCC = 0.3;

	public GodDependencyInjectionClass() {
		super();
	}

	@Override
	public ElementResult processRule(CompilationUnit cu) {
		
		int complexity = MetricBusiness.getInstance().getCyclomaticComplexity(cu);

		boolean WMCisApplied = complexity > WMC ? true : false;

		List<AbstractElement> injectedElements = InjectionBusiness.getInjectedElementsFromClass(cu);
		
		boolean ATFDisApplied = injectedElements.size() > ATFD ? true : false;

		ElementResult elementResult = new ElementResult();

		elementResult.setResult( WMCisApplied && ATFDisApplied );
		
		return elementResult;
	}

}
