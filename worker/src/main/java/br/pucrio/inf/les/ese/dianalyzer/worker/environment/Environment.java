package br.pucrio.inf.les.ese.dianalyzer.worker.environment;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

// TODO colocar isso em um projeto common
public class Environment {
	
	private final Log log = LogFactory.getLog(Environment.class);

	private final TreeSet<String> pathsToIgnore = new TreeSet<String>();

	private final TreeSet<String> filesToIgnore = new TreeSet<String>();

	public Environment(){

		this.pathsToIgnore.add(".mvn");
		this.filesToIgnore.add("package-info.java");
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
	
	public List<String> readFilesFromFolder(String folder, boolean buildPath) throws Exception{
		
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

	private boolean isFileToIgnore(String fileName){
		Iterator<String> iterator =  filesToIgnore.descendingIterator();

		while ( iterator.hasNext() ){

			String current = iterator.next();

			if( fileName.contentEquals( current ) ){
				return true;
			}

		}

		return false;
	}

	private boolean isPathToIgnore(String path){

		Iterator<String> iterator =  pathsToIgnore.descendingIterator();

		while ( iterator.hasNext() ){

			String current = iterator.next();

			if( path.contains( current ) ){
				return true;
			}

		}

		return false;
	}

	private void processFolderFiles(List<String> files, File[] listOfFiles) throws Exception {

		Integer index = 0;
		final Integer size = listOfFiles.length;

		for (final File fileEntry : listOfFiles) {

			index = index + 1;
			log.info("Processing file " +index+ " of "+size);
			
			//checa se fileEntry eh um folder. se for, chamada recursiva
			if( fileEntry.isDirectory() ){
				processFolderFiles(files,fileEntry.listFiles());
			}
			else {

				String path = fileEntry.toPath().toString();

				String extension = FilenameUtils.getExtension( path );

				String fileName = FilenameUtils.getName( path );

				if(extension.equals("java") && !isPathToIgnore(path) && !isFileToIgnore(fileName) ) {

					try {
						String content = new String(Files.readAllBytes(fileEntry.toPath()));
						files.add(content);

					} catch (Exception e) {
						log.error(e.getMessage());
						log.error(e.getStackTrace());
					}

				}

			}

	    }
		
	}
	

}
