package br.pucrio.inf.les.ese.dianalyzer.diast.identification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.*;
import br.pucrio.inf.les.ese.dianalyzer.repository.locator.ServiceLocator;
import br.pucrio.inf.les.ese.dianalyzer.repository.model.Tuple;
import br.pucrio.inf.les.ese.dianalyzer.repository.source.IBeanDataSource;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;

public class FieldDeclarationInjectionIdentificator extends AbstractInjectionIdentificator {

	private final IBeanDataSource dataSource;

	public FieldDeclarationInjectionIdentificator() {
		super(InjectionType.FIELD);
		this.dataSource = (IBeanDataSource) ServiceLocator.getInstance().getBeanInstance("IDataSource");
	}

	@Override
	public List<AbstractElement> identify(CompilationUnit cu){
		
		List<AbstractElement> elements = new ArrayList<AbstractElement>();
		
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
				InjectedElement elem = new InjectedElement();
				
				List<String> modifiers = new ArrayList<String>();
				f.getModifiers().stream().forEach( m -> { modifiers.add( m.asString() ); } );
				 				
				elem.setModifiers(modifiers);
				
				VariableDeclarator variable = f.getVariables().get(0);
				
				elem.setType(variable.getType().asString());
				
				elem.setInjectionType(InjectionType.FIELD);

				// primeiro busco em memoria, se nao encontrar, me baseio na informacao do javaparser
				Tuple tuple = (Tuple) dataSource.getBeanByName(elem.getType());

				if(tuple != null){
					log.info("Associated tuple found");
					if(tuple.isInterface){
						elem.setObjectType(ObjectType.INTERFACE);
					} else{
						elem.setObjectType(ObjectType.CLASS);
					}
				} else {
					if(variable.getType().getClass().isInterface()){
						elem.setObjectType(ObjectType.INTERFACE);
					} else {
						elem.setObjectType(ObjectType.CLASS);
					}
				}
				
				List<String> annotations = f.getAnnotations()
											.stream()
											.distinct()
											.map(e -> e.getName().asString().replace( "javax.annotation.","" )  )
											.collect( Collectors.toList() );							
				
				elem.setName(variable.getName().toString());
				
				InjectionAnnotation injectionAnnotation = null;
				for(String annotation : annotations){
					try{
						injectionAnnotation = getInjectionAnnotationFromString(annotation);
					}
					catch(Exception e){
						continue;
					}
				}
				
				try {
					elem.setAnnotation(injectionAnnotation);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				elements.add( elem );
			}
		);

		return elements;
		
	}

}
