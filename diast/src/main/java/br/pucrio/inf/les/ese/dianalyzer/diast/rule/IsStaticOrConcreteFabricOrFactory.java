package br.pucrio.inf.les.ese.dianalyzer.diast.rule;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.AbstractElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.AttributeElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ElementResult;
import com.github.javaparser.ast.CompilationUnit;

public class IsStaticOrConcreteFabricOrFactory extends AbstractRuleWithElement {

	private final String regex = ".*Factory.*|.*factory.*|.*fabric.*|.*Fabric.*";

	public IsStaticOrConcreteFabricOrFactory() {
		super();
	}

	@Override
	public ElementResult processRule(CompilationUnit cu, AbstractElement element) {
		
		ElementResult elementResult = new ElementResult();
		
		elementResult.setElement(element);
		elementResult.setResult(false);

		AttributeElement attributeElement = (AttributeElement) element;

		if( attributeElement.getName().matches( regex )
				|| attributeElement.getType().matches( regex ) ){

			// TODO outra heuristica seria a existencia de metodo createInstance, createBean, create, por exemplo
			//if(attributeElement.getObjectType() == ObjectType.CLASS){
				elementResult.setResult(true);
			//}

		}

		return elementResult;
		

	}
	
	

}
