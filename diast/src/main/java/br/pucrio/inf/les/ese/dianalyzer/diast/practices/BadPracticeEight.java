package br.pucrio.inf.les.ese.dianalyzer.diast.practices;

import br.pucrio.inf.les.ese.dianalyzer.diast.logic.InjectionBusiness;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.AbstractElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.CompilationUnitResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ElementResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.rule.InjectionOpenForExternalAccessOrExternalPassing;
import com.github.javaparser.ast.CompilationUnit;

import java.util.List;

public class BadPracticeEight extends AbstractPractice {
	
	private InjectionOpenForExternalAccessOrExternalPassing rule;

	public BadPracticeEight() {
		rule = new InjectionOpenForExternalAccessOrExternalPassing();
		setName("Useless injection by passing through or opened for passing through");
		setNumber(8);		
	}

	@Override
	public CompilationUnitResult process(final CompilationUnit cu) {
		
		CompilationUnitResult cuResult = new CompilationUnitResult();

		List<AbstractElement> elements = InjectionBusiness.getInjectedElementsFromClass(cu);
        
        for (AbstractElement elem : elements) {
        	ElementResult result = rule.processRule(cu, elem);
        	if(result.getResult()){
        		cuResult.addElementResultToList(result);
        	}
        }
        
        return cuResult;
		
	}

}
