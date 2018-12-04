package br.pucrio.inf.les.ese.dianalyzer.diast_test.env;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Environment {
	
	public String buildPath(String value){
		
		String pathConnector;
		if(System.getProperty("os.name").toLowerCase().indexOf("win") >= 0) {
			pathConnector = "\\";
			value = value.replace("//","\\");
		}
		else {
			pathConnector = "//";
			value = value.replace("\\","//");
		}
		
		String filepath = System.getProperty("user.dir") + pathConnector + value;

		return filepath;
		
	}
	
	public List<String> readFilesFromFolder(String folder, boolean buildPath) throws IOException{
		
		if(buildPath){
			folder = buildPath(folder);
		}
		
		final File fileFolder = new File(folder);
		
		List<String> files = new ArrayList<String>();
		
		for (final File fileEntry : fileFolder.listFiles()) {
			
			String content = "";
			
			content = new String ( Files.readAllBytes( fileEntry.toPath() ) );

            files.add(content);

	    }

		return files;
		
	}
	

}
