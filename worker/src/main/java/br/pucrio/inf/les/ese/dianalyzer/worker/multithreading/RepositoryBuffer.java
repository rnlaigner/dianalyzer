package br.pucrio.inf.les.ese.dianalyzer.worker.multithreading;

//Fig. 26.16: BlockingBuffer.java
// Creating a synchronized buffer using the ArrayBlockingQueue class.
import java.util.concurrent.ArrayBlockingQueue;

public class RepositoryBuffer implements IRepositoryBuffer
{
   private final ArrayBlockingQueue<String> buffer; // shared buffer

   public RepositoryBuffer( int size )
   {
      buffer = new ArrayBlockingQueue<String>( size );
   } // end BlockingBuffer constructor
   
   public boolean bufferIsEmpty() {
	   return buffer.size() > 0;
   }
   
   // place value into buffer
   public void set( String value ) throws InterruptedException
   {
      buffer.put( value ); // place value in buffer
      System.out.printf( "%s%s\t%s%d\n", "Producer writes ", value,
         "Buffer cells occupied: ", buffer.size() );
   } // end method set

   // return value from buffer
   public String get() throws InterruptedException
   {
      String readValue = buffer.take(); // remove value from buffer
      System.out.printf( "%s %s\t%s%d\n", "Consumer reads ", 
         readValue, "Buffer cells occupied: ", buffer.size() );

      return readValue;
   } // end method get
} // end class BlockingBuffer


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