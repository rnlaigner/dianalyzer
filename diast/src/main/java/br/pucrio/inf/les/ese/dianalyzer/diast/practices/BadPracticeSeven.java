package br.pucrio.inf.les.ese.dianalyzer.diast.practices;

import br.pucrio.inf.les.ese.dianalyzer.diast.identification.ContainerCallIdentificator;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.AbstractElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.CompilationUnitResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ElementResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.InjectedElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.rule.DirectContainerCall;
import com.github.javaparser.ast.CompilationUnit;

import java.util.List;

public class BadPracticeSeven extends AbstractPractice {
	
	private DirectContainerCall rule;

	public BadPracticeSeven() {
		rule = new DirectContainerCall();
		setName("Direct container calls to obtain class instance");
		setNumber(7);
	}

	@Override
	public CompilationUnitResult process(final CompilationUnit cu) {
		
		CompilationUnitResult cuResult = new CompilationUnitResult();
		
        ContainerCallIdentificator contId = new ContainerCallIdentificator();

        List<AbstractElement> elements = contId.identify(cu);
        
        for (AbstractElement element : elements) {
        	InjectedElement elem = (InjectedElement) element;
        	ElementResult result = rule.processRule(cu, elem);
        	
        	cuResult.addElementResultToList(result);
        }
        
        return cuResult;
		
	}

}
