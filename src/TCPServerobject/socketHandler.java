package TCPServerobject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.*;



public class socketHandler extends Thread  {
	Socket incoming;
	
	
	socketHandler(Socket _in  )
	{
		this.incoming=_in;
		
	}
	
	public void run()
	{
	
		// Main steps required:
//		1. Get a connection to database
//		2. Create a statement 
//		3. Execute SQL query
//		4. process the result set
		Object clientObject; 
		ObjectInputStream inFromClient = null;
		DataOutputStream outToClient = null ;
		try {
			 inFromClient = new ObjectInputStream (incoming.getInputStream());
			 outToClient = new DataOutputStream(incoming.getOutputStream()); 
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   double sum=0 ; 
	   double count=0;
	   double result=0;
	
	   try {
		   Person s = null;
		   Tenant l=null;
		  
          while (true){
			
        	  System.out.println("thread"+ Thread.currentThread().getName()+ "is establishing connection per user");
        	  clientObject = inFromClient.readObject();
		
			if( clientObject instanceof Student){
				 s = (Student) clientObject;
			}
				

			
			
			 count= s.getPayment();
			 outToClient.writeByte(count+"/n");
          }
         
	   } catch (ClassNotFoundException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	}

