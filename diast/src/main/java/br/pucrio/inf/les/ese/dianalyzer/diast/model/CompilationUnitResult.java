package br.pucrio.inf.les.ese.dianalyzer.diast.model;

import java.util.ArrayList;
import java.util.List;

public class CompilationUnitResult {
	
	private List<ElementResult> elementResultList;

	public CompilationUnitResult(){
		this.elementResultList = new ArrayList<ElementResult>();
	}
	
	public Boolean badPracticeIsApplied(){
		return elementResultList.size() > 0;
	}
	
	public void addElementResultToList(ElementResult elemResult){
		elementResultList.add(elemResult);
	}
	
	public void addAllElementResultToList(List<ElementResult> elementResults){
		elementResultList.addAll(elementResults);
	}
	
	public List<ElementResult> getElementResults(){
		return elementResultList;
	}

}
