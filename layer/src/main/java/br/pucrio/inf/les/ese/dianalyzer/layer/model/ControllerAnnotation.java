package br.pucrio.inf.les.ese.dianalyzer.layer.model;

public enum ControllerAnnotation {
	
	CONTROLLER("Controller");
	
	private String value;
	
	ControllerAnnotation(String value) {
		this.value = value;
    }

	public String getValue() {
		return value;
	}

}
