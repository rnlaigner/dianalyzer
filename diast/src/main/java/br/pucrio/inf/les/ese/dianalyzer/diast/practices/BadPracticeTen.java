package br.pucrio.inf.les.ese.dianalyzer.diast.practices;

import java.util.List;

import br.pucrio.inf.les.ese.dianalyzer.diast.logic.InjectionBusiness;
import com.github.javaparser.ast.CompilationUnit;

import br.pucrio.inf.les.ese.dianalyzer.diast.identification.ConstructorInjectionIdentificator;
import br.pucrio.inf.les.ese.dianalyzer.diast.identification.FieldDeclarationInjectionIdentificator;
import br.pucrio.inf.les.ese.dianalyzer.diast.identification.MethodInjectionIdentificator;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.AbstractElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.CompilationUnitResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ElementResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.InjectedElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.rule.InjectionOpenedForChange;

public class BadPracticeTen extends AbstractPractice {
	
	private InjectionOpenedForChange rule;

	public BadPracticeTen() {
		rule = new InjectionOpenedForChange();
		setName("Injected instance enabled to change on a method");
		setNumber(10);
	}

	@Override
	public CompilationUnitResult process(final CompilationUnit cu) {
		
		CompilationUnitResult cuResult = new CompilationUnitResult();
		
        /*
         * should I consider container? I think it should not, since a container call can be wrapped in class for better modularization
         * ContainerCallIdentificator contId = new ContainerCallIdentificator();
         */
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
