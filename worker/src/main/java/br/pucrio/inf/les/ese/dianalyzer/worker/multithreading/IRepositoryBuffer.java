package br.pucrio.inf.les.ese.dianalyzer.worker.multithreading;

// Fig. 26.11: Buffer.java
// Buffer interface specifies methods called by Producer and Consumer.
public interface IRepositoryBuffer
{
   // place int value into Buffer
   public void set( String value ) throws InterruptedException; 

   // obtain int value from Buffer
   public String get() throws InterruptedException; 
   
   public boolean bufferIsEmpty();
   
} // end interface Buffer


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