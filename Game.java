/**
class：Game
function：implement move of rectangle
athor： Ping Liu
version：1.0
last update：2011.10.13
*/
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
//主类Game，实例化了DrawFrame
public class Game    
 {
 		public static void main(String[] args)
    {
				EventQueue.invokeLater(new Runnable()
				{
				public void run()
					{
						DrawFrame frame =new DrawFrame();
						frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						frame.setVisible(true);
					}
				});			
    }
 }
//类DrawFrame，实例化了GamePanel
    class DrawFrame extends JFrame
    {

      public DrawFrame()
      {
      	 setTitle("game");
      	 setBounds(DEFAULT_LEFT,DEFAULT_TOP,DEFAULT_WIDTH,DEFAULT_HEIGHT);      
         GamePanel panel =new GamePanel();        
         add(panel);      	
      }	
      public static final int DEFAULT_WIDTH=608;
      public static final int DEFAULT_HEIGHT=628;
      public static final int DEFAULT_LEFT=400;
      public static final int DEFAULT_TOP=100;      
    }
//类GamePanel  
    class GamePanel extends JPanel 
    {
    	
    	  public int  leftX=240;
        public int  topY=240;
        public int  width=120;
        public int  height=120;
        public int[]  left={0, 540, 0, 540};
        public int[]  top= {0, 0, 540, 540};
        public int[]  Top= {240,240,300,300};
        public int[]  Left={240,300,240,300};
        public int  width2=60;
        public int  height2=60;
        private int  keyWord=0;
        private int  keyWay=0;
        private int  keyL=0;
        private int  keyU=0;
       
        public GamePanel()
        {
            setSize(600,600);  
            MyListener listener =new MyListener();
            addKeyListener(listener);    
     	      setFocusable(true);
        }
        public void paintComponent(Graphics g)
       {
       	   super.paintComponent(g);
           g.setColor(Color.red); 
           g.drawRect(leftX,topY,width,height);
           g.drawLine(leftX+width/2,topY,leftX+width/2,topY+height);
           g.drawLine(leftX,topY+height/2,leftX+width,topY+height/2);            
           for(int i=0;i<4;i++)
           {
           	 if(i==(keyWay-1))
	           {
	            left[i]=left[i]+keyL*60;
	            top[i]=top[i]+keyU*60;
	            for(int a=0;a<4;a++)
	            {
	            	
	               if((top[i]==top[a])&&(left[i]==left[a])&&a!=i)//判断是否有重复
	               {
	               	 g.drawString("重复了",100,100);
	               	 left[i]=left[i]-keyL*60;
	                 top[i]=top[i]-keyU*60;
	                 g.setColor(Color.red);
	                 g.fillRect(left[i],top[i],width2, height2);
	               }
	               else if(top[i]>540||top[i]<0||left[i]>540||left[i]<0)//判断是否出了边界
	               {
	                 	g.drawString("out of boundary！",100,100);
	                 	left[i]=left[i]-keyL*60;
	                  top[i]=top[i]-keyU*60;
	                  g.setColor(Color.red);
	                  g.fillRect(left[i],top[i],width2, height2);
	               }	
	               else
	               {
	               	 g.setColor(Color.red);
	                 g.fillRect(left[i],top[i],width2, height2);
	               }        	                 	              
	            }	          
	           }
	           else
	           {
	            g.setColor(Color.blue);
	            g.fillRect(left[i],top[i],width2, height2);   
             } 
           }   
		           int m=match();
		           if(m==4)
		           {
		           	     g.setColor(Color.red);	           
			           	   g.drawString("congratulation, game over！",leftX,topY-120);
		           }        
            
       }
         
		       public int match()  //判断是否游戏结束
		       {
		       	 int number=0;
		       	 for(int l=0;l<4;l++)
		           {           	            	
		           	 for(int k=l;k<4;k++) 
		           	 { 
		           	 	 if(top[l]==Top[k]&&left[l]==Left[k])           	 	 
		           	 	    number++;  	               	 	         	    
				         }        	 
		           } 
		           return number;       
		       }

       public class MyListener implements KeyListener //内部类，键盘监听器
      {    	   
      	  public void keyReleased(KeyEvent k)
      	  {
      	  	 keyWord=k.getKeyCode();       
      	  	 if(keyWord==KeyEvent.VK_1)
      	  	 {     	  	 	
      	  	 	   keyL=0 ; 
      	  	 	   keyU=0;
      	  	 	   keyWay=1;        	  	 	     	  	 	        	  	 	 
      	  	 	   repaint();
      	  	 }
      	  	 if(keyWord==KeyEvent.VK_2 )
      	  	 {
                  keyL=0 ; 
      	  	 	    keyU=0;    
      	  	      keyWay=2; 
      	  	      repaint(); 
      	  	 }
      	  	 if(keyWord==KeyEvent.VK_3)
      	  	 {
                  keyL=0 ; 
      	  	 	    keyU=0;    
      	  	 	    keyWay=3; 
      	  	      repaint(); 
      	  	 }
      	  	 if(keyWord==KeyEvent.VK_4)
      	  	 {
      	   	 	    keyL=0 ; 
      	  	 	    keyU=0;    
      	  	      keyWay=4; 
      	  	      repaint(); 
      	  	 }
      	  	 if(keyWord==KeyEvent.VK_UP)
      	  	 {
                    keyU=-1; 
                    keyL=0; 
                    repaint();        
      	  	 }
      	  	 if(keyWord==KeyEvent.VK_RIGHT)
      	  	 {      	  	    
      	  	           keyL=1; 
      	  	           keyU=0;  
                     repaint(); 
      	  	 }
      	  	 if(keyWord==KeyEvent.VK_LEFT)
      	  	 {
      	  	          keyL=-1; 
      	  	          keyU=0;  
                     repaint(); 
      	  	 }
      	  	 if(keyWord==KeyEvent.VK_DOWN)
      	  	 {
      	  	 	      keyL=0;
    	  	          keyU=1;    	  	           
                    repaint(); 
      	  	 }
      	  }
      	   public void keyPressed(KeyEvent k)
      	   {
      	   	
      	   }
      	    public void keyTyped(KeyEvent k)
      	   {
      	   	
      	   }
      }
    }
       
  
