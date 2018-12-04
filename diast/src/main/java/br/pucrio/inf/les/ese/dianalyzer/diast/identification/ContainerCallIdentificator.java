package br.pucrio.inf.les.ese.dianalyzer.diast.identification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.type.ClassOrInterfaceType;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.ContainerClassType;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.Element;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.InjectionType;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ObjectType;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.VariableDeclarationElement;

public class ContainerCallIdentificator extends AbstractIdentificator {
	
	public ContainerCallIdentificator() {
		super(InjectionType.CONTAINER);
	}

	@Override
	public List<Element> identify(CompilationUnit cu){
		
		List<Element> elements = new ArrayList<Element>();
		
		cu.findAll(ClassOrInterfaceType.class).stream()
			.filter(f -> { 
				return 
						f.getName()
							.stream()
							.anyMatch(
									a -> a.toString()
									.matches(getContainerClassTypeRegex()));
			} )
			.forEach(f -> {
				
				VariableDeclarationElement variableDeclarationElement = new VariableDeclarationElement();
				
				Optional<Node> declaration = f.getParentNode();
				
				VariableDeclarator declarator = (VariableDeclarator) declaration.get();
				
				variableDeclarationElement.setType(f.getName().asString());
				
				variableDeclarationElement.setClassType(ObjectType.CLASS);
				
				variableDeclarationElement.setMethodCall(declarator.getInitializer().toString());
				
				variableDeclarationElement.setVariableName(declarator.getName().getIdentifier());
	
				elements.add(variableDeclarationElement);
				
			} );
		
		return elements;
		
	}
	
	protected String getContainerClassTypeRegex(){
		return ContainerClassType.SPRING.getClassName().toString() + "|" + ContainerClassType.CDI.getClassName().toString();
	}
	
}
