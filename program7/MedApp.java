/**
 * ASSIGNMENT 7 - MedApp
 * 
 * This assignment is a simulation of a hospital.  Patients can stay 
 * at a hospital and recieve medicine or surgeries.  At the end, the
 * expenses of each patient as well as the incomes of the individual
 * hospitals and physicans are reported.
 * 
 * The MedApp implements the MedAppIF interface.  It reads and
 * interpets the commands and takes the appropriate actions.  It is
 * the "brain" of the application.
 * 
 * @author   Carmen St. Jean
 * @date     November 5, 2009
 */

import java.text.*;
import java.util.*;

class MedApp implements MedAppIF
{ 
   // ----------------------------------------------------------------
   // instance variables ---------------------------------------------
   HashMap< String, Billable > billables;
   HashMap< String, Payable > payables;
   HashMap< String, Procedure > procedures;
   private Scanner   sc;
   private String    commandLine;
   private String[ ] token;
   
   // ----------------------------------------------------------------
   // constructor ----------------------------------------------------
   
   /**
    * Creates a new MedApp from a scanner.  Intializes the collections
    * of billables, payables, and procedures.
    * 
    * @param       sc      the scanner to read the input
    */
   public MedApp( Scanner sc )
   {
      billables = new HashMap< String, Billable >( );
      payables = new HashMap< String, Payable >( );
      procedures = new HashMap< String, Procedure >( );
      this.sc = sc;
   }
   
   // ----------------------------------------------------------------
   // MedAppIF methods -----------------------------------------------
   
   /**
    * Returns all the Billable objects (physicians and hospitals) in
    * an array.
    * 
    * @return        an array of Billable objects
    */
   public Billable[ ] allBillables( )
   { 
      ArrayList< String > keys = new ArrayList< String >( );
      keys.addAll( billables.keySet( ) );
      Collections.sort( keys );
      
      Iterator it = keys.iterator( );
      Billable [ ] arr = new Billable[ billables.size( ) ];
      int count = 0;
      
      while ( it.hasNext( ) )
      {
         Object key = it.next( );
         arr[ count ] = billables.get( key );
         count++;
      }
      
      return arr;
   }
   
   // ----------------------------------------------------------------
   
   /**
    * Returns all the Payable objects (patients) in an array.
    * 
    * @return        an array of Payable objects
    */
   public Payable[ ] allPayables( )
   {
      ArrayList< String > keys = new ArrayList< String >( );
      keys.addAll( payables.keySet( ) );
      Collections.sort( keys );
      
      Iterator it = keys.iterator( );
      Payable [ ] arr = new Payable[ payables.size( ) ];
      int count = 0;
      
      while ( it.hasNext( ) )
      {
         Object key = it.next( );
         arr[ count ] = payables.get( key );
         count++;
      }
      
      return arr; 
   } 
   
   // ----------------------------------------------------------------
   
   /**
    * Calls the MedApp to read the input and do the required actions.
    */
   public void action( )
   {
      while ( sc.hasNext( ) )
      {
         boolean valid = true;
         commandLine = sc.nextLine( ).trim( );
         
         if ( commandLine.length( ) > 0 )
         {
            if ( commandLine.charAt( 0 ) == '#' )
            {
               System.out.println( commandLine );
               continue;
            }
            
            token = commandLine.split( "\\s+" );
            
            if ( token[ 0 ].equals( "person" ) )
               valid = this.person( token );
            else if ( token[ 0 ].equals( "hospital" ) )
               valid = this.hospital( token );
            else if ( token[ 0 ].equals( "stay" ) )
               valid = this.stay( token );
            else if ( token[ 0 ].equals( "medicine" ) )
               valid = this.medicine( token );
            else if ( token[ 0 ].equals( "prescription" ) )
               valid = this.prescription( token );
            else if ( token[ 0 ].equals( "procedure" ) )
               valid = this.procedure( token );
            else if ( token[ 0 ].equals( "surgery" ) )
               valid = this.surgery( token );
            else
            {
               System.out.println( "Command not recognized: " );
               System.out.println( commandLine );
            }
            
            if ( !valid )
            {
               System.out.print( commandLine + "\n" );
            }
         }
      }
   }
   
   // ----------------------------------------------------------------
   // Other methods --------------------------------------------------
   
   /**
    * Checks to see if the tokens of the "person" command are valid.
    * If so, then it interprets the information and makes a Person
    * object.  If the command specifies the person is a physician, 
    * then it makes a Physician object.
    * 
    * @param    token     the command tokenized
    */
   private boolean person( String [ ] token )
   {
      boolean physician = true;
      String name, profession = "";
      double multiplier = 0;
      
      try
      {
         name = token[ 1 ];
      }
      catch ( Exception e )
      {
         System.err.print( "Invalid person command syntax: " );
         return false;
      }
      
      try
      {
         profession = token[ 2 ];
      }
      catch ( Exception e )
      {
         physician = false;
      }
      
      if ( physician )
      {
         if ( !profession.equals( "physician" ) )
         {
            System.err.print( "Invalid person command syntax: " );
            return false;
         }
         
         boolean hasMultiplier = true;
         
         try
         {
            multiplier = ( new Double( token[ 3 ] ) ).doubleValue( );
         }
         catch( Exception e )
         {
            hasMultiplier = false;
            multiplier = 0;
         }
         
         if ( hasMultiplier && ( multiplier < 0.01 ||
                                 multiplier > 0.99 ) )
         {
            System.err.print( "Invalid person command syntax: " );
            return false;
         }
         
         if ( payables.containsKey( name ) ||
              billables.containsKey( name ) )
         {
            System.err.print( "Invalid person command syntax: " );
            return false;
         }
         
         Person p = new Physician( name, multiplier );
         Billable phys = (Billable) p;
         billables.put( name, phys );
         Payable person = (Payable) p;
         payables.put( name, person );
      }
      else
      {
         if ( payables.containsKey( name ) )
         {
            System.err.print( "Invalid person command syntax: " );
            return false;
         }
         
         Payable ptnt = new Person( name );
         payables.put( name, ptnt );
      }
      
      return true;
   }
   
   // ----------------------------------------------------------------
   
   /**
    * Checks to see if the tokens of the "hospital" command are valid.
    * If so, then it interprets the information and makes a Hospital
    * object.
    * 
    * @param    token     the command tokenized
    */
   private boolean hospital( String [ ] token )
   {
      String name;
      double oprRoom, room, privateRoom;
      
      try
      {
         name = token[ 1 ];
         oprRoom = ( new Double( token[ 2 ] ) ).doubleValue( );
         room = ( new Double( token[ 3 ] ) ).doubleValue( );
         privateRoom = ( new Double( token[ 4 ] ) ).doubleValue( );
      }
      catch ( Exception e )
      {
         System.err.print( "Invalid hospital command syntax: " );
         return false;
      }
      
      if ( oprRoom <= 0 || room <= 0 || privateRoom <= 0 )
      {
         System.err.print( "Invalid hospital command syntax: " );
         return false;
      }
      
      if ( billables.containsKey( name ) )
      {
         System.err.print( "Invalid hospital command syntax: " );
         return false;
      }
      
      Billable hosp = new Hospital( name, oprRoom, room,
                                    privateRoom );
      billables.put( name, hosp );
      
      return true;
   }
      
   // ----------------------------------------------------------------
   
   /**
    * Checks to see if the tokens of the "stay" command are valid.
    * If so, then it interprets the information and makes a
    * HospitalStay object.
    * 
    * @param    token     the command tokenized
    */
   private boolean stay( String [ ] token )
   {
      String name, hospName, startDate, endDate;
      boolean privateStay = false;
      
      try
      {
         name = token[ 1 ];
         hospName = token[ 2 ];
         startDate = token[ 3 ];
         endDate = token[ 4 ];
      }
      catch ( Exception e )
      {
         System.err.print( "Invalid hospstay command syntax: " );
         return false;
      }
      
      if ( !payables.containsKey( name ) ||
           !billables.containsKey( hospName ) ||
           !Date.valid( startDate ) || !Date.valid( endDate ) )
      {
         System.err.print( "Invalid hospstay command syntax: " );
         return false;
      }
      
      if ( token.length > 5 && token[ 5 ].equals( "private" ) )
         privateStay = true;
      
      Date start = new Date( startDate );
      Date end   = new Date( endDate );
      
      Payable p = payables.get( name );
      Person person = ( Person ) p;
      Billable b = billables.get( hospName );
      Hospital hosp = ( Hospital ) b;
      
      if ( start.before( end ) || start.equals( end ) )
      {
         TimeFrame t = new TimeFrame( start, end );
         
         if ( person.notBusy( t ) )
         {
            HospitalStay stay = new HospitalStay( person, hosp,
                                                  t, privateStay);
            person.addStay( stay, t );
            hosp.addStay( stay );
            
            return true;
         }
      }
      else
      {
         System.err.print( "Invalid hospstay command syntax: " );
         return false;
      }
      
      System.err.print( "Rejected hospital stay: " );
      return false;
   }
   
   // ----------------------------------------------------------------
   
   /**
    * Checks to see if the tokens of the "medicine" command are valid.
    * If so, then it interprets the information and makes a Medicine
    * object.
    * 
    * @param    token     the command tokenized
    */
   private boolean medicine( String [ ] token )
   {
      String medName, hospName;
      double cost;
      
      try
      {
         medName = token[ 1 ];
         hospName = token[ 2 ];
         cost = ( new Double( token[ 3 ] ) ).doubleValue( );
      }
      catch ( Exception e )
      {
         System.err.print( "Invalid medicine command syntax: " );
         return false;
      }
      
      if ( !billables.containsKey( hospName ) || cost < 0 )
      {
         System.err.print( "Invalid medicine command syntax: " );
         return false;
      }
      
      Billable b = billables.get( hospName );
      
      if ( b instanceof Hospital )
      {
         Hospital h = (Hospital) b;
         return h.addMedicine( medName, cost );
      }
      else
      {
         System.err.print( "Invalid medicine command: " );
         return false;
      }
   }
   
   // ----------------------------------------------------------------
   
   /**
    * Checks to see if the tokens of the "prescription" command are
    * valid.  If so, then it interprets the information and makes a
    * Prescription object.
    * 
    * @param    token     the command tokenized
    */
   private boolean prescription( String [ ] token )
   {
      String name, medName, hospName;
      int num, days;
      
      try
      {
         name = token[ 1 ];
         num = ( new Integer( token[ 2 ] ) ).intValue( );
         medName = token[ 3 ];
         hospName = token[ 4 ];
         days = ( new Integer( token[ 5 ] ) ).intValue( );
      }
      catch ( Exception e )
      {
         System.err.print( "Invalid prescription command syntax: " );
         return false;
      }
      
      if ( num < 0 || days <= 0 || !payables.containsKey( name ) ||
           !billables.containsKey( hospName ) )
      {
         System.err.print( "Invalid prescription command syntax: " );
         return false;
      }
         
      Billable b = billables.get( hospName );
      
      if ( b instanceof Hospital )
      {
         Hospital h = (Hospital) b;
         
         if ( h.containsMedicine( medName ) )
         {
            Medicine m = h.getMedicine( medName );
            
            Payable pay = payables.get( name );
            Person p = ( Person ) pay;
            
            Prescription pre = new Prescription( m, p, h, num, days );
            p.addPrescription( pre );
            h.addPrescription( pre );
            
            return true;
         }
         else
         {
            System.err.print( "Invalid prescription command: " );
            return false;
         }
      }
      else
      {
         System.err.print( "Invalid prescription command syntax: " );
         return false;
      }
   }

   // ----------------------------------------------------------------
   
   /**
    * Checks to see if the tokens of the "procedure" command are
    * valid.  If so, then it interprets the information and makes a
    * Procedure object.
    * 
    * @param    token     the command tokenized
    */
   private boolean procedure( String [ ] token )
   {
      String procedureName;
      double fee;
      
      try
      {
         procedureName = token[ 1 ];
         fee = ( new Double( token[ 2 ] ) ).doubleValue( );
      }
      catch ( Exception e )
      {
         System.err.print( "Invalid procedure command syntax: " );
         return false;
      }
      
      if ( procedures.containsKey( procedureName ) || fee < 0 )
      {
         System.err.print( "Invalid procedure command syntax: " );
         return false;
      }
      
      Procedure p = new Procedure( procedureName, fee );
      procedures.put( procedureName, p );
      
      return true;
   }
   
   // ----------------------------------------------------------------
   
   /**
    * Checks to see if the tokens of the "surgery" command are
    * valid.  If so, then it interprets the information and makes a
    * Surgery object.
    * 
    * @param    token     the command tokenized
    */
   private boolean surgery( String [ ] token )
   {
      String name, procName, surgName, hospName, date;
      int startTime, endTime;
      
      try
      {
         name = token[ 1 ];
         procName = token[ 2 ];
         surgName = token[ 3 ];
         hospName = token[ 4 ];
         date = token[ 5 ];
         startTime = ( new Integer( token[ 6 ] ) ).intValue( );
         endTime = ( new Integer( token[ 7 ] ) ).intValue( );
      }
      catch ( Exception e )
      {
         System.err.print( "Invalid surgery command syntax: " );
         return false;
      }
      
      if ( !billables.containsKey( hospName ) ||
           !billables.containsKey( surgName ) ||
           !procedures.containsKey( procName ) ||
           !payables.containsKey( name ) || !Date.valid( date ) ||
           endTime < startTime || startTime < 0 || endTime < 0 ||
           startTime > 2400 || endTime > 2400 )
      {
         System.err.print( "Invalid surgery command syntax: " );
         return false;
      }
      
      Date d = new Date( date );
      TimeFrame t = new TimeFrame( d, d, startTime, endTime );
      
      Billable b1 = billables.get( hospName );
      Hospital h;
      
      if ( b1 instanceof Hospital )
      {
         h = ( Hospital ) b1;
      }
      else
      {
         System.err.print( "Invalid surgery command: " );
         return false;
      }
      
      Billable b2 = billables.get( surgName );
      Physician surg;
      
      if ( b2 instanceof Physician )
      {
         surg = ( Physician ) b2;
      }
      else
      {
         System.err.print( "Invalid surgery command: " );
         return false;
      }
      
      Payable p = payables.get( name );
      Person person = ( Person ) p;
      
      if ( person.notBusySurg( t ) && h.notBusy( t ) &&
           surg.notBusy( t ) )
      {
         Procedure pro = procedures.get( procName );
         Surgery surgery = new Surgery( h, pro, surg, person, t );
         
         person.hadSurgery( surgery, t );
         surg.didSurgery( surgery, t );
         h.addSurgery( surgery, t );
         
         return true;
      }
      else
      {
         System.err.print( "Rejected surgery: " );
         return false;
      }
   }
}        