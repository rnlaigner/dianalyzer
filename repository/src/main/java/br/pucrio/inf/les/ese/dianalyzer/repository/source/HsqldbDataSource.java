package br.pucrio.inf.les.ese.dianalyzer.repository.source;

import br.pucrio.inf.les.ese.dianalyzer.repository.model.AbstractPersistent;
import br.pucrio.inf.les.ese.dianalyzer.repository.model.AssociatedTuple;
import br.pucrio.inf.les.ese.dianalyzer.repository.model.Tuple;
import br.pucrio.inf.les.ese.dianalyzer.repository.repository.AssociatedTupleRepository;
import br.pucrio.inf.les.ese.dianalyzer.repository.repository.TupleRepository;

import java.util.Collection;
import java.util.Iterator;

public class HsqldbDataSource implements
        IDataSource<Long, AbstractPersistent>,
        IAssociatedDataSource<String,AssociatedTuple> {

    private final TupleRepository tupleRepository;

    private final AssociatedTupleRepository associatedTupleRepository;

    public HsqldbDataSource(TupleRepository tupleRepository, AssociatedTupleRepository associatedTupleRepository){
        this.tupleRepository = tupleRepository;
        this.associatedTupleRepository = associatedTupleRepository;
    }

    @Override
    public Iterator findAll() {
        return tupleRepository.findAll().iterator();
    }

    @Override
    public AbstractPersistent find(Long key) {
        return tupleRepository.findById(key).get();
    }

    @Override
    public AbstractPersistent insert( AbstractPersistent element) {
        if (element instanceof Tuple){
            return tupleRepository.save((Tuple) element);
        }
        return associatedTupleRepository.save((AssociatedTuple) element);
    }

    @Override
    public void delete(Long key) {
        tupleRepository.deleteById(key);
    }

    @Override
    public Collection<AssociatedTuple> findAssociated(String key) {
        return associatedTupleRepository.findAssociated(key);
    }
}
