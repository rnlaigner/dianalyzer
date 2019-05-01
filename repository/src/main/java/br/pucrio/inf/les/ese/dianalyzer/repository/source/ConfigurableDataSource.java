package br.pucrio.inf.les.ese.dianalyzer.repository.source;

public abstract class ConfigurableDataSource<S, T> implements IDataSource<S, T> {

    protected abstract void start();

    protected abstract void finish();

}
