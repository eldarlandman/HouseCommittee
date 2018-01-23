package Message;

import java.util.ArrayList;

public class RequestMsg extends Message {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Fileds
	private Header header;
	private Sender sender;
	
	//Constructors
	public RequestMsg(Header header,Sender sender, ArrayList<String> args){
		super(args);
		this.header=header;
		this.sender=sender;
	}
		
	//Methods
	public Header getHeader(){
		return header;
	}
	
	public Sender getSender(){
		return sender;
	}


	@Override
	public void ToString() {
		System.out.println("Message with header: "+ header.toString()+" from "+ sender.toString());		
	}
	
}
