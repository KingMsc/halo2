package project.src.client;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import project.src.util.CheckUtil;

public class ChatClient extends JFrame
{ 
	/**
	 * 
	 */
	private static final long serialVersionUID = 3452901554774086218L;
	private JButton jButton1;
	private JButton jButton2;
	private JPanel jPanel1;
	private JPanel jPanel2;
	private JPanel jPanel3;
	private JScrollPane jScrollPane1;
	private JScrollPane jScrollPane2;
	private JTextArea jTextArea1;
	private JTextArea jTextArea2;
	private JTextField jTextField;
	
	private ClientConnection clientConnection;
	
	public ChatClient(ClientConnection clientConnection)
	{
		this.clientConnection = clientConnection;
		
		initComponents();
	}
	public ChatClient()
	{
		//this.clientConnection = clientConnection;
		
		initComponents();
	}

	public JTextArea getJTextArea2()
	{
		return jTextArea2;
	}

	public JTextArea getJTextArea1()
	{
		return jTextArea1;
	}
	
	
	

	private void initComponents()
	{
		jPanel1 = new JPanel();
		jScrollPane1 = new JScrollPane();
		jTextArea1 = new JTextArea(15,50);
		jTextField = new JTextField(20);
		jButton1 = new JButton();
		jButton2 = new JButton();
		
		jPanel2 = new JPanel();
		jScrollPane2 = new JScrollPane();
		jTextArea2 = new JTextArea();

		jPanel3 = new JPanel();
		
		

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("ROOM");
		setResizable(false);
		
		jPanel1.setBorder(BorderFactory.createTitledBorder("Chat Room Information"));
		jPanel2.setBorder(BorderFactory.createTitledBorder("Online User List"));
		
		jTextArea1.setColumns(30);
		jTextArea1.setRows(25);

		jTextArea2.setColumns(20);
		jTextArea2.setRows(25);

		this.jTextArea1.setEditable(false);
		this.jTextArea2.setEditable(false);
		
		jPanel3.add(jTextField);
		jPanel3.add(jButton1);
		jPanel3.add(jButton2);

		jPanel1.setLayout(new BorderLayout());
		jPanel1.add(jScrollPane1, BorderLayout.NORTH);
		jPanel1.add(jPanel3, BorderLayout.SOUTH);   

		jPanel2.add(jScrollPane2);
		

		jScrollPane1.setViewportView(jTextArea1);
		jScrollPane2.setViewportView(jTextArea2);
		jTextArea1.selectAll();
//---------------------
	
		jButton1.setText("Send/Enter");
		jButton2.setText("Clean");
		
		jButton1.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				ChatClient.this.sendMessage(e);
				
			}
		});
		/*
		 * 实现回车发送
		 */
		
		jTextField.addActionListener(new ActionListener() {
	            
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	ChatClient.this.sendMessage(e);
	            		              	               
	            }
	        });
		
		
		
		
		this.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				ChatClient.this.clientConnection.sendMessage("client close", 5);
			}
			
		});
		
		jButton2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				ChatClient.this.getJTextArea1().setText("");
				
			}
		});
		

		this.setLayout(new FlowLayout());
		this.getContentPane().add(jPanel1);
		this.getContentPane().add(jPanel2);


		this.pack();
		this.setVisible(true);
	}
	
	
	private void sendMessage(ActionEvent event)
	{
		
		
		
		String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
//		光标移到最下实现滚动条最下，异常！		
		
		jTextArea1.selectAll();
	
	
		
		// 用户聊天的数据
		String message =(time)+"\n"+this.clientConnection.getUsername()+": "+this.jTextField.getText();
		// 清空聊天数据
		this.jTextField.setText("");
		// 向服务器端发送聊天数据
		this.clientConnection.sendMessage(message, CheckUtil.CLIENT_MESSAGE);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
