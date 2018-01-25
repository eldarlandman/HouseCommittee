package Message;

import java.util.ArrayList;

public class ResponseMsg extends Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Fields
	private boolean querySucceeded;
	private String msgInfo;

	//Constructors
	public ResponseMsg(boolean querySucc, String msgInfo, ArrayList<String> content){
		super(content);
		this.querySucceeded=querySucc;
		this.msgInfo=msgInfo;
	}
	
	//Methods
	public void setSucceed(boolean succeed){
		this.querySucceeded=succeed;
	}
	
	public void setMsgInfo(String msg){
		msgInfo=msg;
	}
	
	public String getMsgInfo(){
		return msgInfo;
	}
	
	public boolean isSucceed(){
		return querySucceeded==true;
	}
	
	public void ToString() {
		System.out.println("Message from your server");		
	}
	
	
	
}
