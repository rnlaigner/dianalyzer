package br.pucrio.inf.les.ese.dianalyzer.repextractor.logic;

import br.pucrio.inf.les.ese.dianalyzer.repextractor.exception.GitHubConnectionException;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;

import java.io.File;
import java.io.IOException;

public class GitHubConnectionInitializer {
	
	private GitHub github = null;
	
	public GitHub getGitHubConnection(String login, String password) throws GitHubConnectionException {
		
		if(github != null){
			return github;
		}
		
		GitHubBuilder self = new GitHubBuilder();
        self.withPassword(login,password);
        
        try{
        	github = self.build();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			throw new GitHubConnectionException("Erro ao se conectar ao GitHub com as credenciais providas no arquivo .github");
		}
        
        return github;
        
	}
	
	public GitHub getGitHubConnection() throws GitHubConnectionException
	{
		
		if(github != null){
			return github;
		}
		
		try 
		{			
			File homeDir = new File(System.getProperty("user.home"));
			
			//Default file = application.properties
	        File propertyFile = new File(homeDir, "application.properties");
			
			GitHubBuilder builder = GitHubBuilder.fromPropertyFile(propertyFile.getPath());
			
			github = builder.build();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			throw new GitHubConnectionException("Erro ao se conectar ao GitHub com as credenciais providas no arquivo .github");
		}
		
		return github;
	}

}
