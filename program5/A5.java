/**
 * ASSIGNMENT 5 - A5
 * 
 * This assignment is a simulation of a vending machine.  It reads in
 * commands from the system or a file and defines products, vending
 * machines, and slots.  From there, the customer may make purchases
 * or the machine may be refilled.  At the end, the results are
 * printed.
 * 
 * Note:  Based partially Professor Sparr's solution to A1
 * 
 * @author   Carmen St. Jean
 * @date     October 8, 2009
 */

import java.io.*;
import java.util.*;

public class A5
{   
  public static void main( String[ ] args )
  {
    Scanner sc = new Scanner( System.in ); 
    
    try
    {
      if ( args.length > 0 )
        sc = new Scanner( new File( args[ 0 ] ) );
    }
    catch ( FileNotFoundException e )
    {
      System.err.printf( "\nError opening file: %s \n\n", args[ 0 ] );
      System.exit( 1 );
    }

    Commands c = new Commands( );
    App a = new App( sc, c );
    a.processInput( );
    c.printResults( );
    
    return;
  }   
}