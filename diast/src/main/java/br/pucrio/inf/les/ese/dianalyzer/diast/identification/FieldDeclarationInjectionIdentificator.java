package br.pucrio.inf.les.ese.dianalyzer.diast.identification;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.*;
import br.pucrio.inf.les.ese.dianalyzer.repository.locator.ServiceLocator;
import br.pucrio.inf.les.ese.dianalyzer.repository.model.AssociatedTuple;
import br.pucrio.inf.les.ese.dianalyzer.repository.model.Tuple;
import br.pucrio.inf.les.ese.dianalyzer.repository.source.IAssociatedBeanDataSource;
import br.pucrio.inf.les.ese.dianalyzer.repository.source.IBeanDataSource;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

				elem.setName(variable.getName().toString());
				
				try {

					// primeiro busco em memoria, se nao encontrar, me baseio na informacao do javaparser
					Tuple tuple = (Tuple) dataSource.findByName(elem.getName());

					if(tuple != null){
						log.info("Associated tuple found");
					} else {
						if(variable.getType().getClass().isInterface()){
							elem.setObjectType(ObjectType.INTERFACE);
						} else {
							elem.setObjectType(ObjectType.CLASS);
						}
					}



				} catch (Exception e2) {
					e2.printStackTrace();
				}
				
				List<String> annotations = f.getAnnotations()
											.stream()
											.distinct()
											.map(e -> e.getName().asString())
											.collect( Collectors.toList() );
				
				InjectionAnnotation injectionAnnotation = null;
				for(String annotation : annotations){
					try{
						injectionAnnotation = getInjectionAnnotationFromString(annotation);
					}
					catch(Exception e){
						continue;
					}
				}
				
//				TODO Nao quero ter de adicionar ao metodo. Pensar em algo				
//				if(injectionAnnotation == null){
//					throw new Exception("No annotations were identified.");
//				}
				
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
