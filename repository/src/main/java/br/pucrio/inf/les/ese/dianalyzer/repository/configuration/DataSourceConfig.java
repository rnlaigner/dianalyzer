package br.pucrio.inf.les.ese.dianalyzer.repository.configuration;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseFactory;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Configuration
@PropertySource("classpath:/hibernate.properties")
public class DataSourceConfig {

    private static final String PROPERTY_NAME_DB_DRIVER_CLASS = "hibernate.connection.driver_class";
    private static final String PROPERTY_NAME_DB_URL = "hibernate.connection.url";
    private static final String PROPERTY_NAME_DB_USER = "hibernate.connection.username";
    private static final String PROPERTY_NAME_DB_PASSWORD = "hibernate.connection.password";

    @Autowired
    private Environment env;

    @Bean
    public DataSource dataSource() {

//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//
//        dataSource.setDriverClassName(env.getProperty(PROPERTY_NAME_DB_DRIVER_CLASS));
//        // dataSource.setUrl(env.getProperty(PROPERTY_NAME_DB_URL));
//        dataSource.setUsername(env.getProperty(PROPERTY_NAME_DB_USER));
//        dataSource.setPassword(env.getProperty(PROPERTY_NAME_DB_PASSWORD));

        EmbeddedDatabaseFactory factory = new EmbeddedDatabaseFactory();

        factory.setDatabaseType(EmbeddedDatabaseType.HSQL);

        EmbeddedDatabase dataSource = factory.getDatabase();

        return dataSource;

    }

}

