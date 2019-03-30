package br.pucrio.inf.les.ese.dianalyzer.worker.logic;

import br.pucrio.inf.les.ese.dianalyzer.diast.practices.*;
import br.pucrio.inf.les.ese.dianalyzer.worker.report.Report;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Constructor;
import java.util.*;

public abstract class AbstractProjectExecutor implements IProjectExecutor {

	protected Set<AbstractPractice> badPracticesApplied;

	protected final Log log = LogFactory.getLog(ProjectExecutor.class);

	public AbstractProjectExecutor(){
		buildBadPracticesApplied();
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
