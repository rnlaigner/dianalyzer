package br.pucrio.inf.les.ese.dianalyzer.diast.practices;

import java.util.List;

import com.github.javaparser.ast.CompilationUnit;

import br.pucrio.inf.les.ese.dianalyzer.diast.identification.ConstructorInjectionIdentificator;
import br.pucrio.inf.les.ese.dianalyzer.diast.identification.FieldDeclarationInjectionIdentificator;
import br.pucrio.inf.les.ese.dianalyzer.diast.identification.MethodInjectionIdentificator;
import br.pucrio.inf.les.ese.dianalyzer.diast.identification.SetMethodInjectionIdentificator;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.CompilationUnitResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.Element;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ElementResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.rule.InjectionOpenedForChange;

public class BadPracticeTen extends AbstractPractice {
	
	private InjectionOpenedForChange rule;

	public BadPracticeTen(CompilationUnit cu) {
		super(cu);
		rule = new InjectionOpenedForChange();
	}

	@Override
	public CompilationUnitResult process() {
		
		CompilationUnitResult cuResult = new CompilationUnitResult();
		
        /*
         * should I consider? I think it should not, since a container call can be wrapped in class for better modularization
         * ContainerCallIdentificator contId = new ContainerCallIdentificator();
         */
        FieldDeclarationInjectionIdentificator fieldId = new FieldDeclarationInjectionIdentificator();
        ConstructorInjectionIdentificator constructorId = new ConstructorInjectionIdentificator();
        MethodInjectionIdentificator methodId = new MethodInjectionIdentificator();
        SetMethodInjectionIdentificator setMethodId = new SetMethodInjectionIdentificator();
        
        List<Element> elements = fieldId.identify(cu);
        elements.addAll(constructorId.identify(cu));
        elements.addAll(methodId.identify(cu));
        elements.addAll(setMethodId.identify(cu));
        
        for (Element elem : elements) {
        	ElementResult result = rule.processRule(cu, elem);
        	
        	if(result.getResult()){
        		cuResult.addElementResult(result);
        	}
        	
        }
        
        return cuResult;
		
	}

}
