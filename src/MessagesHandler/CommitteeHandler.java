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

public class CommitteeHandler extends AbstractHandler {


	public Map<Integer, int[]> TenantsTable=new HashMap<Integer, int[]>();

	public CommitteeHandler(RequestMsg req) {
		super(0,req,null);
	}

	public CommitteeHandler(int reqNum, RequestMsg req, ResponseMsg res) {
		super(reqNum, req, res);
		
	}

	@Override
	public void processMsg() throws SQLException {
		Message currMsg=super.getRequestMsg();
		Header currHeader=((RequestMsg)currMsg).getHeader();
		switch (currHeader){
		case LOGOUT:
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
		case GET_BUILDING_PAYMENTS_BY_APARTMENT:
			break;
		case DELETE_PAYMENTS:
			break;
		case GET_BUILDING_PAYMENTS_BY_MONTH:
			break;
		case GET_CONTRACTOR:
			break;
		case GET_MONTHLY_REVENUE:
			break;
		case GET_TENANTS_PAYMENTS:
			break;
		case INSERT_CONTRACTOR:
			break;
		case UPDATE_PAYMENTS:
			break;
		default:
			break;

			//TODO: Implement other cases

		}

	}


	//input: 
	
	private void setCommitteeCache() {
		//this.TenantsTable= //TODO implement: Bring chache from DB

	}

	@Override
	public boolean checkUserCredential() throws SQLException {
		//extract username+passowrd from abstarctHandler.requestMsg
		ArrayList<String> args=getRequestMsg().getArgs();
		String userName=args.get(0);
		String password=args.get(1);
		//Create query(username,password)
		String query=createQueryForLogIn(userName, password);
		//answer=Execute query()
		ResultSet answer=executeQueryAgainstDB(query);
		//return answer- true if the cursor is on a valid row;
		//false if there are no rows in the result set
		return answer.first(); 
	}

	private String createQueryForLogIn(String userName, String password) {
		return "SELECT committeesTable.ID from committeesTable WHERE userName=\""+userName+ "\" AND password=\""+password+"\""; 		
	}





}
