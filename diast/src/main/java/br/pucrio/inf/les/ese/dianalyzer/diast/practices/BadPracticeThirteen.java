package br.pucrio.inf.les.ese.dianalyzer.diast.practices;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.CompilationUnitResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ElementResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.rule.IsPrototypeScopeBeanAsSingleton;
import com.github.javaparser.ast.CompilationUnit;

public class BadPracticeThirteen extends AbstractPractice {

	private IsPrototypeScopeBeanAsSingleton rule;

	public BadPracticeThirteen() {
		rule = new IsPrototypeScopeBeanAsSingleton();
		
		// setName("Classes injected in multiple places must be singleton (in order to avoid multiple instances)");
		setName("Prototype bean as singleton");

		// caso onde um bean prototype eh injetado em um bean singleton, sendo assim tratado como singleton tambem
		// nessa dependencia

		setNumber(13);
	}

	@Override
	public CompilationUnitResult process(final CompilationUnit cu) {

		CompilationUnitResult cuResult = new CompilationUnitResult();

		ElementResult result =  rule.processRule(cu);
		if(result.getResult()){
			cuResult.addElementResultToList(result);
		}

		return cuResult;
		
	}

}
