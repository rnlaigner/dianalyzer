package br.pucrio.inf.les.ese.dianalyzer.diast.practices;

import java.util.List;

import br.pucrio.inf.les.ese.dianalyzer.diast.logic.InjectionBusiness;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.InjectedElement;
import com.github.javaparser.ast.CompilationUnit;

import br.pucrio.inf.les.ese.dianalyzer.diast.identification.ConstructorInjectionIdentificator;
import br.pucrio.inf.les.ese.dianalyzer.diast.identification.FieldDeclarationInjectionIdentificator;
import br.pucrio.inf.les.ese.dianalyzer.diast.identification.MethodInjectionIdentificator;
import br.pucrio.inf.les.ese.dianalyzer.diast.identification.SetMethodInjectionIdentificator;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.AbstractElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.CompilationUnitResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ElementResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.rule.FrameworkSpecificAnnotation;

public class BadPracticeNine extends AbstractPractice {
	
	private FrameworkSpecificAnnotation rule;

	public BadPracticeNine() {
		rule = new FrameworkSpecificAnnotation();
		setName("Framework specific annotation or configuration");
		setNumber(9);
	}

	@Override
	public CompilationUnitResult process(final CompilationUnit cu) {
		
		CompilationUnitResult cuResult = new CompilationUnitResult();

		List<AbstractElement> elements = InjectionBusiness.getInjectedElementsFromClass(cu);
        
    	List<ElementResult> result = rule.processRule(cu, elements);
    	cuResult.addAllElementResultToList(result);
        
        return cuResult;
		
	}

}
