/**
 * The Matchmaker class uses the "Stable Marriage" algorithm to sort
 * pairs of proposers and proposees into stable couples.  This method
 * allows men proposers to propose each cycle based on preferences,
 * while the proposees accept their favorite among the received
 * proposals of that cycle.
 * 
 * This class is to be called by the A2 to intialize the data from
 * the input.  If the data is correct, then it uses the algorithm
 * and prints out the results.
 * 
 * Note:  This class is based partially on my own Matchmaker class
 *        from A1 and partially on Professor Sparr's solution to A1.
 * 
 * @author   Carmen St. Jean
 * @date     September 16, 2009
 */

// -------------------------------------------------------------------
// imports -----------------------------------------------------------
import java.util.*;
import java.io.*;
import java.lang.Integer;

public class Matchmaker implements Marriage
{
   // ----------------------------------------------------------------
   // instance variables ---------------------------------------------
   private ArrayList<Proposer> proposers;
   private ArrayList<Proposee> proposees;
   private ArrayList<Integer> tempPref;
   private Scanner scanner;
   private String headerText = "";
   private int nbrEligible;

   // ----------------------------------------------------------------
   // constructor ----------------------------------------------------
   
   /**
    * Creates an instance of Matchmaker and only instantiates the
    * lists of proposers and proposees for now.
    * 
    * @param   s    a Scanner reading the input
    */
   public Matchmaker( Scanner s )
   {
      scanner = s;
      proposers = new ArrayList<Proposer>( );
      proposees = new ArrayList<Proposee>( );
   }
   
   // ----------------------------------------------------------------
   // Marriage interface methods -------------------------------------
   
   /**
    * Reads in the data while checking for and reporting errors.  If
    * a single error is found, returns a false.  Otherwise, returns
    * a true for valid data.
    * 
    * @return       the status of the data
    */
   public boolean initialize( )
   {
     String     line;
     String[ ]  token;
     boolean    validInput = true;
 
     tempPref = new ArrayList<Integer>( );
     line = scanner.nextLine( ).trim( );
     
     while ( line.charAt( 0 ) == '#' )
     {
        System.out.printf( "%s\n", line );
        line = scanner.nextLine( ).trim( );    
     } 
     
     token = line.split( " " );
     
     if ( token.length <= 0 )
     { 
        System.out.printf( "\nError: scanner empty.\n" );
        return false;
     }
     
     try { nbrEligible = Integer.parseInt( token[ 0 ] ); }
     catch ( NumberFormatException e ) 
     { 
        System.out.printf( "\nError: invalid integer for number " +
                           "of candidates.\n" );
        return false;
     }
     
     if ( nbrEligible < 0 ) 
     {
        System.out.printf( "\nError: improper candidate count: %d\n",
                           nbrEligible );
        return false;
     }
     
     if ( nbrEligible == 0 )
     {
        System.out.printf( "\nError: no candidates this cycle.\n" );
        return false;
     }
     
     for ( int n = 0; n < 2*nbrEligible; n++ )
     { 
        tempPref = new ArrayList<Integer>();
        if ( !scanner.hasNextLine( ) )
        {
           System.out.println( "\nError: insufficient preference " +
                               "values." );
           return false;
        }
        
        line = scanner.nextLine( ).trim( );
        token = line.split( "\\s+" );
        
        if ( token.length < nbrEligible )
        {    
           System.out.printf( "\nError:too few preference values %s\n", line );
           validInput = false;
        }
        else
        {
           boolean validRankingLine = lineChecker( line );
           
           if ( validRankingLine )
           {
              if ( n >= 0 && n < nbrEligible )
                 proposers.add(new Proposer( tempPref ));
              else
                 proposees.add(new Proposee( tempPref, proposers ));
           }
           else
              validInput = false;
        }
     }
     return validInput;
   }
      
   // ----------------------------------------------------------------
   
   /**
    * The actual implementation of the Stable Marriage algorithm. The
    * program will only reach this method if the input was determined
    * to be valid.
    */
   public void doMatches( )
   {
      while ( isStable( ) == false )
      {
         for ( int i = 0; i < proposers.size( ); i++ )
         {
            if ( proposers.get( i ).propose( ) == false)
            {
               int hisCrush = proposers.get( i ).getFirstChoice( );
               proposers.get( i ).propose( );
               proposees.get( hisCrush-1 ).proposal( i + 1 );
            }
         }
      }
      printResults( );
   }
   
   // ----------------------------------------------------------------
   
   /**
    * Returns true if every single proposee has been matched
    * successfully to a proposee and false if even one proposer is
    * not matched.
    * 
    * @return    the group status of the proposers
    */
   public boolean isStable( )
   {
      boolean b = true;
      for ( int i = 0; i < proposers.size( ); i++ )
      {
         b = b && proposers.get( i ).propose( );
      }
      return b;
   }
   
   // ----------------------------------------------------------------
   // Other methods --------------------------------------------------
   
   /**
    * Checks a ranking line to see if it is valid.  If it contains a
    * duplicate or an improper value, then it reports the error(s)
    * and returns a false.  Returns true for a valid line.
    * 
    * @param    line    a String of preferences to be check
    * @return           the status of the given line
    */
   private boolean lineChecker( String line )
   {
      Scanner s = new Scanner( line );
      int i = 0;
      while ( i < nbrEligible && s.hasNext( ) )
      {
         int x = s.nextInt( );
         if ( x > 0 && x <= nbrEligible )
         {
            if ( i == 0 )
            {
               tempPref.add( new Integer( x ) );
            }
            else
            {
               boolean notDuplicate = true;
               for ( int j = 0; j < tempPref.size( ); j++ )
               {
                  if ( x == tempPref.get( j ).intValue( ) )
                     notDuplicate = false;
               }
               
               if ( notDuplicate )
               {
                  tempPref.add( new Integer( x ) );
               }
               else
               {
                  System.err.println( "\nError: a ranking line " +
                                      "contains a duplicate." );
                  return false;
               }
            }
         }
         else
         {
            System.err.println( "\nError: a ranking line contains " +
                                "an invalid number." );
            return false;
         }
         i++;
      }
      return true;
   }
   
   // ----------------------------------------------------------------
   
   /**
    * After successfully matching all pairs, prints the results
    * to the system.
    */
   private void printResults( )
   {
      System.out.println( "\nProposer Proposee");
      for ( int i = 0; i < proposers.size( ); i++ )
      {
         System.out.printf( "%4d     %4d\n", ( i+1 ),
                            proposers.get( i ).getFirstChoice( ) );
      }
      System.out.print( "\n" );
   }
}