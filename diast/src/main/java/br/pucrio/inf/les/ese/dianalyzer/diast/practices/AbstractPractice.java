package br.pucrio.inf.les.ese.dianalyzer.diast.practices;

import com.github.javaparser.ast.CompilationUnit;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.CompilationUnitResult;

public abstract class AbstractPractice {

	private String name;
	
	private String description;
	
	private Integer number;

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

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public abstract CompilationUnitResult process(final CompilationUnit cu);
	
	
}
