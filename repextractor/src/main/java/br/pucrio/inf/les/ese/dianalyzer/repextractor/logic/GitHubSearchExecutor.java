package br.pucrio.inf.les.ese.dianalyzer.repextractor.logic;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHRepositorySearchBuilder;
import org.kohsuke.github.PagedSearchIterable;

import br.pucrio.inf.les.ese.dianalyzer.repextractor.model.RepositoryUrlSizeDTO;

public class GitHubSearchExecutor {

	private List<String> orderRepositoriesByIncreasingSize(PagedSearchIterable<GHRepository> repositoryList)
	{
		List<RepositoryUrlSizeDTO> repositoryURLsWithSize = new ArrayList<RepositoryUrlSizeDTO>();
		
		for(GHRepository repo : repositoryList )
		{
			RepositoryUrlSizeDTO pair = new RepositoryUrlSizeDTO(repo.getHttpTransportUrl(),repo.getSize());
			repositoryURLsWithSize.add(pair);
		}
		
		List<String> repositoryURLS = repositoryURLsWithSize
										.stream()
										.sorted(Comparator.comparing(RepositoryUrlSizeDTO::getRight))
										.map(repositoryPair -> repositoryPair.getLeft())
										.collect(Collectors.toList());
		
		return repositoryURLS;
	}
	
	public List<String> execute(GHRepositorySearchBuilder searchBuilder) 
	{		
		PagedSearchIterable<GHRepository> repositoryList = searchBuilder.list();
		
		List<String> repositoryURLS = new ArrayList<String>();
		
		if(repositoryList.getTotalCount() == 0)
		{
			return repositoryURLS;
		}
		
		repositoryURLS = orderRepositoriesByIncreasingSize(repositoryList);
		
		return repositoryURLS;
		
	}
	
}
