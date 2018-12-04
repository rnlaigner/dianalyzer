package br.pucrio.inf.les.ese.dianalyzer.repextractor.model;

import java.util.List;

public class GitHubQueryProperties 
{

	private String language;
	
	private List<String> q;

	public String getLanguage() 
	{
		return language;
	}

	public void setLanguage(String language) 
	{
		this.language = language;
	}

	public List<String> getQ() {
		return q;
	}

	public void setQ(List<String> q) {
		this.q = q;
	}
	
}
