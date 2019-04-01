package br.pucrio.inf.les.ese.dianalyzer.worker.logic;

import java.util.*;
import java.util.stream.Collectors;

import br.pucrio.inf.les.ese.dianalyzer.diast.practices.*;
import br.pucrio.inf.les.ese.dianalyzer.repository.locator.ServiceLocator;
import br.pucrio.inf.les.ese.dianalyzer.repository.model.IDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.github.javaparser.ast.CompilationUnit;

import br.pucrio.inf.les.ese.dianalyzer.diast.environment.IParser;
import br.pucrio.inf.les.ese.dianalyzer.diast.environment.JavaParserParser;
import br.pucrio.inf.les.ese.dianalyzer.diast.environment.ParseException;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.CompilationUnitResult;
import br.pucrio.inf.les.ese.dianalyzer.worker.environment.Environment;
import br.pucrio.inf.les.ese.dianalyzer.worker.report.IWorkbookCreator;
import br.pucrio.inf.les.ese.dianalyzer.worker.report.Report;
import br.pucrio.inf.les.ese.dianalyzer.worker.report.WorkbookCreator;

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
public class ProjectExecutor extends AbstractProjectExecutor {
	
	private final Log log = LogFactory.getLog(ProjectExecutor.class);
	
	private Environment env;
	
	private Set<AbstractPractice> badPracticesApplied;
	
	public ProjectExecutor(){
		super();
		env = new Environment();
	}
	
	/*
	   FIXME

	   Exception in thread "main" java.lang.OutOfMemoryError: GC overhead limit exceeded

	   TODO

	   Guardar scope dos beans e suas respectivas dependencias em uma tabela

	   Assim, um bean a ser analizado, realiza uma query ao banco

	  */

	@Override
	public void execute(String projectPath, String outputPath) throws Exception {
		
		IParser parser = new JavaParserParser();
		
		List<String> files = env.readFilesFromFolder(projectPath,false);
		
		Set<CompilationUnitResult> results = new HashSet<CompilationUnitResult>();
		
		Report report = buildReportInfo(projectPath);
		
		Integer index = 0;
		int size = files.size();

		IDataSource dataSource = (IDataSource) ServiceLocator.getInstance().getBeanInstance("IDataSource");

		for(String file : files){
			
			index = index + 1;
			log.info("File "+index+" of "+size+" parsed");
			
			CompilationUnit parsedObject = null;
			try{
				parsedObject = (CompilationUnit) parser.parse(file);

				String key = null;
                try {
                    key = parsedObject.getPrimaryTypeName().get();
                }catch(Exception e){
                    log.info("Parsed object has no primary type");
                }

                if(key == null){
                    try{
                        key = parsedObject.getType(0).getNameAsString();
                    }catch(Exception e){
                        log.info("Parsed object has no name");
                        key = index.toString();
                    }
                }

				dataSource.insert( key, parsedObject );
			}
			catch(ParseException e){
				log.error(e.getMessage());
				log.error(e.getStackTrace());
				continue;
			}
			
		}

		Integer i = 0;

		Collection<Map.Entry> entries = dataSource.findAll();

		log.info("Starting processing...");

		for(Map.Entry entry : entries){

			//for each bad practice, process this file
			for(AbstractPractice practice : badPracticesApplied){

				CompilationUnit parsedObject = (CompilationUnit) entry.getValue();

				CompilationUnitResult result = practice.process( parsedObject );

				addToReportIfBadPracticeIsApplied(results, report, parsedObject, practice, result);

			}

			log.info("File "+i+" of "+size+" processed");

			i++;

		}
		
		IWorkbookCreator workbookCreator = new WorkbookCreator();
		
		workbookCreator.create(report, outputPath);
		
	}

}
