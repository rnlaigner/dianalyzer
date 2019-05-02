package br.pucrio.inf.les.ese.dianalyzer.worker.config;

import br.pucrio.inf.les.ese.dianalyzer.worker.app.App;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public App mainRunner() {
       return new App();
    }
}
