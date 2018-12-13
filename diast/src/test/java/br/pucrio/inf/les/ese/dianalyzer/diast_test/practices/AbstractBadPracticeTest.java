package br.pucrio.inf.les.ese.dianalyzer.diast_test.practices;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory; 

import org.junit.After;
import org.junit.Before;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.CompilationUnitResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.practices.AbstractPractice;
import br.pucrio.inf.les.ese.dianalyzer.diast_test.annotation.ResourceFolder;
import br.pucrio.inf.les.ese.dianalyzer.diast_test.env.Environment;

import static org.junit.Assert.assertThat;

import static org.hamcrest.CoreMatchers.is;

public abstract class AbstractBadPracticeTest {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	private List<String> classes = new ArrayList<String>();
	
	private Environment env = new Environment();
	
	public AbstractBadPracticeTest(){}
	
	public abstract Class<? extends AbstractPractice> getConcretePractice();
	
	@Test
	public void execute() throws NoSuchMethodException, 
								 SecurityException, 
								 InstantiationException, 
								 IllegalAccessException, 
								 IllegalArgumentException, 
								 InvocationTargetException{
		
		Constructor<? extends AbstractPractice> constructor = 
				getConcretePractice().getConstructor();
		
		for(String file : classes){
			
	       CompilationUnit cu;
	        
	       cu = JavaParser.parse(file);
	       
	       AbstractPractice practice = constructor.newInstance();
	       
	       CompilationUnitResult cuResult = practice.process(cu);
	       
	       //O ideal eh que esse assert seja uma classe abstrata 
	       //implementada pela classe concreta
	       //A classe concreta teria os elementos esperados,
	       //retornados dentro do cuResult
	       assertThat( cuResult.badPracticeIsApplied(), is(Boolean.TRUE) );
    
		}
		
	}

	//Configure environment for given test
	@Before
	public void setUp() {
		
		//
		if(!classes.isEmpty()){
			return;
		}
		
		//read files from resource folder
		try{
			String resourceFolder = getResourceFolder(getClass());
			classes = env.readFilesFromFolder(resourceFolder, true);
		}
		catch(IOException e){
			log.error(e.getStackTrace());
		}
		
	}
	
	//Free resources
	@After
	public void setDown(){
		
	}
	
	private String getResourceFolder(Class<? extends AbstractBadPracticeTest> c){		

	    try {
	    	
	    	if (c == null){
	    		c = getClass();
	    	}
	    	
	        ResourceFolder anno = (ResourceFolder) c.getAnnotation(ResourceFolder.class);
	      	
	        return anno.value();
	      
	    }
	    catch(Exception e){
	    	log.error(e.getStackTrace());
	    }
	    
	    return null;
		
	}

}
