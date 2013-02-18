/**
 * ASSIGNMENT 7 - Medicine
 * 
 * The Medicine class represents a medicine which can be prescribed
 * to a patient by a hospital.  It can report its cost.
 * 
 * @author   Carmen St. Jean
 * @date     November 5, 2009
 */

class Medicine
{
   // ----------------------------------------------------------------
   // instance variables ---------------------------------------------
   private String name;
   private double cost;
   
   // ----------------------------------------------------------------
   // constructor ----------------------------------------------------
   
   /**
    * Creates a Medicine object from a name and a cost.
    * 
    * @param     n      the name of the medicine as a String
    * @param     c      the cost of the medicine as a double
    */
   public Medicine( String n, double c )
   {
      name = n;
      cost = c;
   }
   
   // ----------------------------------------------------------------
   // accessor methods -----------------------------------------------
   
   /**
    * Returns the cost of the medicine.
    * 
    * @return           the cost of the medicine as a double
    */
   public double cost( )
   {
      return cost;
   }
}