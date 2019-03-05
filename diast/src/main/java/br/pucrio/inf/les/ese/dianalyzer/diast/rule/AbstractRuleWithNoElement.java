package br.pucrio.inf.les.ese.dianalyzer.diast.rule;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.ElementResult;
import com.github.javaparser.ast.CompilationUnit;

public abstract class AbstractRuleWithNoElement extends AbstractRule {
	
	protected abstract ElementResult processRule(CompilationUnit cu) throws Exception;
	
}
