package br.pucrio.inf.les.ese.dianalyzer.worker.app;


import br.pucrio.inf.les.ese.dianalyzer.repository.configuration.ContextWrapper;
import br.pucrio.inf.les.ese.dianalyzer.repository.configuration.DataSourceConfig;
import br.pucrio.inf.les.ese.dianalyzer.repository.configuration.JpaConfig;
import br.pucrio.inf.les.ese.dianalyzer.repository.repository.AssociatedTupleRepository;
import br.pucrio.inf.les.ese.dianalyzer.repository.repository.TupleRepository;
import br.pucrio.inf.les.ese.dianalyzer.worker.config.AppConfig;
import br.pucrio.inf.les.ese.dianalyzer.worker.logic.IProjectExecutor;
import br.pucrio.inf.les.ese.dianalyzer.worker.logic.InMemoryProjectExecutor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

// @PropertySource("classpath:hibernate.properties")
public class App {

	public void run() {
		System.out.println( "running from bean" );
	}

	public static void main(String[] args) {

		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(AppConfig.class);
		ctx.register(ContextWrapper.class);
		ctx.register(DataSourceConfig.class);
		ctx.register(JpaConfig.class);
		ctx.register(TupleRepository.class);
		ctx.register(AssociatedTupleRepository.class);
		ctx.refresh();

		App runner = (App) ctx.getBean("mainRunner");
		runner.run();
		
		String projectPath;
		//projectPath = "C:\\Users\\Henrique\\workspace\\agilefant\\webapp\\src\\main\\java\\fi\\hut\\soberit\\agilefant";
		//projectPath = projectPath + "\\business\\impl";

		//TODO read from args

		projectPath = "C:\\Users\\Henrique\\workspace\\BroadleafCommerce";
		
		String outputPath = "C:\\Users\\Henrique";
		
		IProjectExecutor projectExecutor = null;

		// projectExecutor = new SimpleProjectExecutor();
		projectExecutor =  new InMemoryProjectExecutor();
		
		try {
			projectExecutor.execute(projectPath,outputPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.exit(0);

	}

}
