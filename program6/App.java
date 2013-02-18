/**
 * ASSIGNMENT 6 - App
 * 
 * This assignment is a simulation of a vending machine.  It reads in
 * commands from the system or a file and defines products, vending
 * machines, and slots.  From there, the customer may make purchases
 * or the machine may be refilled.  At the end, the results are
 * printed.
 * 
 * The App class manages the input from the system or file until there
 * is no input.  For each command, it determines which command is 
 * being called and calls the appropriate method in the Commands
 * class.
 * 
 * Note:  Based partially Professor Sparr's solution to A1
 * 
 * @author   Carmen St. Jean
 * @date     October 20, 2009
 */

import java.util.*;
import java.io.*;

public class App
{
   // ----------------------------------------------------------------
   // instance variables ---------------------------------------------
   private Commands  cmds;
   private Scanner   sc;
   private String    commandLine;
   private String[ ] token;
   
   // ----------------------------------------------------------------
   // constructor ----------------------------------------------------
   
   public App( Scanner s, Commands c )
   {
      sc = s;
      cmds = c;
   }
   
   // ----------------------------------------------------------------
   // methods --------------------------------------------------------
   
   /**
    * Reads input until end of file and determines which command has
    * been called and calls for the appropriate method.
    */
   public void processInput( )
   {
      while ( sc.hasNext( ) )
      {
         boolean b = true;
         commandLine = sc.nextLine( ).trim( );
         
         if ( commandLine.length( ) > 0 )
         {
            if ( commandLine.charAt( 0 ) == '#' )
            {
               System.out.println( commandLine );
               continue;
            }

            token = commandLine.split( "\\s+" );
            
            if ( token[ 0 ].equals( "product" ) )
               b = cmds.product( token );
            else if ( token[ 0 ].equals( "vendmach" ) )
               b = cmds.machine( token );
            else if ( token[ 0 ].equals( "slot" ) )
               b = cmds.slot( token );
            else if ( token[ 0 ].equals( "purchase" ) )
            {
               System.out.print( commandLine );
               b = cmds.purchase( token );
            }
            else if ( token[ 0 ].equals( "refill" ) )
               b = cmds.refill( token );
            else
               System.out.println( "Command not recognized" );
            
            if ( !b )
            {
               System.out.print( commandLine + "\n        " );
               System.out.print( "Parse failure -- REJECTED\n" );
            }
         }
      }
   }
}