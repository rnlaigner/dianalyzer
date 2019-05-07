package br.pucrio.inf.les.ese.dianalyzer.worker.multithreading;

import java.util.List;
import java.util.Random;

// Producer.java
// Producer's run method stores the values 1 to 10 in buffer.

public class RepositoryProducer implements Runnable 
{
   private final static Random generator = new Random();
   private final RepositoryBuffer sharedLocation; // reference to shared object
   private final List<String> repositoryList;

   // constructor
   public RepositoryProducer( RepositoryBuffer shared, List<String> repositoryList )
   {
      sharedLocation = shared;
      
      this.repositoryList = repositoryList;
      
   } // end Producer constructor

   public void run()
   {

      for ( String repo : repositoryList ) 
      {
         try // sleep 0 to 3 seconds, then place value in Buffer
         {
            Thread.sleep( generator.nextInt( 3000 ) ); // sleep thread   
            sharedLocation.set( repo ); // set value in buffer
         } // end try
         catch ( InterruptedException exception ) 
         {
            exception.printStackTrace();
         } // end catch
      } // end for

      System.out.println( 
         "Producer done producing\nTerminating Producer" );
   } // end method run
} // end class Producer


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