import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RMISecurityManager;


public class Server implements GAInterface{
	public static void Main(String args[]){
		try {
			if(System.getSecurityManager() == null){
				System.setSecurityManager(new RMISecurityManager());
			}
			Server obj = new Server();
			GAInterface stub = (GAInterface)UnicastRemoteObject.exportObject(obj, 0);
			registry = LocateRegistry.getRegistry(1337);
			registry.bind("Server",stub);		
			
				
				
			System.err.println("[Server] Server ready");
		} catch (Exception e) {
				System.err.println("[Server] Server exception: " + e.toString());
				e.printStackTrace();
		}
	}


}