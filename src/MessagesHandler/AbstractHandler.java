package MessagesHandler;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.atomic.AtomicBoolean;

import Message.Message;

public abstract class AbstractHandler{
	
	//Fields
	public int requestsNumber; //count the number of requests from user	
	private Message requestMsg, responseMsg;
	//Constructors
	public AbstractHandler(int reqNum,  Message req, Message res){
		this.requestsNumber=reqNum;
		this.setRequestMsg(req);
		this.setResponseMsg(res);
	}
	
	//Abstract Methods
	public abstract void processMsg();
	//public abstract void buildResponseMsg();
	
	
	//Concrete methods
	private static ResultSet executeQueryAgainstDB(String query) throws SQLException {
		// Main steps required:
		//		1. Get a connection to database
		Connection myConn= DriverManager.getConnection("jdbc:mysql://localhost:3306/world","eldar","1234");
		//		2. Create a statement 
		Statement myStmt= myConn.createStatement();
		//		3. Execute SQL query
		ResultSet myRs= myStmt.executeQuery(query);
		//		4. process the result set  
		return myRs;
	}

	public void sendMsg(ObjectOutputStream stream){
		try {
			stream.writeObject(getResponseMsg());
		} catch (IOException e) {
			System.out.println("Problem with sending "+getResponseMsg().toString()+" message to client");
			e.printStackTrace();
		}
	}


	public Message getRequestMsg() {
		return requestMsg;
	}


	protected void setRequestMsg(Message requestMsg) {
		this.requestMsg = requestMsg;
	}


	protected Message getResponseMsg() {
		return responseMsg;
	}


	protected void setResponseMsg(Message responseMsg) {
		this.responseMsg = responseMsg;
	}

//	public Message getRequestMsg() {
//		return RequestMsg;
//	}
//
//	
//
//	public void setResponseMsg(Message buildRequestMsg) {
//		this.ResponseMsg=buildRequestMsg
//		
//	}
	
}
