package MessagesHandler;

import java.util.HashMap;
import java.util.Map;

import Message.Message;
import Message.RequestMsg;
import Message.ResponseMsg;
import Message.Message.Header;

public class CommitteeHandler extends AbstractHandler {


	public Map<Integer, int[]> TenantsTable=new HashMap<Integer, int[]>();

	public CommitteeHandler(Message req) {
		super(0,req,null);
	}

	public CommitteeHandler(int reqNum, Message req, Message res) {
		super(reqNum, req, res);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void processMsg() {
		Message currMsg=super.getRequestMsg();
		Header currHeader=((RequestMsg)currMsg).getHeader();
		switch (currHeader){
		case LOGIN:
			super.setResponseMsg(new ResponseMsg(true, " ", null));
			setCommitteeCache();
			
			break;
		case GET_BUILDING_PAYMENTS_BY_APARTMENT:
			break;

			//TODO: Implement other cases

		}

	}



	private void setCommitteeCache() {
		//this.TenantsTable= //TODO implement!

	}





}
