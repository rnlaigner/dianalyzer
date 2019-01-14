package br.pucrio.inf.les.ese.dianalyzer.layer.model;

public enum ServiceAnnotation {
	
	SERVICE("Service");
	
	private String value;
	
	ServiceAnnotation(String value) {
		this.value = value;
    }

	public String getValue() {
		return value;
	}

}
