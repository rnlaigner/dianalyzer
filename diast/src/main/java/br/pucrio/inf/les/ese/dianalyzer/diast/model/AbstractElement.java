package br.pucrio.inf.les.ese.dianalyzer.diast.model;

import java.util.List;

// TODO laigner transform in abstract class...
public class AbstractElement {
	
	private List<String> modifiers;

	private String name;

	public List<String> getModifiers() {
		return modifiers;
	}
	
	public void setModifiers(List<String> modifiers) {
		this.modifiers = modifiers;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}	
}
