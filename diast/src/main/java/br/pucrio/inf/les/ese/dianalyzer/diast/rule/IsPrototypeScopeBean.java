package br.pucrio.inf.les.ese.dianalyzer.diast.rule;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.ElementResult;
import br.pucrio.inf.les.ese.dianalyzer.repository.locator.ServiceLocator;
import br.pucrio.inf.les.ese.dianalyzer.repository.model.IDataSource;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.AnnotationDeclaration;

import java.util.Collection;
import java.util.Map;

public class IsPrototypeScopeBean extends AbstractRuleWithElementWithNoElementSingleResult {

	@Override
	public ElementResult processRule(CompilationUnit cu) {

		// default is singleton on spring

		AnnotationDeclaration annotation = cu.getAnnotationDeclarationByName("Scope").get();

		// if is not prototype, return

		IDataSource dataSource = (IDataSource) ServiceLocator.getInstance().getBeanInstance("IDataSource");

		Collection<Map.Entry> entries = dataSource.findAll();

		return null;
	}
}
