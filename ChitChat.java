import java.io.*;
import java.net.*;
import java.util.*;

public class ChitChat{
	public static void main(String args[]) throws IOException{
		EServer server = new EServer();
		server.start();
		EClient client = new EClient();
		client.start();
	}
}
class EClient extends Thread{

 	public void run(){
		try{
			Scanner sc = new Scanner(System.in);
			//String str = "192.168.0.103";
			System.out.println("\nEnter IP address");
			String str = sc.nextLine();
			Socket client = new Socket(str,3000);
			
			boolean quit = false;
			BufferedReader buff = new BufferedReader(new InputStreamReader (System.in));
			OutputStream out = client.getOutputStream();
			DataOutputStream dos = new DataOutputStream(out);
			String message;
			String name = buff.readLine();
			while(!quit) {
				message = buff.readLine();
				dos.writeUTF(name + " :: " + message);
				dos.flush();
				if(message.equals("Q")){
					quit = true;
					//Thread.sleep(500);
				}
			} 		
			//System.out.println(str);
			client.close();
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
 	}
}
class EServer extends Thread{

 	public void run(){
		try{
			ServerSocket serverSocket = new ServerSocket(2000);

			while(true)
			{
				Socket client = serverSocket.accept();
				boolean quit = false;
				
				InputStream in = client.getInputStream();
				DataInputStream dis = new DataInputStream(in);
				String msg;
				
				while(!quit) {
					msg = dis.readUTF();
					System.out.println("\t\t" + msg);
					if(msg.equals("Q")){
						quit = true;
						System.out.println("quiting!");
					}
				}
				try{
					client.close();
					System.out.println("closing client socket");
				}	
				catch(Exception e){
						System.out.println(e.getMessage());
				}
			}
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
 	}
}