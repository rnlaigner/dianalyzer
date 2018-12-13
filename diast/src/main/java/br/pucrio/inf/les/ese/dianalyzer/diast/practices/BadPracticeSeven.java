package br.pucrio.inf.les.ese.dianalyzer.diast.practices;

import java.util.List;

import com.github.javaparser.ast.CompilationUnit;

import br.pucrio.inf.les.ese.dianalyzer.diast.identification.ContainerCallIdentificator;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.AbstractElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.CompilationUnitResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.InjectedElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ElementResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.rule.DirectContainerCall;

public class BadPracticeSeven extends AbstractPractice {
	
	private DirectContainerCall rule;

	public BadPracticeSeven() {
		rule = new DirectContainerCall();
	}

	@Override
	public CompilationUnitResult process(final CompilationUnit cu) {
		
		CompilationUnitResult cuResult = new CompilationUnitResult();
		
        ContainerCallIdentificator contId = new ContainerCallIdentificator();

        List<AbstractElement> elements = contId.identify(cu);
        
        for (AbstractElement element : elements) {
        	InjectedElement elem = (InjectedElement) element;
        	ElementResult result = rule.processRule(cu, elem);
        	
        	cuResult.addElementResult(result);
        }
        
        return cuResult;
		
	}

}
