package br.pucrio.inf.les.ese.dianalyzer.diast.rule;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.ElementResult;
import br.pucrio.inf.les.ese.dianalyzer.repository.locator.ServiceLocator;
import br.pucrio.inf.les.ese.dianalyzer.repository.model.IDataSource;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.AnnotationDeclaration;

import java.util.Collection;
import java.util.Map;
import java.util.NoSuchElementException;

public class IsPrototypeScopeBeanAsSingleton extends AbstractRuleWithNoElement {

	private Collection<Map.Entry> entries;

	public IsPrototypeScopeBeanAsSingleton(){
		IDataSource dataSource = (IDataSource) ServiceLocator.getInstance().getBeanInstance("IDataSource");
		entries = dataSource.findAll();
	}

	@Override
	public ElementResult processRule(CompilationUnit cu) {

		// default is singleton on spring

		AnnotationDeclaration annotation;

		try {
			annotation = cu.getAnnotationDeclarationByName("Scope").get();
		}
		catch(NoSuchElementException e){
			return null;
		}

		// if it is not prototype, return
		if(annotation == null){

		}

		return null;
	}
}
