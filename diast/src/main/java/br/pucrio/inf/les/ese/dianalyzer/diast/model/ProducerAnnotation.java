package br.pucrio.inf.les.ese.dianalyzer.diast.model;

public enum ProducerAnnotation {

	PRODUCES("Produces",false),
	BEAN("Bean",true),
	PROVIDES("Provides",true);
	
	private String value;
	private boolean specific;
	
	ProducerAnnotation(String value, boolean specific) {
		this.value = value;
		this.specific = specific;
    }

	public String getValue() {
		return value;
	}
	
	public boolean isSpecific() {
		return specific;
	}
	
	public static String getProducerAnnotationsRegex(){
		return BEAN.getValue() + "|" + PRODUCES.getValue() + "|" + PROVIDES.getValue();
	}
	
}
