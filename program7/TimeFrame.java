/**
 * ASSIGNMENT 7 - TimeFrame
 * 
 * The TimeFrame class represents a frame of time between a start
 * date and an end date.  It can tell if two frames of time coincide,
 * which is used to help schedule hospital stays and surgeries.
 * 
 * @author   Carmen St. Jean
 * @date     November 5, 2009
 */

class TimeFrame
{ 
   // ----------------------------------------------------------------
   // instance variables ---------------------------------------------
   private Date startDate, endDate;
   private int startTime, endTime, length;
   
   // ----------------------------------------------------------------
   // constructors ---------------------------------------------------
   
   /**
    * Creates a TimeFrame object from a start date and an end date.
    * Sets the start and end times to the default (0000 and 2400,
    * respectively).
    * 
    * @param    s        the start date for the time frame
    * @param    e        the end date for the time frame
    */
   public TimeFrame( Date s, Date e )
   {
      startDate = s;
      endDate = e;
      startTime = 0000;
      endTime = 2400;
      length = startDate.daysBetween( endDate );
   }
   
   // ----------------------------------------------------------------
   
   /**
    * Creates a TimeFrame object from a start date and an end date,
    * as well as start and end times.
    * 
    * @param    s        the start date for the time frame
    * @param    e        the end date for the time frame
    * @param    sT       the start time for the time frame
    * @param    eT       the end time for the time frame
    */
   public TimeFrame( Date s, Date e, int sT, int eT )
   {
      startDate = s; 
      endDate = e;
      startTime = sT;
      endTime = eT;
      length = startDate.daysBetween( endDate );
   }
   
   // ----------------------------------------------------------------
   // accessors ------------------------------------------------------
   
   /**
    * Returns the length of days in the time frame.
    * 
    * @return          the length of days as an integer
    */
   public int length( )
   {
      return length;
   }
   
   // ----------------------------------------------------------------
   // comparison methods ---------------------------------------------
   
   /**
    * Compares two time frames to see if they coincide with one.
    * There are three cases:
    * 
    * CASE A:
    * this|-----------|
    *                that|-----------|  (do not coincide)
    * 
    * CASE B:
    *                this|-----------|
    * that|-----------|                 (do not coincide)
    * 
    * CASE C:
    * (any overlap at all)
    * this|-----------|
    *       that|------------|          (do coincide)
    * 
    * @param     that    the other TimeFrame for comparison with this
    * @return            true if they coincide, false if not
    */
   public boolean coincides( TimeFrame that )
   {
      if ( this.endDate.before( that.startDate ) )
         return false; // Case A for dates
      
      if ( this.startDate.after( that.endDate ) )
         return false; // Case B for dates
      
      // Same day
      if ( this.length == 1 && that.length == 1 )
      {
         if ( this.endTime <= that.startTime )
            return false; // Case A for times
         else if ( this.startTime >= that.endTime )
            return false; // Case B for times
      }
      
      return true; // Case C for dates/times
   }
}   