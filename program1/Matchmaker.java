/**
 * The Matchmaker class uses the "Stable Marriage" algorithm to sort
 * pairs of men and women into couples.  This method allows men to
 * make proposals each cycle based on preferences, while the women
 * accept their favorite among the received proposals of that cycle.
 * 
 * This class is to be called by the A1 if the input is found to be
 * correct.  After using the algorithm, it prints out the results
 * as well as the comment lines from the original input.
 * 
 * @author   Carmen St. Jean
 * @date     September 8, 2009
 */

// -------------------------------------------------------------------
// imports -----------------------------------------------------------
import java.util.*;
import java.io.*;
import java.lang.Integer;

public class Matchmaker
{
   // ----------------------------------------------------------------
   // instance variables ---------------------------------------------
   private ArrayList<Proposer> men;
   private ArrayList<Proposee> women;
   
   // ----------------------------------------------------------------
   // constructor ----------------------------------------------------
   
   /**
    * Creates an instance of Matchmaker provided a group of proposers
    * and proposers.
    * 
    * @param   m   an ArrayList of Proposers (men)
    * @param   w   an ArrayList of Proposees (women)
    * @param   t   a String of header comments from the original input
    */
   public Matchmaker( ArrayList<Proposer> m, ArrayList<Proposee> w,
                      String t)
   {
      men = m; women = w;
            
      while ( allMatched( ) == false )
      {         
         for ( int i = 0; i < men.size( ); i++ )
         {
            if ( men.get( i ).isMatched( ) == false)
            {
               int hisCrush = men.get( i ).getFirstChoice( );
               women.get( hisCrush-1 ).receiveProposal( men.get(i) );
            }
         }
         for ( int j = 0; j < women.size( ); j++ )
         {
            women.get( j ).processProposals( );
         }
      }
      printResults( t );
   }
   
   // ----------------------------------------------------------------
   // methods --------------------------------------------------------
   
   /**
    * Once the stable marriage algorithm has been calculated, this
    * method is called to print the final results as well as the
    * original header comments.
    * 
    * @param   t   a String of header comments from the original input
    */
   private void printResults( String t )
   {
      if ( t != null )
         System.out.println( "\n" + t );
      System.out.println( "Proposer Proposee");
      for ( int i = 0; i < men.size( ); i++ )
         System.out.printf( "%4d     %4d\n",
                            (i+1), men.get( i ).getFiancee( ) );
      System.out.print( "\n" );
   }
   
   // ----------------------------------------------------------------
   
   /**
    * Returns true if every single man's proposal has been accepted by
    * a woman.  If one or more of the men is still single, then
    * returns a false.
    * 
    * @return    the group status of the proposers
    */
   private boolean allMatched( )
   {
      boolean b = true;
      for ( int i = 0; i < men.size( ); i++ )
         b = b && men.get( i ).isMatched( );
      return b;
   }
}