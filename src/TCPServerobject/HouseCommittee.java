package TCPServerobject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class HouseCommittee extends Person  implements Runnable{
//	public int seniority;
//	private String userName;
//	private String password;
//	
//	public HouseCommittee(String userName, String password){
//		this.setPassword(password);
//		this.setUserName(userName);
//	}
//	
//	public int getSeniority() {
//		return this.seniority;
//	}
//
//	public void setSeniority(int seniority) {
//		this.seniority = seniority;
//	}
//
//	public String getPassword() {
//		return password;
//	}
//
//	public void setPassword(String password) {
//		this.password = password;
//	}
//
//	public String getUserName() {
//		return userName;
//	}
//
//	public void setUserName(String userName) {
//		this.userName = userName;
//	}
//	
//	private static void establishServerConnection() throws UnknownHostException, IOException {
//		Socket clientSocket = new Socket("localhost", 10000); 		
//
//		ObjectOutputStream  outToServer = new ObjectOutputStream (clientSocket.getOutputStream() );
//		BufferedReader inFromServer = 
//				new BufferedReader(new
//						InputStreamReader(clientSocket.getInputStream()));
//		outToServer.writeObject();		
//		System.out.println(inFromServer.readLine());
//	}
//
//
//	@Override
//	public void run() {
//		
//		Socket clientSocket = new Socket("localhost", 10000); 		
//		ObjectOutputStream  outToServer = new ObjectOutputStream (clientSocket.getOutputStream() );
//		BufferedReader inFromServer = new BufferedReader  (new InputStreamReader(clientSocket.getInputStream()) );
//		boolean exit=false;
//		
//		while (!exit){
//			if (userExist(outToServer,inFromServer)){
//				//start exchanging data against the server
//				
//			}
//			else{
//				System.out.println("user "+this.getName() + "doesnt exist!");
//				System.out.println("Exiting...");
//				exit=true;
//			}
//		}
//		
//		//outToServer.writeObject();	
//		
//	}
//
//	private boolean userExist(ObjectOutputStream outToServer, BufferedReader inFromServer) {
//		// TODO Auto-generated method stub
//		return false;
//	}

}
