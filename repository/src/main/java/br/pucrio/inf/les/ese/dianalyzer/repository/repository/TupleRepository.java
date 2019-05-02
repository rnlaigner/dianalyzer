package br.pucrio.inf.les.ese.dianalyzer.repository.repository;

import br.pucrio.inf.les.ese.dianalyzer.repository.model.Tuple;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("tupleRepository")
public interface TupleRepository extends CrudRepository<Tuple, Long> {

}

