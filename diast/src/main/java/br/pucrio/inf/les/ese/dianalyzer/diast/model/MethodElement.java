package br.pucrio.inf.les.ese.dianalyzer.diast.model;

import com.github.javaparser.ast.stmt.BlockStmt;

import java.util.ArrayList;
import java.util.List;

public class MethodElement extends AbstractElement {
	
	private List<AbstractElement> parameters;
	
	private String returnType;
	
	private BlockStmt body;
	
	public MethodElement(){
		this.parameters = new ArrayList<AbstractElement>();	
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

	public BlockStmt getBody() {
		return body;
	}

	public void setBody(BlockStmt body) {
		this.body = body;
	}
	
	

}
