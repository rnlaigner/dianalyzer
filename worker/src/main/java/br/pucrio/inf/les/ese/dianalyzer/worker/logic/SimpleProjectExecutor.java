package br.pucrio.inf.les.ese.dianalyzer.worker.logic;

import br.pucrio.inf.les.ese.dianalyzer.diast.environment.IParser;
import br.pucrio.inf.les.ese.dianalyzer.diast.environment.JavaParserParser;
import br.pucrio.inf.les.ese.dianalyzer.diast.environment.ParseException;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.CompilationUnitResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.practices.*;
import br.pucrio.inf.les.ese.dianalyzer.worker.environment.Environment;
import br.pucrio.inf.les.ese.dianalyzer.worker.report.IWorkbookCreator;
import br.pucrio.inf.les.ese.dianalyzer.worker.report.Report;
import br.pucrio.inf.les.ese.dianalyzer.worker.report.WorkbookCreator;
import com.github.javaparser.ast.CompilationUnit;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

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
public class SimpleProjectExecutor extends AbstractProjectExecutor {

	private final Log log = LogFactory.getLog(SimpleProjectExecutor.class);

	private Environment env;

	public SimpleProjectExecutor(){
		env = new Environment();
		buildBadPracticesApplied();
	}

	@Override
	public void execute(String projectPath, String outputPath) throws Exception {
		
		IParser parser = new JavaParserParser();
		
		List<String> files = env.readFilesFromFolder(projectPath,false);
		
		Set<CompilationUnitResult> results = new HashSet<CompilationUnitResult>();
		
		Report report = buildReportInfo(projectPath);
		
		Integer index = 0;
		int size = files.size();

		for(String file : files){
			
			index = index + 1;
			log.info("File "+index+" of "+size+" parsed");
			
			CompilationUnit parsedObject = null;
			try{

				parsedObject = (CompilationUnit) parser.parse(file);

				for(AbstractPractice practice : badPracticesApplied){

					log.info("Bad practice being searched: "+ practice.getName());

					CompilationUnitResult result = practice.process( parsedObject );

					addToReportIfBadPracticeIsApplied(results, report, parsedObject, practice, result);

				}

			}
			catch(ParseException e){
				log.error(e.getMessage());
				log.error(e.getStackTrace());
				continue;
			}
			
		}
		
		IWorkbookCreator workbookCreator = new WorkbookCreator();
		
		workbookCreator.create(report, outputPath);
		
	}



}
