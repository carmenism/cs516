/**
 * ASSIGNMENT 7 - TimeFrame
 * 
 * The Hospital class represents a hospital.  It keeps track of
 * hospital stays, surgeries, and prescriptions that take place at
 * this hospital.  It can also determine if the operating room is
 * available for surgeries.  It can calculate its income.
 * 
 * @author   Carmen St. Jean
 * @date     November 5, 2009
 */

import java.text.*;
import java.util.*;

class Hospital implements Billable
{ 
   // ----------------------------------------------------------------
   // instance variables ---------------------------------------------
   private String name;
   private double oprRoom, room, privateRoom;
   private HashMap< String, Medicine > medicines;
   private ArrayList< TimeFrame > schedule;
   protected ArrayList< HospitalStay > stays;
   protected ArrayList< Surgery > surgeries;
   protected ArrayList< Prescription > meds;
   
   // ----------------------------------------------------------------
   // constructor ----------------------------------------------------
   
   /**
    * Creates a new Hospital object from a name and the various costs
    * of the rooms in this hospital.
    */
   public Hospital( )
   {
      name = "";
      oprRoom = 0;
      room = 0;
      privateRoom = 0;
      medicines = new HashMap< String, Medicine >( );
      schedule = new ArrayList< TimeFrame >( );
      stays = new ArrayList< HospitalStay >( );
      surgeries = new ArrayList< Surgery >( );
      meds = new ArrayList< Prescription >( );
   }
   
   // ----------------------------------------------------------------
   
   /**
    * Creates a new Hospital object from a name and the various costs
    * of the rooms in this hospital.
    * 
    * @param     n      name of the hospital
    * @param     o      cost of the operating room
    * @param     r      cost of a patient room
    * @param     p      cost of a private patient room
    */
   public Hospital( String n, double o, double r, double p )
   {
      name = n;
      oprRoom = o;
      room = r;
      privateRoom = p;
      medicines = new HashMap< String, Medicine >( );
      schedule = new ArrayList< TimeFrame >( );
      stays = new ArrayList< HospitalStay >( );
      surgeries = new ArrayList< Surgery >( );
      meds = new ArrayList< Prescription >( );
   }
   
   // ----------------------------------------------------------------
   // Billable methods -----------------------------------------------
   
   /**
    * Calculates and returns the total income of this hospital from
    * stays, surgeries, and prescriptions.
    * 
    * @return         the total income from this hospital
    */
   public double totIncome( )
   {
      double income = 0;
      
      for ( int i = 0; i < stays.size( ); i++ )
         income += stays.get( i ).cost( );
      
      for ( int i = 0; i < surgeries.size( ); i++ )
         income += oprRoom;
      
      for ( int i = 0; i < meds.size( ); i++ )
         income += meds.get( i ).cost( );
      
      return income;
   }
   
   // ----------------------------------------------------------------
   
   /**
    * Calculates and returns the total income of this hospital from
    * one specific payer.
    * 
    * @param    p     the specific payer to check for
    * @return         the total income from this hospital
    */
   public double totIncome( Payable p )
   {
      double income = 0;
      String name = p.toString( );
         
      for ( int i = 0; i < stays.size( ); i++ )
         if ( stays.get( i ).patient( ).equals( name ) )
            income += stays.get( i ).cost( );
      
      for ( int i = 0; i < surgeries.size( ); i++ )
         if ( surgeries.get( i ).patient( ).equals( name ) )
            income += oprRoom;
      
      for ( int i = 0; i < meds.size( ); i++ )
         if ( meds.get( i ).patient( ).equals( name ) )
            income += meds.get( i ).cost( );
      
      return income;
   }
   
   // ----------------------------------------------------------------
   
   /**
    * Returns the name of the hospital as a String.
    * 
    * @return         the name of the hospital
    */
   public String toString( )
   {
      return name;
   }
   
   // ----------------------------------------------------------------
   // scheduler methods ----------------------------------------------
   
   /**
    * Checks to see if this hospital's operating room is busy during
    * the given time frame.
    * 
    * @param    t     the TimeFrame to check for
    * @return         true if the OR is unoccupied
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
   // medicine methods -----------------------------------------------
   
   /**
    * First checks to see if the hospital already has a medicine of
    * the given name.  If not, then it creates and stores a medicine
    * object.
    * 
    * @param    name     the name of the medicine as a string
    * @param    cost     the cost of the medicine as a double
    * @return            false if a medicine of this name exists
    */
   public boolean addMedicine( String name, double cost )
   {
      if ( medicines.containsKey( name ) )
      {
         return false;
      }
      else
      {
         Medicine m = new Medicine( name, cost );
         medicines.put( name, m );
         return true;
      }
   }
   
   // ----------------------------------------------------------------
   
   /**
    * Checks to see if the hospital contains medicine with the
    * given name.
    * 
    * @param    name     the name of the medicine as a string
    * @return            true if the hospital has this medicine
    */
   public boolean containsMedicine( String name )
   {
      return medicines.containsKey( name );
   }
   
   // ----------------------------------------------------------------
   
   /**
    * Returns a Medicine object matching the given name.
    * 
    * @param    name     the name of the medicine as a string
    * @return            a Medicine object matching the name
    */
   public Medicine getMedicine( String name )
   {
      return medicines.get( name );
   }
  
   // ----------------------------------------------------------------
   // surgery methods ------------------------------------------------
   
   /**
    * Adds a surgery and adds the time of the surgery to the
    * hospital's schedule.
    * 
    * @param    s        a surgery taking place in this hospital
    * @param    t        the timeframe of this surgery
    */
   public void addSurgery( Surgery s, TimeFrame t )
   {
      surgeries.add( s );
      schedule.add( t );
   }
   
   // ----------------------------------------------------------------
   // hospital stay methods ------------------------------------------
   
   /**
    * Adds a hospital stay to the collection of stays.
    * 
    * @param    h        a hospital stay in this hospital
    */
   public void addStay( HospitalStay h )
   {
      stays.add( h );
   }
   
   // ----------------------------------------------------------------
   // prescription methods -------------------------------------------
   
   /**
    * Adds a prescription to the list of prescriptions filled out
    * by this hospital.
    * 
    * @param    p        a prescription filled out by this hospital
    */
   public void addPrescription( Prescription p )
   {
      meds.add( p );
   }
   
   // ----------------------------------------------------------------
   // cost getters ---------------------------------------------------
   
   /**
    * Returns the cost of this hospital's operating room.
    * 
    * @return        cost of the operating room as a double
    */
   public double oprRoomCost( )
   {
      return oprRoom;
   }
   
   // ----------------------------------------------------------------
   
   /**
    * Returns the cost of this hospital's patient rooms.
    * 
    * @return        cost of a patient room as a double.
    */
   public double roomCost( )
   {
      return room;
   }
   
   // ----------------------------------------------------------------
   
   /**
    * Returns the cost of this hospital's private patient rooms.
    * 
    * @return        cost of a private patient room as a double.
    */
   public double privateRoomCost( )
   {
      return privateRoom;
   }
}   