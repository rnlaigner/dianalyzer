package br.pucrio.inf.les.ese.dianalyzer.diast.model;

public class VariableDeclarationElement extends InjectedElement {
	
	private String methodCall;
	
	private String variableName;

	public String getMethodCall() {
		return methodCall;
	}

	public void setMethodCall(String methodCall) {
		this.methodCall = methodCall;
	}

	public String getVariableName() {
		return variableName;
	}

	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}
	
}
