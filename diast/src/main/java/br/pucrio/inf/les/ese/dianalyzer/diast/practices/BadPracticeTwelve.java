package br.pucrio.inf.les.ese.dianalyzer.diast.practices;

import br.pucrio.inf.les.ese.dianalyzer.diast.logic.InjectionBusiness;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.AbstractElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.CompilationUnitResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ElementResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.rule.MultipleFormsOfInjection;
import com.github.javaparser.ast.CompilationUnit;

import java.util.List;

public class BadPracticeTwelve extends AbstractPractice {
	
	private final MultipleFormsOfInjection rule;

	public BadPracticeTwelve() {
		rule = new MultipleFormsOfInjection();
		setName("Multiple forms of injection for a given element");
		setNumber(12);
	}

	@Override
	public CompilationUnitResult process(final CompilationUnit cu) {
		
		CompilationUnitResult cuResult = new CompilationUnitResult();

		final List<AbstractElement> elements = InjectionBusiness.getInjectedElementsFromClass(cu);
        
        final List<ElementResult> results = rule.processRule(cu, elements);
        
        cuResult.addAllElementResultToList(results);
    
        return cuResult;
		
	}

}
