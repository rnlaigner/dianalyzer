package br.pucrio.inf.les.ese.dianalyzer.worker.app;

import br.pucrio.inf.les.ese.dianalyzer.worker.logic.IProjectExecutor;
import br.pucrio.inf.les.ese.dianalyzer.worker.logic.InMemoryProjectExecutor;
import br.pucrio.inf.les.ese.dianalyzer.worker.logic.SimpleProjectExecutor;

public class App {

	public static void main(String[] args) {

		String projectPath = args[0];
		String outputPath = args[1];
		Boolean inMemory = Boolean.getBoolean(args[2]);
		
		IProjectExecutor projectExecutor = null;

		if ( inMemory ) {

			projectExecutor = new InMemoryProjectExecutor();
		}
		else {
			projectExecutor = new SimpleProjectExecutor();
		}

		try {
			projectExecutor.execute(projectPath,outputPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.exit(0);

	}

}
