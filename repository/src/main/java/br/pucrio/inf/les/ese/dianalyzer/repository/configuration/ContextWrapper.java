package br.pucrio.inf.les.ese.dianalyzer.repository.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class ContextWrapper {

    private static ApplicationContext context;

    @Autowired
    public ContextWrapper(ApplicationContext ac) {
        context = ac;
    }

    public static ApplicationContext getContext() {
        return context;
    }

}
