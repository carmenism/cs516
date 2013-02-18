/**
 * ASSIGNMENT 6 - Product
 * 
 * This assignment is a simulation of a vending machine.  It reads in
 * commands from the system or a file and defines products, vending
 * machines, and slots.  From there, the customer may make purchases
 * or the machine may be refilled.  At the end, the results are
 * printed.
 * 
 * The Product class represents a product.  It is defined with a
 * name and a price for this given product.  Each time this product
 * is purchased from a slot, the purchase is also recorded here in
 * this class.
 * 
 * @author   Carmen St. Jean
 * @date     October 20, 2009
 */

import java.util.*;
import java.io.*;

public class Product
{
   // ----------------------------------------------------------------
   // instance variables ---------------------------------------------
   private int unitsSold;
   private double price, profits;
   private String name;
   
   // ----------------------------------------------------------------
   // constructor ----------------------------------------------------
   
   public Product( String n, double p )
   {
      name = n;
      price = p;
      unitsSold = 0;
   }
   
   // ----------------------------------------------------------------
   
   /**
    * When informed by a slot a purchase of this product has
    * occurred, increments the number of units sold.
    */
   public void purchase( )
   {
      unitsSold++;
   }
   
   // ----------------------------------------------------------------
   
   /**
    * Returns the price of this given product as was defined.
    * 
    * @return        the price as represented by a double
    */
   public double price( )
   {
      return price;
   }
   
   // ----------------------------------------------------------------
   
   /**
    * Returns the profits of this given product.
    * 
    * @return        the profits as represented by a double
    */
   public double profits( )
   {
      return ( price * unitsSold );
   }
   
   // ----------------------------------------------------------------
   
   /**
    * Returns the name of the product in a string for printing.
    * 
    * @return        the name of the product in a string
    */
   public String toString( )
   {
      return name;
   }
   
   // ----------------------------------------------------------------
   
   /**
    * Prints the final sales of the given product.
    */
   public void print( )
   {
      System.out.print( "\n  Product: " + name + ", price: " );
      System.out.printf( "%1.2f", price );
      System.out.printf( ", units sold: " + unitsSold + ", income " );
      System.out.printf( "%1.2f", this.profits( ) );
   }
}