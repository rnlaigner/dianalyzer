package br.pucrio.inf.les.ese.dianalyzer.layer.model;

import java.util.List;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.AbstractElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ObjectType;

public class SpringControllerClass extends AbstractElement {

	private ObjectType type;
	
	private List<SpringServiceClass> services;
	
	private ControllerAnnotation annotation;
	
	
	
}