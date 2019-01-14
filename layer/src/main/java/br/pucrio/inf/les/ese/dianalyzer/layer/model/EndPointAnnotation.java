package br.pucrio.inf.les.ese.dianalyzer.layer.model;

public enum EndPointAnnotation {
	
	REQUEST_MAPPING("RequestMapping");
	
	private String value;
	
	EndPointAnnotation(String value) {
		this.value = value;
    }

	public String getValue() {
		return value;
	}

}
