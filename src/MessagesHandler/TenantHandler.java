package MessagesHandler;



import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Message.Message;
import Message.RequestMsg;
import Message.ResponseMsg;
import Message.Message.Header;

public class TenantHandler extends AbstractHandler {
	private Map<Integer,int[]> tenantsTable=new HashMap<Integer, int[]>();
	private String username, password;
	private ResultSet TenantDetails;

	public TenantHandler(RequestMsg req) {
		super(0,req,null);
	}
	public TenantHandler(int reqNum, RequestMsg req, ResponseMsg res) {
		super(reqNum, req, res);	
		
	}

	
	//@Override
	public void processMsg() throws SQLException {
		
		Message currMsg=super.getRequestMsg();
		ArrayList<String> args=currMsg.getArgs();
		Header currHeader=((RequestMsg)currMsg).getHeader();
		switch (currHeader){
		case LOG_OUT:
			disconnect();
			setResponseMsg(new ResponseMsg(true, "User logged out", null));
			break;
		case LOGIN:
			if (checkUserCredential()){
				setResponseMsg(new ResponseMsg(true, "Welcome "+getRequestMsg().getSender()+" !", null));
				setTenantCache();
				break;
			}
			setResponseMsg(new ResponseMsg(false, "Incorrect userName or Password !", null));

			break;
		case GET_ALL_PAYMENTS_BY_MONTH://choose a  id of tenant and it will show all the payments
		
			args=currMsg.getArgs();
			int tenantId=Integer.parseInt(args.get(0));
			int[] payments=getPaymentsByTenantId(tenantId);
			
			if (payments!=null){				
				setResponseMsg(new ResponseMsg(true, "All the payments of "+tenantId+" :", wrapArgsInArrayList(payments)));
			}
			else{
				setResponseMsg(new ResponseMsg(false, "Invalid tenantId!", null));
			}
			break;
		default:
			break;
		}
		System.out.println("Server got Message from Tenant!");
		
		
	}
	private ArrayList<String> wrapArgsInArrayList(int[] payments) {
		ArrayList<String> paymentsWrapped=new ArrayList<String>();
		for (int payment: payments){
			paymentsWrapped.add(""+payment);				
		}
		return paymentsWrapped;
	}
private int[] getPaymentsByTenantId(int tenantId) {
		
		if (this.tenantsTable.get(new Integer(tenantId))!=null){
			return this.tenantsTable.get(new Integer(tenantId));
		}
		
		return null;
	}
private void setTenantCache() throws SQLException {
	int buildingID = retrieveBuildingIdFromDB();
	ResultSet idRecord=executeQueryAgainstDB(SQLCommands.getTenantsOfCommittee(buildingID));
	
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
private int retrieveBuildingIdFromDB() throws SQLException {
		
	TenantDetails=executeQueryAgainstDB(SQLCommands.getTenantDetail(this.username,this.password));
		int buildingID=-1;
		if (TenantDetails.next()){			
			buildingID=TenantDetails.getInt("building_id");
		}
		else{
			System.err.println("building id for house committee have not been set yet!");			
		}
		return buildingID;
	}
	
	@Override
	public boolean checkUserCredential() throws SQLException {
		//extract username+passowrd from abstarctHandler.requestMsg
				ArrayList<String> args=getRequestMsg().getArgs();
				String userName=args.get(0);
				String password=args.get(1);
				//Create query(username,password)
				String query=SQLCommands.loginTenant(userName, password);
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
