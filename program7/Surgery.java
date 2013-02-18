/**
 * ASSIGNMENT 7 - Surgery
 * 
 * The Surgery represents a surgery for a patient performed by a
 * surgeon at a hospital.  The class can calculate the cost of this
 * surgery, determined in part by the skill level of the surgeon.
 * 
 * @author   Carmen St. Jean
 * @date     November 5, 2009
 */

class Surgery
{
   // ----------------------------------------------------------------
   // instance variables ---------------------------------------------
   private Hospital hospital;
   private Procedure procedure;
   private Physician surgeon;
   private Person patient;
   private TimeFrame timeFrame;
   private double cost;
   
   // ----------------------------------------------------------------
   // constructor ----------------------------------------------------
   
   /**
    * Creates a Surgery object from a hospital, procedure, physician,
    * patient, and a timeframe.
    * 
    * @param      h     the hospital where the surgery takes place
    * @param      p     the procedure of the surgery in question
    * @param      s     the surgeon performing the surgery
    * @param      pt    the patient receiving the surgery
    * @param      t     the timeframe the surgery occurs
    */
   public Surgery( Hospital h, Procedure p,
                   Physician s, Person pt, TimeFrame t )
   {
      hospital = h;
      procedure = p;
      surgeon = s;
      patient = pt;
      timeFrame = t;
      
      cost = hospital.oprRoomCost( );
      cost += procedure.cost( );
      cost += ( surgeon.multiplier( ) * procedure.cost( ) );
   }
   
   // ----------------------------------------------------------------
   // accessor methods -----------------------------------------------
   
   /**
    * Returns the cost of the surgery.
    * 
    * @return         the cost of the surgery as a double
    */
   public double cost( )
   {
      return cost;
   }
   
   // ----------------------------------------------------------------
   
   /**
    * Returns the income made by the hospital for this surgery.
    * 
    * @return         the hospital's income of the surgery as a double
    */
   public double hospitalIncome( )
   {
      return hospital.oprRoomCost( );
   }
   
   // ----------------------------------------------------------------
   
   /**
    * Returns the income made by the surgeon for this surgery.
    * 
    * @return         the surgeon's income of the surgery as a double
    */   
   public double surgeonIncome( )
   {
      double income = procedure.cost( );
      income += ( surgeon.multiplier( ) * procedure.cost( ) );
      return income;
   }
   
   // ----------------------------------------------------------------
   
   /**
    * Returns the timeframe during which the surgery takes place.
    * 
    * @return       the timeframe during which the surgery takes place
    */   
   public TimeFrame time( )
   {
      return timeFrame;
   }
   
   // ----------------------------------------------------------------
   
   /**
    * Returns the location where the surgery takes place.
    * 
    * @return       the name of the hospital as a String
    */   
   public String location( )
   {
      return hospital.toString( );
   }
   
   // ----------------------------------------------------------------
   
   /**
    * Returns the name of the surgeon who performed the surgery.
    * 
    * @return       the name of the surgeon as a String
    */   
   public String surgeon( )
   {
      return surgeon.toString( );
   }
   
   // ----------------------------------------------------------------
   
   /**
    * Returns the name of the patient who had the surgery.
    * 
    * @return       the name of the patient as a String
    */   
   public String patient( )
   {
      return patient.toString( );
   }
}