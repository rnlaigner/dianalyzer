package br.pucrio.inf.les.ese.dianalyzer.diast.model;

import java.util.ArrayList;
import java.util.List;

public class CompilationUnitResult {
	
	private List<ElementResult> elementsResult;

	public CompilationUnitResult(){
		this.elementsResult = new ArrayList<ElementResult>();
	}
	
	public Boolean badPracticeIsApplied(){
		
		return elementsResult.stream().anyMatch( p -> p.getResult() );
		
	}
	
	public void addElementResult(ElementResult elemResult){
		elementsResult.add(elemResult);
	}

}
