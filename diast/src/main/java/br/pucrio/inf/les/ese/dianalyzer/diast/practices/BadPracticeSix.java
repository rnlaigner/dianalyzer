package br.pucrio.inf.les.ese.dianalyzer.diast.practices;

import br.pucrio.inf.les.ese.dianalyzer.diast.identification.NonPrimitiveTypeFieldIdentificator;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.AbstractElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.AttributeElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ElementResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.rule.IsStaticOrConcreteFabricOrFactory;
import com.github.javaparser.ast.CompilationUnit;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.CompilationUnitResult;

import java.util.List;

public class BadPracticeSix extends AbstractPractice {

	private final NonPrimitiveTypeFieldIdentificator nonPrimitiveTypeFieldIdentificator;

	private final IsStaticOrConcreteFabricOrFactory rule;

	public BadPracticeSix() {
		setName("Fabric to obtain injected instance");
		setNumber(6);
		this.nonPrimitiveTypeFieldIdentificator = new NonPrimitiveTypeFieldIdentificator();
		this.rule = new IsStaticOrConcreteFabricOrFactory();
	}

	@Override
	public CompilationUnitResult process(final CompilationUnit cu) {

		CompilationUnitResult cuResult = new CompilationUnitResult();

		List<AbstractElement> elements = nonPrimitiveTypeFieldIdentificator.identify(cu);

		for(AbstractElement elem : elements){

			AttributeElement attributeElement = (AttributeElement) elem;

			ElementResult result = rule.processRule(cu, attributeElement);
			if(result.getResult()){
				cuResult.addElementResultToList(result);
			}

		}

        return cuResult;
	}

}
