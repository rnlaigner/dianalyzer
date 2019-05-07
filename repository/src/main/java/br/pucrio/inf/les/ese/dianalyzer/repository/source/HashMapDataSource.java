package br.pucrio.inf.les.ese.dianalyzer.repository.source;

import br.pucrio.inf.les.ese.dianalyzer.repository.model.AssociatedTuple;
import br.pucrio.inf.les.ese.dianalyzer.repository.model.Tuple;

import java.util.*;

public class HashMapDataSource implements IBeanDataSource<String, Tuple, AssociatedTuple> {

    private final Map<String, TreeSet<AssociatedTuple>> associatedBeanHashMap;

    private final Map<String, Tuple> beanHashMap;

    public HashMapDataSource(){
        this.associatedBeanHashMap = new HashMap<String, TreeSet<AssociatedTuple>>();
        this.beanHashMap = new HashMap<String, Tuple>();
    }

    @Override
    public Iterator findAll(){
        return beanHashMap.entrySet().iterator();
    }

    @Override
    public Tuple find(String name) {
        return this.beanHashMap.get(name);
    }

    @Override
    public Tuple insert(Tuple tuple) {
        return beanHashMap.put(tuple.type, tuple);
    }

    @Override
    public void delete(String name) {
        this.beanHashMap.remove(name);
    }

    @Override
    public Collection<AssociatedTuple> findAssociatedBeans(String beanName) {
        return associatedBeanHashMap.get(beanName);
    }

    @Override
    public Tuple getBeanByName(String name) {
        return this.beanHashMap.get(name);
    }

    @Override
    public boolean isAssociatedBean(String beanName, String associatedBeanName) {

        TreeSet<AssociatedTuple> treeSet = associatedBeanHashMap.get(beanName);

        Iterator<AssociatedTuple> it = treeSet.iterator();

        while(it.hasNext() ){

            AssociatedTuple associatedTuple = it.next();

            if(associatedTuple.type.contentEquals(associatedBeanName)){
                return true;
            }


        }

        return false;

    }

    @Override
    public boolean insertAssociatedBean(AssociatedTuple associatedTuple) {
        TreeSet<AssociatedTuple> treeSet = associatedBeanHashMap.get( associatedTuple.father );

        if(treeSet == null){

            treeSet = new TreeSet<AssociatedTuple>();
            associatedBeanHashMap.put(associatedTuple.father,treeSet);

        }

        return treeSet.add( associatedTuple );
    }
}
