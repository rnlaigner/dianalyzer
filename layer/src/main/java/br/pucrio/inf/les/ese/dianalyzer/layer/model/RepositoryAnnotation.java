package br.pucrio.inf.les.ese.dianalyzer.layer.model;

public enum RepositoryAnnotation {
	
	REPOSITORY("Repository");
	
	private String value;
	
	RepositoryAnnotation(String value) {
		this.value = value;
    }

	public String getValue() {
		return value;
	}

}
