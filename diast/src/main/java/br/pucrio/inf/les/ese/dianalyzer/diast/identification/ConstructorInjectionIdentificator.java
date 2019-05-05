package br.pucrio.inf.les.ese.dianalyzer.diast.identification;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.AbstractElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.InjectionAnnotation;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.InjectionType;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ConstructorDeclaration;

import java.util.ArrayList;
import java.util.List;

public class ConstructorInjectionIdentificator extends AbstractMethodInjectionIdentificator {
	
	public ConstructorInjectionIdentificator() {
		super(InjectionType.CONSTRUCTOR);
	}

	@Override
	public List<AbstractElement> identify(CompilationUnit cu){
		
		List<AbstractElement> elements = new ArrayList<AbstractElement>();
		
		cu.findAll(ConstructorDeclaration.class).stream()
			.filter(f -> { 
				return 
						f.getAnnotations()
						.stream()
						.anyMatch(a -> a
								.getName()
								.getIdentifier()
								.matches(InjectionAnnotation.getInjectionAnnotationsRegex()));
			} )
			.forEach(f -> {
				 
				try {
					elements.addAll(identifyFromParameters(f));
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		);
		
		return elements;
		
	}

}
