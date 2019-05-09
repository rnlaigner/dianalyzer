package br.pucrio.inf.les.ese.dianalyzer.diast.model;

import java.util.ArrayList;
import java.util.List;

public class ElementResult {
	
	private final boolean result;
	
	private final AbstractElement element;
	
	private final List<AbstractElement> children;
	
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

	public boolean getResult() {
		return result;
	}

	public AbstractElement getElement() {
		return element;
	}

}
