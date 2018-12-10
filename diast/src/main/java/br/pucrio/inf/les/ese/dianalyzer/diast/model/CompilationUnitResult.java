package br.pucrio.inf.les.ese.dianalyzer.diast.model;

import java.util.ArrayList;
import java.util.List;

public class CompilationUnitResult {
	
	private List<ElementResult> elementsResult;

	public CompilationUnitResult(){
		this.elementsResult = new ArrayList<ElementResult>();
	}
	
	public Boolean badPracticeIsApplied(){
		return elementsResult.size() > 0;
	}
	
	public void addElementResult(ElementResult elemResult){
		elementsResult.add(elemResult);
	}
	
	public void addElementResults(List<ElementResult> elementResults){
		elementsResult.addAll(elementResults);
	}

}
