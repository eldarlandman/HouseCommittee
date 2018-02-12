package TCPClientobject;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import java.util.ArrayList;
import java.util.Arrays;

import java.util.Scanner;

import com.sun.xml.internal.bind.v2.Messages;

import Message.RequestMsg;
import Message.ResponseMsg;
import Message.Message;
import Message.Message.Header;
import Message.Message.Sender;

public class HouseCommittee extends Person  implements Runnable{
	/**
	 * 
	 */
	private enum options{LOG_OUT}//TODO Fill all options
	private static final long serialVersionUID = 1L;
	public int seniority;
	private String userName;
	private String password;
	private boolean exit;
	int sumOfpayments;
	private ObjectOutputStream outToServer;
	//Data bases for chache data:
	private ObjectInputStream inFromServer;
	

	public HouseCommittee(String userName, String password){
		this.setPassword(password);
		this.setUserName(userName);
		this.exit=false;
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
			ObjectInputStream inFromServer = new ObjectInputStream(clientSocket.getInputStream() );
			this.outToServer=outToServer;
					this.inFromServer=inFromServer;
			
			//Scanner sc=new Scanner(System.in);
			while (!userExist()){				
				BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
				System.out.println("write your username:");
				this.userName=br.readLine();
				System.out.println("write your password:");
				this.password=br.readLine();
				br.close();
			}

			
			//start exchanging data with the server
			while (!exit){
				BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
				displayMenu();
				Message.Header options=userOption();
						
				switch (options){
				case LOGOUT: //
					logOut();
					clientSocket.close();
					break;
					//send
				case GET_TENANTS_PAYMENTS:
					System.out.println("insert tenant id:");
					
					ArrayList<String> payments=getPaymentsOfTenant(br.read());
					displayPayments(payments);
				default:
					
					break;
					

				}
				
				br.close();
			}
			


		}
		catch (IOException | ClassNotFoundException e) {
			System.out.println("ERROR");
		}

		//outToServer.writeObject();	

	}

	private void displayPayments(ArrayList<String> payments) {
		System.out.println("payments should be displayed here!"); //TODO: implement display payments to house_committee
		
	}

	private Header userOption() throws IOException {
		
		
			//String input=br.readLine();
		/*int index=Integer.parseInt(input);
		while (!(1<=index && index<=8)){
			System.out.println("wrong input! try again");
			displayMenu();
			index=Integer.parseInt(br.readLine());
			
		}*/
		//	Scanner input=new Scanner(System.in);
			String opt="2";
		switch (opt){
		case "1": return Message.Header.LOGOUT;
		case "2": return Message.Header.GET_TENANTS_PAYMENTS;
		case "3": return Message.Header.GET_BUILDING_PAYMENTS_BY_APARTMENT;
		case "4": return Message.Header.SET_NEW_BUILDING;
		case "5": return Message.Header.UPDATE_PAYMENTS;
		case "6": return Message.Header.DELETE_PAYMENTS;
		case "7": return Message.Header.GET_MONTHLY_REVENUE;
		case "8": return Message.Header.GET_CONTRACTOR;
		case "9":return Message.Header.INSERT_CONTRACTOR;
		}
		//input.close();
		return null;
		
	}

	private ArrayList<String> getPaymentsOfTenant(int arg) throws IOException, ClassNotFoundException {
		Message msg=new RequestMsg(Header.GET_TENANTS_PAYMENTS, Sender.COMMITTEE, wrapArgInArrayList(arg)); //Message (instead of RequestMsg) for Possible future abstraction usage on serverSide
		outToServer.writeObject(msg);
		ResponseMsg response=(ResponseMsg)inFromServer.readObject();
		System.out.println(response.getMsgInfo());
		
		return response.getArgs();
	}

	private void displayMenu() {
//	case 1: return Message.Header.LOGOUT;
//	case 2: return Message.Header.GET_TENANTS_PAYMENTS;
//	case 3: return Message.Header.GET_BUILDING_PAYMENTS_BY_APARTMENT;
//	case 4: return Message.Header.GET_BUILDING_PAYMENTS_BY_MONTH; changed_>:should be set a new building
//	case 5: return Message.Header.UPDATE_PAYMENTS;
//	case 6: return Message.Header.DELETE_PAYMENTS;
//	case 7: return Message.Header.GET_MONTHLY_REVENUE;
//	case 8: return Message.Header.GET_CONTRACTOR;
//	case 9:return Message.Header.INSERT_CONTRACTOR;
		System.out.println("Choose one of the foloowing options:");
		System.out.println("1. LogOut");
		System.out.println("2. Get Monthly Payments of a practicular tenant");
		System.out.println("3. Get Monthly Revenue of a practicular building");
		System.out.println("4. Set New building ");
		System.out.println("5. Update Monthly Payments of a practicular tenant  ");
		System.out.println("6. Delete Payments of a practicular tenant  ");
		System.out.println("7. Get Monthly Income of a practicular building  ");
		System.out.println("8. Show All contractor that employed right now");
		System.out.println("9. Set new contractor that was hired  ");
		

	}
	private void logOut() throws IOException, ClassNotFoundException {
		Message msg=new RequestMsg(Header.LOGOUT, Sender.COMMITTEE, null); //Message (instead of RequestMsg) for Possible future abstraction usage on serverSide
		outToServer.writeObject(msg);
		ResponseMsg response=(ResponseMsg)inFromServer.readObject();
		System.out.println(response.getMsgInfo());
		exit=true;
		
		
	}

	private boolean userExist() throws IOException, ClassNotFoundException {
		ArrayList<String> args=new ArrayList<String>(Arrays.asList(this.userName,this.password));
		Message msg=new RequestMsg(Header.LOGIN, Sender.COMMITTEE, args); //Message (instead of RequestMsg) for Possible future abstraction usage on serverSide
		outToServer.writeObject(msg);
		ResponseMsg response=(ResponseMsg)inFromServer.readObject();
		System.out.println(response.getMsgInfo());
		return response.isSucceed();


	}
	
	private ArrayList<String> wrapArgInArrayList(int arg) {
		ArrayList<String> argWrapped=new ArrayList<String>();
		argWrapped.add(arg+"");
		return argWrapped;
	}

}
