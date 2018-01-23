package TCPClientobject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import Message.RequestMsg;

import Message.Message;
import Message.Message.Header;
import Message.Message.Sender;

public class HouseCommittee extends Person  implements Runnable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int seniority;
	private String userName;
	private String password;
	//Data bases for chache data:
	Map<Integer, int[]> tenants=new HashMap<Integer, int[]>(); 
	int sumOfpayments;
	//TODO ....more

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
						System.out.println("Tenant inserted apartment number: "+apartment);
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
				System.out.println("user "+this.getName() + "doesn't exist!");
				System.out.println("Exiting...");
				clientSocket.close();
			}
			sc.close();
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
		Message msg=new RequestMsg(Header.LOGIN, Sender.COMMITTEE, args);
		outToServer.writeObject(msg);		
		return inFromServer.readLine().equals("OK"); //TODO wait for respnse Object

	}

}
