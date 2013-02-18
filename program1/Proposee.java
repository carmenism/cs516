/**
 * The Proposer class is created for a given female with her ranked
 * male preferences.  A woman has the power to either accept or
 * reject male proposals.
 * 
 * If a woman accepts more than one proposal, then she selects her
 * favorite among them and rejects the rest.  Her choice carries on
 * to the next cycle, but may not remain if she receives a proposal
 * from another man she prefers more.
 * 
 * @author   Carmen St. Jean
 * @date     September 8, 2009
 */

// -------------------------------------------------------------------
// imports -----------------------------------------------------------
import java.util.*;
import java.io.*;
import java.lang.Integer;

public class Proposee
{
   // ----------------------------------------------------------------
   // instance variables ---------------------------------------------
   private int idNumber = 0, totalPeople = 0;
   private Proposer currentAcceptance = null;
   private ArrayList<Integer> pref;
   private ArrayList<Proposer> suitors;
   
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
   public Proposee( int id, int total, String s )
   {
      idNumber = id;
      totalPeople = total;
      pref = new ArrayList<Integer>( );
      suitors = new ArrayList<Proposer>( );
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
                  if ( x == pref.get( j ).intValue( ) )
                     duplicate = true;
               if ( duplicate != true )
                  pref.add( new Integer( x ) );
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
    * Receives a proposal and stores it for later consideration at
    * the end of the cycle.
    * 
    * @param  newSuitor  a new Proposer whose first perference is this
    */
   public void receiveProposal( Proposer newSuitor )
   {
      suitors.add( newSuitor );
   }
   
   // ----------------------------------------------------------------
   
   /**
    * Compares all received proposals of that round.  If there is only
    * one, then she accepts him.  If there are more than one, she
    * accepts her favorite and rejects the rest.  If there are none,
    * this method does nothing.
    */
   public void processProposals( )
   {
      if ( suitors.size( ) == 1 )
      {
         currentAcceptance = suitors.get( 0 );
         suitors.get( 0 ).beAccepted( idNumber );
      }
      if ( suitors.size( ) > 1 )
      {
         if ( currentAcceptance == null )
            currentAcceptance = suitors.get( 0 );
         for ( int i = 0; i < suitors.size( ); i++ )
         {
            Proposer newMan = suitors.get( i );
            int indexOfCurrent = 0, indexOfNewMan = 0;
            for ( int j = 0; j < pref.size( ); j++ )
            {
               if ( currentAcceptance.getNumber( )
                    == pref.get( j ).intValue( ) )
               {
                  indexOfCurrent = j;
               }
               if ( newMan.getNumber( ) == pref.get( j ).intValue( ) )
               {
                  indexOfNewMan = j;
               }
            }
            if ( indexOfNewMan < indexOfCurrent )
            {
               currentAcceptance = newMan;
            }
            suitors.get( i ).beRejected( idNumber );
         }
         currentAcceptance.beAccepted( idNumber );
         suitors.clear( );
         suitors.add( currentAcceptance );
      }
   }
}