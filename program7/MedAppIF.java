
public interface MedAppIF
{
   // needs constructor with scanner argument
   // MedApp_IF( Scanner sc );
   
   public abstract void action( ); // invokes med app to do the job         
   public abstract Billable[ ] allBillables( );
   public abstract Payable[ ]  allPayables( );
   
}

