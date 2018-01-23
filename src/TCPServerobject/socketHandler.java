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
					concreteHandler=new TenantHandler(msg);
				}
				else{
					concreteHandler=new CommitteeHandler(msg); //TODO same logic in TenantHandler
				}
			}
			else{
				System.out.println("Server got Message not instance of RequestMessage");
			}
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/////////////////////////////////////////////Handling Client Message STARTS HERE/////////////////////////////////////////////
		
		while (this.connected.get())
		{
			
			concreteHandler.processMsg(); 
			
			
			//			Msg msg = receive request messsage
			//					Response res = this.concreteHandler.processMessage(msg)
			//					send res
		}
	}
}

