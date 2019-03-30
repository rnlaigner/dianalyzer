package br.pucrio.inf.les.ese.dianalyzer.worker.app;

import br.pucrio.inf.les.ese.dianalyzer.worker.logic.IProjectExecutor;
import br.pucrio.inf.les.ese.dianalyzer.worker.logic.ProjectExecutor;
import br.pucrio.inf.les.ese.dianalyzer.worker.logic.SimpleProjectExecutor;

public class App {

	public static void main(String[] args) {
		
		String projectPath;
		//projectPath = "C:\\Users\\Henrique\\workspace\\agilefant\\webapp\\src\\main\\java\\fi\\hut\\soberit\\agilefant";
		//projectPath = projectPath + "\\business\\impl";

		//TODO read from args

		projectPath = "C:\\Users\\Henrique\\workspace\\libreplan";
		
		String outputPath = "C:\\Users\\Henrique";
		
		IProjectExecutor projectExecutor = null;

		projectExecutor = new SimpleProjectExecutor();
		//new ProjectExecutor();
		
		try {
			projectExecutor.execute(projectPath,outputPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.exit(0);

	}

}
