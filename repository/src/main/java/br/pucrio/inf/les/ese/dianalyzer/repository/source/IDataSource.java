package br.pucrio.inf.les.ese.dianalyzer.repository.source;

import java.util.Iterator;

public interface IDataSource<S, T> {

    Iterator<?> findAll();

    T find( S key );

    T insert( T element );

    void delete( S key );

}
