package br.pucrio.inf.les.ese.dianalyzer.diast.model;

public class AttributeElement extends AbstractElement {
	
	private ObjectType objectType;
	
	private String type;
	
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
	
}
