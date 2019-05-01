package br.pucrio.inf.les.ese.dianalyzer.repository.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TUPLE")
public class Tuple {

    @Id
    @Column(name = "ID", nullable = false, length = 40)
    private Long id;

    public Tuple() {
    }

    public Tuple(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
