package br.pucrio.inf.les.ese.dianalyzer.repextractor.logic;

import br.pucrio.inf.les.ese.dianalyzer.repextractor.exception.GitHubQueryException;
import br.pucrio.inf.les.ese.dianalyzer.repextractor.model.GitHubQueryProperties;
import org.kohsuke.github.GHRepositorySearchBuilder;
import org.kohsuke.github.GitHub;

public class GitHubSearchBuilder {
	
	public GHRepositorySearchBuilder getGitHubQuery(
			GitHubQueryProperties queryProperties, 
			GitHub gitHubConnection) throws GitHubQueryException {
		
		GHRepositorySearchBuilder searchBuilder = gitHubConnection.searchRepositories();
		
		for(String q : queryProperties.getQ())
		{
			searchBuilder.q(q);
		}
		
		searchBuilder.language(queryProperties.getLanguage());
		
		return searchBuilder;
	}

}
