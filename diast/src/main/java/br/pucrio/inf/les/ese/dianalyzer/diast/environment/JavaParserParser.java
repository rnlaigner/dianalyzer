package br.pucrio.inf.les.ese.dianalyzer.diast.environment;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseProblemException;
import com.github.javaparser.TokenMgrException;
import com.github.javaparser.ast.CompilationUnit;

public class JavaParserParser implements IParser {
	
	private final Log log = LogFactory.getLog(JavaParserParser.class);

	@Override
	public Object parse(String clazz) throws ParseException {
		return concreteParse(clazz);
	}

	private CompilationUnit concreteParse(String clazz) throws ParseException{
		
		CompilationUnit cu = null;
		
        try 
        {
        	cu = JavaParser.parse(clazz);
        }
        catch(TokenMgrException e){
        	log.info("Class is not a compilation unit");
        	throw new ParseException("Class is not a compilation unit");
        }
        catch(ParseProblemException e){
        	log.info("Class is not a compilation unit");
        	throw new ParseException("Class is not a compilation unit");
        }
        catch(Exception e)
        {
        	log.info("Proble on parse is unknown. Check logs for further information.");
        	log.info(clazz);
        	throw new ParseException(e.getMessage());
        }
		return cu;
		
	}
	
}
