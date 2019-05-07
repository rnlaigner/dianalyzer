package br.pucrio.inf.les.ese.dianalyzer.diast.rule;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.AbstractElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ElementResult;
import com.github.javaparser.ast.CompilationUnit;

import java.util.List;

public abstract class AbstractRuleWithElements extends AbstractRule {
	
	protected abstract List<ElementResult> processRule(CompilationUnit cu, List<AbstractElement> elements) throws Exception;
	
}
