package br.pucrio.inf.les.ese.dianalyzer.diast.rule;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.AbstractElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ElementResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.InjectedElement;
import br.pucrio.inf.les.ese.dianalyzer.repository.locator.ServiceLocator;
import br.pucrio.inf.les.ese.dianalyzer.repository.model.Tuple;
import br.pucrio.inf.les.ese.dianalyzer.repository.source.IBeanDataSource;
import com.github.javaparser.ast.CompilationUnit;

public class ReferenceOnConcreteClass extends AbstractRuleWithElement {

	private final IBeanDataSource dataSource;

	public ReferenceOnConcreteClass() {
		super();
		this.dataSource = (IBeanDataSource) ServiceLocator.getInstance().getBeanInstance("IDataSource");
	}

	@Override
	public ElementResult processRule(CompilationUnit cu, AbstractElement element) {
		
		ElementResult elementResult = new ElementResult();
		
		elementResult.setElement(element);
		elementResult.setResult(false);

		InjectedElement injectedElement = null;

		try {
			injectedElement = (InjectedElement) element;

			Tuple tuple = (Tuple) dataSource.getBeanByName(injectedElement.getType());

			if(tuple != null){
				log.info("Associated tuple found");
				if(!tuple.isInterface){
					elementResult.setResult(true);
				}
			}

		} catch(Exception e){
			log.info("Noooo");
		}
		
		return elementResult;
	}
	
	

}
