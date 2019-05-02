//package br.pucrio.inf.les.ese.dianalyzer.repository.source;
//
//import java.util.*;
//
//public class HashMapDataSource implements IDataSource<String, Object> {
//
//    private final HashMap hashMap;
//
//    public HashMapDataSource(){
//        this.hashMap = new HashMap<String,Object>();
//    }
//
//    @Override
//    public Iterator<Map.Entry> findAll(){
//        return hashMap.entrySet().iterator();
//    }
//
//    @Override
//    public Collection<Object> find(String key) {
//        List list = new ArrayList<Object>();
//        list.add( this.hashMap.get(key));
//        return list;
//    }
//
//    @Override
//    public Object insert(String key, Object element) {
//        this.hashMap.put(key,element);
//        return element;
//    }
//
//    @Override
//    public Object delete(String key) {
//        Object object = this.find(key);
//        this.hashMap.remove(key);
//        return object;
//    }
//}
