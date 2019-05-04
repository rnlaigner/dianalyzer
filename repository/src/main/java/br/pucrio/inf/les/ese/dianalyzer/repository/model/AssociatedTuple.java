package br.pucrio.inf.les.ese.dianalyzer.repository.model;


import java.util.Objects;

public class AssociatedTuple extends AbstractTuple implements Comparable<AssociatedTuple> {

    public String father;

    public String name;

    public int hashId;

    public AssociatedTuple(String father, String name, String type) {
        this.father = father;
        this.name = name;
        this.type = type;
        this.hashId = Objects.hash(this.name);
    }

    @Override
    public boolean equals(Object o) {
        AssociatedTuple tuple = (AssociatedTuple) o;
        boolean result = tuple.name.contentEquals( this.name ) ? true : false;
        return result;
    }

    @Override
    public int hashCode() {
        return hashId;
    }

    @Override
    public int compareTo(AssociatedTuple o) {
        if ( this.hashId > hashId ) return 1;
        if ( this.hashId < hashId ) return -1;
        return 0;
    }
}
