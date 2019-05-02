package br.pucrio.inf.les.ese.dianalyzer.repository.source;

import java.util.Collection;

public interface IAssociatedDataSource<S, T> {

    Collection<T> findAssociated(S key);

}
