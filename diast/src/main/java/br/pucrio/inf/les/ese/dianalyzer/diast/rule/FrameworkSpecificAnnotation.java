package br.pucrio.inf.les.ese.dianalyzer.diast.rule;

import java.util.ArrayList;
import java.util.List;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.AbstractElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ProducerMethodElement;
import com.github.javaparser.ast.CompilationUnit;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.ElementResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.InjectedElement;

public class FrameworkSpecificAnnotation extends AbstractRuleWithElements {

	@Override
	public List<ElementResult> processRule(CompilationUnit cu, List<AbstractElement> elements) {
		List<ElementResult> results = new ArrayList<ElementResult>();

		// busca por anotacoes especificas baseado na anotacao do elemento
		//e os enums injectionAnnotation e producerAnnotation
		try {
			elements.stream()
					.filter(p -> (p instanceof InjectedElement
							&& ((InjectedElement) p).getAnnotation().isSpecific())
							|| (p instanceof ProducerMethodElement
							&& ((ProducerMethodElement) p).getAnnotation().isSpecific()))
					.forEach(p -> {
						ElementResult result = new ElementResult();
						result.setElement(p);
						result.setResult(true);
						results.add(result);
					});
		}
		catch(NullPointerException e){
			log.info("There are elements with no annotation");
		}

		return results;
	}
}
