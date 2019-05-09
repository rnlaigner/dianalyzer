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
		
		final int complexity = MetricBusiness.getInstance().getCyclomaticComplexity(cu);

		final boolean WMCisApplied = complexity > WMC ? true : false;

		final List<AbstractElement> injectedElements = InjectionBusiness.getInjectedElementsFromClass(cu);
		
		final boolean ATFDisApplied = injectedElements.size() > ATFD ? true : false;

		if( WMCisApplied && ATFDisApplied ){
			final ElementResult result = new ElementResult(true,null);
			return result;
		} else {
			final ElementResult result = new ElementResult(false,null);
			return result;
		}
	}

}
