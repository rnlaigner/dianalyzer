package br.pucrio.inf.les.ese.dianalyzer.diast.identification;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.InjectionAnnotation;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.InjectionType;

public abstract class AbstractInjectionIdentificator extends AbstractIdentificator {
	
	public AbstractInjectionIdentificator(InjectionType injectionType) {
		super(injectionType);
	}

	protected String getInjectAnnotationsRegex(){
		return InjectionAnnotation.getInjectionAnnotationsRegex();
	}
	
	protected InjectionAnnotation getInjectionAnnotationFromString(String annotation) throws Exception {
		
		InjectionAnnotation annotationFromString = InjectionAnnotation.getFromString(annotation);

		if(annotationFromString == null)
		{
			throw new Exception("Annotation '"+ annotation +"' is not recognized");
		}

		return annotationFromString;
	}

}
