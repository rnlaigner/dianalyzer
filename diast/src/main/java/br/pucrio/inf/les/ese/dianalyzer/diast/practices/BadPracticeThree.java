package br.pucrio.inf.les.ese.dianalyzer.diast.practices;

import br.pucrio.inf.les.ese.dianalyzer.diast.identification.ProducerMethodIdentificator;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.AbstractElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.CompilationUnitResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ElementResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ProducerMethodElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.rule.ProducerMethodWithHighComplexity;
import com.github.javaparser.ast.CompilationUnit;

import java.util.List;

public class BadPracticeThree extends AbstractPractice {

	private ProducerMethodWithHighComplexity rule;

	/**
	 * A implementacao hoje busca classes anotadas com @Service.
	 * Uma classe service usada dentro de @Produces/@Bean nao tem problema.
	 * Entretanto, mais que uma, a sugestao eh que seja refatorada para outra classe.
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

			ProducerMethodElement elem = (ProducerMethodElement) element;
        	ElementResult result = rule.processRule(cu, elem);

        	if (result.getResult()) {
				cuResult.addElementResultToList(result);
			}
        }
        
        return cuResult;

	}

}
