/**
 * The Proposer class is created for a given male with his ranked
 * female preferences.  He has no knowledge of the women; he will only
 * know whether he is accepted or rejected after attempting to
 * propose to his favorite choice.
 * 
 * Upon rejection, a man no longer considers this woman in this
 * proposal.  Acceptance is only certain if he remains her favorite
 * among other male suitors.
 * 
 * @author   Carmen St. Jean
 * @date     September 8, 2009
 */

// -------------------------------------------------------------------
// imports -----------------------------------------------------------
import java.util.*;
import java.io.*;
import java.lang.Integer;

public class Proposer
{
   // ----------------------------------------------------------------
   // instance variables ---------------------------------------------
   private int idNumber = 0, totalPeople = 0, acceptedWoman = 0;
   private boolean matched = false;
   protected ArrayList<Integer> pref;
   
   // ----------------------------------------------------------------
   // constructor ----------------------------------------------------
   
   /**
    * Creates a new instance of the Proposee class.  Initially stores
    * information but also calls the lineProcessor method.
    * 
    * @param  id     the identification of this person (from 1...n)
    * @param  total  the total number of pairs eligible (n)
    * @param  s      a String of preferences for opposite gender
    */
   public Proposer( int id, int total, String s )
   {
      idNumber = id;
      totalPeople = total;
      pref = new ArrayList<Integer>( );
      lineProcessor( s );
   }
      
   // ----------------------------------------------------------------
   // methods --------------------------------------------------------
   
   /**
    * Processes the information from a String to figure out the
    * person's preferences for marriage or catches errors.
    * 
    * @param  line    a String of preferences for opposite gender
    */
   private void lineProcessor( String line )
   {      
      Scanner s = new Scanner( line ); int i = 0;
      while ( i < totalPeople && s.hasNext( ) )
      {
         int x = s.nextInt();
         if ( x > 0 && x <= totalPeople )
         {
            if ( i == 0 )
               pref.add( new Integer( x ) );
            else
            {
               boolean duplicate = false;
               for ( int j = 0; j < pref.size( ); j++ )
               {
                  if ( x == pref.get( j ).intValue( ) )
                     duplicate = true;
               }
               if ( duplicate != true )
               {
                  pref.add( new Integer( x ) );
               }
               else
               {
                  System.err.println( "Error: the ranking for " +
                                      "this candidate contains a " +
                                      "duplicate" );
                  System.exit( 1 );
               }
            }
         }
         else
         {
            System.err.println( "Error: ranking number excedes " +
                                "bounds" );
            System.exit( 1 );
         }
         i++;
      }
      if ( i < totalPeople )
      {
         System.err.println( "Error: not enough ranking numbers" );
         System.exit( 1 );
      }
   }
   
   // ----------------------------------------------------------------
   
   /**
    * Checks to see if this Proposer has been matched (accepted by
    * a woman).  Returns true if he has been; false if still single.
    * 
    * @return     the status of proposer
    */
   public boolean isMatched( )
   {
      return matched;
   }
   
   // ----------------------------------------------------------------
   
   /**
    * Returns the number of the man's accepted female proposee.  If he
    * is still single, this will return 0.
    * 
    * @return     the identification number of his fiancee
    */
   public int getFiancee( )
   {
      return acceptedWoman;
   }
   
   // ----------------------------------------------------------------
   
   /**
    * Returns the identification number of the proposer.
    * 
    * @return     the identification number of the proposer
    */
   public int getNumber( )
   {
      return idNumber;
   }
  
   // ----------------------------------------------------------------
   
   /**
    * Returns the identification number of the proposer's current top
    * preference of women.
    * 
    * @return     the identification number of the favored woman
    */
   public int getFirstChoice( )
   {
      return pref.get( 0 ).intValue( );
   }
   
   // ----------------------------------------------------------------
   
   /**
    * The proposer is rejected by the woman in the parameters and
    * thus removes her from his list of preferences so he may never
    * propose to her again.
    * 
    * @param  woman   the identification number of the rejecting woman
    */
   public void beRejected( int woman )
   {
      for ( int i = 0; i < pref.size( ); i++ )
      {
         if ( pref.get( i ).intValue( ) == woman )
         {
            pref.remove( i );
         }
      }
      matched = false;
   }
   
   // ----------------------------------------------------------------
   
   /**
    * The proposer is accepted by the woman in the parameters and
    * thus saves her name and changes his status to matched.
    * 
    * @param  woman   the identification number of the accepting woman
    */
   public void beAccepted( int woman )
   {
      acceptedWoman = woman;
      matched = true;
      return;
   }
}