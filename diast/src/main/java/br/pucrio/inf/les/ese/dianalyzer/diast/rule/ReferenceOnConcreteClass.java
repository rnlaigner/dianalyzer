package br.pucrio.inf.les.ese.dianalyzer.diast.rule;

import com.github.javaparser.ast.CompilationUnit;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.InjectedElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.AbstractElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ElementResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ObjectType;

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
		
		if (! element_.getClassType().equals(ObjectType.INTERFACE)){
			elementResult.setResult(true);
		}
		
		return elementResult;
	}
	
	

}
