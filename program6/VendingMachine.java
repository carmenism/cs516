/**
 * ASSIGNMENT 6 - VendingMachine
 * 
 * This assignment is a simulation of a vending machine.  It reads in
 * commands from the system or a file and defines products, vending
 * machines, and slots.  From there, the customer may make purchases
 * or the machine may be refilled.  At the end, the results are
 * printed.
 * 
 * The VendingMachine class represents a vending machine, defined
 * by a name and dimensions for the machine.  It is composed of as
 * many VendingSlots as necessary to match the given dimensions.
 * 
 * The machine manages the coins involved in a purchase.  If the
 * dispenser contains the proper amount of coins for change, then
 * the machine dispenses the change.  The machine also stores all
 * coins from purchases in either the dispenser or the bucket.  If no
 * change is available, then a purchase is not made.
 * 
 * @author   Carmen St. Jean
 * @date     October 20, 2009
 */

import java.util.*;
import java.io.*;

public class VendingMachine
{
   // ----------------------------------------------------------------
   // instance variables ---------------------------------------------
   public static final double DIME = 0.10;
   public static final double QUARTER = 0.25;
   public static final double NICKEL = 0.05;
   protected String name;
   private VendingSlot[][] slots;
   private int row, col;
   private int nCap, dCap, qCap; // coin capacities
   private int nBucket=0, dBucket=0, qBucket=0; // amount in bucket
   private int nickels=0, dimes=0, quarters=0; // amount in dispenser
   
   // ----------------------------------------------------------------
   // constructor ----------------------------------------------------
   public VendingMachine( String n, int r, int c )
   {
      name = n;
      row = r;
      col = c;
      slots = new VendingSlot[ 26 ][ 150 ];
      
      for ( int i = 0; i < row; i++ )
      {
         for ( int j = 0; j < col; j++ )
         {
            slots[ i ][ j ] = new VendingSlot( i, j );
         }
      }
   }
   
   // ----------------------------------------------------------------
   // methods --------------------------------------------------------
   
   /**
    * Determines the capacities for the coin dispenser of the given
    * vending machine from the input
    * 
    * @param  token      tokens of the command line
    */
   public boolean determineCap( String [ ] token )
   {
      for ( int i = 4; i < 10; i = i + 2 )
      {
         String coinName;
         int cap = 0;
         
         try
         {
            coinName = token[ i ];
            cap = Integer.parseInt( token[ i + 1 ] );
         }
         catch ( Exception e )
         {
            System.err.print( "Invalid vendmach command syntax: " );
            return false;
         }
         
         if ( cap < 0 )
         {
            System.err.print( "Invalid vendmach command syntax: " );
            return false;
         }
         
         if ( coinName.equals( "nickel" ) )
         {
            nCap = cap;
         }
         else if ( coinName.equals( "dime" ) )
         {
            dCap = cap;
         }
         else if ( coinName.equals( "quarter" ) )
         {
            qCap = cap;
         }
         else
         {
            System.err.print( "Invalid vendmach command syntax: " );
            return false;
         }
      }
      
      nickels = nCap;
      dimes = dCap;
      quarters = qCap;
      
      return true;
   }
   
   // ----------------------------------------------------------------
   
   /**
    * Purchases from the given slot if the parameters are in bounds.
    * 
    * @param    r      integer for the row
    * @param    c      integer for the column
    * @return          true if the purchase was a success
    */
   public boolean purchase( int r, int c, String [ ] token )
   {
      boolean valid = true;
      
      if ( r < row && c < col )
      {
         valid = true; 
      }
      else
      {
         System.err.print( "Invalid purchase parameters: " );
         return false;
      }
      
      int n = 0, d = 0, q = 0;
      for ( int i = 4; i < 10; i = i + 2 )
      {
         String coinName = "";
         int num = 0;
         
         try
         {
            coinName = token[ i ];
            num = Integer.parseInt( token[ i + 1 ] );
         }
         catch ( Exception e )
         {
            //System.err.print( "Invalid purchase command syntax: " );
            //return false;
         }
         
         if ( num > 0 )
         {
            if ( coinName.equals( "nickel" ) )
            {
               n = num;
            }
            else if ( coinName.equals( "dime" ) )
            {
               d = num;
            }
            else if ( coinName.equals( "quarter" ) )
            {
               q = num;
            }
         }
      }
      
      double paid = n*NICKEL + d*DIME + q*QUARTER;
      double price = slots[ r ][ c ].priceCheck( );

      if ( paid < price )
      {
         System.err.print( "REFUSED -- INSUFFICIENT FUNDS PROVIDED" );
         return false;
      }
      else if ( paid == price )
      {
         valid = slots[ r ][ c ].purchase( );
         if ( valid )
            this.storeCoins( n, d, q );
      }
      else // change is needed
      {
         if ( slots[ r ][ c ].inStock( ) )
         {
            if ( determineChange( price, paid ) )
            {
               slots[ r ][ c ].purchase( );
               this.storeCoins( n, d, q ); // order?
            }
         }
         else
         {
            System.err.println( "Slot " + r + " " + c + " empty" );
         }
      }

      return valid;
   }
   
   // ----------------------------------------------------------------
   
   /**
    * Determines the amount of change for a given transaction.  If the
    * machine's dispenser contains the proper amount of coins to give
    * the buyer change, then it returns a true.  If change cannot be
    * provided, then it returns a false.
    * 
    * @param  pr     price of the product as a double
    * @param  p      total paid amount as a double
    * @return        true if change was made, false otherwise
    */
   private boolean determineChange( double pr, double p )
   {
      int price = (int)(100*pr); int paid = (int)(100*p);
      
      if (price%5 != 0) // in case conversion lost precision
         price += (5 - price%5);
      if (paid%5 != 0) // in case conversion lost precision
         paid += (5 - paid%5);
      
      int change = paid - price;
      int qCh = 0, dCh = 0, nCh = 0;
      
      if ( quarters > 0 )
      {
         qCh = change / 25;
         if ( qCh > quarters )
            qCh = quarters;
         change -= qCh*25;
      }
      
      if ( dimes > 0 )
      {
         dCh = change / 10;
         if ( dCh > dimes )
            qCh = dimes;
         change -= dCh*10;
      }
      
      if ( nickels > 0 )
      {
         nCh = change / 5;
         if ( nCh > nickels )
            nCh = nickels;
         change -= nCh*5;
      }
      
      if ( change > 0 )
      {
         System.err.print( " REFUSED -- INSUFFICIENT CHANGE" );
         return false;
      }

      nickels -= nCh;
      dimes -= dCh;
      quarters -= qCh;
      
      System.out.print( " -- change" );
      if ( qCh > 0 )
         System.out.print( " quarter " + qCh );
      if ( dCh > 0 )
         System.out.print( " dime " + dCh );
      if ( nCh > 0 )
         System.out.print( " nickel " + nCh );
      
      return true;
   }
   
   // ----------------------------------------------------------------
   
   /**
    * Stores the coins from the purchase in either the dispenser or
    * the bucket for later collection.
    * 
    * @param  n     number of nickels as an int
    * @param  d     number of dimes as an int
    * @param  q     number of quarters as an int
    */
   private void storeCoins( int n, int d, int q )
   {
      for ( int i = 0; i < n; i++ )
      {
         if ( nickels < nCap )
            nickels++;
         else
            nBucket++;
      }
      
      for ( int i = 0; i < d; i++ )
      {
         if ( dimes < dCap )
            dimes++;
         else
            dBucket++;
      }
      
      for ( int i = 0; i < q; i++ )
      {
         if ( quarters < qCap )
            quarters++;
         else
            qBucket++;
      }
   }
   
   // ----------------------------------------------------------------
   
   /**
    * Defines the given slot if the parameters are in bounds.
    * 
    * @param    r      integer for the row
    * @param    c      integer for the column
    * @param    cap    integer for the capacity
    * @param    p      Product for the slot to contain
    * @return          true if the definition was a success
    */
   public boolean slotDefine( int r, int c, int cap, Product p )
   {
      nickels = nCap;
      dimes = dCap;
      quarters = qCap;
      
      if ( r < row && c < col )
      {
         return slots[ r ][ c ].define( cap, p );
      }
      else
      {
         System.err.print( "Invalid slotdefine parameter(s): " );
         return false;
      }
   }
   
   // ----------------------------------------------------------------
   
   /**
    * Refills all defined slots in the machine with products for sale.
    * 
    * @return       true if all slots were defined and thus refilled
    */
   public boolean refill( )
   {
      boolean b = true;
      
      nickels = nCap;
      dimes = dCap;
      quarters = qCap;
      
      for ( int i = 0; i < row; i++ )
      {
         for ( int j = 0; j < col; j++ )
         {
            b = slots[ i ][ j ].refill( );
         }
      }
      
      return b;
   }
   
   // ----------------------------------------------------------------
   
   /**
    * Prints the final results of this given machine.
    */
   public void print( )
   {
      String s = "\n Vending Machine: " + name + " - " + row;
      s += " row(s), " + col + " column(s)";
      System.out.print( s );
      for ( int i = 0; i < row; i++ )
      {
         for ( int j = 0; j < col; j++ )
         {
            slots[ i ][ j ].print( );
         }
      }
   }
}