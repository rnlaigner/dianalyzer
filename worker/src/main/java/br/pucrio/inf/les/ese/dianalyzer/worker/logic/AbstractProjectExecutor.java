package br.pucrio.inf.les.ese.dianalyzer.worker.logic;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.CompilationUnitResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.practices.*;
import br.pucrio.inf.les.ese.dianalyzer.worker.report.Report;
import com.github.javaparser.ast.CompilationUnit;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Constructor;
import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractProjectExecutor implements IProjectExecutor {

	protected Set<AbstractPractice> badPracticesApplied;

	protected final Log log = LogFactory.getLog(ProjectExecutor.class);

	protected void addToReportIfBadPracticeIsApplied(Set<CompilationUnitResult> results, Report report, CompilationUnit parsedObject, AbstractPractice practice, CompilationUnitResult result) {

		if(result.badPracticeIsApplied()) {

			results.add(result);

			List<String> elementsInvolved = result
					.getElementResults()
					.stream()
					.map( p -> p.getElement().getName() )
					.collect(Collectors.toList());

			String className = null;
			if(parsedObject.getTypes().size() > 1){
				List<String> list = parsedObject.getTypes().stream().map(p->p.getNameAsString()).collect(Collectors.toList());
				className = String.join(",",list);
			}else{
				className = parsedObject.getTypes().get(0).getNameAsString();
			}

			String elements = String.join(",", elementsInvolved);

			//Mount report line
			List<String> line = new ArrayList<String>();

			line.add( practice.getNumber().toString() );
			line.add( practice.getName() );
			line.add( className );
			line.add( elements );

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
	
	@SuppressWarnings("unchecked")
	protected void buildBadPracticesApplied(){

	    try {
	    	
	    	BadPracticesApplied annotations = (BadPracticesApplied) getClass().getAnnotation(BadPracticesApplied.class);

	    	badPracticesApplied = new HashSet<AbstractPractice>();
	    	
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
