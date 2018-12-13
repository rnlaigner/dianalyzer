package br.pucrio.inf.les.ese.dianalyzer.diast.identification;

import java.util.List;

import com.github.javaparser.ast.CompilationUnit;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.AbstractElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.InjectedElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.InjectionType;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ObjectType;

public abstract class AbstractIdentificator {
	
	private InjectionType injectionType;
	
	public AbstractIdentificator(InjectionType injectionType){
		this.injectionType = injectionType;
	}
	
	public abstract List<AbstractElement> identify(CompilationUnit cu);
	
	protected ObjectType getObjectTypeFromString(String objectType) throws Exception {
		
		// TODO: ver como o ast pode me dar se eh classe ou interface. Por ora, ele diz que eh apenas interface ou classe
		if(objectType.toLowerCase().contains( ObjectType.CLASS.getValue().toString() )) {
			return ObjectType.CLASS;
		}
		
		if(objectType.equals( ObjectType.INTERFACE.getValue().toString() )) {
			return ObjectType.INTERFACE;
		}
		
		throw new Exception("Errado");
		
	}

	public InjectionType getInjectionType() {
		return injectionType;
	}

}
