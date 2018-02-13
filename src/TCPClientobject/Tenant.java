package TCPClientobject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import Message.Message;
import Message.RequestMsg;
import Message.ResponseMsg;
import Message.Message.Header;
import Message.Message.Sender;
//import TCPClientobject.HouseCommittee.options;

public class Tenant extends Person implements Runnable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private enum options{
		LOG_OUT,
		GET_ALL_PAYMENTS_BY_MONTH}

	public String apartament;
	public double payment;
	public double sizeApartament;
	private String userName;
	private String password;
	private boolean exit;
	private Scanner sc;
	private ObjectOutputStream outToServer;
	private ObjectInputStream inFromServer;
	
	public Tenant(String userName, String password, Scanner sc){
		this.setPassword(password);
		this.setUserName(userName);
		this.sc=sc;
		this.exit=false;
	}
	
	
	public String getApartament() {
		return this.apartament;
	}
	public void setApartament(String apartament) {
		this.apartament = apartament;
	}
	public double getPayment() {
		return this.payment;
	}
	public void setPayment(double payment) {
		this.payment = payment;
	}
	public double getSizeApartament() {
		return this.sizeApartament;
	}
	public void setSizeApartament(double sizeApartament) {
		this.sizeApartament = sizeApartament;
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
			ObjectInputStream inFromServer = new ObjectInputStream(clientSocket.getInputStream() );
			this.outToServer=outToServer;
			this.inFromServer=inFromServer;
			
			while (!userExist(outToServer,inFromServer)){		
				
				System.out.println("write your username:");
				this.userName=sc.nextLine();
				System.out.println("write your password:");
				this.password=sc.nextLine();	
			}
				while (!exit){

					displayMenu();
					String input=sc.nextLine();
					options option=getOptionFromInt(Integer.parseInt(input));
					switch (option){
					case LOG_OUT: //logout
						logOut(outToServer, inFromServer);
						break;
					case GET_ALL_PAYMENTS_BY_MONTH: //logout
						//TODO -->
						//display all the payment by id
						getAllPaymentsByMonth();
						
						break;
					default: 
						
						break;
					}
			}
			
		}
		catch (IOException | ClassNotFoundException e) {
			System.out.println("error");
			
		}
	}
	private void getAllPaymentsByMonth() {
		// TODO Auto-generated method stub
		
	}


	private void logOut(ObjectOutputStream outToServer, ObjectInputStream inFromServer) throws IOException, ClassNotFoundException {
		Message msg=new RequestMsg(Header.LOG_OUT, Sender.TENANT, null); //Message (instead of RequestMsg) for Possible future abstraction usage on serverSide
		outToServer.writeObject(msg);
		ResponseMsg response=(ResponseMsg)inFromServer.readObject();
		System.out.println(response.getMsgInfo());
		exit=true;
		
		}
		private void displayMenu() {
			System.out.println("Choose one of the foloowing options:");
			System.out.println("1. LogOut");
			System.out.println("2. Get All The Payments that have been paid");
			
		}
		private options getOptionFromInt(int parseInt) {
			switch(parseInt){
			case 1:
				return options.LOG_OUT; 			
			case 2:
				return options.GET_ALL_PAYMENTS_BY_MONTH;
			
				
			default:
				System.out.println("wrong input!");
			}
			return null;
			
				
		}
	private boolean userExist(ObjectOutputStream outToServer, ObjectInputStream inFromServer) throws IOException, ClassNotFoundException {
		ArrayList<String> args=new ArrayList<String>(Arrays.asList(this.userName,this.password));
		Message msg=new RequestMsg(Header.LOGIN, Sender.TENANT, args); //Message (instead of RequestMsg) for Possible future abstraction usage on serverSide
		outToServer.writeObject(msg);
		ResponseMsg response=(ResponseMsg)inFromServer.readObject();
		System.out.println(response.getMsgInfo());
		return response.isSucceed();

	}
}
