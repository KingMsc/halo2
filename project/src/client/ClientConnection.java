package project.src.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import project.src.util.CheckUtil;

public class ClientConnection extends Thread{
	
	private String username;
	
	private String hostAddress;
	
	private int serverPort;
	
	private Client client;
	
	private Socket socket;
	
	private InputStream is;
	
	private OutputStream os;
	
	private ChatClient chatClient;
	
	public ClientConnection(String username,String hostAddress, int serverPort, Client client){
		
		this.username=username;
		this.hostAddress=hostAddress;
		this.serverPort=serverPort;
		this.client=client;
		
		client2ServerConnection(hostAddress, serverPort);
		
	}
	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void client2ServerConnection(String hostAddress,int serverPort){
	
		try {
			
			socket=new Socket(hostAddress, serverPort);
			
			is=socket.getInputStream();
			
			os=socket.getOutputStream();
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean login(){
		
		String userInfo=this.username+"#"+this.hostAddress+"#"+this.serverPort;
		
		try {
			
			this.sendMessage(userInfo, CheckUtil.LOGIN);
			
			byte[] buffer= new byte[3000];
			
			is.read(buffer);
			
			String serverInfoBack=(new String(buffer)).trim();
			
			if(serverInfoBack.equals("success")){
				
				this.client.setVisible(false);
				
				chatClient=new ChatClient(this);
				
				return true;
				
			}else if(serverInfoBack.equals("failure")){
				
				JOptionPane.showMessageDialog(this.client, "Username cannot be repeated", "Notice", JOptionPane.WARNING_MESSAGE);
				
			}
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		return false;
	}
	
	public void sendMessage(String message, int mark){
			
			String sMessage=mark+"#"+message;
			
			if(mark==CheckUtil.CLOSE_CLIENT_WINDOW){
				sMessage=mark+"#"+username;
			}
			
			
			try {
				
				os.write(sMessage.getBytes());
							
			} catch (IOException e) {
				e.printStackTrace();
			}
			
	}
	
	@Override
	public void run() {
		
		while(true){

				try {
					
					byte[] buffer=new byte[3000];
					
					is.read(buffer);
					
					String serverInfo=new String(buffer).trim();
					
					String[] receivedMessage = serverInfo.split("#");  
					
					int mark=Integer.parseInt(receivedMessage[0]);  //信息的标记代号
					
					String content=receivedMessage[1]; //消息的内容
					
					if(CheckUtil.USER_LIST==mark){
						
						this.chatClient.getJTextArea2().setText(content);	
					}
					
					if(CheckUtil.CLIENT_MESSAGE==mark){
						
						content=content+"\n";
						
						this.chatClient.getJTextArea1().append(content);	
						
					}
					
					if(CheckUtil.CLOSE_CLIENT_WINDOW==mark){
						
						this.is.close();
						this.os.close();
						this.socket.close();
						System.exit(0);
						
					}
					
					if(CheckUtil.CLOSE_SERVER_WINDOW==mark){
						
						JOptionPane.showMessageDialog(this.client, "Server disconnect", "Offline", JOptionPane.WARNING_MESSAGE);
						this.is.close();
						this.os.close();
						this.socket.close();
						System.exit(0);
						
					}
									
				}   catch (IOException e) {
					
					e.printStackTrace();
					
				}
				
		}
			
	}
}
