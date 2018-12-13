package br.pucrio.inf.les.ese.dianalyzer.diast.rule;

import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.CompilationUnit;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.AbstractElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ElementResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.InjectedElement;

public class FrameworkSpecificAnnotation extends AbstractRule {

	@Override
	public ElementResult processRule(CompilationUnit cu, AbstractElement element) throws Exception {
		throw new Exception("Excecao!");
	}
	
	public List<ElementResult> processRule(CompilationUnit cu, List<InjectedElement> elements) {
				
		List<ElementResult> results = new ArrayList<ElementResult>();
		
		//TODO: busca por anotacoes especificas baseado na anotacao do elemento 
		//e os enums injectionAnnotation e producerAnnotation
		
        return results;
	}

}
