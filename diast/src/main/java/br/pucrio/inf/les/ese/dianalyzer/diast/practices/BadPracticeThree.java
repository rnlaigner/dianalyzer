package br.pucrio.inf.les.ese.dianalyzer.diast.practices;

import java.util.List;

import com.github.javaparser.ast.CompilationUnit;

import br.pucrio.inf.les.ese.dianalyzer.diast.identification.ProducerMethodIdentificator;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.AbstractElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.CompilationUnitResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.InjectedElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ElementResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.rule.ProducerMethodWithHighComplexity;

public class BadPracticeThree extends AbstractPractice {

	private ProducerMethodWithHighComplexity rule;

	/**
	 * A implementacao hoje busca classes anotadas com @Service.
	 * Uma classe service usada dentro de @Produces/@Bean nao tem problema.
	 * Entretanto, mais que uma, a sugestao eh que seja refatorada paa outra classe.
	 *
	 * Ou melhor, complexidade ciclomatica maior que um threshold
	 * entao tal conjunto de linhas deve ser refatorada para uma classe
	 */
	public BadPracticeThree() {
		rule = new ProducerMethodWithHighComplexity();
		setName("Producer method with high cyclomatic complexity.");
		setNumber(3);
	}

	@Override
	public CompilationUnitResult process(final CompilationUnit cu) {
		
		CompilationUnitResult cuResult = new CompilationUnitResult();
		
		/* identifica elementos que bad practice pode se aplicar */
		ProducerMethodIdentificator methods = new ProducerMethodIdentificator();
        
        List<AbstractElement> elements =  methods.identify(cu);
        
        for (AbstractElement element : elements) {
        	
        	InjectedElement elem = (InjectedElement) element;
        	ElementResult result = rule.processRule(cu, elem);
        	
        	cuResult.addElementResultToList(result);
        	
        }
        
        return cuResult;

	}

}
