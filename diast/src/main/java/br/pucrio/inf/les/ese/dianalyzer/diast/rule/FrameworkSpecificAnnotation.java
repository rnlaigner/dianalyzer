package br.pucrio.inf.les.ese.dianalyzer.diast.rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.javaparser.ast.CompilationUnit;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.Element;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ElementResult;

public class FrameworkSpecificAnnotation extends AbstractRule {

	@Override
	public ElementResult processRule(CompilationUnit cu, Element element) throws Exception {
		throw new Exception("Excecao!");
	}
	
	public List<ElementResult> processRule(CompilationUnit cu, List<Element> elements) {
				
		List<ElementResult> results = new ArrayList<ElementResult>();
		
		//TODO: busca por anotacoes especificas baseado na anotacao do elemento 
		//e os enums injectionAnnotation e producerAnnotation
		
        return results;
	}

}
