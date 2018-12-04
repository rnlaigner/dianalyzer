package br.pucrio.inf.les.ese.dianalyzer.diast.model;

import java.util.List;

public class MethodElement extends Element {
	
	private List<AttributeElement> parameters;
	
	private ProducerAnnotation annotation;
	
	private String returnType;
	
	private String body;

	public List<AttributeElement> getParameters() {
		return parameters;
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
