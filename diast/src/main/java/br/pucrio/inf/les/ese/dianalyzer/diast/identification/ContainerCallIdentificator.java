package br.pucrio.inf.les.ese.dianalyzer.diast.identification;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.utils.Log;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.AbstractElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ContainerClassType;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.InjectionType;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ObjectType;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.VariableDeclarationElement;

public class ContainerCallIdentificator extends AbstractIdentificator {
	
	public ContainerCallIdentificator() {
		super(InjectionType.CONTAINER);
	}

	@Override
	public List<AbstractElement> identify(CompilationUnit cu){
		
		List<AbstractElement> elements = new ArrayList<AbstractElement>();
		
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

				// TODO refactor

				VariableDeclarationElement variableDeclarationElement = new VariableDeclarationElement();
				
				Optional<Node> declaration = f.getParentNode();
				
				VariableDeclarator declarator = null;
				
				try{
					declarator = (VariableDeclarator) declaration.get();
				}
				catch(ClassCastException e){
					log.error(e.getMessage());
					//case where the method return an application context
					return;
				}
				
				variableDeclarationElement.setType(f.getName().asString());
				
				variableDeclarationElement.setObjectType(ObjectType.CLASS);

				try {
					variableDeclarationElement.setMethodCall(declarator.getInitializer().get().toString());
				}
				catch (NoSuchElementException e){
					log.info("Element has no initializer");
				}
				
				variableDeclarationElement.setVariableName(declarator.getName().getIdentifier());

				String variable = variableDeclarationElement.getType() + " " + variableDeclarationElement.getVariableName();

				variableDeclarationElement.setName( variable );
	
				elements.add(variableDeclarationElement);

				elements.addAll(identifyElementsObtainedByContainerCall(cu,f));
				
			} );
		
		return elements;
		
	}

	private List<AbstractElement> identifyElementsObtainedByContainerCall(CompilationUnit cu, ClassOrInterfaceType parent){

		// Aqui eu encontro os elementos que se obtem uma instancia via container call
		List<AbstractElement> elements = new ArrayList<AbstractElement>();

        // retorna applicationContext
        VariableDeclarator containerDeclarator = (VariableDeclarator) parent.getParentNode().get();

		cu.findAll(VariableDeclarator.class).stream()
				.filter(f -> {

                    // se for igual, retorno
					if( f.getNameAsString().equals(containerDeclarator.getNameAsString()) ){
						return false;
					}

					return true;
				}).forEach(f -> {

					try{

						MethodCallExpr methodCallExpr = null;

						try {
							CastExpr castExpr = (CastExpr) f.getInitializer().get();
							methodCallExpr = (MethodCallExpr) castExpr.getExpression();
						}
						catch( Exception e ){
							log.info("It is not a method call expression");
							return;
						}

						if ( methodCallExpr.getNameAsString().equals( ContainerClassType.SPRING.getContainerCall() ) ) {

							VariableDeclarationElement variableDeclarationElement = new VariableDeclarationElement();

							variableDeclarationElement.setVariableName( f.getNameAsString() );

							variableDeclarationElement.setType( f.getTypeAsString() );

							String variable = variableDeclarationElement.getType() + " " + variableDeclarationElement.getVariableName();

							variableDeclarationElement.setName( variable );

							elements.add(variableDeclarationElement);

						}

					}
					catch(ClassCastException | NoSuchElementException e){
						log.error(e.getMessage());
					}

				});

		return elements;
	}
	
	private String getContainerClassTypeRegex(){
		return ContainerClassType.SPRING.getClassName().toString() + "|" + ContainerClassType.CDI.getClassName().toString();
	}
	
}
