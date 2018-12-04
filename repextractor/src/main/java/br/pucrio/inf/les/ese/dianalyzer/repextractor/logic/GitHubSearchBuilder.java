package br.pucrio.inf.les.ese.dianalyzer.repextractor.logic;

import org.kohsuke.github.GHRepositorySearchBuilder;
import org.kohsuke.github.GitHub;

import br.pucrio.inf.les.ese.dianalyzer.repextractor.model.GitHubQueryProperties;
import br.pucrio.inf.les.ese.dianalyzer.repextractor.exception.GitHubQueryException;

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
