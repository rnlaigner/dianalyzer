package br.pucrio.inf.les.ese.dianalyzer.diast.practices;

import com.github.javaparser.ast.CompilationUnit;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.CompilationUnitResult;

public class BadPracticeSix extends AbstractPractice {

	// TODO aqui fazer por heuristica. classes comecando com fabric

	public BadPracticeSix() {
		setName("Fabric to obtain injected instance");
		setNumber(6);
	}

	@Override
	public CompilationUnitResult process(final CompilationUnit cu) {
		CompilationUnitResult cuResult = new CompilationUnitResult();        
        return cuResult;
	}

}
