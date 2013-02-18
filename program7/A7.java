/**
 * ASSIGNMENT 7 - A7
 * 
 * This assignment is a simulation of a hospital.  Patients can stay 
 * at a hospital and recieve medicine or surgeries.  At the end, the
 * expenses of each patient as well as the incomes of the individual
 * hospitals and physicans are reported.
 * 
 * @author   Carmen St. Jean
 * @date     November 5, 2009
 */

import java.text.*;
import java.util.*;

public class A7
{
   public static void main( String[ ] args )
   {
      Scanner sc = new Scanner( System.in );
      MedApp  medApp = new MedApp( sc );
      medApp.action( );
      
      Billable [ ] bills = medApp.allBillables( );
      Payable [ ] pymts = medApp.allPayables( );
      
      DecimalFormat money = new DecimalFormat( "#0.00" );  

      System.out.printf( "\npatients\n" );
      for ( int k = 0; k < pymts.length; k++ )
      {
         if ( pymts[ k ].totExpense( ) > 0 )
         {
            for ( int m = 0; m < bills.length; m++ )
            {
               if ( pymts[ k ].totExpense( bills[ m ] ) > 0 )
               {
                  System.out.printf( "  %-14s pays  $%8s to %s\n",
                      pymts[ k ].toString( ),
                      money.format( pymts[k].totExpense( bills[m] ) ),
                      bills[ m ].toString( ) );
               }
            }
         }
      }    
      
      System.out.printf( "\nphysicians\n" );     
      for ( int k = 0; k < bills.length; k++ )
      {
         if ( bills[ k ] instanceof Physician )
            System.out.printf( "  %-14s earns  $%8s\n",
                               bills[ k ].toString( ),
                               money.format( bills[ k ].totIncome( ) ) );
      }
      
      System.out.printf( "\nhospitals\n" );     
      for ( int k = 0; k < bills.length; k++ )
      {
         if ( bills[ k ] instanceof Hospital )
            System.out.printf( "  %-14s earns  $%8s\n",
                               bills[ k ].toString( ),
                               money.format( bills[ k ].totIncome( ) ) );
      }    
   }        
}