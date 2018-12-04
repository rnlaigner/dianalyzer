package br.pucrio.inf.les.ese.dianalyzer.diast.identification;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.InjectionAnnotation;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.InjectionType;

public abstract class AbstractInjectionIdentificator extends AbstractIdentificator {
	
	public AbstractInjectionIdentificator(InjectionType injectionType) {
		super(injectionType);
	}

	protected String getInjectAnnotationsRegex(){
		return InjectionAnnotation.AUTOWIRED.getValue().toString() + "|" + InjectionAnnotation.INJECT.getValue().toString();
	}
	
	protected InjectionAnnotation getInjectionAnnotationFromString(String annotation) throws Exception {
		
		if(annotation.equals( InjectionAnnotation.AUTOWIRED.getValue().toString() ) )
				return InjectionAnnotation.AUTOWIRED;
		if(annotation.equals( InjectionAnnotation.INJECT.getValue().toString() ) )
				return InjectionAnnotation.INJECT;
		throw new Exception("Errado!");
	
	}

}
