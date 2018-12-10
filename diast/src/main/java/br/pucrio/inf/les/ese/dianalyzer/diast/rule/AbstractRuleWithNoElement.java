package br.pucrio.inf.les.ese.dianalyzer.diast.rule;

import java.util.List;

import com.github.javaparser.ast.CompilationUnit;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.ElementResult;

public abstract class AbstractRuleWithNoElement extends AbstractRule {
	
	protected abstract List<ElementResult> processRule(CompilationUnit cu) throws Exception;
	
}
