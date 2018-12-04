package br.pucrio.inf.les.ese.dianalyzer.diast.practices;

import java.util.List;

import com.github.javaparser.ast.CompilationUnit;

import br.pucrio.inf.les.ese.dianalyzer.diast.identification.ContainerCallIdentificator;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.CompilationUnitResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.Element;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ElementResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.rule.DirectContainerCall;

public class BadPracticeSeven extends AbstractPractice {
	
	private DirectContainerCall rule;

	public BadPracticeSeven(CompilationUnit cu) {
		super(cu);
		rule = new DirectContainerCall();
	}

	@Override
	public CompilationUnitResult process() {
		
		CompilationUnitResult cuResult = new CompilationUnitResult();
		
        ContainerCallIdentificator contId = new ContainerCallIdentificator();

        List<Element> elements = contId.identify(cu);
        
        for (Element elem : elements) {
        	ElementResult result = rule.processRule(cu, elem);
        	
        	cuResult.addElementResult(result);
        	
        }
        
        return cuResult;
		
	}

}
