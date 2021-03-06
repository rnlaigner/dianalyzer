package br.pucrio.inf.les.ese.dianalyzer.diast.model;

public class InjectedElement extends AbstractElement {
	
	private ObjectType objectType;
	
	private String type;
	
	private InjectionType injectionType;
	
	private InjectionAnnotation annotation;
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public ObjectType getObjectType() {
		return objectType;
	}
	
	public void setObjectType(ObjectType objectType) {
		this.objectType = objectType;
	}

	public InjectionType getInjectionType() {
		return injectionType;
	}

	public void setInjectionType(InjectionType injectionType) {
		this.injectionType = injectionType;
	}
	
	public InjectionAnnotation getAnnotation() {
		return annotation;
	}
	
	public void setAnnotation(InjectionAnnotation annotation) {
		this.annotation = annotation;
	}
	
}
