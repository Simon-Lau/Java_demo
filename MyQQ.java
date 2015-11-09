/**
 filaName； MyQQ.java
 author：Ping Liu
 date；2011.11.01
 version 1.0
 description：implement port to port communication
 */
import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.ServerSocket;

     //主类定义了MyFrame
    public class MyQQ
    {
    	public static void main(String[] args)
    	{
    		MyFrame frame=new MyFrame();
    		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    		frame.setVisible(true);
    	}
    }
    //实现了主类Frame，带了一个菜单选项
    class MyFrame extends JFrame
    {
       	public static final int DEFAULT_WIDTH=508;
        public static final int DEFAULT_HEIGHT=620;
        public static final int DEFAULT_LEFT=300;
        public static final int DEFAULT_TOP=100;    
      	public MyFrame()
     	{   			
   	   	setTitle("My QQ");
   	   	setBounds(DEFAULT_LEFT,DEFAULT_TOP,DEFAULT_WIDTH,DEFAULT_HEIGHT);   	 
   	   	JMenuBar menuBar = new JMenuBar();
   	   	setJMenuBar(menuBar);
   	   	JMenu menu = new JMenu("System");
        menuBar.add(menu);
        JMenuItem openItem = new JMenuItem("setting");
        menu.add(openItem);
        openItem.addActionListener(new FileOpenListener());
        JMenuItem exitItem = new JMenuItem("exit");
        menu.add(exitItem);
        exitItem.addActionListener(new 
        ActionListener()
        {
        	public void actionPerformed(ActionEvent event)
        	{
        		System.exit(0);   //点击退出键的时候退出程序
        	}
        });
         add(new panel());      
        
     	}     	    
      private class FileOpenListener implements ActionListener
      {
      	public void actionPerformed(ActionEvent event)
      	{
      		  Frame2 frame=new Frame2();    		 
    		    frame.setVisible(true);
      	}
      }
    }
    class panel extends JPanel
    {
    	private JTextArea ta1;
    	private JTextField tf2;
    	private JTextField tf3;
    	private JButton bt2;
    	private JScrollPane Jp;
    	private JFileChooser chooser1;
    	private JFileChooser chooser2;
    	private	String file2=null;
    	private String[] data=new String[5];
    	private byte[] p=new byte[1024];
    	private String record=null;
    	private DatagramSocket ds;
    	private Socket socket;
    	private DataInputStream in;
    	public panel()
    	{
    		setSize(500,600);
    		setLayout(null);
    		JLabel LB1=new JLabel("Chat Dialog");
    		JLabel LB2=new JLabel("Nick Name:");
    		LB1.setFont(new Font("SansSerif", Font.BOLD, 14));
    		LB2.setFont(new Font("SansSerif", Font.BOLD, 14));
    		ta1=new JTextArea();
    		tf2=new JTextField();
    		tf3=new JTextField();
    		Jp=new JScrollPane(ta1);
    	  JButton bt1=new JButton("transfer file"); 
    	  bt2=new JButton("receive file");
    	  bt2.setEnabled(false); 
    	  JButton bt3=new JButton("send"); 
    	  JButton bt4=new JButton("Start");
    		add(LB1);
    		add(LB2);
    	  add(tf2);
    	  add(tf3);
    	  add(bt1);
    	  add(bt2);
    	  add(bt3);
    	  add(bt4);
    	  add(Jp);
    		LB1.setBounds(10,20,100,20);
    		LB2.setBounds(10,360,50,30);
    	  Jp.setBounds(10,50,480,300);
    		tf2.setBounds(60,360,420,30);
    		tf3.setBounds(10,400,480,100);
    		bt1.setBounds(30,520,100,30);
    		bt2.setBounds(200,520,100,30);
    		bt3.setBounds(380,520,100,30);
    		bt4.setBounds(300,20,100,20);
    		bt2.addActionListener(new SaveFileListener());
    		bt1.addActionListener(new SendFileListener());    		
    		bt3.addActionListener(new SendTextListener());
    		bt4.addActionListener(new StartListener());    		
    		chooser1 = new JFileChooser(); 
    		chooser2 = new JFileChooser();
      }

    	//定义了一个start按钮
    	private class StartListener implements ActionListener
    	{
	    		public void actionPerformed(ActionEvent event)
	    		{
		      	 String ustring;
		      	 int i=0;	      	 
		      	 try
		      	 {
			      	 RandomAccessFile fos=new RandomAccessFile("Data.txt","r");
				  	   while((ustring =fos.readLine())!=null)   
				  	    {        	    	      
					         data[i]=ustring;                
					         i++;           		          
			          }
			          fos.close();
			          StartRunText st=new StartRunText();
			          StartRunFile sf=new StartRunFile();
	              new Thread(st).start();                 //两个线程           
	              new Thread(sf).start();
			    	 }
			    	   catch(IOException e)
		    	     {e.getMessage();}			    	     
	    		}
    			private class StartRunText implements Runnable    //开始text传送，打开receive
    			{
    				 byte[] buf=new byte[1024];    	
    				 public void run()
    				 {
		    				try
    			    	{  
    			    		 	ds=new DatagramSocket(Integer.parseInt(data[0]));	
		    				   while(true)
				          {  					          		          	 	    		 
					    		  DatagramPacket getPacket=new DatagramPacket(buf,1024);
					    		  ds.receive(getPacket);   			    		   
					    		  String s=new String(buf,0,getPacket.getLength()); 	
					    		  StringTokenizer t=new StringTokenizer(s,"#");
					    		  String a=t.nextToken();
					    		  String b=t.nextToken();
					    		  String c=t.nextToken();
					    		  ta1.append(b+"\r\n");
					    		  ta1.append(a+":  "+c+"\r\n");	 
				    		  }	
				    		}
				    		catch(IOException e)
			    	     {e.getMessage();}	  
	    	     }   
    			}
    			private class StartRunFile implements Runnable        //开始文件传送，打开seversocket
    			{
    				public void run()
    				{    						
    					try 
    					{	    					
    						ServerSocket serverSocket =new ServerSocket(Integer.parseInt(data[1]));    						
           		  while(true)
           		  {
	    						socket=serverSocket.accept();	 
	    						in=new DataInputStream(socket.getInputStream());   
	    						String temp=in.readUTF();     						  							
	    			  		if(temp!=null)
	    			  		{  
	    			  			  ta1.append("Do you want to receive "+temp+"?"+"\r\n");
	    			  			  bt2.setEnabled(true);  
	    			  		}				 					
    					  }											    		
    					}
    					catch(IOException e)
    					{
    						e.getMessage();
    					}
    				}
    			}
    	}
    	private class SaveFileListener implements ActionListener     //保存文件
    	{
    		public void actionPerformed(ActionEvent event)
    		{    			 
    				byte[] buf = new byte[1024];   			
    				String file2=null;
    				int result = chooser2.showSaveDialog(panel.this);  
    				chooser2.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);  			 
    			  if(result == JFileChooser.APPROVE_OPTION)
    			  {    			  	  
    			  	  file2= chooser2.getSelectedFile().getPath();    			  	  
    			  }
    			  try
    			  {   in=new DataInputStream(socket.getInputStream());   			  	      			  		
	  			  	  RandomAccessFile fis = new RandomAccessFile(file2, "rw");			  			  	   					    		
			    			int num = in.read(buf,0,1024);
			    			while(num!=-1)
			    			{
			    				fis.write(buf, 0, num);
			    				num=in.read(buf,0,1024);
			    			}
				    			in.close();
				    			fis.close();	    	 
    			  }
    			  catch(IOException e)
    		     { e.getMessage(); }
    		}
    	}  
    	private class SendFileListener implements ActionListener   //发送文件的监听器
    	{
    		String  file1=null;
    		public void actionPerformed(ActionEvent event)
    		{
    			  int result = chooser1.showOpenDialog(panel.this);    			 
    			  if(result == JFileChooser.APPROVE_OPTION)
    			  {
    			  	  file1= chooser1.getSelectedFile().getPath();  
    			  	  file2= chooser1.getSelectedFile().getName();    			  	  
    			  }
    			  try
    			  {
    			   FileInputStream fos= new FileInputStream(file1);
    			   InetAddress IP;
						 IP=InetAddress.getByName(data[2]);
    			   Socket so=new Socket(IP,Integer.parseInt(data[4])); 
    				 DataOutputStream out = new DataOutputStream(so.getOutputStream());    			
    				 byte[] buf = new byte[1024];    			
    				 byte[] p=file2.getBytes();
    				 out.writeUTF(file2);
    				 int num=fos.read(buf);
    				 while(num!=-1)
    				 {    				
    				 	out.write(buf,0,num);
    				 	out.flush();
    				 	num=fos.read(buf);
    				 }
	    				 fos.close();
	    				 out.close();
    		    }
    		    catch(IOException e)
    		    {e.printStackTrace();}
    		}
    	} 
    
    		private class SendTextListener implements ActionListener  //发送文本的监听器
    	{
    		public void actionPerformed(ActionEvent event)
    		{
    			try
    			{	   					  	  					     
			       	 String s2=tf2.getText();
			       	 String s3=tf3.getText();
			       	 InetAddress IP;
			       	 IP=InetAddress.getByName(data[2]);
			       	 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				       String time=df.format(new Date());
			       	 record=s2+"#"+time+"#"+s3;
			       	 ta1.append(time+"\r\n");
			       	 ta1.append(s2+":  "+s3+"\r\n");
			       	 byte[] p=record.getBytes( );			       	 
			       	 FileWriter fin=new FileWriter("Record.txt",true);			  					  		
						   fin.write(record+"\r\n");			  						  	
						   fin.flush();
						   fin.close();   						          
		    			 DatagramPacket dataPacket=new DatagramPacket(p,record.length(),IP,Integer.parseInt(data[3]));              
		    			 ds.send(dataPacket);	
    	    }
    	     catch(IOException e)
    	    {	e.getMessage(); }
    		}
    	} 
    
    }
   // 配置文件窗口
    class Frame2 extends JFrame
    {
       	public static final int DEFAULT_WIDTH=308;
        public static final int DEFAULT_HEIGHT=320;
        public static final int DEFAULT_LEFT=200;
        public static final int DEFAULT_TOP=100;    
    	public Frame2()
    	{
    	setTitle("设置");
    	setBounds(DEFAULT_LEFT,DEFAULT_TOP,DEFAULT_WIDTH,DEFAULT_HEIGHT);
    	setVisible(true);
    	add(new panel2(this));
      }
    
    }
    class panel2 extends JPanel
    {
    	 	private JTextField tf1;
    	 	private JTextField tf2;
    	 	private JTextField tf3;
    	 	private JTextField tf4;
    	 	private JTextField tf5;
    	 	private JFrame frame2=null;
    	 	private String[] data=new String[5];
    	 	 	 	 	 	
    	public panel2(JFrame frame2)
    	{
    		this.frame2=frame2;
    		setLayout(null);
    		setSize(300,300);
    		JLabel lb1=new JLabel("text receive port：");
    		JLabel lb2=new JLabel("file receive port");
    		JLabel lb3=new JLabel("target IP address");
    	  JLabel lb4=new JLabel("target text port：");
    	  JLabel lb5=new JLabel("target file port");
    	  lb1.setFont(new Font("SansSerif", Font.BOLD, 14));
    		lb2.setFont(new Font("SansSerif", Font.BOLD, 14));
    		lb3.setFont(new Font("SansSerif", Font.BOLD, 14));
    		lb4.setFont(new Font("SansSerif", Font.BOLD, 14));
    		lb5.setFont(new Font("SansSerif", Font.BOLD, 14));
    	  tf1=new JTextField();
    	  tf2=new JTextField();
    	  tf3=new JTextField();
    	  tf4=new JTextField();
    	  tf5=new JTextField();
    	  try
    	  {
    	  	GetDta();
    	  	tf1.setText(data[0]);
    	  	tf2.setText(data[1]);
    	  	tf3.setText(data[2]);
    	  	tf4.setText(data[3]);
    	  	tf5.setText(data[4]);
    	  	
    	  }
    	  catch(IOException e)
    	  {
    	  	e.getMessage();
    	  }
    	  JButton bt1=new JButton("Yes");
        JButton bt2=new JButton("No");
    		add(lb1);
    		add(lb2);
    		add(lb3);
    		add(lb4);
    		add(lb5);
    		add(tf1);
    		add(tf2);
    		add(tf3);
    		add(tf4);
    		add(tf5);
    		add(bt1);
    		add(bt2);
    		lb1.setBounds(10,10,110,30);
    		lb2.setBounds(10,50,110,30);
    		lb3.setBounds(10,90,110,30);
    		lb4.setBounds(10,130,110,30);
    		lb5.setBounds(10,170,110,30);
    		tf1.setBounds(130,10,150,30);
    		tf2.setBounds(130,50,150,30);
    		tf3.setBounds(130,90,150,30);
    		tf4.setBounds(130,130,150,30);
    		tf5.setBounds(130,170,150,30);
    		bt1.setBounds(30,230,100,30);
    		bt2.setBounds(150,230,100,30);
    		bt1.addActionListener(new SureListener());
    		bt2.addActionListener(new CancelListener());
    	}
    	  public void GetDta()throws IOException
      {
      	 String ustring;
      	 int i=0;
      	 RandomAccessFile fos=new RandomAccessFile("Data.txt","r");
	  	   while((ustring =fos.readLine())!=null)   
	  	    {        	    	      
		         data[i]=ustring;                
		         i++;                
          }
          fos.close();
      }
    	private class SureListener implements ActionListener
    	{
    		public void actionPerformed(ActionEvent event)
    		{
    			data[0]=tf1.getText();
    			data[1]=tf2.getText();
    			data[2]=tf3.getText();
    			data[3]=tf4.getText();
    			data[4]=tf5.getText();
    			try
    			{
    				FileWriter fis=new FileWriter("Data.txt");
			  		for(int i=0;i<5;i++)
			  		{
			  			fis.write(data[i]+"\r\n");			  			
			  		}
			  		fis.flush();
			  		fis.close();			  	
			  		frame2.dispose();
    			}				
    			catch(IOException e)
    			{
    				e.getMessage();
    			}
    		}
    	}
    	private class CancelListener implements ActionListener
    	{
    		public void actionPerformed(ActionEvent event)
    		{
    			frame2.dispose();
    		
    		}
    	}
    }