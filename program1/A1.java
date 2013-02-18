/**
 * Assignment 1 reads an input file which has the data for two equal
 * groups of men and women to be put into "stable" pairings.  A
 * marriage is unstable if a man and a woman prefer someone besides
 * their own mate.
 * 
 * In the A1 class requires an input file with a number n meaning
 * there are n men and n women.  Then there must be 2*n lines of 
 * data, one for each person, ranking and individual's preferences.
 * In the case of bad input, the program will report the problem and
 * terminate unsuccessfully.
 * 
 * @author   Carmen St. Jean
 * @date     September 8, 2009
 */

// -------------------------------------------------------------------
// imports -----------------------------------------------------------
import java.util.*;
import java.io.*;
import java.lang.Integer;

public class A1
{
   // ----------------------------------------------------------------
   // instance variables ---------------------------------------------
   private Scanner s;
   private int number = -1;
   private boolean haveNumber = false;
   private ArrayList<Proposer> men;
   private ArrayList<Proposee> women;
   private ArrayList<String> rankingLines;
   private String headerText = "";
   private Matchmaker yente;
   
   // ----------------------------------------------------------------
   // constructor ----------------------------------------------------
   public A1( )
   {
      s = new Scanner( System.in );
      men = new ArrayList<Proposer>( );
      women = new ArrayList<Proposee>( );
      rankingLines = new ArrayList<String>( );
      inputProcessor( );
      yente = new Matchmaker(men, women, headerText);
   }
   
   // ----------------------------------------------------------------
   // methods --------------------------------------------------------
   
   /**
    * Processes the input from System.in for use in by the Proposer,
    * Proposee, and Matchmaker classes.  In the case of errors, it
    * reports them and exits the program unsuccessfully.
    */
   private void inputProcessor( )
   {
      while ( s.hasNext( ) && haveNumber == false )
      {
         String nextLine = s.nextLine( );       
         if ( nextLine.startsWith( "#" ) )
            headerText += nextLine + "\n";
         else if ( haveNumber == false )
         {
            Scanner sc = new Scanner( nextLine );
            number = sc.nextInt( );
            haveNumber = true;
         }        
      }
      if ( number > 0 )
      {
         int i = 0;
         while ( s.hasNext( ) && i < 2*number )
         {
            rankingLines.add( s.nextLine( ) );
            i++;
         }
         if ( i == 2*number )
         {
            sortPreferences( );
         }
         else
         {
            System.err.println( "Error: too few ranking lines" +
                                " provided." );
            System.exit( 1 );
         }
      }
      else
      {
         System.err.println( "Error: the number of candidates" +
                             " must be a non-negative integer." );
         System.exit( 1 );
      }
   }
   
   // ----------------------------------------------------------------
   
   /**
    * Sorts the information from the input that follows the comment
    * lines and the n value for the number of candidates.  Then it
    * creates the groups of men and women.
    */
   private void sortPreferences( )
   {
      for (int i = 0; i < 2*number; i++)
      {
         if ( i >= 0 && i < number )
         {
            men.add( new Proposer( i + 1, number,
                                   rankingLines.get( i ) ) );
         }
         else
         {
            women.add( new Proposee( i + 1 - number, number,
                                     rankingLines.get( i ) ) );
         }
      }
   }
   
   // ----------------------------------------------------------------
   // main method ----------------------------------------------------
   public static void main( String[] args )
   {
      A1 app = new A1( );
   }
}