package br.pucrio.inf.les.ese.dianalyzer.diast.identification;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.AbstractElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ProducerAnnotation;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ProducerMethodElement;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProducerMethodIdentificator extends AbstractIdentificator {

	public ProducerMethodIdentificator() {}

	@Override
	public List<AbstractElement> identify(CompilationUnit cu) {

		List<AbstractElement> elements = new ArrayList<AbstractElement>();
		
		cu.findAll(MethodDeclaration.class).stream()
			.filter(f -> {
				return
						f.getAnnotations()
						.stream()
						.anyMatch(a -> a
								.getName()
								.getIdentifier()
								.matches(ProducerAnnotation.getProducerAnnotationsRegex()));
			} )
			.forEach(f -> {
				ProducerMethodElement elem = new ProducerMethodElement();
				
				List<String> modifiers = new ArrayList<String>();
				f.getModifiers().stream().forEach( m -> { modifiers.add( m.asString() ); } );
				 				
				elem.setModifiers(modifiers);

//				List<String> parameters = new ArrayList<String>();
//				f.getParameters().stream().forEach( param -> { parameters.add( param. )  } );
				
				/*
				elem.setType(f.getType().asString());
				
				try {
					elem.setClassType(getObjectTypeFromString
							( f.getType().getClass().getName() ));
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				*/
				
				String annotation = f.getAnnotations()
											.stream()
											.distinct()
											.map(e -> e.getName().asString())
											.collect( Collectors.toList() )
											.get(0);							
				
				elem.setName(f.getName().toString());

				elem.setBody( f.getBody().get() );

				elem.setReturnType( f.getTypeAsString() );
				
				try {
					elem.setAnnotation(getProducerAnnotationFromString(annotation));
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				elements.add( elem );
			}
		);
		
		
		return elements;
		
	}

	protected ProducerAnnotation getProducerAnnotationFromString(String annotation) throws Exception {
		
		if(annotation.equals( ProducerAnnotation.BEAN.getValue().toString() ) )
				return ProducerAnnotation.BEAN;
		if(annotation.equals( ProducerAnnotation.PRODUCES.getValue().toString() ) )
				return ProducerAnnotation.PRODUCES;
		throw new Exception("Errado!");
	
	}
	
}
