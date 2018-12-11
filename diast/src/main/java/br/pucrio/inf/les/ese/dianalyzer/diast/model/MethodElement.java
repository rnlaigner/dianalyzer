package br.pucrio.inf.les.ese.dianalyzer.diast.model;

import java.util.ArrayList;
import java.util.List;

public class MethodElement extends Element {
	
	private List<AttributeElement> parameters;
	
	//This was only used for bad practice for producer annotation
	//Later, I found out this could be good for another bad practice
	private ProducerAnnotation annotation;
	
	private String returnType;
	
	private String body;
	
	public MethodElement(){
		this.parameters = new ArrayList<AttributeElement>();	
	}

	public List<AttributeElement> getParameters() {
		return parameters;
	}
	
	public void addParameter(AttributeElement parameter) {
		this.parameters.add(parameter);
	}

	public void setParameters(List<AttributeElement> parameters) {
		this.parameters = parameters;
	}

	public ProducerAnnotation getAnnotation() {
		return annotation;
	}

	public void setAnnotation(ProducerAnnotation annotation) {
		this.annotation = annotation;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
	

}
