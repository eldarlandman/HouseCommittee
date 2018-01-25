package MessagesHandler;



import Message.Message;
import Message.RequestMsg;

public class TenantHandler extends AbstractHandler {

	public TenantHandler(RequestMsg req) {
		super(0,req,null);
	}
	
	//@Override
	public void processMsg() {
		// TODO Implement- same concept like Committee
		System.out.println("Server got Message from Tenant!");
		
		
	}



	

}
