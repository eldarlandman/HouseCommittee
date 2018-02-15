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
		GET_PAYMENTS_BY_APARTMENT,
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
				case GET_PAYMENTS_BY_APARTMENT:					
					getBuildingPaymentsByApartament();
					break;
					
				case SET_NEW_BUILDING:
					
					setNewBuilding();
					break;
				case UPDATE_TENANT_PAYMENT_BY_MONTH:									
					updateTenantPaymentByMonth();
					break;
				case DELETE_TENANT_PAYMENT_BY_MONTH:					
					deleteTenantPaymentByMonth();
					break;
				case GET_BUILDING_MONTHLY_REVENUE:
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
		System.out.println("This is the list of all contractors that hierd right now:");
		
		ArrayList<String> constractor=getAllConstractort();
		
		 displayConstractor(constractor);
	}

	private ArrayList<String> getAllConstractort() {
		
		return null;
	}

	private void displayConstractor (ArrayList<String> constractor) {
		
	}

	private void getBuildingMonthlyRevenue() throws ClassNotFoundException, IOException {
		
		System.out.println("input number of month :");//for January insert 0.....
		int month=Integer.parseInt(sc.nextLine());
		int revenue=getMonthlyRevenue(month);
		displayMonthlyRevenue(month,revenue);
	}

	
	private void displayMonthlyRevenue(int month,int revenue) {
		System.out.printf("The revenue for month %d is %d",month,revenue);
		System.out.println("\n_______________________________________\n");
	}

	private int getMonthlyRevenue(int month) throws IOException, ClassNotFoundException {
		
		Message msg=new RequestMsg(Header.GET_BUILDING_MONTHLY_REVENUE, Sender.COMMITTEE, wrapArgInArrayList(month)); //Message (instead of RequestMsg) for Possible future abstraction usage on serverSide
		outToServer.writeObject(msg);
		ResponseMsg response=(ResponseMsg)inFromServer.readObject();
		System.out.println(response.getMsgInfo());
		if (response.isSucceed()){
			
			return Integer.parseInt(response.getArgs().get(0));
		}
		else{
			System.err.println("operation failed");
			return -1;
		}
		
	}

	private void deleteTenantPaymentByMonth() throws ClassNotFoundException, IOException {
		
		System.out.println("input tenant id :");
		int tenant_id=Integer.parseInt(sc.nextLine());
		System.out.println("input number of month :");//for January insert 0.....
		int month=Integer.parseInt(sc.nextLine());
		deleteTenantPaymentByMonth(tenant_id,month);
	}

	private void deleteTenantPaymentByMonth(int tenant_id, int month) throws IOException, ClassNotFoundException {
		ArrayList<String> args=new ArrayList<String>();
		args.add(tenant_id+""); args.add(month+"");
		Message msg=new RequestMsg(Header.DELETE_TENANT_PAYMENT_BY_MONTH, Sender.COMMITTEE, args); //Message (instead of RequestMsg) for Possible future abstraction usage on serverSide
		outToServer.writeObject(msg);
		ResponseMsg response=(ResponseMsg)inFromServer.readObject();
		System.out.println(response.getMsgInfo());
		System.out.println("\n_______________________________________\n");
		
	}

	private void updateTenantPaymentByMonth() throws ClassNotFoundException, IOException {
		// TODO Auto-generated method stub
		System.out.println("input tenant id :");
		int tenant_id=Integer.parseInt(sc.nextLine());
		System.out.println("input number of month :");//for January insert 0.....
		int month=Integer.parseInt(sc.nextLine());
		System.out.println("input payment amount :");
		int amount=Integer.parseInt(sc.nextLine());
		updateTenantPaymentByMonth(tenant_id,month,amount);
	}

	private void updateTenantPaymentByMonth(int tenant_id, int month, int amount) throws IOException, ClassNotFoundException {
		ArrayList<String> args=new ArrayList<String>();
		args.add(tenant_id+""); args.add(month+""); args.add(amount+"");
		Message msg=new RequestMsg(Header.DELETE_TENANT_PAYMENT_BY_MONTH, Sender.COMMITTEE, args); //Message (instead of RequestMsg) for Possible future abstraction usage on serverSide
		outToServer.writeObject(msg);
		ResponseMsg response=(ResponseMsg)inFromServer.readObject();
		System.out.println(response.getMsgInfo());
		System.out.println("\n_______________________________________\n");
		
	}

	private void getBuildingPaymentsByApartament() throws ClassNotFoundException, IOException {

		System.out.println("input apartment number:");
		int apartment_number=Integer.parseInt(sc.nextLine());
		ArrayList<String> apartment_payments=getPaymentsOfBuilding(apartment_number);
		displayPayments(apartment_payments);
		// TODO Auto-generated method stub
		
	}



	private void setNewBuilding() throws ClassNotFoundException, IOException {
		
		System.out.println("type building id:");

		int building_id=Integer.parseInt(sc.nextLine());
		System.out.println("type building address:");
		String building_address=sc.nextLine();
		System.out.println("type building capacity:");
		int building_cap=Integer.parseInt(sc.nextLine());
		ArrayList<String> args=new ArrayList<String>(Arrays.asList(building_id+"",building_address+"",building_cap+""));
		Message msg=new RequestMsg(Header.SET_NEW_BUILDING, Sender.COMMITTEE, args); //Message (instead of RequestMsg) for Possible future abstraction usage on serverSide
		outToServer.writeObject(msg);
		ResponseMsg response=(ResponseMsg)inFromServer.readObject();
		System.out.println(response.getMsgInfo());
		
		
		
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
		System.out.println("\n_______________________________________\n");
		
	
	}
	private options getOptionFromInt(int parseInt) {
		switch(parseInt){
		case 1:
			return options.LOG_OUT; 			
		case 2:
			return options.GET_PAYMENTS_BY_TENANT_ID;
		case 3:
			return options.GET_PAYMENTS_BY_APARTMENT;
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
		System.out.println("3. Get Monthly Payments of a practicular apartment");
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
		Message msg=new RequestMsg(Header.GET_PAYMENTS_BY_APARTMENT, Sender.COMMITTEE, wrapArgInArrayList(arg)); //Message (instead of RequestMsg) for Possible future abstraction usage on serverSide
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
	

