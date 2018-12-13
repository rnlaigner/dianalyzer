package br.pucrio.inf.les.ese.dianalyzer.diast.practices;

import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.CompilationUnit;

import br.pucrio.inf.les.ese.dianalyzer.diast.identification.ProducerMethodIdentificator;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.AbstractElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.CompilationUnitResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.InjectedElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ElementResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.rule.ProducerMethodWithBusinessRule;

public class BadPracticeThree extends AbstractPractice {
	
	private ProducerMethodWithBusinessRule rule;

	public BadPracticeThree(CompilationUnit cu) {
		super(cu);
		
		List<String> list = identifyBusinessClasses();
		
		rule = new ProducerMethodWithBusinessRule(list);
	}

	@Override
	public CompilationUnitResult process() {
		
		CompilationUnitResult cuResult = new CompilationUnitResult();
		
		/* identifica elementos que bad practice pode se aplicar */
		ProducerMethodIdentificator methods = new ProducerMethodIdentificator();
        
        List<AbstractElement> elements =  methods.identify(cu);
        
        for (AbstractElement element : elements) {
        	
        	InjectedElement elem = (InjectedElement) element;
        	ElementResult result = rule.processRule(cu, elem);
        	
        	cuResult.addElementResult(result);
        	
        }
        
        return cuResult;
        
        
		
	}
	
	//TODO: encontrar todas as classes de negocio do projeto
	private List<String> identifyBusinessClasses(){
		return new ArrayList<String>();
	}

}
