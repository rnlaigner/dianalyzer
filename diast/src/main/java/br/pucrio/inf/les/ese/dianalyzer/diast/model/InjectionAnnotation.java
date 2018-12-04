package br.pucrio.inf.les.ese.dianalyzer.diast.model;

public enum InjectionAnnotation {
	
	AUTOWIRED("Autowired",true),
	INJECT("Inject",false);
	
	private String value;
	private boolean specific;
	
	InjectionAnnotation(String value, boolean specific) {
		this.value = value;
		this.specific = specific;
    }

	public String getValue() {
		return value;
	}
	
	public boolean isSpecific() {
		return specific;
	}
	
}
