package br.pucrio.inf.les.ese.dianalyzer.diast.rule;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.AbstractElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ElementResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.InjectedElement;
import com.github.javaparser.ast.CompilationUnit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultipleFormsOfInjection extends AbstractRuleWithElements {

	@Override
	public List<ElementResult> processRule(CompilationUnit cu, List<AbstractElement> elements) {
				
		List<ElementResult> results = new ArrayList<ElementResult>();
		
		//busco por elementos repetidos
		
		Map<String,Integer> elementCountMap = new HashMap<String,Integer>();
		
		for(AbstractElement element : elements){

			element = (InjectedElement) element;
			
			String key = element.getName();
			
			//verifica se existe
			if (elementCountMap.containsKey(key)) {
				
				int value = elementCountMap.get(key);
				
				//valor um apenas uma vez
				if(value == 1){
					ElementResult result = new ElementResult();
					result.setElement(element);
					//nao preciso colocar result para esse bad practice
					results.add(result);
				}
				
				value++;
				elementCountMap.put(key, value);
				
		    } else {
		       // Definitely no such key
		    	elementCountMap.put(key, 1);
		    }
			
		}
		
        return results;
	}

}
