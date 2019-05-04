package br.pucrio.inf.les.ese.dianalyzer.repository.repository;

import br.pucrio.inf.les.ese.dianalyzer.repository.model.Tuple;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("tupleRepository")
public interface TupleRepository extends CrudRepository<Tuple, Long> {

    @Query("SELECT u FROM Tuple u WHERE u.type = :name")
    Tuple findByName(String name);

}

