/**
 * ASSIGNMENT 6 - VendingSlot
 * 
 * This assignment is a simulation of a vending machine.  It reads in
 * commands from the system or a file and defines products, vending
 * machines, and slots.  From there, the customer may make purchases
 * or the machine may be refilled.  At the end, the results are
 * printed.
 * 
 * The VendingSlot class represents a vending machine slot, defined
 * by its location in the machine, a capacity for the number of
 * products it can hold at once, and a product which it contains.
 * 
 * @author   Carmen St. Jean
 * @date     October 20, 2009
 */

import java.util.*;
import java.io.*;

public class VendingSlot
{
   // ----------------------------------------------------------------
   // instance variables ---------------------------------------------
   public static final double DIME = 0.10;
   public static final double QUARTER = 0.25;
   public static final double NICKEL = 0.05;
   private Product product;
   private int row, col, capacity, remaining, sold;
   private boolean defined;
   private char rowc;
   
   // ----------------------------------------------------------------
   // constructor ----------------------------------------------------
   
   public VendingSlot( int r, int c )
   {
      row = r;         rowc = (char) ( row + 65 );
      col = c;
      defined = false;
   }
   
   // ----------------------------------------------------------------
   // methods --------------------------------------------------------
   
   /**
    * Defines the slot with the given capacity and the given product.
    * If the product is already defined, then reports error.
    * 
    * @param   cap    capacity for the slot to hold product
    * @param   p      the product for the slot
    * @return         false if the product has already been defined
    */
   public boolean define( int cap, Product p )
   {
      if ( defined )
      {
         System.err.print("Invalid slotdefine (already defined): ");
         return false;
      }
      else
      {
         product = p;
         capacity = cap;
         remaining = capacity;
         defined = true;
         return defined;
      }
   }
   
   // ----------------------------------------------------------------
   
   /**
    * Returns the price of the product which is contained in this
    * slot.
    * 
    * @return      the price of the product as a double
    */
   public double priceCheck( )
   {
      return ( product.price( ) );
   }
   
   // ----------------------------------------------------------------
   
   /**
    * Checks to see if the slot is both defined and has more than
    * zero remaining products in stock.
    * 
    * @return       true if the slot is not empty
    */
   public boolean inStock( )
   {
      return ( remaining > 0 && defined );
   }
   
   // ----------------------------------------------------------------
   
   /**
    * Purchases from the slot if the slot is both defined and the
    * remaining stock is not zero.
    * 
    * @return       true if the slot is defined or empty
    */
   public boolean purchase( )
   {
      if ( defined && remaining > 0 )
      {
         sold++;
         remaining--;
         product.purchase( );
         return true;
      }
      else if ( !defined )
      {
         System.err.print( "Invalid purchase (undefined slot): " );
         return false;
      }
      else
      {
         System.err.println( "Slot " + rowc + " " + col + " empty" );
         return true;
      }
   }
   
   // ----------------------------------------------------------------
   
   /**
    * Refills the slot if defined (sets remaining to capacity).
    * 
    * @return       true if the slot is defined
    */
   public boolean refill( )
   {
      if ( defined )
      {
         remaining = capacity;
         return true;
      }
      else
      {
         System.err.print( "Invalid refill (undefined slot): " );
         return false;
      }
   }
   
   // ----------------------------------------------------------------
   
   /**
    * Reports the profits of this slot.
    * 
    * @return        profits of the given slot as a double
    */
   public double profits( )
   {
      if ( defined )
         return (product.price( ) * sold );
      else
         return 0;
   }
   
   // ----------------------------------------------------------------
   
   /**
    * Prints the final results of the given slot.
    */
   public void print( )
   {
      String o = "\n   Slot: " + rowc + " " + col + ", ";
      if ( defined )
      {
         o += "product: " + product.toString( ) +  ", capacity: ";
         o += capacity + ", remaining: " + remaining + ", sold: ";
         o += sold + ", income: ";
         System.out.print( o );
         System.out.printf( "%1.2f", this.profits( ) );
      }
      else
      {
         o += "undefined slot";
         System.out.println( o );
      }
   }
}