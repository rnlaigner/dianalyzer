package br.pucrio.inf.les.ese.dianalyzer.worker.app;

import br.pucrio.inf.les.ese.dianalyzer.repextractor.exception.GitHubConnectionException;
import br.pucrio.inf.les.ese.dianalyzer.repextractor.exception.GitHubQueryException;
import br.pucrio.inf.les.ese.dianalyzer.repextractor.logic.GitHubCloneExecutor;
import br.pucrio.inf.les.ese.dianalyzer.repextractor.logic.GitHubConnectionInitializer;
import br.pucrio.inf.les.ese.dianalyzer.repextractor.logic.GitHubSearchBuilder;
import br.pucrio.inf.les.ese.dianalyzer.repextractor.logic.GitHubSearchExecutor;
import br.pucrio.inf.les.ese.dianalyzer.repextractor.model.GitHubQueryProperties;
import br.pucrio.inf.les.ese.dianalyzer.worker.logic.IProjectExecutor;
import br.pucrio.inf.les.ese.dianalyzer.worker.logic.InMemoryProjectExecutor;
import br.pucrio.inf.les.ese.dianalyzer.worker.logic.SimpleProjectExecutor;
import org.kohsuke.github.GHRepositorySearchBuilder;
import org.kohsuke.github.GitHub;

import java.util.List;

public class App {

	public static void main(String[] args) throws GitHubConnectionException, GitHubQueryException {

		if(args.length < 3){
			System.out.println("Expected params are: " +
					"1 - <option> " +
					"if option == 'mining' then" +
					"2 - <output_directory> " +
					"3 to n - <query_string> " +
					"else" +
					"2 - <project_directory> " +
					"3 - <output_directory> " +
					"4 - <in_memory> (optional)");
			return;
		}

		if(!args[0].contentEquals("mining")){

			String projectPath = args[1];
			String outputPath = args[2];

			Boolean inMemory = true;

			if(args.length > 3){
				inMemory = !args[3].contentEquals("false");
			}

			IProjectExecutor projectExecutor;

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

		} else {

			GitHubQueryProperties properties = new GitHubQueryProperties();

			properties.setLanguage("java");

			for(int i = 2; i < args.length; i++){
				properties.addQ(args[i]);
			}

			GitHubConnectionInitializer connection = new GitHubConnectionInitializer();

			GitHub github = connection.getGitHubConnection();

			GitHubSearchBuilder builder = new GitHubSearchBuilder();

			GHRepositorySearchBuilder searchBuilder = builder.getGitHubQuery( properties, github );

			GitHubSearchExecutor executor = new GitHubSearchExecutor();

			List<String> repositories = executor.execute( searchBuilder );

			for(String repository : repositories){

				GitHubCloneExecutor cloneExecutor = new GitHubCloneExecutor(args[1],repository);

				cloneExecutor.execute();

			}

		}
		
		System.exit(0);

	}

}
