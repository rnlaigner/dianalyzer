package br.pucrio.inf.les.ese.dianalyzer.diast.rule;

import java.util.List;

import com.github.javaparser.ast.CompilationUnit;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.Element;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ElementResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.MethodElement;

public class ProducerMethodWithBusinessRule extends AbstractRule {
	
	// TODO: fazer o recebimento das classes de negocio
	// uma vez que um package de classes de negocio seja tomada como input, 
	// o programa identifica as classes
	// pertencentes a tal pacote
	private List<String> businessClasses;

	public ProducerMethodWithBusinessRule(List<String> businessClasses) {
		super();
		this.businessClasses = businessClasses;
	}



	@Override
	public ElementResult processRule(CompilationUnit cu, Element element) {
		
		MethodElement methodElement = (MethodElement) element;
		
		
		
		return null;
	}

}
