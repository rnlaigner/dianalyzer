package br.pucrio.inf.les.ese.dianalyzer.diast.model;

public enum InjectionAnnotation {

	RESOURCE("Resource",false),
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
	
	public static String getInjectionAnnotationsRegex(){
		return InjectionAnnotation.RESOURCE.getValue() + "|" +
				InjectionAnnotation.AUTOWIRED.getValue() + "|" +
				InjectionAnnotation.INJECT.getValue();
	}

	public static InjectionAnnotation getFromString(String annotation){

		if(annotation.equals( InjectionAnnotation.AUTOWIRED.getValue() ) ){
			return InjectionAnnotation.AUTOWIRED;
		}
		if(annotation.equals( InjectionAnnotation.INJECT.getValue() ) ){
			return InjectionAnnotation.INJECT;
		}

		if(annotation.equals( InjectionAnnotation.RESOURCE.getValue() ) ){
			return InjectionAnnotation.RESOURCE;
		}

		return null;

	}
	
}
