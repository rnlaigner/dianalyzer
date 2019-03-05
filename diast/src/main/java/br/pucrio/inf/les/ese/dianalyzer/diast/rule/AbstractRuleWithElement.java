package br.pucrio.inf.les.ese.dianalyzer.diast.rule;

import com.github.javaparser.ast.CompilationUnit;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.AbstractElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ElementResult;

public abstract class AbstractRuleWithElement extends AbstractRule {
	
	protected abstract ElementResult processRule(CompilationUnit cu, AbstractElement element) throws Exception;
	
}
