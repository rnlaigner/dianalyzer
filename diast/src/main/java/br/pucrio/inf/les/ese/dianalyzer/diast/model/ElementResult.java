package br.pucrio.inf.les.ese.dianalyzer.diast.model;

public class ElementResult {
	
	private boolean result;
	
	private Element element;
	
	public ElementResult() {}
	
	public ElementResult(boolean result, Element element){
		this.result = result;
		this.element = element;
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
