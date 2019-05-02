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
import br.pucrio.inf.les.ese.dianalyzer.diast.model.InjectedElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ElementResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.rule.IsNonUsedInjection;

public class BadPracticeFive extends AbstractPractice {
	
	private IsNonUsedInjection rule;

	public BadPracticeFive() {
		rule = new IsNonUsedInjection();
		setName("Non used injection");
		setNumber(5);
	}

	@Override
	public CompilationUnitResult process(final CompilationUnit cu) {
		
		CompilationUnitResult cuResult = new CompilationUnitResult();

		List<AbstractElement> elements = InjectionBusiness.getInjectedElementsFromClass(cu);
        
        for (AbstractElement element : elements) {
        	
        	InjectedElement elem = (InjectedElement) element;
        	
        	ElementResult result = rule.processRule(cu, elem);
        	
        	//is non used injection?
        	if ( result.getResult() ) {
        		cuResult.addElementResultToList(result);	
        	}
        	
        }
        
        return cuResult;
		
	}

}
