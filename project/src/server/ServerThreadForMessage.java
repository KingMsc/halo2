package project.src.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Collection;
import java.util.Set;

import project.src.util.CheckUtil;

public class ServerThreadForMessage extends Thread{
	
	private Server server;
	
	private InputStream is;
	
	private OutputStream os;

	public ServerThreadForMessage(Socket socket, Server server) {
		
		try {
			
			this.is = socket.getInputStream();
			this.os = socket.getOutputStream();	
			this.server = server;
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	
	public InputStream getIs() {
		return is;
	}

	public OutputStream getOs() {
		return os;
	}

	
	public void updateAllUsers(){
		
	
		Set<String> set=this.server.getMap().keySet();
		
		String str="";
		
		for(String s:set){
			str+=s+"\n";
		}	
		this.server.getUsersArea().setText(str);
		
	
		
		Collection<ServerThreadForMessage> collection=this.server.getMap().values();
		
		for (ServerThreadForMessage serverThreadForMessage : collection) {
			
			serverThreadForMessage.sendMessage(str, CheckUtil.USER_LIST);
			
		}
	}
	
	
	
	
	
	public void sendMessage(String message, int mark){
		
		String sMessage=mark+"#"+message;
		
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
				
				String userInfo=new String(buffer).trim();
				
				String[] receivedMessage = userInfo.split("#");  
				
				int mark=Integer.parseInt(receivedMessage[0]);  
				
				String content=receivedMessage[1]; 
				
				if(CheckUtil.CLIENT_MESSAGE==mark){
					
					
					Set<String> set=this.server.getMap().keySet();
					
					for (String string : set) {
						
						ServerThreadForMessage st=this.server.getMap().get(string);
						
						st.sendMessage(content, CheckUtil.CLIENT_MESSAGE);
												
					}
					
				}
				
				if(CheckUtil.CLOSE_CLIENT_WINDOW==mark){
					
					   this.sendMessage("close", mark);
					   this.server.getMap().remove(content);
					   this.updateAllUsers();
					   this.is.close();
					   this.os.close();
					   break;   									
				}
					
				
				
				
				
				
				
			}   catch (IOException e) {
				
				e.printStackTrace();
				
			}
			
	}
		
	}
	
}
