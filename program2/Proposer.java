/**
 * The Proposer class is created for a given proposer with ranked
 * preferences.  A proposer has no knowledge of the proposers; he/she
 * will only know if a proposal attempt was accepted or rejected.
 * 
 * Upon rejection, a proposer no longer considers this proposee for
 * future proposals.  Acceptance is only certain if no more proposers
 * "outrank" the proposer in the proposee's preferences.
 * 
 * Note:  This class is based on my own Proposer class of A1.
 * 
 * @author   Carmen St. Jean
 * @date     September 16, 2009
 */

// -------------------------------------------------------------------
// imports -----------------------------------------------------------
import java.util.*;
import java.io.*;
import java.lang.Integer;

public class Proposer implements MarriageProposer
{
   // ----------------------------------------------------------------
   // instance variables ---------------------------------------------
   private boolean matched = false;
   protected ArrayList<Integer> pref;
   
   // ----------------------------------------------------------------
   // constructor ----------------------------------------------------
   
   /**
    * Creates a new instance of the Proposee class.
    * 
    * @param  p      an ArrayList of preferences for opposite gender
    */
   public Proposer( ArrayList<Integer> p )
   {
      pref = p;
   }
      
   // ----------------------------------------------------------------
   // MarriageProposer methods ---------------------------------------

   /**
    * Determines whether or not this proposer is available to make a 
    * proposal or not this round.  Returns a true if the proposer
    * has been matched or false if the proposer is single.
    * 
    * @return        status of the proposer
    */
   public boolean propose( )  
   {
      return matched;
   }
   
   // ----------------------------------------------------------------
   
   /**
    * Upon being dumped, the proposer changes his status and removes
    * the the first proposee from the preference list.
    */
   public void dump( )
   {
      pref.remove( 0 );
      matched = false;
   }
   
   // ----------------------------------------------------------------
   // Other methods --------------------------------------------------

   /**
    * Returns the identification number of the proposer's current top
    * preference from the list of proposees.
    * 
    * @return     the identification number of the favored proposee
    */
   public int getFirstChoice( )
   {
      return pref.get( 0 ).intValue();
   }
   
   // ----------------------------------------------------------------
   
   /**
    * Sets the match status to true.
    */
   public void accept( )
   {
      matched = true;
   }
}