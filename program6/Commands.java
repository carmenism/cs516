/**
 * ASSIGNMENT 6 - Commands
 * 
 * This assignment is a simulation of a vending machine.  It reads in
 * commands from the system or a file and defines products, vending
 * machines, and slots.  From there, the customer may make purchases
 * or the machine may be refilled.  At the end, the results are
 * printed.
 * 
 * The Commands class manages the commands by determining if they were
 * properly formed (as far as syntax goes).  If so, it takes the
 * appropriate action.  This class also manages all products and
 * machines in their own HashMaps.
 * 
 * @author   Carmen St. Jean
 * @date     October 20, 2009
 */

import java.util.*;
import java.io.*;

public class Commands
{
   // ----------------------------------------------------------------
   // instance variables ---------------------------------------------
   private HashMap<String, Product> products;
   private HashMap<String, VendingMachine> machines;
   
   // ----------------------------------------------------------------
   // constructor ----------------------------------------------------
   
   public Commands( )
   {
      products = new HashMap<String, Product>( );
      machines = new HashMap<String, VendingMachine>( );
   }
   
   // ----------------------------------------------------------------
   // methods --------------------------------------------------------
   
   /**
    * Reads the tokens on the line of a product command and creates
    * a new product or finds errors with the input.
    * 
    * @param   token     array of strings from the command line
    * @return            true if the input was good, false otherwise
    */
   public boolean product( String [ ] token )
   {
      boolean valid = true;
      String name;
      double price;
      
      try
      {
         name = token[ 1 ];
         price = ( new Double( token[ 2 ] ) ).doubleValue( );
      }
      catch ( Exception e )
      {
         System.err.print( "Invalid product command syntax: " );
         return false;
      }
      
      if ( products.containsKey( name ) )
      {
         System.err.print( "Invalid product id (duplicate): " );
         valid = false;
      }
      else
      {
         products.put( name, new Product( name, price ) );
      }
      
      return valid;
   }
   
   // ----------------------------------------------------------------
   
   /**
    * Reads the tokens on the line of a machine command and creates
    * a new machine or finds errors with the input.
    * 
    * @param   token     array of strings from the command line
    * @return            true if the input was good, false otherwise
    */
   public boolean machine( String [ ] token )
   {
      boolean valid = true;
      String name;
      int row, col;
      
      try
      {
         name = token[ 1 ];
         row = this.convertLetter( token[ 2 ].charAt( 0 ) ) + 1;
         col = Integer.parseInt( token[ 3 ] ) + 1;
      }
      catch ( Exception e )
      {
         System.err.print( "Invalid vendmach command syntax: " );
         return false;
      }
      
      if ( machines.containsKey( name ) )
      {
         System.err.print( "Invalid machine id (duplicate): " );
         valid = false;
      }
      else if ( row <= 0 || col <= 0 )
      {
         System.err.print( "Invalid machine size parameters: " );
         valid = false;
      }
      else
      {
         machines.put( name, new VendingMachine( name, row, col ) );
         valid = machines.get( name ).determineCap( token );
      }
      
      return valid;
   }
   
   // ----------------------------------------------------------------
   
   /**
    * Reads the tokens on the line of a slot command and defines
    * the given slot or finds errors with the input.
    * 
    * @param   token     array of strings from the command line
    * @return            true if the input was good, false otherwise
    */
   public boolean slot( String [ ] token )
   {
      boolean valid = true;
      String mName, pName;
      int row, col, cap;
      
      try
      {
         mName = token[ 1 ];
         row = this.convertLetter( token[ 2 ].charAt( 0 ) );
         col = Integer.parseInt( token[ 3 ] );
         cap = Integer.parseInt( token[ 4 ] );
         pName = token[ 5 ];
      }
      catch ( Exception e )
      {
         System.err.print( "Invalid slot command syntax: " );
         return false;
      }
      
      VendingMachine m = machines.get( mName );
      Product p = products.get( pName );
      
      if ( m == null )
      {
         System.err.print( "Invalid vending machine id: " );
         valid = false;
      }
      if ( p == null )
      {
         System.err.print( "Invalid product id: " );
         valid = false;
      } 
      if ( cap <= 0 )
      {
         System.err.print( "Invalid slot capacity: " );
         valid = false;
      }
      if ( valid )
      {
         valid = m.slotDefine( row, col, cap, p );
      }
      
      return valid;
   }
   
   // ----------------------------------------------------------------
   
   /**
    * Reads the tokens on the line of a purchase command and purchases
    * from the given slot or finds errors with the input.
    * 
    * @param   token     array of strings from the command line
    * @return            true if the input was good, false otherwise
    */
   public boolean purchase( String [ ] token )
   {
      boolean valid = true;
      String mName;
      int row, col;
      
      try
      {
         mName = token[ 1 ];
         row = this.convertLetter( token[ 2 ].charAt( 0 ) );
         col = Integer.parseInt( token[ 3 ] );
      }
      catch ( Exception e )
      {
         System.err.print( "Invalid purchase command syntax: " );
         return false;
      }
      
      VendingMachine m = machines.get( mName );
      
      if ( m == null )
      {
         System.err.print( "Invalid machine id: " );
         valid = false;
      }
      else
      {
         valid = m.purchase( row, col, token );
         System.out.print( "\n" );
      }
      
      return valid;
   }
   
   // ----------------------------------------------------------------
   
   /**
    * Reads the tokens on the line of a refill command and refills the
    * given machine or finds errors with the input.
    * 
    * @param   token     array of strings from the command line
    * @return            true if the input was good, false otherwise
    */
   public boolean refill( String [ ] token )
   {
      boolean valid = true;
      String mName;
      
      try
      {
         mName = token[ 1 ];
      }
      catch ( Exception e )
      {
         System.err.print( "Invalid refill command syntax: " );
         return false;
      }
      
      VendingMachine m = machines.get( mName );
      
      if ( m == null )
      {
         System.err.print( "Invalid machine id: " );
         valid = false;
      }
      else
      {
         m.refill( );
      }
      
      return valid;
   }
   
   // ----------------------------------------------------------------
   
   /**
    * Prints a summary of the status and profits from all machines and
    * all products.
    */
   public void printResults( )
   {
      System.out.println( "\n\n*** Summary ***" );
      
      System.out.println( "\nVending Machine Status:" );
      Iterator mIt = machines.keySet( ).iterator( );
      while ( mIt.hasNext( ) )
      {
         Object key = mIt.next( );
         machines.get( key ).print( );
      }
      
      System.out.println( "\n\nProduct Sales:" );
      Iterator pIt = products.keySet( ).iterator( );
      while ( pIt.hasNext( ) )
      {
         Object key = pIt.next( );
         products.get( key ).print( );
      }
      
      System.out.println( "\n\n*** End of Summary ***" );
   }
   
   // ----------------------------------------------------------------
   
   /**
    * Converts a character to an integer so that A corresponds to 0,
    * B corresponds to 1, C corresponds to 2, etc...
    * 
    * @param     c      character to be converted
    * @return           corresponding integer value
    */
   private int convertLetter( char c )
   {
      return ( Character.getNumericValue( c ) - 10 );
   }
}