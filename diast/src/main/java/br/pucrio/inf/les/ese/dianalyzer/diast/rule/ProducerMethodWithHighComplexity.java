package br.pucrio.inf.les.ese.dianalyzer.diast.rule;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.AbstractElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ProducerMethodElement;
import com.github.javaparser.ast.CompilationUnit;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ElementResult;
import com.github.javaparser.ast.stmt.BlockStmt;

public class ProducerMethodWithHighComplexity extends AbstractRuleWithElement {

	@Override
	public ElementResult processRule(CompilationUnit cu, AbstractElement element) {

		ProducerMethodElement producerMethodElement = (ProducerMethodElement) element;

		BlockStmt codeBlock = producerMethodElement.getBody();

		// TODO verify cyclomatic complexity

		return null;
	}
}
