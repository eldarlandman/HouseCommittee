package TCPClientobject;



/*
 * Java provides a mechanism, called object serialization where an object can be represented as a sequence of bytes that includes the object's data as well as information about the object's type and the types of data stored in the object.

After a serialized object has been written into a file, it can be read from the file and deserialized that is, the type information and bytes that represent the object and its data can be used to recreate the object in memory.

Most impressive is that the entire process is JVM independent, meaning an object can be serialized on one platform and deserialized on an entirely different platform.

Classes ObjectInputStream and ObjectOutputStream are high-level streams that contain the methods for serializing and deserializing an object.
 * 
 * pay attation that if the client sends you integer so in the server you will get integer 
 * and if it sends you string so you will get string . 
 * 
 * 
 */


import java.util.Scanner;



class TCPClient_with_serialized { 


	public enum Mode {HouseCommittee,Tenant};
	public enum Operation {SignUp,Login};

	 
	public static void main(String argv[]) throws Exception 
	{ 


		//establishServerConnection();

		Scanner sc = new Scanner(System.in);
		System.out.println("Choose mode:"+'\n'+"1.House Committee"+'\n'+"2.Tenant");
		Mode mode=readUserMode(sc);
		System.out.println("Choose operation:"+'\n'+"1.Login"+'\n'+"2.Sign-up");
		Operation operation=readUserOperation(sc);		
		if (operation==Operation.SignUp){
			register(mode,sc);
		}
		else{
			assert(operation==Operation.Login);
			connect(mode ,sc);
		}





	}

	private static void connect(Mode mode, Scanner sc) throws InterruptedException {

		//Read password and user name
		sc.nextLine();
		System.out.println("write your username:");
		String userName=sc.nextLine();
		System.out.println("write your password:");
		String password=sc.nextLine();
		//sc.close();		
	
		switch(mode){
		case HouseCommittee:
			//Create houseCom object with password and user name
			HouseCommittee hc=new HouseCommittee(userName, password,sc);			
			//Run thread on this object which establish a connection and ask verification against DB
			Thread t=new Thread(hc);
			t.start();
			t.join();
			
			break;
		case Tenant:
			//Create Tenant object with password and user name
			Tenant ten=new Tenant(userName, password,sc);	
			//Run thread on this object which establish a connection and ask verification against DB
			Thread tt=new Thread(ten);
			tt.start();
			tt.join();
			
			break;
		}

	}

	private static void register(Mode mode,Scanner sc) throws InterruptedException {
		sc.nextLine();
		System.out.println("write your username:");
		String userName=sc.nextLine();
		System.out.println("write your password:");
		String password=sc.nextLine();
		//sc.close();		
	
		switch(mode){
		case HouseCommittee:
			//Create houseCom object with password and user name
			HouseCommittee hc=new HouseCommittee(userName, password,sc);			
			//Run thread on this object which establish a connection and ask verification against DB
			Thread t=new Thread(hc);
			t.start();
			t.join();
			
			break;
		case Tenant:
			//Create Tenant object with password and user name
			Tenant ten=new Tenant(userName, password,sc);	
			//Run thread on this object which establish a connection and ask verification against DB
			Thread tt=new Thread(ten);
			tt.start();
			tt.join();
			
			break;
		}

	}
		
	




	private static Mode readUserMode(Scanner sc) {		
		while (true){
			int option=sc.nextInt();
			if (option==1) {
				return Mode.HouseCommittee;							
			}
			else if (option==2){
				return Mode.Tenant;
			}
			sc.close();
			System.out.println("wrong input! press 1 or 2");			
		}		
	}

	private static Operation readUserOperation(Scanner sc) {		
		while (true){
			int option=sc.nextInt();			
			if (option==1) {
				return Operation.Login;							
			}
			else if (option==2){
				return Operation.SignUp;
			}
			System.out.println("wrong input! press 1 or 2");			
		}		
	}
} 

