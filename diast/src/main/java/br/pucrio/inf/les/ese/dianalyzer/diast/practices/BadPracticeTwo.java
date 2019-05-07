package br.pucrio.inf.les.ese.dianalyzer.diast.practices;

import br.pucrio.inf.les.ese.dianalyzer.diast.logic.InjectionBusiness;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.AbstractElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.CompilationUnitResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ElementResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.InjectedElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.rule.ReferenceOnConcreteClass;
import com.github.javaparser.ast.CompilationUnit;

import java.util.List;

public class BadPracticeTwo extends AbstractPractice {
	
	private final ReferenceOnConcreteClass rule;

	public BadPracticeTwo() {
		rule = new ReferenceOnConcreteClass();
		setName("Direct reference on implementation class for injection");
		setNumber(2);
	}

	@Override
	public CompilationUnitResult process(final CompilationUnit cu) {
		
		CompilationUnitResult cuResult = new CompilationUnitResult();
        
        List<AbstractElement> elements = InjectionBusiness.getInjectedElementsFromClass(cu);
        
        for (AbstractElement element : elements) {
        	InjectedElement elem = (InjectedElement) element;
        	ElementResult result = rule.processRule(cu, elem);
        	if(result.getResult()){
        		cuResult.addElementResultToList(result);
        	}
        }
        
        return cuResult;
		
	}
	
	

}
