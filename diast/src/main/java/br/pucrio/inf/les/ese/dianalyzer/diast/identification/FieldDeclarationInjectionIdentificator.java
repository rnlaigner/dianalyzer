package br.pucrio.inf.les.ese.dianalyzer.diast.identification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.AttributeElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.Element;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.InjectionType;

public class FieldDeclarationInjectionIdentificator extends AbstractInjectionIdentificator {
	
	public FieldDeclarationInjectionIdentificator() {
		super(InjectionType.FIELD);
	}

	@Override
	public List<Element> identify(CompilationUnit cu){
		
		List<Element> elements = new ArrayList<Element>();
		
		cu.findAll(FieldDeclaration.class).stream()
			.filter(f -> { 
				return 
						f.getAnnotations()
						.stream()
						.anyMatch(a -> a
								.getName()
								.getIdentifier()
								.matches(getInjectAnnotationsRegex()));
			} )
			.forEach(f -> {
				AttributeElement elem = new AttributeElement();
				
				List<String> modifiers = new ArrayList<String>();
				f.getModifiers().stream().forEach( m -> { modifiers.add( m.asString() ); } );
				 				
				elem.setModifiers(modifiers);
				
				VariableDeclarator variable = f.getVariables().get(0);
				
				elem.setType(variable.getType().asString());
				
				try {
					elem.setClassType( getObjectTypeFromString
										(variable.getType().
												getClass().
												getSimpleName() ) );
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				
				String annotation = f.getAnnotations()
											.stream()
											.distinct()
											.map(e -> e.getName().asString())
											.collect( Collectors.toList() )
											.get(0);							
				
				elem.setName(variable.getName().toString());
				
				try {
					elem.setAnnotation(getInjectionAnnotationFromString(annotation));
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				elements.add( elem );
			}
		);

		return elements;
		
	}

}
