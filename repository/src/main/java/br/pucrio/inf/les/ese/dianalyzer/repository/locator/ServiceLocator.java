package br.pucrio.inf.les.ese.dianalyzer.repository.locator;

import br.pucrio.inf.les.ese.dianalyzer.repository.configuration.ContextWrapper;
import br.pucrio.inf.les.ese.dianalyzer.repository.repository.AssociatedTupleRepository;
import br.pucrio.inf.les.ese.dianalyzer.repository.repository.TupleRepository;
import br.pucrio.inf.les.ese.dianalyzer.repository.source.HsqldbIBeanDataSource;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

// irony
public class ServiceLocator {

    private static ServiceLocator INSTANCE = null;

    private static Map<String,Class<?>> context;

    private static Map<String,Object> instances;

    private ServiceLocator(){
        this.context = new HashMap<>();
        this.instances = new HashMap<>();
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

        // try spring first
        if(beanName.contentEquals("IDataSource")) {

            TupleRepository tupleRepository = ContextWrapper.getContext().getBean("tupleRepository", TupleRepository.class);
            AssociatedTupleRepository associatedTupleRepository = ContextWrapper.getContext().getBean("associatedTupleRepository", AssociatedTupleRepository.class);

            if (tupleRepository != null) {

                HsqldbIBeanDataSource dataSource = new HsqldbIBeanDataSource(tupleRepository, associatedTupleRepository);

                instances.put(beanName, dataSource);

                return dataSource;
            }
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
