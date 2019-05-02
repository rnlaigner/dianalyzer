package br.pucrio.inf.les.ese.dianalyzer.repository.repository;

import br.pucrio.inf.les.ese.dianalyzer.repository.model.AssociatedTuple;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository("associatedTupleRepository")
public interface AssociatedTupleRepository extends CrudRepository<AssociatedTuple, Long> {

    @Query("SELECT u FROM AssociatedTuple u WHERE u.father = :father")
    Collection<AssociatedTuple> findAssociated(String father);

}

