package br.pucrio.inf.les.ese.dianalyzer.worker.logic;

import br.pucrio.inf.les.ese.dianalyzer.diast.environment.ParseException;
import br.pucrio.inf.les.ese.dianalyzer.diast.identification.ConstructorInjectionIdentificator;
import br.pucrio.inf.les.ese.dianalyzer.diast.identification.FieldDeclarationInjectionIdentificator;
import br.pucrio.inf.les.ese.dianalyzer.diast.identification.MethodInjectionIdentificator;
import br.pucrio.inf.les.ese.dianalyzer.diast.identification.SetMethodInjectionIdentificator;
import br.pucrio.inf.les.ese.dianalyzer.diast.logic.InjectionBusiness;
import br.pucrio.inf.les.ese.dianalyzer.diast.logic.ScopeBusiness;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.AbstractElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.InjectedElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.practices.*;
import br.pucrio.inf.les.ese.dianalyzer.repository.locator.ServiceLocator;
import br.pucrio.inf.les.ese.dianalyzer.repository.model.AssociatedTuple;
import br.pucrio.inf.les.ese.dianalyzer.repository.model.Tuple;
import br.pucrio.inf.les.ese.dianalyzer.repository.source.IBeanDataSource;
import br.pucrio.inf.les.ese.dianalyzer.repository.source.IDataSource;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

import java.util.List;

@BadPracticesApplied(values={
		BadPracticeOne.class,
		BadPracticeTwo.class,
		BadPracticeThree.class,
		/*BadPracticeFour.class,*/
		BadPracticeFive.class,
		/*BadPracticeSix.class,*/
		BadPracticeSeven.class,
		BadPracticeEight.class,
		BadPracticeNine.class,
		BadPracticeTen.class,
		BadPracticeEleven.class,
		BadPracticeTwelve.class,
		// BadPracticeThirteen.class
})
public class InMemoryProjectExecutor extends AbstractProjectExecutor {

	private final IBeanDataSource dataSource;

	public InMemoryProjectExecutor(){
		super();
		buildBadPracticesApplied();
		this.dataSource = (IBeanDataSource) ServiceLocator.getInstance().getBeanInstance("IDataSource");
	}
	
	/*
	   FIXME

	   Exception in thread "main" java.lang.OutOfMemoryError: GC overhead limit exceeded

	  */

	private void preProcessProjectClasses(List<String> files){

		Integer index = 0;

		final int size = files.size();

		for(String file : files){

			index = index + 1;
			log.info("File "+index+" of "+size+" parsed");

			CompilationUnit parsedObject = null;
			try{
				parsedObject = (CompilationUnit) parser.parse(file);

				String type = null;
				try {
					type = parsedObject.getType(0).getNameAsString();
				}catch(Exception e){
					log.info("Parsed object has no primary type");
				} finally {

					if (type == null) {

						try {
							type = parsedObject.getPrimaryTypeName().get();
						} catch (Exception e) {
							log.info("Parsed object has no name");
							type = index.toString();
						}
					}

				}

				String scope = ScopeBusiness.extractScopeAnnotationValueAsString(parsedObject);

				Boolean isInterface = false;
				try {
					if (parsedObject.getType(0) instanceof ClassOrInterfaceDeclaration) {
						ClassOrInterfaceDeclaration classOrInterfaceDeclaration = (ClassOrInterfaceDeclaration) parsedObject.getType(0);
						isInterface = classOrInterfaceDeclaration.isInterface();
					}
				}
				catch(Exception e){
					log.error(e.getMessage());
				}

				Tuple tuple = new Tuple(scope, isInterface, type);

				dataSource.insert( tuple );

				List<AbstractElement> injectedAttributes = InjectionBusiness.
						getInjectedElementsFromClass( parsedObject );

				insertAssociatedData(type, injectedAttributes);

			}
			catch(ParseException e){
				log.error(e.getMessage());
				log.error(e.getStackTrace());
				continue;
			}

		}

	}

	private void insertAssociatedData(String father, List<AbstractElement> elements){

		for(AbstractElement elem : elements){

			String type = ( (InjectedElement) elem).getType();

			if(type == null){
				log.info("element name: "+elem.getName()+" has no type");
			}

			AssociatedTuple associatedTuple = new AssociatedTuple(father,elem.getName(),type);

			dataSource.insertAssociatedBean(associatedTuple);

		}

	}

	@Override
	public void execute(String projectPath, String outputPath) throws Exception {
		
		List<String> files = env.readFilesFromFolder(projectPath,false);

		preProcessProjectClasses(files);

		process(projectPath, outputPath, files);
		
	}

}
