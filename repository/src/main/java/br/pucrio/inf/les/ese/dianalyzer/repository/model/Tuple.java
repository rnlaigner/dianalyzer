package br.pucrio.inf.les.ese.dianalyzer.repository.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Tuple")
public class Tuple extends AbstractPersistent {

    @Column
    private String scope;

    @Column
    private String type;

    @Column
    private Boolean isInterface;

    public Tuple() { }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean isInterface() {
        return isInterface;
    }

    public void setInterface(Boolean isInterface) {
        this.isInterface = isInterface;
    }
}
