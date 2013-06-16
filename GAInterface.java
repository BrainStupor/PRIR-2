import java.rmi.*;

public interface GMInterface extends Remote{   
   public GAParams runGA( GAParams parameters ) throws RemoteException;
}