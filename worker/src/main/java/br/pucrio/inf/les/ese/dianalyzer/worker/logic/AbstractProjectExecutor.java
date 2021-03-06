package br.pucrio.inf.les.ese.dianalyzer.worker.logic;

import br.pucrio.inf.les.ese.dianalyzer.diast.environment.IParser;
import br.pucrio.inf.les.ese.dianalyzer.diast.environment.JavaParserParser;
import br.pucrio.inf.les.ese.dianalyzer.diast.environment.ParseException;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.CompilationUnitResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ElementResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.practices.AbstractPractice;
import br.pucrio.inf.les.ese.dianalyzer.worker.environment.Environment;
import br.pucrio.inf.les.ese.dianalyzer.worker.report.IWorkbookCreator;
import br.pucrio.inf.les.ese.dianalyzer.worker.report.Report;
import br.pucrio.inf.les.ese.dianalyzer.worker.report.WorkbookCreator;
import com.github.javaparser.ast.CompilationUnit;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractProjectExecutor implements IProjectExecutor {

	protected Set<AbstractPractice> badPracticesApplied;

	protected final Environment env;

	protected final IParser parser;

	protected final Log log = LogFactory.getLog(getClass());

	public AbstractProjectExecutor(){
		this.env = new Environment();
		this.parser = new JavaParserParser();
	}

	protected void process(String projectPath, String outputPath, List<String> files) throws IOException {

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

					addToReportIfBadPracticeIsApplied(report, parsedObject, practice, result);

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

	protected void addToReportIfBadPracticeIsApplied(Report report,
													 CompilationUnit parsedObject,
													 AbstractPractice practice,
													 CompilationUnitResult result) {

		if(!result.badPracticeIsApplied()) {
			return;
		}

		String className = null;
		if (parsedObject.getTypes().size() > 1){
			List<String> list = parsedObject.getTypes().stream().map(p->p.getNameAsString()).collect(Collectors.toList());
			className = String.join(",",list);
		} else {
			className = parsedObject.getTypes().get(0).getNameAsString();
		}

		for( ElementResult element : result.getElementResults() ) {

			String elementName = element.getElement().getName();

			//Mount report line
			List<String> line = new ArrayList<String>();

			line.add(practice.getNumber().toString());
			line.add(practice.getName());
			line.add(className);
			line.add(elementName);

			report.addLine(line);

		}

	}

	protected Report buildReportInfo(String projectPath){

		Report report = new Report();

		String projectName = projectPath.substring(projectPath.lastIndexOf("\\")+1);

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

	protected void buildBadPracticesApplied(){

	    try {
	    	
	    	BadPracticesApplied annotations = (BadPracticesApplied) getClass().getAnnotation(BadPracticesApplied.class);

	    	this.badPracticesApplied = new HashSet<AbstractPractice>();
	    	
	        for(Class<? extends AbstractPractice> clazz : annotations.values()){
	        	
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
