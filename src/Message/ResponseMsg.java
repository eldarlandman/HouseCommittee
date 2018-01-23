package Message;

import java.util.ArrayList;

public class ResponseMsg extends Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Fields
	private boolean querySucceeded;
	private String failureMsg;

	//Constructors
	public ResponseMsg(boolean querySucc, String failure, ArrayList<String> args){
		super(args);
		this.querySucceeded=querySucc;
		this.failureMsg=failure;
	}
	
	//Methods
	public void setSucceed(boolean succeed){
		this.querySucceeded=succeed;
	}
	
	public void setFailureMsg(String msg){
		failureMsg=msg;
	}
	
	public String getFailureMsg(){
		return failureMsg;
	}
	
	public boolean isSucceed(){
		return querySucceeded==true;
	}
	
	public void ToString() {
		System.out.println("Message from your server");		
	}
	
}
