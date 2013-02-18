/**
 * ASSIGNMENT 7 - Person
 * 
 * The Person class can be a patient at a hospital, either staying,
 * receiving medication, or having a surgery.  The person class can
 * determine time conflicts and report its total expenses.
 * 
 * A person may also be a physician.  See the Physician class.
 * 
 * @author   Carmen St. Jean
 * @date     November 5, 2009
 */

import java.text.*;
import java.util.*;

class Person implements Payable
{
   // ----------------------------------------------------------------
   // instance variables ---------------------------------------------
   protected String name;
   protected ArrayList< TimeFrame > schedule;
   protected ArrayList< HospitalStay > stays;
   protected ArrayList< Surgery > surgeries;
   protected ArrayList< Prescription > meds;
   
   // ----------------------------------------------------------------
   // constructor ----------------------------------------------------

   /**
    * Creates a person.
    */
   public Person( )
   {
      name = "";
      schedule = new ArrayList< TimeFrame >( );
      stays = new ArrayList< HospitalStay >( );
      surgeries = new ArrayList< Surgery >( );
      meds = new ArrayList< Prescription >( );
   }
   
   // ----------------------------------------------------------------
    
   /**
    * Creates a person from a name.
    * 
    * @param     n      the name of the person as a String
    */
   public Person( String n )
   {
      name = n;
      schedule = new ArrayList< TimeFrame >( );
      stays = new ArrayList< HospitalStay >( );
      surgeries = new ArrayList< Surgery >( );
      meds = new ArrayList< Prescription >( );
   }
   
   // ----------------------------------------------------------------
   // Payable methods ------------------------------------------------
   
   /**
    * Calculates and returns the total expenses of this person from
    * stays, surgeries, and prescriptions.
    * 
    * @return         the total expenses from this person as a double
    */
   public double totExpense( )
   {
      double expenses = 0;
      
      for ( int i = 0; i < stays.size( ); i++ )
         expenses += stays.get( i ).cost( );
      
      for ( int i = 0; i < surgeries.size( ); i++ )
         expenses += surgeries.get( i ).cost( );
      
      for ( int i = 0; i < meds.size( ); i++ )
         expenses += meds.get( i ).cost( );
      
      return expenses;
   }
  
   // ----------------------------------------------------------------
   
   /**
    * Calculates and returns the total expenses of this person from
    * one specific biller (either a physican or a hospital).
    * 
    * @param    b     the specific biller to check for
    * @return         the total expenses from this person
    */
   public double totExpense( Billable b )
   {
      double expenses = 0;
      
      if ( b instanceof Hospital )
      {
         String name = b.toString( );
         
         for ( int i = 0; i < stays.size( ); i++ )
            if ( stays.get( i ).location( ).equals( name ) )
               expenses += stays.get( i ).cost( );
                  
         for ( int i = 0; i < surgeries.size( ); i++ )
            if ( surgeries.get( i ).location( ).equals( name ) )
               expenses += surgeries.get( i ).hospitalIncome( );
         
         for ( int i = 0; i < meds.size( ); i++ )
            if ( meds.get( i ).location( ).equals( name ) )
               expenses += meds.get( i ).cost( );
      }
         
      if ( b instanceof Physician )
      {
         String name = b.toString( );
                  
         for ( int i = 0; i < surgeries.size( ); i++ )
            if ( surgeries.get( i ).surgeon( ).equals( name ) )
               expenses += surgeries.get( i ).surgeonIncome( );
      }
      
      return expenses;
   }
   
   // ----------------------------------------------------------------
   
   /**
    * Returns the name of the person as a String.
    * 
    * @return         the name of the person
    */
   public String toString( )
   {
      return name;
   }
   
   // ----------------------------------------------------------------
   // scheduler methods ----------------------------------------------
   
   /**
    * Checks to see if this person is busy during the given timeframe.
    * 
    * @param    t     the TimeFrame to check for
    * @return         true if the person is unoccupied
    */
   public boolean notBusy( TimeFrame t )
   {
      for ( int i = 0; i < schedule.size( ); i++ )
      {
         if ( t.coincides( schedule.get( i ) ) )
            return false;
      }
      
      return true;
   }
   
   // ----------------------------------------------------------------
   
   /**
    * Checks to see if this person is busy during the given timeframe.
    * However, it differs from the notBusy( TimeFrame t ) method in 
    * this checks only for previously scheduled surgeries, because
    * surgeries can take place independent of hospital stays.
    * 
    * @param    t     the TimeFrame to check for
    * @return         true if the person is unoccupied
    */
   public boolean notBusySurg( TimeFrame t )
   {
      for ( int i = 0; i < surgeries.size( ); i++ )
      {
         if ( t.coincides( surgeries.get( i ).time( ) ) )
            return false;
      }
      
      return true;
   }
   
   // ----------------------------------------------------------------
   // other methods --------------------------------------------------
   
   /**
    * Adds a hospital stay to the list of stays and adds the timeframe
    * of that stay for scheduling purposes.
    * 
    * @param     h     the hospital stay to be added
    * @param     t     the timeframe to be added
    */
   public void addStay( HospitalStay h, TimeFrame t )
   {
      stays.add( h );
      schedule.add( t );
   }
   
   // ----------------------------------------------------------------
   
   /**
    * Adds a prescription to the list of prescriptions.
    * 
    * @param     p      the prescription filled out to this person
    */
   public void addPrescription( Prescription p )
   {
      meds.add( p );
   }
   
   // ----------------------------------------------------------------
   
   /**
    * Adds a surgery this person was a patient for to the list of
    * surgeries.  Also adds the timeframe for scheduling purposes.
    * 
    * @param     h     the surgery performed on this person
    * @param     t     the timeframe of the surgery
    */
   public void hadSurgery( Surgery s, TimeFrame t )
   {
      surgeries.add( s );
      schedule.add( t );
   }
}