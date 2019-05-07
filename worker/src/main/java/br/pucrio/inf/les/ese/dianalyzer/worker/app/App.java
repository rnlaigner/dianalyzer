package br.pucrio.inf.les.ese.dianalyzer.worker.app;

import br.pucrio.inf.les.ese.dianalyzer.worker.logic.IProjectExecutor;
import br.pucrio.inf.les.ese.dianalyzer.worker.logic.InMemoryProjectExecutor;
import br.pucrio.inf.les.ese.dianalyzer.worker.logic.SimpleProjectExecutor;

public class App {

	public static void main(String[] args) {

		if(args.length < 2){
			System.out.println("Expected params are: <project_directory> <output_directory> <in_memory_enabled>");
			System.out.println("<in_memory_enabled> is true by default");
			return;
		}

		String projectPath = args[0];
		String outputPath = args[1];

		Boolean inMemory = true;

		if(args.length > 2){
			inMemory = args[2].contentEquals("false");
		}

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
