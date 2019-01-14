package br.pucrio.inf.les.ese.dianalyzer.layer.model;

import java.util.List;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.AbstractElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.MethodElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ObjectType;

public class SpringEndPointMethod extends MethodElement {

	private ObjectType type;
	
	private List<SpringServiceClass> services;
	
	private ControllerAnnotation annotation;
	
	
	
}