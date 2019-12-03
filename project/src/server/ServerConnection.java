package project.src.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerConnection extends Thread {

	private ServerSocket serverSocket;

	private Server server;
	
	//private HashMap<Socket, String> userMap = new HashMap<>();

	public ServerConnection(int port, Server server) {

		this.server = server;

		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.server.getStartServerButton().setEnabled(false);
		this.server.getPortText().setEnabled(false);
		this.server.getServerInfo().setText("R");

	}

	@Override
	public void run() {

		while (true) {
			
			try {
				
				Socket socket=serverSocket.accept();

				InputStream is = socket.getInputStream();
				OutputStream os = socket.getOutputStream();

				byte[] buffer = new byte[3000];

				is.read(buffer);

				String userInfo = (new String(buffer)).trim();
				
				String[] receivedMessage = userInfo.split("#");
				
				String username=receivedMessage[1];
				
				String loginResult = "";
				
				boolean isLogin = false;
				
				if(this.server.getMap().containsKey(username)){
					
					loginResult = "failure";
					
				}else
				{
					loginResult = "success";
					
					isLogin = true;
				}
				
			   os.write(loginResult.getBytes());
			   
			   if(isLogin==true){
				   
				   ServerThreadForMessage stfm=new ServerThreadForMessage(socket,this.server); 
				   
				   this.server.getMap().put(username, stfm);
				   
				   stfm.updateAllUsers();
				   
				   stfm.start();
				   
			   }
			   
			
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
	}

}
