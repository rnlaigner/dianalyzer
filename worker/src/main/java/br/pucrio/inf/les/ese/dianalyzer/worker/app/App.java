package br.pucrio.inf.les.ese.dianalyzer.worker.app;


import br.pucrio.inf.les.ese.dianalyzer.worker.logic.IProjectExecutor;
import br.pucrio.inf.les.ese.dianalyzer.worker.logic.InMemoryProjectExecutor;

// @PropertySource("classpath:hibernate.properties")
public class App {

	public static void main(String[] args) {

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
