package TCPClientobject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import TCPClientobject.myProtocol.HeaderContent;
import TCPClientobject.myProtocol.HeaderType;

public class HouseCommittee extends Person  implements Runnable{
	public int seniority;
	private String userName;
	private String password;
	//Data bases for chache data:
	Map<Integer, String> map=new Map<Integer, String>; 

	public HouseCommittee(String userName, String password){
		this.setPassword(password);
		this.setUserName(userName);
	}

	public int getSeniority() {
		return this.seniority;
	}

	public void setSeniority(int seniority) {
		this.seniority = seniority;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	private static void establishServerConnection() throws UnknownHostException, IOException {
		Socket clientSocket = new Socket("localhost", 10000); 		

		ObjectOutputStream  outToServer = new ObjectOutputStream (clientSocket.getOutputStream() );
		BufferedReader inFromServer = 
				new BufferedReader(new
						InputStreamReader(clientSocket.getInputStream()));
		outToServer.writeObject();		
		System.out.println(inFromServer.readLine());
	}



	public void run(){

		try {

			Socket clientSocket = new Socket("localhost", 10000); 		
			ObjectOutputStream  outToServer = new ObjectOutputStream (clientSocket.getOutputStream() );
			BufferedReader inFromServer = new BufferedReader  (new InputStreamReader(clientSocket.getInputStream()) );

			boolean exit=false;
			Scanner sc=new Scanner(System.in);
			if (userExist(outToServer,inFromServer)){
				
				//start exchanging data with the server
				while (!exit){
					
					displayMenu();
					int option=sc.nextInt();
					
					switch (option){
					case 1: //payments
						System.out.println("input apartment number:");
						int apartment=sc.nextInt();
						
						//build message
						
						//send
						break;
					case 2:
						break;
					case 3:
						break;
					case 4:
						break;
					case 5:
						break;						
					case 6:
						break;
					case 7:
						break;
					case 8:
						break;
					}
				}
			}
			else{
				System.out.println("user "+this.getName() + "doesnt exist!");
				System.out.println("Exiting...");
				clientSocket.close();
			}
		}
		catch (IOException e) {
			System.out.println("Cannot connect to port 1000");
		}









		//outToServer.writeObject();	

	}

	private void displayMenu() {
		System.out.println("Choose one of the foloowing options:");
		//1.
		//2.
		//..8
		
	}

	private boolean userExist(ObjectOutputStream outToServer, BufferedReader inFromServer) throws IOException {
		ArrayList<String> args=new ArrayList<String>(Arrays.asList(this.userName,this.password));
		Message msg=new Message("LOGIN","LOGIN",args,this);
		outToServer.writeObject(msg);		
		return inFromServer.readLine().equals("OK"); //TODO consider if server needs to implement message

	}

}
