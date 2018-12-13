package br.pucrio.inf.les.ese.dianalyzer.diast.model;

public class ProducerMethodElement extends MethodElement {

	//This was only used for bad practice for producer annotation
	//Later, I found out this could be good for bad practice 11 
	private ProducerAnnotation annotation;
	
	public ProducerMethodElement(){
		super();	
	}	

	public ProducerAnnotation getAnnotation() {
		return annotation;
	}

	public void setAnnotation(ProducerAnnotation annotation) {
		this.annotation = annotation;
	}

}
