/**
 * ASSIGNMENT 5 - VendingMachine
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
 * @author   Carmen St. Jean
 * @date     October 8, 2009
 */

import java.util.*;
import java.io.*;

public class VendingMachine
{
   // ----------------------------------------------------------------
   // instance variables ---------------------------------------------
   protected String name;
   private VendingSlot[][] slots;
   private int row, col;
   
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
    * Purchases from the given slot if the parameters are in bounds.
    * 
    * @param    r      integer for the row
    * @param    c      integer for the column
    * @return          true if the purchase was a success
    */
   public boolean purchase( int r, int c )
   {
      if ( r < row && c < col )
         return slots[ r ][ c ].purchase( );
      else
      {
         System.err.print( "Invalid purchase parameters: " );
         return false;
      }
   }
   
   // ----------------------------------------------------------------
   
   /**
    * Defines the given slot if the parameters are in bounds.
    * 
    * @param    r      integer for the row
    * @param    c      integer for the column
    * @param    cap    integer for the capacity
    * @return          true if the definition was a success
    */
   public boolean slotDefine( int r, int c, int cap, Product p )
   {
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
    */
   public boolean refill( )
   {
      boolean b = true;
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