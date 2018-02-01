package MessagesHandler;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Authenticator.RequestorType;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.atomic.AtomicBoolean;

import com.mysql.jdbc.CallableStatement;

import Message.Message;
import Message.RequestMsg;
import Message.ResponseMsg;

public abstract class AbstractHandler{
	
	//Fields
	public int requestsNumber; //count the number of requests from user	
	private RequestMsg requestMsg;
	private ResponseMsg responseMsg;
	private boolean connected;
	
	//Constructors
	public AbstractHandler(int reqNum,  RequestMsg req, ResponseMsg res){
		this.requestsNumber=reqNum;
		this.setRequestMsg(req);
		this.setResponseMsg(res);
		connect(); //At this point, when abstractHandler instance is created, we already have socket with client
		
	}
	
	//Abstract Methods
	public abstract void processMsg() throws SQLException;
	public abstract boolean checkUserCredential() throws SQLException;
		
	//public abstract void buildResponseMsg();
	
	
	//Concrete methods
	protected void connect(){
		this.connected=true;
	}
	
	protected void disconnect(){
		this.connected=false;
	}
	
	protected static ResultSet executeQueryAgainstDB(String query) throws SQLException {
		// Main steps required:
		//		1. Get a connection to database
		Connection myConn= DriverManager.getConnection("jdbc:mysql://localhost:3306/housecommittee","eldar","1234");
		//		2. Create a statement 
		Statement myStmt= myConn.createStatement();
		
		//		3. Execute SQL query
		
		ResultSet myRs= myStmt.executeQuery(query);
		//		4. process the result set  
		return myRs;
	}

	public void sendMsg(ObjectOutputStream stream){
		try {
			stream.writeObject(getResponseMsg()); //WRITE TO CLIENT the response message!
		} catch (IOException e) {
			System.out.println("Problem with sending '"+getResponseMsg().toString()+"' to client");
			e.printStackTrace();
		}
	}


	public RequestMsg getRequestMsg() {
		return requestMsg;
	}


	public void setRequestMsg(RequestMsg requestMsg) {
		this.requestMsg = requestMsg;
	}


	public ResponseMsg getResponseMsg() {
		return responseMsg;
	}


	public void setResponseMsg(ResponseMsg responseMsg) {
		this.responseMsg = responseMsg;
	}

	public boolean isAlive() {
		return this.connected;		
	}

	
}
