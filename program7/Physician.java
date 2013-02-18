/**
 * ASSIGNMENT 7 - Physician
 * 
 * The Physician class represents a person who is a physician.
 * Physicians receive income from surgeries they perform.  Skilled
 * surgeons can charge more with a multiplier, a value between 0.01
 * and 0.99 (i.e. a multiplier of 0.50 applied to a surgery which
 * is normally $100 dollars means the surgery costs $100 + $50).
 * 
 * Since a physician is a person, this means a physician may be a
 * patient and have expenses too.
 * 
 * @author   Carmen St. Jean
 * @date     November 5, 2009
 */

import java.text.*;
import java.util.*;

class Physician extends Person implements Billable
{
   // ----------------------------------------------------------------
   // instance variables ---------------------------------------------
   private ArrayList< Surgery > surgeriesDone;
   private double multiplier;
   
   // ----------------------------------------------------------------
   // constructor ----------------------------------------------------
   
   /**
    * Creates a physician.
    */
   public Physician( )
   {
      super( );
      multiplier = 0;
      surgeriesDone = new ArrayList< Surgery >( );
   }
   
   // ----------------------------------------------------------------
   
   /**
    * Creates a physician from a name and multiplier.
    * 
    * @param     n      the name of the person as a String
    * @param     m      the multiplier of this physician as a double
    */
   public Physician( String n, double m )
   {
      super( n );
      multiplier = m;
      surgeriesDone = new ArrayList< Surgery >( );
   }
   
   // ----------------------------------------------------------------
   // Billable methods -----------------------------------------------
   
   /**
    * Calculates and returns the total income of this physician from
    * surgeries.
    * 
    * @return         the total income from this physician
    */
   public double totIncome( )
   {
      double income = 0;
      
      for ( int i = 0; i < surgeriesDone.size( ); i++ )
         income += surgeriesDone.get( i ).surgeonIncome( );
      
      return income;
   }

   // ----------------------------------------------------------------
   
   /**
    * Calculates and returns the total income of this physician from
    * one specific payer.
    * 
    * @param    p     the specific payer to check for
    * @return         the total income from this physician
    */
   public double totIncome( Payable p )
   {
      double income = 0;
      String name = p.toString( );
      
      for ( int i = 0; i < surgeriesDone.size( ); i++ )
         if ( surgeriesDone.get( i ).patient( ).equals( name ) )
            income += surgeriesDone.get( i ).surgeonIncome( );

      return income;
   }
   
   // ----------------------------------------------------------------
   
   /**
    * Returns the name of the physician as a String.
    * 
    * @return         the name of the physician
    */
   public String toString( )
   {
      return name;
   }
   
   // ----------------------------------------------------------------
   // accessor methods -----------------------------------------------
   
   /**
    * Returns the multiplier of this physician as a double.
    * 
    * @return       the multiplier of this physician.
    */
   public double multiplier( )
   {
      return multiplier;
   }
   
   // ----------------------------------------------------------------
   // other methods --------------------------------------------------
   
   /**
    * Records the physician did a surgery during the given timeframe.
    * 
    * @param    s      the surgery performed by this physician
    * @param    t      the timeframe during which the surgery occured
    */
   public void didSurgery( Surgery s, TimeFrame t )
   {
      surgeriesDone.add( s );
      super.schedule.add( t );
   }
}