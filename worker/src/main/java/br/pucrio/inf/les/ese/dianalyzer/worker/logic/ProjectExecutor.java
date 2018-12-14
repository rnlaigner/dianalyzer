package br.pucrio.inf.les.ese.dianalyzer.worker.logic;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
import br.pucrio.inf.les.ese.dianalyzer.diast.practices.BadPracticeNine;
import br.pucrio.inf.les.ese.dianalyzer.diast.practices.BadPracticeOne;
import br.pucrio.inf.les.ese.dianalyzer.diast.practices.BadPracticeSeven;
import br.pucrio.inf.les.ese.dianalyzer.diast.practices.BadPracticeTen;
import br.pucrio.inf.les.ese.dianalyzer.diast.practices.BadPracticeTwelve;
import br.pucrio.inf.les.ese.dianalyzer.diast.practices.BadPracticeTwo;
import br.pucrio.inf.les.ese.dianalyzer.worker.environment.Environment;
import br.pucrio.inf.les.ese.dianalyzer.worker.report.IWorkbookCreator;
import br.pucrio.inf.les.ese.dianalyzer.worker.report.Report;
import br.pucrio.inf.les.ese.dianalyzer.worker.report.WorkbookCreator;

@BadPracticesApplied(values={BadPracticeOne.class,BadPracticeTwo.class,/*BadPracticeThree.class,*/
		/*BadPracticeFour.class,*/BadPracticeFive.class,/*BadPracticeSix.class,*/ BadPracticeSeven.class,
		BadPracticeEight.class,BadPracticeNine.class,BadPracticeTen.class,BadPracticeTwelve.class})
public class ProjectExecutor implements IProjectExecutor {
	
	protected final Log log = LogFactory.getLog(ProjectExecutor.class);
	
	private Environment env;
	
	private Set<AbstractPractice> badPracticesApplied;
	
	public ProjectExecutor(){
		env = new Environment();
		buildBadPracticesApplied();
	}
	
	private Report buildReportInfo(String projectPath){
		Report report = new Report();
		
		String projectName = projectPath.substring(projectPath.lastIndexOf("\\"));
		
		List<String> headers = new ArrayList<String>() { 
            /**
			 * 
			 */
			private static final long serialVersionUID = -899730739044720212L;

			{ 
                add("Bad Practice ID");
                add("Bad Practice Name");
                add("Class"); 
                add("Source"); 
            } 
        }; 
		
		report.setProject(projectName);
		
		report.setHeaders(headers);
		
		return report;
	}

	@Override
	public void execute(String projectPath, String outputPath) throws IOException, ParseException, Exception {
		
		IParser parser = new JavaParserParser();
		
		List<String> files = env.readFilesFromFolder(projectPath,false);
		
		Set<CompilationUnitResult> results = new HashSet<CompilationUnitResult>();
		
		Report report = buildReportInfo(projectPath);
		
		for(String file : files){
			
			CompilationUnit parsedObject = (CompilationUnit) parser.parse(file);
			
			//for each bad practice, process this file
			for(AbstractPractice practice : badPracticesApplied){
				
				CompilationUnitResult result = practice.process( parsedObject);
				
				if(result.badPracticeIsApplied()) {
				
					results.add(result);
					
					List<String> elementsInvolved = result
							.getElementResults()
							.stream()
							.map( p -> p.getElement().getName() )
							.collect(Collectors.toList());
					
					//TODO convert to comma separated list
					
					//FIX
					String className = parsedObject.getTypes().get(0).getNameAsString();
					
					//Mount report line
					List<String> line = new ArrayList<String>() { 
			            /**
						 * 
						 */
						private static final long serialVersionUID = -8971953175798272054L;
	
						{ 
			                add( practice.getNumber().toString() );
			                add( practice.getName() );
							add( className ); 
			                add( elementsInvolved.get(0) ); 
			            } 
			        };
			        
			        report.addLine(line);
		        
				}
		        
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
	      	
	    	badPracticesApplied = new HashSet<AbstractPractice>();
	    	
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
