package br.pucrio.inf.les.ese.dianalyzer.diast.identification;

import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.Element;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.InjectionType;

//TODO opportunity for abstraction with MethodInjectionIdentificator
public class SetMethodInjectionIdentificator extends AbstractMethodInjectionIdentificator {

	public SetMethodInjectionIdentificator() {
		super(InjectionType.SET_METHOD);
	}

	@Override
	public List<Element> identify(CompilationUnit cu){
		
		List<Element> elements = new ArrayList<Element>();
		
		cu.findAll(MethodDeclaration.class).stream()
			.filter(f -> { 
				return 
						f.getAnnotations()
						.stream()
						.anyMatch(a -> a
								.getName()
								.getIdentifier()
								.matches(getInjectAnnotationsRegex()));
			} )
			//esse filtro garante que nao sejam metodos set
			.filter(f -> { 
				return isSetMethod(f);
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
