package br.pucrio.inf.les.ese.dianalyzer.layer.model;

public enum ConfigurationAnnotation {
	
	CONFIGURATION("Configuration");
	
	private String value;
	
	ConfigurationAnnotation(String value) {
		this.value = value;
    }

	public String getValue() {
		return value;
	}

}
