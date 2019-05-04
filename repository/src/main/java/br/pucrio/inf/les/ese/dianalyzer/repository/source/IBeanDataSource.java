package br.pucrio.inf.les.ese.dianalyzer.repository.source;

public interface IBeanDataSource<S, T> extends IDataSource<S, T> {

    T findByName( String name );

}
