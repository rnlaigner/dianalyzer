package br.pucrio.inf.les.ese.dianalyzer.diast.identification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.Element;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.MethodElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ProducerAnnotation;

public class ProducerMethodIdentificator extends AbstractIdentificator {

	public ProducerMethodIdentificator() {
		super(null);
	}

	@Override
	public List<Element> identify(CompilationUnit cu) {

		List<Element> elements = new ArrayList<Element>();
		
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
				MethodElement elem = new MethodElement();
				
				List<String> modifiers = new ArrayList<String>();
				f.getModifiers().stream().forEach( m -> { modifiers.add( m.asString() ); } );
				 				
				elem.setModifiers(modifiers);
				
				elem.setType(f.getType().asString());
				
				try {
					elem.setClassType(getObjectTypeFromString
							( f.getType().getClass().getName() ));
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				
				String annotation = f.getAnnotations()
											.stream()
											.distinct()
											.map(e -> e.getName().asString())
											.collect( Collectors.toList() )
											.get(0);							
				
				elem.setName(f.getName().toString());
				
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
