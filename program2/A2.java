/**
 * Assignment 2 reads an input file which has the data for eligible
 * candidates to be put into "stable" pairings.  A marriage is
 * unstable if a man and a woman prefer someone besides their own
 * mate.
 * 
 * In the A2 class requires an input file with a number n meaning
 * there are n proposer and n proposees.  Then there must be 2*n
 * lines of data, one for each person, ranking an individual's
 * preferences.  In the case of bad input, the program will report
 * the problem and terminate unsuccessfully.
 * 
 * Note:  Based partially on my own class A1 and partially one
 *        Professor Sparr's solution.
 * 
 * @author   Carmen St. Jean
 * @date     September 16, 2009
 */

// -------------------------------------------------------------------
// imports -----------------------------------------------------------
import java.util.*;
import java.io.*;

public class A2
{
   // ----------------------------------------------------------------
   // main method ----------------------------------------------------
   public static void main( String[] args )
   {
      Scanner sc = new Scanner( System.in ); 
      
      try
      {
         if ( args.length > 0 )
            sc = new Scanner( new File( args[ 0 ] ) );
      }
      catch ( FileNotFoundException e )
      {
         System.err.printf( "\nError opening file: %s\n\n", args[0] );
         System.exit( 1 );
      }
      
      Matchmaker m = new Matchmaker( sc );
      
      if ( m.initialize( ) )  
         m.doMatches( );
      else
         System.out.printf( "\nExecution halted.\n\n" );
   }
}