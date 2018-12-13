package br.pucrio.inf.les.ese.dianalyzer.diast.practices;

import java.util.List;

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
	}

	@Override
	public CompilationUnitResult process(final CompilationUnit cu) {
		
		CompilationUnitResult cuResult = new CompilationUnitResult();
		
        /*
         * should I consider container? I think it should not, since a container call can be wrapped in class for better modularization
         * ContainerCallIdentificator contId = new ContainerCallIdentificator();
         */
        FieldDeclarationInjectionIdentificator fieldId = new FieldDeclarationInjectionIdentificator();
        ConstructorInjectionIdentificator constructorId = new ConstructorInjectionIdentificator();
        MethodInjectionIdentificator methodId = new MethodInjectionIdentificator();
        //TODO find a way to define the modifiers of the injected elements by set method
        //SetMethodInjectionIdentificator setMethodId = new SetMethodInjectionIdentificator();
        
        List<AbstractElement> elements = fieldId.identify(cu);
        elements.addAll(constructorId.identify(cu));
        elements.addAll(methodId.identify(cu));
        //elements.addAll(setMethodId.identify(cu));
        
        for (AbstractElement element : elements) {
        	
        	InjectedElement elem = (InjectedElement) element;
        	ElementResult result = rule.processRule(cu, elem);
        	
        	if(result.getResult()){
        		cuResult.addElementResult(result);
        	}
        	
        }
        
        return cuResult;
		
	}

}
