package MessagesHandler;



import java.sql.SQLException;

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

	@Override
	public boolean checkUserCredential() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}



	

}
