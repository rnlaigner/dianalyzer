package br.pucrio.inf.les.ese.dianalyzer.repository.model;

import java.util.Collection;

public interface IDataSource<S, T> {

    Collection<?> findAll();

    Collection<T> find( S key );

    T insert( S key, T element );

    T delete( S key );

}
