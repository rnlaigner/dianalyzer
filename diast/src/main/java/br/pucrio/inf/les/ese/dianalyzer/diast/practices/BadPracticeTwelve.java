package br.pucrio.inf.les.ese.dianalyzer.diast.practices;

import java.util.List;

import br.pucrio.inf.les.ese.dianalyzer.diast.logic.InjectionBusiness;
import com.github.javaparser.ast.CompilationUnit;

import br.pucrio.inf.les.ese.dianalyzer.diast.identification.ConstructorInjectionIdentificator;
import br.pucrio.inf.les.ese.dianalyzer.diast.identification.FieldDeclarationInjectionIdentificator;
import br.pucrio.inf.les.ese.dianalyzer.diast.identification.MethodInjectionIdentificator;
import br.pucrio.inf.les.ese.dianalyzer.diast.identification.SetMethodInjectionIdentificator;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.AbstractElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.CompilationUnitResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ElementResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.rule.MultipleFormsOfInjection;

public class BadPracticeTwelve extends AbstractPractice {
	
	private MultipleFormsOfInjection rule;

	public BadPracticeTwelve() {
		rule = new MultipleFormsOfInjection();
		
		setName("Multiple forms of injection for a given element");
		setNumber(12);
	}

	@Override
	public CompilationUnitResult process(final CompilationUnit cu) {
		
		CompilationUnitResult cuResult = new CompilationUnitResult();

		List<AbstractElement> elements = InjectionBusiness.getInjectedElementsFromClass(cu);
        
        List<ElementResult> results = rule.processRule(cu, elements);
        
        results.stream().forEach(p -> cuResult.addElementResultToList(p));
    
        return cuResult;
		
	}

}
