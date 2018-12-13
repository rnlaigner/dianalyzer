package br.pucrio.inf.les.ese.dianalyzer.diast.practices;

import java.util.List;

import com.github.javaparser.ast.CompilationUnit;

import br.pucrio.inf.les.ese.dianalyzer.diast.identification.ConstructorInjectionIdentificator;
import br.pucrio.inf.les.ese.dianalyzer.diast.identification.FieldDeclarationInjectionIdentificator;
import br.pucrio.inf.les.ese.dianalyzer.diast.identification.MethodInjectionIdentificator;
import br.pucrio.inf.les.ese.dianalyzer.diast.identification.SetMethodInjectionIdentificator;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.AbstractElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.CompilationUnitResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.InjectedElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ElementResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.rule.InjectionAssignedToMoreThanOneAttribute;
import br.pucrio.inf.les.ese.dianalyzer.diast.rule.MethodParameterInjectionAssignedToMoreThanOneAttribute;

public class BadPracticeEleven extends AbstractPractice {
	
	private InjectionAssignedToMoreThanOneAttribute firstRule;
	
	private MethodParameterInjectionAssignedToMoreThanOneAttribute secondRule;

	public BadPracticeEleven() {
		firstRule = new InjectionAssignedToMoreThanOneAttribute();
		secondRule = new MethodParameterInjectionAssignedToMoreThanOneAttribute();
	}

	@Override
	public CompilationUnitResult process(final CompilationUnit cu) {
		
		CompilationUnitResult cuResult = new CompilationUnitResult();
		
        FieldDeclarationInjectionIdentificator fieldId = new FieldDeclarationInjectionIdentificator();
        ConstructorInjectionIdentificator constructorId = new ConstructorInjectionIdentificator();
        MethodInjectionIdentificator methodId = new MethodInjectionIdentificator();
        SetMethodInjectionIdentificator setMethodId = new SetMethodInjectionIdentificator();
        
        List<AbstractElement> elements = fieldId.identify(cu);
        elements.addAll(constructorId.identify(cu));
        elements.addAll(methodId.identify(cu));
        elements.addAll(setMethodId.identify(cu));
        
        //uma vez que um elemento eh instanciado via injecao
        //o objetivo da rotina abaixo eh verificar se outro atributo recebe valor deste
        for (AbstractElement element : elements) {
        	
        	InjectedElement elem = (InjectedElement) element;
        	ElementResult result = firstRule.processRule(cu, elem);
        	if(result.getResult()){
        		cuResult.addElementResult(result);	
        	}
        }
        
        //a rotina abaixo agora verifica se uma injecao via parametro do metodo
        //tem sua instancia assinalada para mais de um atributo dentro do body do metodo
        List<ElementResult> results =  secondRule.processRule(cu);
        cuResult.addElementResults(results);
        
        return cuResult;
		
	}

}
