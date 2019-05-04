package br.pucrio.inf.les.ese.dianalyzer.repository.source;

import java.util.Collection;

public interface IBeanDataSource<S, T, V> extends IDataSource<S, T> {

    Collection<V> findAssociatedBeans(String beanName);

    T getBeanByName(String name);

    boolean isAssociatedBean(String beanName, String associatedBeanName);

    boolean insertAssociatedBean( V element );

}
