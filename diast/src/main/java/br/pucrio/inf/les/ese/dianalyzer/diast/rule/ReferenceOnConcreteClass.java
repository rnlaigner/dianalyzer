package br.pucrio.inf.les.ese.dianalyzer.diast.rule;

import com.github.javaparser.ast.CompilationUnit;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.Element;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ElementResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ObjectType;

public class ReferenceOnConcreteClass extends AbstractRule {

	public ReferenceOnConcreteClass() {
		super();
	}

	@Override
	public ElementResult processRule(CompilationUnit cu, Element element) {
		
		ElementResult elementResult = new ElementResult();
		
		elementResult.setElement(element);
		elementResult.setResult(false);
		
		if (! element.getClassType().equals(ObjectType.INTERFACE)){
			elementResult.setResult(true);
		}
		
		return elementResult;
	}
	
	

}
