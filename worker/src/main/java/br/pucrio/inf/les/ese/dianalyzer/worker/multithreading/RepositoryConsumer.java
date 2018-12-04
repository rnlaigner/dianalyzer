package br.pucrio.inf.les.ese.dianalyzer.worker.multithreading;

// Consumer.java
// Consumer's run method loops ten times reading a value from buffer.
import java.util.Random;

import org.repodriller.scm.GitRemoteRepository;

public class RepositoryConsumer implements Runnable 
{ 
   private final static Random generator = new Random();
   private final IRepositoryBuffer sharedLocation; // reference to shared object
   private String repoDir;

   // constructor
   public RepositoryConsumer( IRepositoryBuffer shared, String repoDir )
   {
      sharedLocation = shared;
      this.repoDir = repoDir;
   } // end Consumer constructor

   // read sharedLocation's value 10 times and sum the values
   public void run()
   {
      String gitUrl = "";

      while (!sharedLocation.bufferIsEmpty())
      {
         // sleep 0 to 3 seconds, read value from buffer and add to sum
         try 
         {
            Thread.sleep( generator.nextInt( 3000 ) );    
            gitUrl = sharedLocation.get();
            
            String projectName = gitUrl.substring(gitUrl.lastIndexOf("/"), gitUrl.lastIndexOf("."));
            
    		//SCMRepository repo;
    		
    		//TODO mount accordingly to the OS
    		String repoFullDir = repoDir + projectName;
    		
    		//repo = 
    				GitRemoteRepository
    				.hostedOn(gitUrl)							// URL like: https://github.com/mauricioaniche/repodriller.git
    				.inTempDir(repoFullDir)							// <Optional>
    				.asBareRepos()								// <Optional> (1)
    				.buildAsSCMRepository();
    		
    		//libera para analise a partir daqui
    		
         } // end try
         catch ( InterruptedException exception ) 
         {
            exception.printStackTrace();
         } // end catch
      } // end for

      System.out.printf( "\n%s %s\n%s\n", 
         "Consumer read values totaling", gitUrl, "Terminating Consumer" );
   } // end method run
} // end class Consumer



/**************************************************************************
 * (C) Copyright 1992-2010 by Deitel & Associates, Inc. and               *
 * Pearson Education, Inc. All Rights Reserved.                           *
 *                                                                        *
 * DISCLAIMER: The authors and publisher of this book have used their     *
 * best efforts in preparing the book. These efforts include the          *
 * development, research, and testing of the theories and programs        *
 * to determine their effectiveness. The authors and publisher make       *
 * no warranty of any kind, expressed or implied, with regard to these    *
 * programs or to the documentation contained in these books. The authors *
 * and publisher shall not be liable in any event for incidental or       *
 * consequential damages in connection with, or arising out of, the       *
 * furnishing, performance, or use of these programs.                     *
 *************************************************************************/