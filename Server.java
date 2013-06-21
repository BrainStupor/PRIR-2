import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RMISecurityManager;


public class Server implements GAInterface{
   
    	static {
		System.loadLibrary("Server");
	} 
    
	public GAParams remote_runGA( GAParams parameters ) throws RemoteException{
		int runga_err = runGA(	parameters.pmut, parameters.pcross,
										parameters.best_fitness, parameters.teacher_ids, parameters.room_ids, parameters.class_ids, parameters.subject_ids);
		return parameters;
	}
	
    public native int runGA(	 double pmut, double pcross, 
			double best_fitness[], int teacher_ids[], int room_ids[], int  class_ids[], int  subject_ids[]);//{System.out.println("hello"); return 0;};

	public static void main(String args[]){
		System.setProperty("java.security.policy", "file:///home/stud0/i0barnus/prircpy/java.policy");
		try {
			if(System.getSecurityManager() == null){
				System.setSecurityManager(new RMISecurityManager());
			}
			Server obj = new Server();
			GAInterface stub = (GAInterface)UnicastRemoteObject.exportObject(obj, 0);
			Registry registry = LocateRegistry.createRegistry(1337);
			registry.bind("Server",stub);		
			
				
				
			System.err.println("[Server] Server ready");
		} catch (Exception e) {
				System.err.println("[Server] Server exception: " + e.toString());
				e.printStackTrace();
		}
	}

	
}

/*
int subjectlist[];
	int teacherlist[];
	int popsize;
	int ngen;
	double pmut;
	double pcross;
	double best_fitness[];
	int teacher_ids[];
	int room_ids[];
	int class_ids[];
	int subject_ids[];

*/
