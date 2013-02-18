/**
 * ASSIGNMENT 7 - Prescription
 * 
 * The Prescription class represents a prescription filled out to a
 * patient from a hospital with a prescription number.  The 
 * prescription also has a number of days which the prescription is 
 * taken by the patient.  It can report its cost, which is
 * dependent on the number of days the patient takes it.
 * 
 * @author   Carmen St. Jean
 * @date     November 5, 2009
 */

class Prescription
{
   // ----------------------------------------------------------------
   // instance variables ---------------------------------------------
   private Medicine medicine;
   private Person patient;
   private Hospital hospital;
   private int number;
   private int days;
   
   // ----------------------------------------------------------------
   // constructor ----------------------------------------------------
   
   /**
    * Creates a prescription from a medicine, a patient, a hospital,
    * a prescription number, and the number of days the patient
    * received this medicine.
    * 
    * @param     m     the medicine of the prescription
    * @param     p     the patient receiving the prescription
    * @param     h     the hospital filling out the prescription
    * @param     n     the prescription number
    * @param     d     the number of days the medicine was taken
    */
   public Prescription( Medicine m, Person p, Hospital h,
                        int n, int d )
   {
      medicine = m;
      patient = p;
      hospital = h;
      number = n;
      days = d;
   }
   
   // ----------------------------------------------------------------
   // accessor methods -----------------------------------------------
   
   /**
    * Returns the cost of the medicine as a double, taking into the 
    * number of days the medicine was taken into account.
    * 
    * @return      the cost of the medicine as a double
    */
   public double cost( )
   {
      return ( days * medicine.cost( ) );
   }

   // ----------------------------------------------------------------
   
   /**
    * Returns the cost of the medicine as a double, taking into the 
    * number of days the medicine was taken into account.
    * 
    * @return      the cost of the medicine as a double
    */
   public String location( )
   {
      return hospital.toString( );
   }
   
   // ----------------------------------------------------------------
   
   /**
    * Returns the name of the patient who has the prescription.
    * 
    * @return      the name of the patient as a string
    */
   public String patient( )
   {
      return patient.toString( );
   }
}