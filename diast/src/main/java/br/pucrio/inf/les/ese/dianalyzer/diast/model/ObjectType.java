package br.pucrio.inf.les.ese.dianalyzer.diast.model;

public enum ObjectType {

	INTERFACE("interface"),
	ENUM("enum"),
	CLASS("class"),
	ABSTRACT_CLASS("abstract class");
	
	private String value;
	
	ObjectType(String value){
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
}
