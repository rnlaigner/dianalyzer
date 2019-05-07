package br.pucrio.inf.les.ese.dianalyzer.diast.practices;

import br.pucrio.inf.les.ese.dianalyzer.diast.logic.InjectionBusiness;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.AbstractElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.CompilationUnitResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ElementResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.InjectedElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.rule.AppearsInEveryMethod;
import com.github.javaparser.ast.CompilationUnit;

import java.util.List;

public class BadPracticeOne extends AbstractPractice {
	
	private AppearsInEveryMethod rule;

	public BadPracticeOne(){
		rule = new AppearsInEveryMethod();
		setName("Injection on elements on which there is no certainty that they will be referenced on runtime");
		setNumber(1);
	}

	@Override
	public CompilationUnitResult process(final CompilationUnit cu) {
		
		CompilationUnitResult cuResult = new CompilationUnitResult();

		List<AbstractElement> elements = InjectionBusiness.getInjectedElementsFromClass(cu);
        
        for (AbstractElement element : elements) {
        	
        	InjectedElement elem = (InjectedElement) element;
        	
        	ElementResult result = rule.processRule(cu, elem);
        	
        	//does not appear in every method?
        	if(!result.getResult()){
        		cuResult.addElementResultToList(result);
        	}
        	
        }
        
        return cuResult;
        
	}

}
