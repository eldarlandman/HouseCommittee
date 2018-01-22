package TCPServerobject;

import java.io.*; 
import java.net.*; 
import java.sql.*;

class TCPServer { 
    
  public static void main(String argv[]) throws Exception 
    { 
	// Main steps required:
//		1. Get a connection to database
	  Connection myConn= DriverManager.getConnection("jdbc:mysql://localhost:3306/world","eldar","1234");
//		2. Create a statement 
	  Statement myStmt= myConn.createStatement();
//		3. Execute SQL query
	  ResultSet myRs= myStmt.executeQuery("select * from city");
//		4. process the result set  
	  while (myRs.next()){
		  System.out.println(myRs.getString("Name"));
	  }
//	  ServerSocket s = null;
//	 
//		try {
//		    s = new ServerSocket(10000);
//		   
//		} catch(IOException e) {
//		    System.out.println(e);
//		    System.exit(1);
//		}
//
//		while (true) {
//		    Socket incoming = null;
//		    
//		    try {
//			incoming = s.accept();
//			
//		    } catch(IOException e) {
//			System.out.println(e);
//			continue;
//		    }
//
//		    new socketHandler(incoming).start();
//
//		}
    } 
} 
