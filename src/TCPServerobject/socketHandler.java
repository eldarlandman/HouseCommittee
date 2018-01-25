package TCPServerobject;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.concurrent.atomic.AtomicBoolean;

import Message.Message;
import Message.Message.Header;
import Message.Message.Sender;
import Message.RequestMsg;
import Message.ResponseMsg;
import MessagesHandler.AbstractHandler;
import MessagesHandler.CommitteeHandler;
import MessagesHandler.TenantHandler;



public class socketHandler extends Thread  {


	Socket incoming;	
	AbstractHandler concreteHandler;
	AtomicBoolean connected;


	socketHandler(Socket in , AtomicBoolean alive )
	{
		this.incoming= in;
		connected=alive;

	}

	public void run()
	{

		// Main steps required:
		//		1. Get a connection to database
		//		2. Create a statement 
		//		3. Execute SQL query
		//		4. process the result set

		ObjectInputStream inFromClient = null;
		ObjectOutputStream outToClient = null ;
		Message msg=null;
		try {
			inFromClient = new ObjectInputStream (incoming.getInputStream());
			outToClient = new ObjectOutputStream (incoming.getOutputStream()); 
			System.out.println("thread"+ Thread.currentThread().getName()+ "is establishing connection per user");
			msg = (Message) inFromClient.readObject(); //Blocking until client send Message Object
			if (msg instanceof RequestMsg){
				Message.Sender sender=((RequestMsg)msg).getSender();
				//TENANT,COMMITTEE
				if (sender==Sender.TENANT){
					concreteHandler=new TenantHandler((RequestMsg)msg);
				}
				else{
					concreteHandler=new CommitteeHandler((RequestMsg)msg); //TODO impement CommitteeHandler- same logic in TenantHandler
				}
			}
			else{
				System.out.println("Server got Message not instance of RequestMessage");
			}
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("Problem with connection establishment");
			e.printStackTrace();
		}

		/////////////////////////////////////////////Handling Client Message STARTS HERE/////////////////////////////////////////////
		
		while (this.concreteHandler.isAlive())
		{								
				concreteHandler.processMsg();			
				concreteHandler.sendMsg(outToClient);
				try {
					readMsg(inFromClient);
				} catch (ClassNotFoundException | IOException e) {
					System.err.println("Server couldnt send message!");
					e.printStackTrace();
				}
			
			
			
			//			Msg msg = receive request messsage
			//					Response res = this.concreteHandler.processMessage(msg)
			//					send res
		}
	}

	private void readMsg(ObjectInputStream inFromClient) throws IOException, ClassNotFoundException {
		Message msg;
		msg = (Message) inFromClient.readObject();
		if (msg instanceof RequestMsg){
			concreteHandler.setRequestMsg((RequestMsg)msg);
		}
		else{
			throw new IllegalArgumentException("Server can't handle this type of message!");
		}
		
	}
}

