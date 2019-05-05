package br.pucrio.inf.les.ese.dianalyzer.diast.identification;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.AbstractElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.AttributeElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.InjectionAnnotation;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ObjectType;
import br.pucrio.inf.les.ese.dianalyzer.repository.locator.ServiceLocator;
import br.pucrio.inf.les.ese.dianalyzer.repository.model.Tuple;
import br.pucrio.inf.les.ese.dianalyzer.repository.source.IBeanDataSource;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;

import java.util.ArrayList;
import java.util.List;

public class NonPrimitiveTypeFieldIdentificator extends AbstractIdentificator {

	private final IBeanDataSource dataSource;

	public NonPrimitiveTypeFieldIdentificator() {
		this.dataSource = (IBeanDataSource) ServiceLocator.getInstance().getBeanInstance("IDataSource");
	}

	@Override
	public List<AbstractElement> identify(CompilationUnit cu){
		
		List<AbstractElement> elements = new ArrayList<AbstractElement>();
		
		cu.findAll(FieldDeclaration.class).stream()
			.filter(f ->
				 !f.getVariable(0).getTypeAsString()
						.matches("String|int|Integer|Double|double|char|Long|long|List|Collection|Map|HashMap")
//						&&
//						!f.getAnnotations()
//						.stream()
//						.anyMatch(a -> a
//								.getName()
//								.getIdentifier()
//								.matches(InjectionAnnotation.getInjectionAnnotationsRegex()))
			 )
			.forEach(f -> {
				AttributeElement elem = new AttributeElement();
				
				List<String> modifiers = new ArrayList<String>();
				f.getModifiers().stream().forEach( m -> { modifiers.add( m.asString() ); } );
				 				
				elem.setModifiers(modifiers);
				
				VariableDeclarator variable = f.getVariables().get(0);
				
				elem.setType(variable.getType().asString());

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
				
				elem.setName(variable.getName().toString());

				elements.add( elem );
			}
		);

		return elements;
		
	}

}
