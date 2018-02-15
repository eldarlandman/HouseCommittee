package MessagesHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import Message.Message;
import Message.RequestMsg;
import Message.ResponseMsg;
import Message.Message.Header;

public class CommitteeHandler extends AbstractHandler {


	private Map<Integer,int[]> tenantsTable=new HashMap<Integer, int[]>();
	private String username, password;
	private ResultSet committeeDetails;
	private int buildingNumber;

	public CommitteeHandler(RequestMsg req) {
		super(0,req,null);
	}

	public CommitteeHandler(int reqNum, RequestMsg req, ResponseMsg res) {
		super(reqNum, req, res);	

	}
	
	@Override
	public void processMsg() throws SQLException {
		Message currMsg=super.getRequestMsg();
		ArrayList<String> args=currMsg.getArgs();
		Header currHeader=((RequestMsg)currMsg).getHeader();


		switch (currHeader){
		case LOG_OUT:
			backUpPaymentsToDB();
			disconnect();
			setResponseMsg(new ResponseMsg(true, "User logged out", null));
			break;
		case LOGIN:
			if (checkUserCredential()){
				setResponseMsg(new ResponseMsg(true, "Welcome "+getRequestMsg().getSender()+" !", null));
				setCommitteeCache();
				break;
			}
			setResponseMsg(new ResponseMsg(false, "Incorrect userName or Password !", null));

			break;
		case GET_PAYMENTS_BY_TENANT_ID://choose a  id of tenant and it will show all the payments
			args=currMsg.getArgs();
			int tenantId=Integer.parseInt(args.get(0));
			int[] payments=getPaymentsByTenantId(tenantId);

			if (payments!=null){				
				setResponseMsg(new ResponseMsg(true, "All the payments of tenants id="+tenantId+" has recived!", wrapArgsInArrayList(payments)));
			}
			else{
				setResponseMsg(new ResponseMsg(false, "Invalid tenantId!", null));
			}
			break;
		case GET_PAYMENTS_BY_APARTMENT://choose a building id and it will show all the payments
			args=currMsg.getArgs();
			int apartment_number=Integer.parseInt(args.get(0));
			payments=getPaymentsByApartment(apartment_number);
			if (payments!=null){				
				setResponseMsg(new ResponseMsg(true, "All the payments of apartment number="+apartment_number+" has recived!", wrapArgsInArrayList(payments)));
			}
			else{
				setResponseMsg(new ResponseMsg(false, "Invalid Apartment number!", null));
			}
			break;

		case UPDATE_TENANT_PAYMENT_BY_MONTH:
			tenantId=Integer.parseInt(args.get(0));
			int monthOfPayment=Integer.parseInt(args.get(1));
			int amount=Integer.parseInt(args.get(2));
			payments=getPaymentsByTenantId(tenantId);
			if (monthOfPayment>=1 && monthOfPayment<=12 && payments!=null){				
				payments[monthOfPayment-1]=amount;
				setResponseMsg(new ResponseMsg(true, "monthly payment of month "+monthOfPayment+" and tenants id="+tenantId+" was updated",null));				
			}
			else{
				setResponseMsg(new ResponseMsg(false, "Invalid tenantId or month!", null));
			}
			break;

		case DELETE_TENANT_PAYMENT_BY_MONTH:
			tenantId=Integer.parseInt(args.get(0));
			monthOfPayment=Integer.parseInt(args.get(1));
			payments=getPaymentsByTenantId(tenantId);
			if (monthOfPayment>=1 && monthOfPayment<=12 && payments!=null){				
				payments[monthOfPayment-1]=0;
				setResponseMsg(new ResponseMsg(true, "monthly payment of month "+monthOfPayment+" and tenants id="+tenantId+" deleted!",null));				
			}
			else{
				setResponseMsg(new ResponseMsg(false, "Invalid tenantId or month!", null));
			}
			break;
		case GET_BUILDING_MONTHLY_REVENUE:
			int monthlyRevenue = 0;
			monthOfPayment=Integer.parseInt(args.get(0));			
			for (int[] key : this.tenantsTable.values()){
				monthlyRevenue+=key[monthOfPayment-1];
			}
			if (monthOfPayment>=1 && monthOfPayment<=12){				

				setResponseMsg(new ResponseMsg(true, "monthy revenue has been calculated",wrapArgsInArrayList(new int[]{monthlyRevenue})));				
			}
			else{
				setResponseMsg(new ResponseMsg(false, "Invalid month!", null));
			}

			break;

		case SET_NEW_BUILDING:

			int newBuildingNumber=Integer.parseInt(args.get(0));
			String newBuildingAddress=args.get(1);
			int newBuildingCapacity=Integer.parseInt(args.get(2));
			boolean setSucceeded=setNewBuilding(newBuildingNumber, newBuildingAddress, newBuildingCapacity);
			if (setSucceeded){
				setResponseMsg(new ResponseMsg(true, "New building "+newBuildingNumber+" was added",null));
			}
			else{
				setResponseMsg(new ResponseMsg(false, "Update Failed!", null));
			}

			break;
		case GET_CONTRACTOR:
			break;
		case SET_CONTRACTOR:
			break;

		default:

			break;

			//TODO: done

		}

	}

	private void backUpPaymentsToDB() {
		for (Integer tenant_id: this.tenantsTable.keySet()){
			int[] payments=this.tenantsTable.get(tenant_id);
//			Iterator it=tenantsTable.entrySet().iterator();
//			
//			while (it.hasNext()){
//				it.next();
//			}
			for (int month=0; month<12;month++){
				try {
					updatePayment(tenant_id,payments[month],month+1);
				} catch (SQLException e) {
					System.out.printf("update for tenant %d of month %d failed!",tenant_id,month-1);
					e.printStackTrace();
				}
			}
		}
		
	}

	private void updatePayment(int tenant_id, int payment, int month) throws SQLException {
		String query=SQLCommands.updateTenantPayment(tenant_id, payment, month);
		executeUpdateAgainstDB(query);
		
	}

	private boolean setNewBuilding(int newBuildingNumber,String newBuildingAddress, int capacity) throws SQLException {
		String query=SQLCommands.setBuildingID(this.committeeDetails.getInt("id"),newBuildingNumber,newBuildingAddress,capacity);

		executeUpdateAgainstDB(query);

		return true;
	}

	private int[] getPaymentsByApartment(int apartment_number) throws SQLException {
		String query=SQLCommands.getTenantByapartment(apartment_number,this.buildingNumber);
		ResultSet idRecord=executeQueryAgainstDB(query);
		if (idRecord.next()){
			//get tenant id
			int id=idRecord.getInt("id");

			//get payments of tenant with id
			return retrievePaymentsByIdFromDB(id);
		}
		else{
			System.err.println("no body lives in apartment"+ apartment_number+"!");
			return null;
		}
	}

	private ArrayList<String> wrapArgsInArrayList(int[] payments) {
		ArrayList<String> paymentsWrapped=new ArrayList<String>();
		for (int payment: payments){
			paymentsWrapped.add(""+payment);				
		}
		return paymentsWrapped;
	}


	//input: 

	private int[] getPaymentsByTenantId(int tenantId) {

		if (this.tenantsTable.get(new Integer(tenantId))!=null){
			return this.tenantsTable.get(new Integer(tenantId));
		}


		return null;
	}
	//	private int[] getPaymentsByBuildingId(int buildingId) {
	//		
	//		if (this.tenantsTable.get(new Integer(buildingId))!=null){
	//			return this.tenantsTable.get(new Integer(buildingId));
	//		}
	//		
	//		return null;
	//	}

	private void setCommitteeCache() throws SQLException {
		retrieveBuildingIdFromDB();
		ResultSet idRecord=executeQueryAgainstDB(SQLCommands.getTenantsOfCommittee(buildingNumber));

		while (idRecord.next()){
			//get tenant id
			int id=idRecord.getInt("id");

			//get payments of tenant with id
			int[] payments=retrievePaymentsByIdFromDB(id);	

			//insert to hash map
			this.tenantsTable.put(id,payments);
		}

	}

	private int[] retrievePaymentsByIdFromDB(int tenant_id) throws SQLException {
		//loop on tenants_payments DB and where tenant_id=if_in_table take payment amount
		//insert to array
		int[] payments=new int[12];
		int month, amount;
		ResultSet paymentsRecord=executeQueryAgainstDB(SQLCommands.getTenantPayments(tenant_id) );
		while (paymentsRecord.next()){
			month=paymentsRecord.getInt("paid_month");
			amount=paymentsRecord.getInt("paid_amount");
			payments[month-1]=amount;
		}
		return payments;
	}


	private void retrieveBuildingIdFromDB() throws SQLException {

		String query=SQLCommands.getCommitteeDetails(this.username,this.password);
		this.committeeDetails=executeQueryAgainstDB(query);
		
		if (committeeDetails.next()){						
			System.out.println("committee details are available now!");
			this.buildingNumber=committeeDetails.getInt("building_id");
		}
		else{
			System.err.println("building id for house committee have not been set yet!");			
		}

	}

	@Override
	public boolean checkUserCredential() throws SQLException {
		//extract username+passowrd from abstarctHandler.requestMsg
		ArrayList<String> args=getRequestMsg().getArgs();
		String userName=args.get(0);
		String password=args.get(1);
		//Create query(username,password)
		String query=SQLCommands.login(userName, password);
		//answer=Execute query()
		ResultSet answer=executeQueryAgainstDB(query);
		//return answer- true if the cursor is on a valid row;
		//false if there are no rows in the result set
		if (answer.first()){
			this.username=userName;
			this.password=password;
			return true;
		}
		return false; 
	}






}
