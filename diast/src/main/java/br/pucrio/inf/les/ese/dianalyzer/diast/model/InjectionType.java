package br.pucrio.inf.les.ese.dianalyzer.diast.model;

public enum InjectionType {

	CONSTRUCTOR("constructor"),
	METHOD("method"),
	SET_METHOD("set_method"),
	CONTAINER("container"),
	FIELD("attribute");
	
	private String value;
	
	InjectionType(String value){
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
}
