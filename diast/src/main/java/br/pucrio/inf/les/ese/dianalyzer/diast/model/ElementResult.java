package br.pucrio.inf.les.ese.dianalyzer.diast.model;

import java.util.ArrayList;
import java.util.List;

public class ElementResult {
	
	private boolean result;
	
	private AbstractElement element;
	
	private List<AbstractElement> children;
	
	public ElementResult() {
		this.children = new ArrayList<AbstractElement>();
	}
	
	public ElementResult(boolean result, AbstractElement element){
		this.result = result;
		this.element = element;
		this.children = new ArrayList<AbstractElement>();
	}
	
	public void addChildren(AbstractElement element){
		this.children.add(element);
	}

	public List<AbstractElement> getChildren() {
		return children;
	}

	public void setChildren(List<AbstractElement> children) {
		this.children = children;
	}

	public boolean getResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public AbstractElement getElement() {
		return element;
	}

	public void setElement(AbstractElement element) {
		this.element = element;
	}

}
