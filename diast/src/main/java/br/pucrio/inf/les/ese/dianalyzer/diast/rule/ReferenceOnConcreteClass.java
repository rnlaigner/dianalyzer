package br.pucrio.inf.les.ese.dianalyzer.diast.rule;

import com.github.javaparser.ast.CompilationUnit;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.AbstractElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ElementResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.InjectedElement;

public class ReferenceOnConcreteClass extends AbstractRuleWithElement {

	public ReferenceOnConcreteClass() {
		super();
	}

	@Override
	public ElementResult processRule(CompilationUnit cu, AbstractElement element) {
		
		ElementResult elementResult = new ElementResult();
		
		elementResult.setElement(element);
		elementResult.setResult(false);
		
		InjectedElement element_ = (InjectedElement) element;
		
		/* 
		if (! element_.getClassType().equals(ObjectType.INTERFACE)){
			elementResult.setResult(true);
		}
		*/
		
		//FIXME the best test would check for the given class
		// TODO adicionar busca do tipo a associated data ...
		//Precisaria ter todos os compilation Unit em memoria para definir isso
		if( !element_.getType().startsWith("I") &&
				/* not contain business */ 
				 !element_.getName().toLowerCase().contains("business")) {
			elementResult.setResult(true);
		}
		
		return elementResult;
	}
	
	

}
