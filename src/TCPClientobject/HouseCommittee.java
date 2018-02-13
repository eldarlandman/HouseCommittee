package TCPClientobject;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.Scanner;



import Message.RequestMsg;
import Message.ResponseMsg;
import Message.Message;
import Message.Message.Header;
import Message.Message.Sender;

public class HouseCommittee extends Person  implements Runnable{
	

	private enum options{
		LOG_OUT,
		GET_PAYMENTS_BY_TENANT_ID,
		GET_BUILDING_PAYMENTS_BY_APARTMENT,
		SET_NEW_BUILDING,
		UPDATE_TENANT_PAYMENT_BY_MONTH,
		DELETE_TENANT_PAYMENT_BY_MONTH,
		GET_BUILDING_MONTHLY_REVENUE,
		GET_CONTRACTOR,
		SET_CONTRACTOR,}
	
	private static final long serialVersionUID = 1L;
	public int seniority;
	private String userName;
	private String password;
	private boolean exit;
	private Scanner sc;
	private ObjectOutputStream outToServer;
	private ObjectInputStream inFromServer;
	int sumOfpayments;
	//Data bases for chache data:
	

	public HouseCommittee(String userName, String password, Scanner sc){
		this.setPassword(password);
		this.setUserName(userName);
		this.sc=sc;
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

			
			//check against server this.username and this.password and returns true if server finds them in DB
			while (!userExist(outToServer,inFromServer)){		
				
				System.out.println("write your username:");
				this.userName=sc.nextLine();
				System.out.println("write your password:");
				this.password=sc.nextLine();				
			}

			
			//start exchanging data with the server
			while (!exit){

				displayMenu();
				String input=sc.nextLine();
				options option=getOptionFromInt(Integer.parseInt(input));
				switch (option){
				case LOG_OUT: //logout
					logOut(outToServer, inFromServer);
					
					break;
					
				case GET_PAYMENTS_BY_TENANT_ID:
					getPaymentsByTenantId();
					
					break;
				case GET_BUILDING_PAYMENTS_BY_APARTMENT:
					//TODO -->
					//get building id 
					//get payment of each tenant with building id that was received
					//display payments for each tenant with building id that was received
					getBuildingPaymentsByApartament();
					break;
				case SET_NEW_BUILDING:
					//TODO -->
					//get as input new tenants
					//create new building with new id and put a new tenant to building
					setNewBuilding();
					break;
				case UPDATE_TENANT_PAYMENT_BY_MONTH:
					//TODO -->
					//get as input tenant apartament_number & month
					//update payment in DB by the info from input
					updateTenantPaymentByMonth();
					break;
				case DELETE_TENANT_PAYMENT_BY_MONTH:
					//TODO -->
					//get as input apartament_number & month
					//set payment in DB by the info to zero
					deleteTenantPaymentByMonth();
					break;
				case GET_BUILDING_MONTHLY_REVENUE:
					//TODO -->
					//get as input buildingID
					//display all income of building that been selected
					getBuildingMonthlyRevenue();
					break;
				case GET_CONTRACTOR:
					//TODO -->
					//display all contractor
					getContractor();
					break;
				case SET_CONTRACTOR:
					//TODO -->
					//get as input contractor profession 
					//check if already exist 
					//if not add to DB else give out put "already exist"
					setContractor();
					
					break;
					
				default: 
					
					break;

				}					
			}
			sc.close();
	
		}
		catch (IOException | ClassNotFoundException e) {
			System.out.println("error");
			
		}

		//outToServer.writeObject();	

	}

	private void setContractor() {
		// TODO Auto-generated method stub
		System.out.println("input Constractor id:");
		int id=Integer.parseInt(sc.nextLine());
		System.out.println("input Constractor name:");
		String name=(sc.nextLine());//input string????
		System.out.println("input Constractor last name:");
		String last_name=(sc.nextLine());//input string????
		System.out.println("input Constractor phone:");
		int phone=Integer.parseInt(sc.nextLine());
		System.out.println("input Constractor profession:");
		String profession=(sc.nextLine());//input string????
	}

	private void getContractor() {
		
		//TODO displayConstractor();
	}

	private void getBuildingMonthlyRevenue() {
		// TODO Auto-generated method stub
		System.out.println("input building id:");
		int building_id=Integer.parseInt(sc.nextLine());
		System.out.println("input number of month :");//for January insert 0.....
		int month=Integer.parseInt(sc.nextLine());
	}

	private void deleteTenantPaymentByMonth() {
		// TODO Auto-generated method stub
		System.out.println("input apartament number:");
		int apartament_number=Integer.parseInt(sc.nextLine());
		System.out.println("input number of month :");//for January insert 0.....
		int month=Integer.parseInt(sc.nextLine());
	}

	private void updateTenantPaymentByMonth() {
		// TODO Auto-generated method stub
		System.out.println("input apartament number:");
		int apartament_number=Integer.parseInt(sc.nextLine());
		System.out.println("input number of month :");//for January insert 0.....
		int month=Integer.parseInt(sc.nextLine());
	}

	private void getBuildingPaymentsByApartament() throws ClassNotFoundException, IOException {
		System.out.println("input building id:");
		int building_id=Integer.parseInt(sc.nextLine());
		ArrayList<String> building_payments=getPaymentsOfBuilding(building_id);
		displayPayments(building_payments);
		// TODO Auto-generated method stub
		
	}


	private void setNewBuilding() {
		System.out.println("input Tenant id:");
		int id=Integer.parseInt(sc.nextLine());
		System.out.println("input Tenant user:");
		String user=(sc.nextLine());//input string????
		System.out.println("input Tenant password:");
		int password=Integer.parseInt(sc.nextLine());
		System.out.println("input Tenant monthly payment amount:");
		int payment_amount=Integer.parseInt(sc.nextLine());
		System.out.println("input Tenant apartament number:");
		int apartament=Integer.parseInt(sc.nextLine());
		System.out.println("input Tenant  name:");
		String name=(sc.nextLine());//input string????
		System.out.println("input Tenant last name:");
		String last_name=(sc.nextLine());//input string????
		System.out.println("input Tenant building id:");
		int building_id=Integer.parseInt(sc.nextLine());
		
		
	}
	
	private void getPaymentsByTenantId() throws ClassNotFoundException, IOException {
		System.out.println("input tenant id:");
		int id=Integer.parseInt(sc.nextLine());
		ArrayList<String> payments=getPaymentsOfTenant(id);
		displayPayments(payments);
		
	}

	private void displayPayments(ArrayList<String> payments) {
		String month[] ={"January","February","March","April","May","June","July","August","September","October","November", "December" } ;
		int i=0;
		for (String arr : payments) {
			 System.out.println("payment for "+month[i]+" = ");
            System.out.println(arr);
            i++;
		}
	
	}
	private options getOptionFromInt(int parseInt) {
		switch(parseInt){
		case 1:
			return options.LOG_OUT; 			
		case 2:
			return options.GET_PAYMENTS_BY_TENANT_ID;
		case 3:
			return options.GET_BUILDING_PAYMENTS_BY_APARTMENT;
		case 4:
			return options.SET_NEW_BUILDING;
		case 5:
			return options.UPDATE_TENANT_PAYMENT_BY_MONTH;
		case 6:
			return options.DELETE_TENANT_PAYMENT_BY_MONTH;
		case 7:
			return options.GET_BUILDING_MONTHLY_REVENUE;
		case 8:
			return options.GET_CONTRACTOR;
		case 9:
			return options.SET_CONTRACTOR;
			
		default:
			System.out.println("wrong input!");
		}
		return null;
		
			
	}

	private void displayMenu() {
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
	private void logOut(ObjectOutputStream outToServer, ObjectInputStream inFromServer) throws IOException, ClassNotFoundException {
		Message msg=new RequestMsg(Header.LOG_OUT, Sender.COMMITTEE, null); //Message (instead of RequestMsg) for Possible future abstraction usage on serverSide
		outToServer.writeObject(msg);
		ResponseMsg response=(ResponseMsg)inFromServer.readObject();
		System.out.println(response.getMsgInfo());
		exit=true;
		
		}
	//EX1
	private ArrayList<String> getPaymentsOfTenant(int arg) throws IOException, ClassNotFoundException {
		Message msg=new RequestMsg(Header.GET_PAYMENTS_BY_TENANT_ID, Sender.COMMITTEE, wrapArgInArrayList(arg)); //Message (instead of RequestMsg) for Possible future abstraction usage on serverSide
		outToServer.writeObject(msg);
		ResponseMsg response=(ResponseMsg)inFromServer.readObject();
		System.out.println(response.getMsgInfo());
		
		return response.getArgs();
	}
	//EX2
	private ArrayList<String> getPaymentsOfBuilding(int arg) throws IOException, ClassNotFoundException {
		Message msg=new RequestMsg(Header.GET_BUILDING_PAYMENTS_BY_APARTMENT, Sender.COMMITTEE, wrapArgInArrayList(arg)); //Message (instead of RequestMsg) for Possible future abstraction usage on serverSide
		outToServer.writeObject(msg);
		ResponseMsg response=(ResponseMsg)inFromServer.readObject();
		System.out.println(response.getMsgInfo());
		return response.getArgs();
	}
	
	private ArrayList<String> wrapArgInArrayList(int arg) {
		ArrayList<String> argWrapped=new ArrayList<String>();
		argWrapped.add(arg+"");
		return argWrapped;
	}

	private boolean userExist(ObjectOutputStream outToServer, ObjectInputStream inFromServer) throws IOException, ClassNotFoundException {
		ArrayList<String> args=new ArrayList<String>(Arrays.asList(this.userName,this.password));
		Message msg=new RequestMsg(Header.LOGIN, Sender.COMMITTEE, args); //Message (instead of RequestMsg) for Possible future abstraction usage on serverSide
		outToServer.writeObject(msg);
		ResponseMsg response=(ResponseMsg)inFromServer.readObject();
		System.out.println(response.getMsgInfo());
		return response.isSucceed();

	}
	
}
	

