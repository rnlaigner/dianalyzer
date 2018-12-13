package br.pucrio.inf.les.ese.dianalyzer.worker.report;

import java.util.ArrayList;
import java.util.List;

public class Report {
	
	private String project;
	
	private List<String> headers;
	
	private List<List<String>> lines;
	
	public Report(){}

	public Report(String project, List<String> headers) {
		this.project = project;
		this.headers = headers;
		this.lines = new ArrayList<List<String>>();
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public List<String> getHeaders() {
		return headers;
	}

	public void setHeaders(List<String> headers) {
		this.headers = headers;
	}

	public List<List<String>> getLines() {
		return lines;
	}

	public void setLines(List<List<String>> lines) {
		this.lines = lines;
	}
	
	public void addLine(List<String> line){
		this.lines.add(line);
	}

}
