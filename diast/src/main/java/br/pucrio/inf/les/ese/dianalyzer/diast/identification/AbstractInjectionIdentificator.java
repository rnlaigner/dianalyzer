package br.pucrio.inf.les.ese.dianalyzer.diast.identification;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.InjectionType;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ObjectType;
import br.pucrio.inf.les.ese.dianalyzer.repository.locator.ServiceLocator;
import br.pucrio.inf.les.ese.dianalyzer.repository.model.Tuple;
import br.pucrio.inf.les.ese.dianalyzer.repository.source.IBeanDataSource;

public abstract class AbstractInjectionIdentificator extends AbstractIdentificator {

	private final InjectionType injectionType;

	private final IBeanDataSource dataSource;

	public AbstractInjectionIdentificator(InjectionType injectionType) {
		this.injectionType = injectionType;
		this.dataSource = (IBeanDataSource) ServiceLocator.getInstance().getBeanInstance("IDataSource");
	}

	public InjectionType getInjectionType() {
		return injectionType;
	}

	protected ObjectType getObjectTypeFromString(String objectType) throws Exception {

		Tuple tuple = (Tuple) dataSource.getBeanByName(objectType);

		if(tuple != null){
			log.info("Associated tuple found");
			if(tuple.isInterface){
				return ObjectType.INTERFACE;
			} else{
				return ObjectType.CLASS;
			}
		} else {

			if (objectType.toLowerCase().contains(ObjectType.CLASS.getValue())) {
				return ObjectType.CLASS;
			}

			if (objectType.equals(ObjectType.INTERFACE.getValue())) {
				return ObjectType.INTERFACE;
			}

			throw new Exception("Errado");

		}

	}

}
