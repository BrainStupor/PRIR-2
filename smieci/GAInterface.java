import java.rmi.*;

public interface GAInterface extends Remote{   
   public GAParams remote_runGA( GAParams parameters ) throws RemoteException;
}