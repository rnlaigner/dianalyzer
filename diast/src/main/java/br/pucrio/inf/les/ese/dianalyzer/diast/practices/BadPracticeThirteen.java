package br.pucrio.inf.les.ese.dianalyzer.diast.practices;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.CompilationUnitResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.rule.MultipleFormOfInjection;
import br.pucrio.inf.les.ese.dianalyzer.repository.locator.ServiceLocator;
import br.pucrio.inf.les.ese.dianalyzer.repository.model.IDataSource;
import com.github.javaparser.ast.CompilationUnit;

public class BadPracticeThirteen extends AbstractPractice {

	private MultipleFormOfInjection rule;

	public BadPracticeThirteen() {
		// rule = new MultipleFormOfInjection();
		
		setName("Multiple forms of injection for a given element");
		setNumber(13);
	}

	@Override
	public CompilationUnitResult process(final CompilationUnit cu) {

        IDataSource dataSource = (IDataSource) ServiceLocator.getInstance().getBeanInstance("IDataSource");



		return null;
		
	}

}
