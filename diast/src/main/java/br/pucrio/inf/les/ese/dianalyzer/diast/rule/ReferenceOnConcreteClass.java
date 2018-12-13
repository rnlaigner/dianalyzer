package br.pucrio.inf.les.ese.dianalyzer.diast.rule;

import com.github.javaparser.ast.CompilationUnit;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.AbstractElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ElementResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.InjectedElement;

public class ReferenceOnConcreteClass extends AbstractRule {

	public ReferenceOnConcreteClass() {
		super();
	}

	@Override
	public ElementResult processRule(CompilationUnit cu, AbstractElement element) {
		
		ElementResult elementResult = new ElementResult();
		
		elementResult.setElement(element);
		elementResult.setResult(false);
		
		InjectedElement element_ = (InjectedElement) element;
		
		/* Precisaria ter todos os compilation Unit em memoria para definir isso
		if (! element_.getClassType().equals(ObjectType.INTERFACE)){
			elementResult.setResult(true);
		}
		*/
		
		//this is bad! 
		//the best test would check for the given class		
		if( !element_.getType().startsWith("I") &&
				/* not contain business */ 
				 !element_.getName().contains("business")) {
			elementResult.setResult(true);
		}
		
		return elementResult;
	}
	
	

}
