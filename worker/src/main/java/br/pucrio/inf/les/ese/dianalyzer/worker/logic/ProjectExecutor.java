package br.pucrio.inf.les.ese.dianalyzer.worker.logic;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.github.javaparser.ast.CompilationUnit;

import br.pucrio.inf.les.ese.dianalyzer.diast.environment.IParser;
import br.pucrio.inf.les.ese.dianalyzer.diast.environment.JavaParserParser;
import br.pucrio.inf.les.ese.dianalyzer.diast.environment.ParseException;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.CompilationUnitResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.practices.AbstractPractice;
import br.pucrio.inf.les.ese.dianalyzer.diast.practices.BadPracticeEight;
import br.pucrio.inf.les.ese.dianalyzer.diast.practices.BadPracticeFive;
import br.pucrio.inf.les.ese.dianalyzer.diast.practices.BadPracticeFour;
import br.pucrio.inf.les.ese.dianalyzer.diast.practices.BadPracticeNine;
import br.pucrio.inf.les.ese.dianalyzer.diast.practices.BadPracticeOne;
import br.pucrio.inf.les.ese.dianalyzer.diast.practices.BadPracticeSeven;
import br.pucrio.inf.les.ese.dianalyzer.diast.practices.BadPracticeTen;
import br.pucrio.inf.les.ese.dianalyzer.diast.practices.BadPracticeThree;
import br.pucrio.inf.les.ese.dianalyzer.diast.practices.BadPracticeTwelve;
import br.pucrio.inf.les.ese.dianalyzer.diast.practices.BadPracticeTwo;
import br.pucrio.inf.les.ese.dianalyzer.worker.environment.Environment;
import br.pucrio.inf.les.ese.dianalyzer.worker.report.IWorkbookCreator;
import br.pucrio.inf.les.ese.dianalyzer.worker.report.Report;
import br.pucrio.inf.les.ese.dianalyzer.worker.report.WorkbookCreator;

@BadPracticesApplied(values={BadPracticeOne.class,BadPracticeTwo.class,BadPracticeThree.class,
		BadPracticeFour.class,BadPracticeFive.class,/*BadPracticeSix.class,*/ BadPracticeSeven.class,
		BadPracticeEight.class,BadPracticeNine.class,BadPracticeTen.class,BadPracticeTwelve.class})
public class ProjectExecutor implements IProjectExecutor {
	
	protected final Log log = LogFactory.getLog(ProjectExecutor.class);
	
	private Environment env;
	
	private Set<AbstractPractice> badPracticesApplied;
	
	public ProjectExecutor(){
		env = new Environment();
		badPracticesApplied = new HashSet<AbstractPractice>();
		buildBadPracticesApplied();
	}
	
	//TODO get project name based on path + init headers
	private Report buildReportInfo(){
		Report report = new Report();
		return null;
	}

	@Override
	public void execute(String projectPath, String outputPath) throws IOException, ParseException {
		
		IParser parser = new JavaParserParser();
		
		List<String> files = env.readFilesFromFolder(projectPath,false);
		
		Set<CompilationUnitResult> results = new HashSet<CompilationUnitResult>();
		
		Report report = buildReportInfo();
		
		for(String file : files){
			
			Object parsedObject = parser.parse(file);
			
			//for each bad practice, process this file
			for(AbstractPractice practice : badPracticesApplied){
				
				CompilationUnitResult result = practice.process((CompilationUnit) parsedObject);
				results.add(result);
				
				//Mount report line
				
			}
			
		}
		
		//TODO Salvar results em uma planilha
		IWorkbookCreator workbookCreator = new WorkbookCreator();
		
		workbookCreator.create(report, outputPath);
		
	}
	
	@SuppressWarnings("unchecked")
	private void buildBadPracticesApplied(){

	    try {
	    	
	    	BadPracticesApplied anno = (BadPracticesApplied) getClass().getAnnotation(BadPracticesApplied.class);
	      	
	        for(Class<? extends AbstractPractice> clazz : anno.values()){
	        	
	        	Constructor<? extends AbstractPractice> constructor = 
	        			clazz.getConstructor();
	        	
	        	AbstractPractice practice = constructor.newInstance();
	        	
	        	badPracticesApplied.add(practice);
	        	
	        }
	      
	    }
	    catch(Exception e){
	    	log.error(e.getStackTrace());
	    }
		
	}

}
