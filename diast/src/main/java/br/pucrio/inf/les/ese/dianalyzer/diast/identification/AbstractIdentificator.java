package br.pucrio.inf.les.ese.dianalyzer.diast.identification;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.AbstractElement;
import com.github.javaparser.ast.CompilationUnit;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

public abstract class AbstractIdentificator {
	
	protected final Log log = LogFactory.getLog(getClass());

	public abstract List<AbstractElement> identify(CompilationUnit cu);

}
