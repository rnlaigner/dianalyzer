package br.pucrio.inf.les.ese.dianalyzer.diast.practices;

import br.pucrio.inf.les.ese.dianalyzer.diast.logic.InjectionBusiness;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.AbstractElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.CompilationUnitResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ElementResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.InjectedElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.rule.InjectionAssignedToMoreThanOneAttribute;
import br.pucrio.inf.les.ese.dianalyzer.diast.rule.MethodParameterInjectionAssignedToMoreThanOneAttribute;
import com.github.javaparser.ast.CompilationUnit;

import java.util.List;

public class BadPracticeEleven extends AbstractPractice {
	
	private InjectionAssignedToMoreThanOneAttribute firstRule;
	
	private MethodParameterInjectionAssignedToMoreThanOneAttribute secondRule;

	public BadPracticeEleven() {
		firstRule = new InjectionAssignedToMoreThanOneAttribute();
		secondRule = new MethodParameterInjectionAssignedToMoreThanOneAttribute();
		setName("Instance injected for use in more than one attribute");
		setNumber(11);
	}

	@Override
	public CompilationUnitResult process(final CompilationUnit cu) {
		
		CompilationUnitResult cuResult = new CompilationUnitResult();

		List<AbstractElement> elements = InjectionBusiness.getInjectedElementsFromClass(cu);
        
        // uma vez que um elemento eh instanciado via injecao
        // o objetivo da rotina abaixo eh verificar se outro atributo recebe valor deste
        for (AbstractElement element : elements) {
        	
        	InjectedElement elem = (InjectedElement) element;
        	ElementResult result = firstRule.processRule(cu, elem);
        	if(result.getResult()){
        		cuResult.addElementResultToList(result);	
        	}
        }
        
        // a rotina abaixo agora verifica se uma injecao via parametro do metodo
        // tem sua instancia assinalada para mais de um atributo dentro do body do metodo
        List<ElementResult> results =  secondRule.processRule(cu);
        cuResult.addAllElementResultToList(results);
        
        return cuResult;
		
	}

}
