package br.pucrio.inf.les.ese.dianalyzer.repository.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "AssociatedTuple")
public class AssociatedTuple extends AbstractPersistent {

    @Column
    private String father;

    @Column
    private String type;

    @Column
    private String name;

    public AssociatedTuple() { }

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
