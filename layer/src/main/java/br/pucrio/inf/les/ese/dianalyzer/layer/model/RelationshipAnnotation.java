package br.pucrio.inf.les.ese.dianalyzer.layer.model;

public enum RelationshipAnnotation {
	
	ONE_TO_MANY("OneToMany"),
	MANY_TO_ONE("ManyToOne");
	
	private String value;
	
	RelationshipAnnotation(String value) {
		this.value = value;
    }

	public String getValue() {
		return value;
	}

}
