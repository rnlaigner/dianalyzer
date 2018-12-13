package br.pucrio.inf.les.ese.dianalyzer.diast.practices;

import java.util.List;

import com.github.javaparser.ast.CompilationUnit;

import br.pucrio.inf.les.ese.dianalyzer.diast.identification.ConstructorInjectionIdentificator;
import br.pucrio.inf.les.ese.dianalyzer.diast.identification.FieldDeclarationInjectionIdentificator;
import br.pucrio.inf.les.ese.dianalyzer.diast.identification.MethodInjectionIdentificator;
import br.pucrio.inf.les.ese.dianalyzer.diast.identification.SetMethodInjectionIdentificator;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.AbstractElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.CompilationUnitResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ElementResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.rule.InjectionOpenForExternalAccessOrExternalPassing;

public class BadPracticeEight extends AbstractPractice {
	
	private InjectionOpenForExternalAccessOrExternalPassing rule;

	public BadPracticeEight() {
		rule = new InjectionOpenForExternalAccessOrExternalPassing();
	}

	@Override
	public CompilationUnitResult process(final CompilationUnit cu) {
		
		CompilationUnitResult cuResult = new CompilationUnitResult();
		
        /*
         * TODO should I consider?
         * ContainerCallIdentificator contId = new ContainerCallIdentificator();
         */
        FieldDeclarationInjectionIdentificator fieldId = new FieldDeclarationInjectionIdentificator();
        ConstructorInjectionIdentificator constructorId = new ConstructorInjectionIdentificator();
        MethodInjectionIdentificator methodId = new MethodInjectionIdentificator();
        SetMethodInjectionIdentificator setMethodId = new SetMethodInjectionIdentificator();
        
        List<AbstractElement> elements = fieldId.identify(cu);
        elements.addAll(constructorId.identify(cu));
        elements.addAll(methodId.identify(cu));
        elements.addAll(setMethodId.identify(cu));
        
        for (AbstractElement elem : elements) {
        	ElementResult result = rule.processRule(cu, elem);
        	
        	cuResult.addElementResult(result);
        	
        }
        
        return cuResult;
		
	}

}
