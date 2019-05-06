package br.pucrio.inf.les.ese.dianalyzer.diast.practices;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.AbstractElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.CompilationUnitResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ElementResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.rule.GodDependencyInjectionClass;
import com.github.javaparser.ast.CompilationUnit;

public class BadPracticeFour extends AbstractPractice {
	
	private GodDependencyInjectionClass rule;

	public BadPracticeFour() {
		rule = new GodDependencyInjectionClass();
		setName("God (dependency injected) class");
		setNumber(4);
	}

	@Override
	public CompilationUnitResult process(final CompilationUnit cu) {   
		
		CompilationUnitResult cuResult = new CompilationUnitResult();
		
		ElementResult result = rule.processRule(cu);

		//is non used injection?
		if ( result.getResult() ) {
			// FIXME deixar de ter isso. fazer cuResult ter haveElement?
			AbstractElement element = new AbstractElement();
			element.setName("");

			result.setElement( element );
			cuResult.addElementResultToList(result);
		}
   
		return cuResult;
		
	}

}
