package MessagesHandler;



import Message.Message;

public class TenantHandler extends AbstractHandler {

	public TenantHandler(Message req) {
		super(0,req,null);
	}
	
	//@Override
	public void processMsg() {
		// TODO call different SQL against DB depending on ReqMessage.header
		System.out.println("Server got Message from Tenant!");
		
		
	}



	

}
