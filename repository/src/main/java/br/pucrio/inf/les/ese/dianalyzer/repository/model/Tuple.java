package br.pucrio.inf.les.ese.dianalyzer.repository.model;


import java.util.Objects;

public class Tuple extends AbstractTuple {

    public String scope;

    public Boolean isInterface;

    public int hashId;

    public Tuple(String scope, Boolean isInterface, String type) {
        this.scope = scope;
        this.isInterface = isInterface;
        this.type = type;
        this.hashId = Objects.hash(this.type);
    }

    @Override
    public boolean equals(Object o) {
        Tuple tuple = (Tuple) o;
        boolean result = tuple.type.contentEquals( this.type ) ? true : false;
        return result;
    }

    @Override
    public int hashCode() {
        return hashId;
    }
}
