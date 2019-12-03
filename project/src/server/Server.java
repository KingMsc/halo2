package project.src.server;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import project.src.util.CheckUtil;



public class Server extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 666666L;

	private JLabel serverInfo;
	
	private JLabel serverStatus;
	
	private JLabel serverPort;
	
	private JButton startServerButton;
	
	private JPanel panel1;
	
	private JPanel panel2;
	
	private JTextArea usersArea;
	
	private JScrollPane usersPane;
	
	private JTextField portText;
	
	private Map<String, ServerThreadForMessage> map=new HashMap<String, ServerThreadForMessage>();
	
	
	public Server(){
		super("Server");
		initComponts();
	}
	
	private void initComponts(){
		
		panel1= new JPanel();
		panel2= new JPanel();
		
		serverInfo = new JLabel();
		serverStatus = new JLabel();
		serverPort = new JLabel();		
		portText = new JTextField(10);
		startServerButton = new JButton();
		
		usersPane = new JScrollPane();
		usersArea=new JTextArea();
		
		panel1.setBorder(BorderFactory.createTitledBorder("Server Info"));
		panel2.setBorder(BorderFactory.createTitledBorder("Online user"));
		
		portText.setText("8266");
		
		serverStatus.setText("Server State");
		serverInfo.setText("STOP");
		serverInfo.setForeground(Color.pink);
		serverPort.setText("Port");
		startServerButton.setText("Start Server");
		
		startServerButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				Server.this.preExecute(e);
			}
			
		});
		panel1.add(serverStatus);
		panel1.add(serverInfo);
		panel1.add(serverPort);
		panel1.add(portText);
		panel1.add(startServerButton);
		
		usersArea.setEditable(false);
		usersArea.setRows(20);
		usersArea.setColumns(30);
		usersArea.setForeground(Color.blue);
		
		usersPane.setViewportView(usersArea);
		panel2.add(usersPane);
		
	
		this.getContentPane().add(panel1,BorderLayout.NORTH);
		this.getContentPane().add(panel2,BorderLayout.SOUTH);
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setAlwaysOnTop(false);	
		this.setResizable(true);
		this.pack();	
		this.setVisible(true);
		
		this.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				
				
				Collection<ServerThreadForMessage> collection=Server.this.getMap().values();
				for (ServerThreadForMessage serverThreadForMessage : collection) {
					serverThreadForMessage.sendMessage("closeAll", CheckUtil.CLOSE_SERVER_WINDOW);
				}
				System.exit(0);	
			}
			
		});
		
	}
	
	private void preExecute(ActionEvent e){
		
		String hostPort=this.portText.getText();
		
		
		if(CheckUtil.isEmpty(hostPort)){
			JOptionPane.showMessageDialog(this,"Port number cannot be empty","Error",JOptionPane.ERROR_MESSAGE); 
			return;
		}
		
		if(!(CheckUtil.isNumber(hostPort))){
			JOptionPane.showMessageDialog(this,"Port number cannot contain non-digital content","Error",JOptionPane.ERROR_MESSAGE); 
			return;
		}
		
		if(CheckUtil.isBigThan65535(hostPort)){
			JOptionPane.showMessageDialog(this,"The port number is in the range of 1024~65535.","Error",JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		int port=Integer.parseInt(hostPort);
		
		new ServerConnection(port,this).start();
		
		
	}
	
	
	
	
	
	public JLabel getServerInfo() {
		return serverInfo;
	}

	public void setServerInfo(JLabel serverInfo) {
		this.serverInfo = serverInfo;
	}

	public JButton getStartServerButton() {
		return startServerButton;
	}

	public void setStartServerButton(JButton startServerButton) {
		this.startServerButton = startServerButton;
	}

	public JTextField getPortText() {
		return portText;
	}

	public void setPortText(JTextField portText) {
		this.portText = portText;
	}

	public Map<String, ServerThreadForMessage> getMap() {
		return map;
	}

	public void setMap(Map<String, ServerThreadForMessage> map) {
		this.map = map;
	}
	
	public JTextArea getUsersArea() {
		return usersArea;
	}

	public void setUsersArea(JTextArea usersArea) {
		this.usersArea = usersArea;
	}


	public static void main(String[] args) {
		new Server();
	}
	
}
