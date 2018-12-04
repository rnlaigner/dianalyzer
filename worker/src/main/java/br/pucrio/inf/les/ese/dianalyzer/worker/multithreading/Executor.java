package br.pucrio.inf.les.ese.dianalyzer.worker.multithreading;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Executor implements IExecutor {

	private List<String> repositoryURLS;
	
	private int BUFFER_SIZE;
	
	private int CONSUMER_SIZE;
	
	private String DIRECTORY_TO_SAVE;
	
	public Executor(List<String> repositoryURLS, int bufferSize, int consumerSize, String directoryToSave) {
		this.repositoryURLS = repositoryURLS;
		this.BUFFER_SIZE = bufferSize;
		this.CONSUMER_SIZE = consumerSize;
		this.DIRECTORY_TO_SAVE = directoryToSave;
	}
	
	public void execute() throws ExecutionException
	{
		// create new thread pool 
	    ExecutorService application = Executors.newCachedThreadPool();
	    
		// create BlockingBuffer to store ints
	    RepositoryBuffer sharedLocation = new RepositoryBuffer(BUFFER_SIZE);
	
	    for(int i = 0; i < repositoryURLS.size(); i++) 
	    { 
	    	try 
	    	{
	    		sharedLocation.set(repositoryURLS.get(i));
	    	}
	    	catch (InterruptedException e) 
	    	{
	    		e.printStackTrace();
	    		throw new ExecutionException(e.getMessage());
	    	} 
	    }
	    
	    for(int i = 0; i < CONSUMER_SIZE; i++) 
	    { 
	    	application.execute( new RepositoryConsumer( sharedLocation, DIRECTORY_TO_SAVE ) );
	    }
	
	    application.shutdown();
	}
	
}
