/**
 * The Proposee class is created for a given proposee with ranked
 * preferences and a list of all proposers.  A proposee accepts
 * proposals and determines whether or not to accept them.
 * 
 * If a proposee accepts more than one proposal, then he/she selects
 * his/her favorite among them and rejects the rest.  This choice is
 * carried on to the next cycle, but may not remain if the proposee
 * receives a proposal he/she prefers more.
 * 
 * Note:  This class is based on my own Proposee class of A1.
 * 
 * @author   Carmen St. Jean
 * @date     September 16, 2009
 */

// -------------------------------------------------------------------
// imports -----------------------------------------------------------
import java.util.*;
import java.io.*;
import java.lang.Integer;

public class Proposee implements MarriageProposee
{
   // ----------------------------------------------------------------
   // instance variables ---------------------------------------------
   private int currentSuitor = 0;
   private ArrayList<Integer> pref;
   private ArrayList<Proposer> allSuitors;
   
   // ----------------------------------------------------------------
   // constructor ----------------------------------------------------

   /**
    * Creates a new instance of the Proposee class.
    * 
    * @param  p      an ArrayList of preferences for opposite gender
    * @param  a      an ArrayList of all the Proposers
    */
   public Proposee( ArrayList<Integer> p, ArrayList<Proposer> a )
   {
      pref = p;
      allSuitors = a;
   }
   
   // ----------------------------------------------------------------
   // MarriageProposee methods ---------------------------------------
   
   /**
    * Receives a proposal and determines whether or not to accept
    * the proposal.
    * 
    * @param  newSuitor   a new proposer seeking this proposee
    */
   public void proposal( int newSuitor )
   {
      if (currentSuitor == 0)
      {
         currentSuitor = newSuitor;
         allSuitors.get(newSuitor - 1).accept( );
      }
      else
      {
         int indexOfCurrent = 0, indexOfNew = 0;
         for ( int j = 0; j < pref.size( ); j++ )
         {
            if ( currentSuitor == pref.get( j ).intValue( ) )
            {
               indexOfCurrent = j;
            }
            if ( newSuitor == pref.get( j ).intValue( ) )
            {
               indexOfNew = j;
            }
         }
         if ( indexOfNew < indexOfCurrent )
         {
            allSuitors.get(newSuitor - 1).accept( );
            allSuitors.get(currentSuitor - 1).dump( );
            currentSuitor = newSuitor;
         }
         else
         {
            allSuitors.get(newSuitor - 1).dump( );
         }
      }
   }
   
   // ----------------------------------------------------------------

}