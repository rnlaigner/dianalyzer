package br.pucrio.inf.les.ese.dianalyzer.layer.model;

public enum TableAnnotation {
	
	ENTITY("Entity"),
	TABLE("Table");
	
	private String value;
	
	TableAnnotation(String value) {
		this.value = value;
    }

	public String getValue() {
		return value;
	}

}
