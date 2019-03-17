package br.pucrio.inf.les.ese.dianalyzer.diast_test.practices;

import br.pucrio.inf.les.ese.dianalyzer.diast_test.annotation.BadPracticeApplied;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.CompilationUnitResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.practices.AbstractPractice;
import org.junit.runner.RunWith;

import br.pucrio.inf.les.ese.dianalyzer.diast_test.env.Environment;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertThat;

import static org.hamcrest.CoreMatchers.is;

@RunWith(JUnitParamsRunner.class)
public abstract class AbstractBadPracticeTest {
	
	protected final Log log = LogFactory.getLog(getClass());

	public AbstractBadPracticeTest(){
	    log.info("Initiating abstract test behavior");
    }
	
	public abstract List<String> setUp();

	// https://github.com/Pragmatists/JUnitParams/wiki/Quickstart

	@Test
	@Parameters(method = "setUp")
	public void execute(String file) throws
								 SecurityException, 
								 InstantiationException, 
								 IllegalAccessException, 
								 IllegalArgumentException {

        Class<? extends AbstractPractice> clazz =  getBadPracticeApplied( getClass() );

        CompilationUnit cu = JavaParser.parse(file);

        AbstractPractice practice = clazz.newInstance();

        CompilationUnitResult cuResult = practice.process(cu);

       //O ideal eh que esse assert seja uma classe abstrata
       //implementada pela classe concreta
       //A classe concreta teria os elementos esperados,
       //retornados dentro do cuResult
        assertThat( cuResult.badPracticeIsApplied(), is(Boolean.TRUE) );
		
	}

	protected List<String> getClassesToParse() {

		Class<? extends AbstractBadPracticeTest> clazz = getClass();

		String folder = clazz.getCanonicalName();

		// remove test from the final
		folder = folder.replace("Test","");

		folder = folder.substring( folder.lastIndexOf(".") + 1 );

		String resourceFolder = "src//test//resources//" + folder;

		List<String> classes = null;
		try {
			classes = Environment.readFilesFromFolder(resourceFolder, true);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return classes;
	}
	
	private Class<? extends AbstractPractice> getBadPracticeApplied(Class<? extends AbstractBadPracticeTest> c){
		try {
			BadPracticeApplied anno = c.getAnnotation(BadPracticeApplied.class);
			return anno.value();
		}
		catch(Exception e){
			log.error(e.getStackTrace());
		}
		return null;
	}

}
