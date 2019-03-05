package br.pucrio.inf.les.ese.dianalyzer.diast.rule;

import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.CompilationUnit;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.ElementResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.InjectedElement;

public class FrameworkSpecificAnnotation extends AbstractRuleWithElements {

	@Override
	protected List<ElementResult> processRule(CompilationUnit cu, List<InjectedElement> elements) throws Exception {
		List<ElementResult> results = new ArrayList<ElementResult>();

		// busca por anotacoes especificas baseado na anotacao do elemento
		//e os enums injectionAnnotation e producerAnnotation

		elements.stream()
				.filter(p -> ((InjectedElement) p).getAnnotation().isSpecific() )
				.forEach(p -> {
					ElementResult result = new ElementResult();
					result.setElement(p);
					result.setResult(true);
					results.add(result);
				});

		return results;
	}
}
