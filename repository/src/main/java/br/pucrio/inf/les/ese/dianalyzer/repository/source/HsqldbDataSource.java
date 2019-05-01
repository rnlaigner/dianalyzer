package br.pucrio.inf.les.ese.dianalyzer.repository.source;

import java.util.*;

// TODO https://git.tecgraf.puc-rio.br/pec/georisco/tree/georisco-ccpd-init/georisco-ccpd/src/main

public class HsqldbDataSource extends ConfigurableDataSource<String, Object> {

    private final HashMap hashMap;

    public HsqldbDataSource(){
        this.hashMap = new HashMap<String,Object>();
    }

    @Override
    public Collection<Map.Entry> findAll(){
        return hashMap.entrySet();
    }

    @Override
    public Collection<Object> find(String key) {
        List list = new ArrayList<Object>();
        list.add( this.hashMap.get(key));
        return list;
    }

    @Override
    public Object insert(String key, Object element) {
        this.hashMap.put(key,element);
        return element;
    }

    @Override
    public Object delete(String key) {
        Object object = this.find(key);
        this.hashMap.remove(key);
        return object;
    }

    @Override
    protected void start() {
//        String url = "jdbc:h2:mem:";
//
//        try (Connection con = DriverManager.getConnection(url);
//             Statement st = con.createStatement();
//             ResultSet rs = st.executeQuery("SELECT 1+1")) {
//
//            if (rs.next()) {
//
//                System.out.println(rs.getInt(1));
//            }
//
//        } catch (SQLException ex) {
//
//            Logger lgr = Logger.getLogger(HsqldbDataSource.class.getName());
//            lgr.log(Level.SEVERE, ex.getMessage(), ex);
//        }
    }

    @Override
    protected void finish() {

    }
}
