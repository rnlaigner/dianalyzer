package br.pucrio.inf.les.ese.dianalyzer.diast.practices;

import com.github.javaparser.ast.CompilationUnit;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.CompilationUnitResult;

public abstract class AbstractPractice {

	private String name;
	
	private String description;
	
	//private List<InjectedElement> elements;
	
//	protected CompilationUnit cu;
//	
//	public AbstractPractice(CompilationUnit cu){
//		this.cu = cu;
//	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
//	public List<InjectedElement> getElements() {
//		return elements;
//	}
//
//	public void setElements(List<InjectedElement> elements) {
//		this.elements = elements;
//	}

	public abstract CompilationUnitResult process(final CompilationUnit cu);
	
	
}
