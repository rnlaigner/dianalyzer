package br.pucrio.inf.les.ese.dianalyzer.repextractor.logic;

import org.repodriller.scm.GitRemoteRepository;
import org.repodriller.scm.SCMRepository;

public class GitHubCloneExecutor {
	
	private String repositoryDirectoryToSave;
	private String gitRepositoryUrl;
	
	public GitHubCloneExecutor(final String repositoryDirectoryToSave, final String gitRepositoryUrl){
		this.repositoryDirectoryToSave = repositoryDirectoryToSave;
		this.gitRepositoryUrl = gitRepositoryUrl;
	}

	public void execute(){
		
		String projectName = gitRepositoryUrl
				.substring(gitRepositoryUrl.lastIndexOf("/"), gitRepositoryUrl.lastIndexOf("."));
        
		SCMRepository repo;
		String repoFullDir = buildPath( projectName );
		
		repo = GitRemoteRepository
				.hostedOn(gitRepositoryUrl)							// URL like: https://github.com/mauricioaniche/repodriller.git
				.inTempDir(repoFullDir)							// <Optional>
				.asBareRepos()								// <Optional> (1)
				.buildAsSCMRepository();
		
		//change to logger 4 j
		System.out.println(repo.getPath());
		
	}
	
	private String buildPath(String projectName){
		String pathConnector;
		if(System.getProperty("os.name").toLowerCase().indexOf("win") >= 0) {
			pathConnector = "\\";
		}
		else
		{
			pathConnector = "//";
		}
		return repositoryDirectoryToSave + pathConnector + projectName;
	}
	
}
