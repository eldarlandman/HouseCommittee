/* CAN TWO DIFFERENT SOCKETS SHARE A PORT?
 * A server socket listens on a single port.
 * All established client connections on that server are associated with that same listening port on the server side of the connection. 
 * An established connection is uniquely identified by the combination of client-side and server-side IP/Port pairs. 
 * Multiple connections on the same server can share the same server-side IP/Port pair as long as
 * they are associated with different client-side IP/Port pairs,
 * and the server would be able to handle as many clients as available system resources allow it to.
 * On the client-side, it is common practice for new outbound connections to use a random client-side port,
 *  in which case it is possible to run out of available ports if you make a lot of connections in a short amount of time.
 */

package TCPServerobject;

import java.io.*; 
import java.net.*; 
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;



class TCPServer implements Runnable{ 
	private int listenPort;
	private ServerSocket serverSocket;

	public TCPServer(int port) {
		listenPort=port;
	}

	public static void main(String argv[]) throws Exception 
	{ 

		
		
		//Scanner sc=new Scanner(System.in);
		System.out.println("insert listening number port:");		
		int port=10000;
				//sc.nextInt();
		TCPServer server=new TCPServer(port); 

		new Thread(server).start(); 

		//sc.close();
		Thread.currentThread().join(); //the main thread is waiting for sun thread to finish

	}





	public void run() {
		//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		AtomicBoolean serverAlive = new AtomicBoolean(true);
		try {
			serverSocket = new ServerSocket(listenPort);
			//serverSocket.setSoTimeout(20000); //random max time for waiting to requests from client
			System.out.println("Listening..." + serverSocket.getLocalPort());

			while (serverAlive.get()){

				Socket soc=serverSocket.accept(); //Server is waiting for client to connect				
				socketHandler newConnection = new socketHandler(soc, serverAlive);

				new Thread(newConnection).start();
				
			}
		}
		catch (IOException e) {
			System.out.println("Cannot listen on port " + listenPort);
		}


	} 
} 
