/**
 * ASSIGNMENT 7 - Procedure
 * 
 * The Procedure class consists of a name for the procedure and a
 * cost.  It can report its cost.
 * 
 * @author   Carmen St. Jean
 * @date     November 5, 2009
 */

class Procedure
{
   // ----------------------------------------------------------------
   // instance variables ---------------------------------------------
   private String name;
   private double cost;
   
   // ----------------------------------------------------------------
   // constructor ----------------------------------------------------
   
   /**
    * Creates a Procedure object from a name and and a cost.
    * 
    * @param     n     the name of the procedure as a string
    * @param     c     the cost of the procedure as a double
    */
   public Procedure( String n, double c )
   {
      name = n;
      cost = c;
   }
   
   // ----------------------------------------------------------------
   // accessor methods -----------------------------------------------
   
   /**
    * Returns the cost of the procedure.
    * 
    * @return             the cost of the procedure as a double
    */
   public double cost( )
   {
      return cost;
   }
}