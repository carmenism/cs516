/**
 * ASSIGNMENT 7 - HospitalStay
 * 
 * The HospitalStay class represents a stay at a hospital by a given
 * patient.  A stay can be private, meaning it costs slightly more.
 * 
 * @author   Carmen St. Jean
 * @date     November 5, 2009
 */

class HospitalStay
{
   // ----------------------------------------------------------------
   // instance variables ---------------------------------------------
   private Person patient;
   private Hospital hospital;
   private TimeFrame timeFrame;
   private boolean isPrivate;
   private int length;
   
   // ----------------------------------------------------------------
   // constructor ----------------------------------------------------
   
   /**
    * Creates a HospitalStay object from a patient, a location,
    * a timeframe of dates, and information about the type of stay
    * (private or not private).
    * 
    * @param      p    the patient staying in the hospital
    * @param      h    the hospital where the stay is located
    * @param      t    the times the patient stays here
    * @param      b    true if the stay is private, false if not
    */
   public HospitalStay( Person p, Hospital h, TimeFrame t, boolean b )
   {
      patient = p;
      hospital = h;
      timeFrame = t;
      isPrivate = b;
      length = timeFrame.length( );
   }
   
   // ----------------------------------------------------------------
   // accessor methods -----------------------------------------------
   
   /**
    * Returns the costs of this stay, taking into account the type of
    * room and the length of the stay.
    * 
    * @return       the cost of the room as a double
    */
   public double cost( )
   {
      if ( isPrivate )
      {
         return ( length * hospital.privateRoomCost( ) );
      }
      else
      {
         return ( length * hospital.roomCost( ) );
      }
   }
   
   // ----------------------------------------------------------------
  
   /**
    * Returns the name of the location of the stay as a String.
    * 
    * @return       the name of the location
    */
   public String location( )
   {
      return hospital.toString( );
   }
   
   // ----------------------------------------------------------------
  
   /**
    * Returns the name of the patient staying as a String.
    * 
    * @return       the name of the patient
    */  
   public String patient( )
   {
      return patient.toString( );
   }
}