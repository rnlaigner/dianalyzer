package br.pucrio.inf.les.ese.dianalyzer.repository.locator;

import br.pucrio.inf.les.ese.dianalyzer.repository.source.HashMapDataSource;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class ServiceLocator {

    private static ServiceLocator INSTANCE = null;

    private static Map<String,Class<?>> context;

    private static Map<String,Object> instances;

    private ServiceLocator(){
        this.context = new HashMap<>();
        this.instances = new HashMap<>();

        // Configuration
        this.context.put("IDataSource", HashMapDataSource.class);
    }

    public static synchronized ServiceLocator getInstance(){
        if(INSTANCE == null){
            INSTANCE = new ServiceLocator();
        }
        return INSTANCE;
    }

    private static Class<?> getBean(String beanName){
        return context.get(beanName);
    }

    public static Object getBeanInstance(String beanName){

        Object instance = instances.get(beanName);

        if(instance != null){
            return instance;
        }

        Constructor<?> constructor = null;
        try {
            constructor = getBean(beanName).getConstructor();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        Object object = null;
        try {
            object = constructor.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        instances.put(beanName,object);

        return object;

    }

}
