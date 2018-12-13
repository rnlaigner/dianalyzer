package br.pucrio.inf.les.ese.dianalyzer.worker.report;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface IWorkbookCreator {

	void create(Report report, String outputPath) throws IOException, FileNotFoundException;
	
}
