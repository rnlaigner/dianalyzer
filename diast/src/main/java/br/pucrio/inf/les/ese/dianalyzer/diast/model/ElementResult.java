package br.pucrio.inf.les.ese.dianalyzer.diast.model;

import java.util.ArrayList;
import java.util.List;

public class ElementResult {
	
	private boolean result;
	
	private Element element;
	
	private List<Element> children;
	
	public ElementResult() {
		this.children = new ArrayList<Element>();
	}
	
	public ElementResult(boolean result, Element element){
		this.result = result;
		this.element = element;
		this.children = new ArrayList<Element>();
	}
	
	public void addChildren(Element element){
		this.children.add(element);
	}

	public List<Element> getChildren() {
		return children;
	}

	public void setChildren(List<Element> children) {
		this.children = children;
	}

	public boolean getResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public Element getElement() {
		return element;
	}

	public void setElement(Element element) {
		this.element = element;
	}

}
