/**
 * ASSIGNMENT 7 - Date
 * 
 * The Date class exists to verify if inputed dates are valid and to
 * compare dates (i.e. to see if one date happens before or after
 * another date).  The Date class can also count the days between two
 * dates, using the Calendar class.
 * 
 * @author   Carmen St. Jean
 * @date     November 5, 2009
 */

class Date
{ 
   // ----------------------------------------------------------------
   // instance variables ---------------------------------------------
   private String asString;
   private int year, month, day;

   // ----------------------------------------------------------------
   // constructor ----------------------------------------------------
   
   /**
    * Creates a Date object from a String in the format of 'yymmdd'.
    * 
    * @param      s      a string containing the date
    */
   public Date( String s )
   {
      asString = s;
      year = findYear( asString );
      month = findMonth( asString );
      day = findDay( asString );
   }
   
   // ----------------------------------------------------------------
   // constructor helper methods -------------------------------------
   
   /**
    * Finds the year from a String in the format of 'yymmdd' and makes
    * it into an integer.
    * 
    * @param       a string containing the date
    * @return      the year as an integer
    */
   private int findYear( String date )
   {
      int y = ( new Integer( date.substring( 0, 2 ) ) ).intValue( );
      return ( 2000 + y );
   }
   
   // ----------------------------------------------------------------
   
   /**
    * Finds the month from a String in the format of 'yymmdd' and
    * makes it into an integer.
    * 
    * @param       a string containing the date
    * @return      the month as an integer
    */
   private static int findMonth( String date )
   {
      return ( new Integer( date.substring( 2, 4 ) ) ).intValue( );
   }
   
   // ----------------------------------------------------------------
   
   /**
    * Finds the day of the month from a String in the format of
    * 'yymmdd' and makes it into an integer.
    * 
    * @param       a string containing the date
    * @return      the day as an integer
    */
   private static int findDay( String date )
   {
      return ( new Integer( date.substring( 4, 6 ) ) ).intValue( );
   }
   
   // ----------------------------------------------------------------
   // comparison methods ---------------------------------------------
   
   /**
    * Compares this Date object with another object to see if this
    * Date occurs before that Date.
    * 
    * @param    that     Date for comparison with this Date
    * @return            true if this occurs before Date in parameter
    */
   public boolean before( Date that )
   {
      if ( this.year > that.year )
         return false; // bad year
      else if ( this.year < that.year )
         return true;  // later year
      else if ( this.month > that.month )
         return false; // same year, bad month
      else if ( this.month < that.month )
         return true;  // same year, later month
      else if ( this.day > that.day )
         return false; // same year, same month, bad day
      else if ( this.day < that.day )
         return true;  // same year, same month, later day
      else
         return false;  // same year, same month, same day
   }
   
   // ----------------------------------------------------------------
   
   /**
    * Compares this Date object with another object to see the two
    * are the same.
    * 
    * @param    that     Date for comparison with this Date
    * @return            true if this equals Date in parameter
    */
   public boolean equals( Date that )
   {
      return ( this.year == that.year && this.month == that.month
               && this.day == that.day );
   }
   
   // ----------------------------------------------------------------
   
   /**
    * Compares this Date object with another object to see if this
    * Date occurs after that Date.
    * 
    * @param    that     Date for comparison with this Date
    * @return            true if this occurs after Date in parameter
    */
   public boolean after( Date that )
   {
      return ( !this.equals( that ) && !this.before( that ) );
   }
   
   // ----------------------------------------------------------------
   // other methods --------------------------------------------------
   
   /**
    * Counts the number of days between this Date and an end Date.  
    * (If the days are equal, then the count will be 1.)
    * 
    * @param    end     the end date to count the days between
    * @return           the number of days between the two dates
    */
   public int daysBetween( Date end )
   {
      if ( this.year == end.year && this.month == end.month )
      {
         return ( end.day - this.day + 1 );
      }
      else
      {
         int days = 0;
         int m = this.month; int d = this.day; int y = this.year;
         
         while ( true )
         {
            days += Calendar.cal[ m ][ d ];
            d++;
            
            if ( d == 32 )
            {
               d = 1;
               m++;
               if ( m == 13 )
               {
                  m = 1;
                  y++;
               }
            }
            
            if ( m == end.month && d == end.day && y == end.year  )
               break;
         }
         
         return ( days + 1 );
      }
   }
   
   // ----------------------------------------------------------------
   // static methods -------------------------------------------------
   
   /**
    * Checks to see if a string contains a valid date.  Rejects
    * any strings which do not contain six digits.  Rejects any
    * strings which contain invalid month or day numbers (i.e. if the
    * month is 13 or the day is 32).  Also checks to see that the day
    * is proper for the given month.
    * 
    * @param    date     a string to be tested for validity
    * @return            true if the string contains a valid date
    */
   public static boolean valid( String date )
   {
      int month, day;
      
      if ( date.length( ) != 6 )
         return false;
      
      if ( !date.matches( "[0-9]{6}" ) )
         return false;
      
      month = findMonth( date );
      
      // check for valid month
      if ( month <= 0 || month > 12 )
         return false;
      
      day = findDay( date );
      
      // check for valid day if Feb
      if ( month == 2 )
      {
         if ( day > 0 && day <= 28 )
            return true;
         else
            return false;
      }
      
      // check for valid day if Sep, Apr, Jun, or Nov
      if ( month == 9 || month == 4 || month == 6 || month == 11 )
      {
         if ( day > 0 && day <= 30 )
            return true;
         else
            return false;
      }
      
      // all other months: Jan, Mar, Apr, May, Jul, Aug, Oct, or Dec
      if ( day > 0 && day <= 31 )
         return true;
      else
         return false;
   }
}   