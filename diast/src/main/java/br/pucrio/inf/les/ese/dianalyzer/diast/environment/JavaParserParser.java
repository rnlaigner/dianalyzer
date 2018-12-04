package br.pucrio.inf.les.ese.dianalyzer.diast.environment;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;

public class JavaParserParser implements IParser {

	@Override
	public Object parse(String clazz) throws ParseException {
		return concreteParse(clazz);
	}

	private CompilationUnit concreteParse(String clazz) throws ParseException{
		
		CompilationUnit cu;
		
        try 
        {
        	cu = JavaParser.parse(clazz);
        }
        catch(Exception e)
        {
        	throw new ParseException();
        }
		return cu;
		
	}
	
}
