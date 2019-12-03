package project.src.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Client extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4755391665426476304L;

	private JButton loginButton;
	
	private JButton resetButton;
	
	private JLabel usernameL;
	
	private JLabel hostAddressL;
	
	private JLabel serverPortL;
	
	private JPanel jPanel;
	
	private JTextField username;
	
	private JTextField hostAddress;
	
	private JTextField serverPort;

	public Client (String name){
		
		super(name);
		
		intiComponents();
		
	}
	
	private void intiComponents(){
		
		jPanel = new JPanel();
		
		usernameL=new JLabel();
		hostAddressL = new JLabel();
		serverPortL = new JLabel();
		
		username = new JTextField(15);
		hostAddress = new JTextField(15);
		serverPort = new JTextField(15);
		
		loginButton = new JButton();
		resetButton = new JButton();
		
		jPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Login"));
		
		usernameL.setText("User");
		hostAddressL.setText("Server");
		serverPortL.setText("Port");
		
		loginButton.setText("Login");
		resetButton.setText("Reset");
		
		loginButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Client.this.login(e);
			}
		});
		
		resetButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Client.this.username.setText("");
				Client.this.hostAddress.setText("");
				Client.this.serverPort.setText("");
			}
		});
		
		username.setText("admin");
		hostAddress.setText("localhost");
		serverPort.setText("8266");
		
		jPanel.add(usernameL);
		jPanel.add(username);
		jPanel.add(hostAddressL);
		jPanel.add(hostAddress);
		jPanel.add(serverPortL);
		jPanel.add(serverPort);
		
		jPanel.add(loginButton);
		jPanel.add(resetButton);
		
		this.getContentPane().add(jPanel);
		
		this.setSize(250, 300);
		this.setVisible(true);	
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setAlwaysOnTop(false);
		this.setResizable(false);
		
	}
	
	private void login(ActionEvent e){
		
		String username = this.username.getText();
		String hostAddress=this.hostAddress.getText();
		String serverPort=this.serverPort.getText();
		
		ClientConnection cc=new ClientConnection(username, hostAddress, Integer.parseInt(serverPort),this);//客户端连接服务器端
			
		if(cc.login()){
			
			cc.start();
			
		}

		}
		
	
	
	public static void main(String[] args) {
		
		new Client("Client");
		
	}
	
}
