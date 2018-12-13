package br.pucrio.inf.les.ese.dianalyzer.diast.model;

import java.util.ArrayList;
import java.util.List;

public class MethodElement extends AbstractElement {
	
	private List<String> modifiers;
	
	private List<AbstractElement> parameters;
	
	private String returnType;
	
	private String body;

	//Added for bad practice 4
	//private El
	
	public MethodElement(){
		this.parameters = new ArrayList<AbstractElement>();	
	}
	
	public List<String> getModifiers() {
		return modifiers;
	}
	
	public void setModifiers(List<String> modifiers) {
		this.modifiers = modifiers;
	}

	public List<AbstractElement> getParameters() {
		return parameters;
	}
	
	public void addParameter(AbstractElement parameter) {
		this.parameters.add(parameter);
	}

	public void setParameters(List<AbstractElement> parameters) {
		this.parameters = parameters;
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
