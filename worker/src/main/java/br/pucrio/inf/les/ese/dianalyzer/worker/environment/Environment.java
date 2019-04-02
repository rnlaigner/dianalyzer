package br.pucrio.inf.les.ese.dianalyzer.worker.environment;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

// TODO colocar isso em um projeto common
public class Environment {
	
	private final Log log = LogFactory.getLog(Environment.class);

	private final Map<String,String> pathsToIgnore = new HashMap<String,String>();

	public Environment(){

		this.pathsToIgnore.put(".mvn","");

	}
	
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
		
		processFolderFiles(files, listOfFiles);

		return files;
		
	}

	private boolean isPathToIgnore(String path){

		for(Map.Entry<String,String> entry : pathsToIgnore.entrySet()){

			if( path.contains( entry.getKey() ) ){
				return true;
			}

		}
		return false;
	}

	private void processFolderFiles(List<String> files, File[] listOfFiles) throws Exception {
		
		for (final File fileEntry : listOfFiles) {
			
			//checa se fileEntry eh um folder. se for, chamada recursiva
			if( fileEntry.isDirectory() ){
				processFolderFiles(files,fileEntry.listFiles());
			}
			else {

				String path = fileEntry.toPath().toString();

				String extension = FilenameUtils.getExtension( path );

				if(extension.equals("java") && !isPathToIgnore(path) ) {


					try {

//					String fileName = fileEntry.getName();
//					String extension = fileName.substring(fileName.lastIndexOf("."));

						String content = new String(Files.readAllBytes(fileEntry.toPath()));
						files.add(content);

					} catch (Exception e) {
						log.error(e.getMessage());
						log.error(e.getStackTrace());
						//throw new Exception("There is a folder opened in the provided project path. Close it and run again the program.");
					}

				}

			}

	    }
		
	}
	

}
