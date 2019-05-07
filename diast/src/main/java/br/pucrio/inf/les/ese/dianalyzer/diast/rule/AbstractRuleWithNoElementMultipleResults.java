package br.pucrio.inf.les.ese.dianalyzer.diast.rule;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.ElementResult;
import com.github.javaparser.ast.CompilationUnit;

import java.util.List;

public abstract class AbstractRuleWithNoElementMultipleResults extends AbstractRule {
	
	protected abstract List<ElementResult> processRule(CompilationUnit cu) throws Exception;
	
}
