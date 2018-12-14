package br.pucrio.inf.les.ese.dianalyzer.worker.environment;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

//TODO colocar isso em um projeto common
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
	
	public List<String> readFilesFromFolder(String folder, boolean buildPath) throws IOException, Exception{
		
		if(buildPath){
			folder = buildPath(folder);
		}
		
		final File fileFolder = new File(folder);
		
		List<String> files = new ArrayList<String>();
		
		File[] listOfFiles = fileFolder.listFiles();
		
		if(listOfFiles == null){
			throw new Exception("The path is wrong or there is no files in the path provided.");
		}
		
		for (final File fileEntry : listOfFiles) {
			
			String content = "";
			
			content = new String ( Files.readAllBytes( fileEntry.toPath() ) );

            files.add(content);

	    }

		return files;
		
	}
	

}
