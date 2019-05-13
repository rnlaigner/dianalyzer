package br.pucrio.inf.les.ese.dianalyzer.diast.practices;

import br.pucrio.inf.les.ese.dianalyzer.diast.logic.InjectionBusiness;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.AbstractElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.CompilationUnitResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ElementResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.InjectedElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.rule.IsNonUsedInjection;
import com.github.javaparser.ast.CompilationUnit;

import java.util.List;

public class BadPracticeFive extends AbstractPractice {
	
	private IsNonUsedInjection rule;

	public BadPracticeFive() {
		rule = new IsNonUsedInjection();
		setName("Non used injection");
		setNumber(5);
	}

	@Override
	public synchronized CompilationUnitResult process(final CompilationUnit cu) {
		
		CompilationUnitResult cuResult = new CompilationUnitResult();

		final List<AbstractElement> elements = InjectionBusiness.getInjectedElementsFromClass(cu);
        
        for (AbstractElement element : elements) {
        	
        	final InjectedElement elem = (InjectedElement) element;
        	
        	final ElementResult result = rule.processRule(cu, elem);
        	
        	//is non used injection?
        	if ( result.getResult() ) {
        		cuResult.addElementResultToList(result);	
        	}
        	
        }
        
        return cuResult;
		
	}

}
