package gol;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import gol.TileManager;




class GUI extends JPanel{
	
	private JFrame f;
	private JPanel panel;
   private JPanel controls;
	private JLabel alivePic;
	private JLabel deadPic;
	private JButton startButton;
   private JButton resetButton;
   private JButton nextButton;
	private ArrayList[][] test;
	private int x = 30;
	private int y = 30;
	
	//states of the game
	private String off = "OFF";
	private String on = "ON";
	private String paused = "PAUSED";
	private String running = "RUNNING";
	
	private String state;
	
	private boolean currRunning = false;
	
	private TileManager tm = new TileManager(x, y);
	
	GUI() throws IOException{
		state = off;
		f = new JFrame("Game of Life");
		FlowLayout layout = new FlowLayout(0, 0, 0);
		panel = new JPanel(layout);
      controls = new JPanel(layout);
	   BufferedImage alive = ImageIO.read(getClass().getResource("../images/alive.png"));
		BufferedImage dead = ImageIO.read(getClass().getResource("../images/dead.png"));
    }
    
	
	public void nextGen() throws IOException {
		System.out.println("Next gen");
		try {
			updateTiles();
		}
		catch(IOException e1) {
			e1.printStackTrace();
		}
		
	}
	
    public void startGame() throws IOException  {
      state = on;
      startButton.removeActionListener(startButton.getActionListeners()[0]);
      startButton.setText("PAUSE");
      startButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e)  {  
            pauseGame();
          } 
      });
      ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
      //System.out.println("START"); // placeholder
      currRunning = true;
      ses.scheduleAtFixedRate(new Runnable() {
    	   @Override
    	   public void run() {
    	      try {
    	    	  
				updateTiles();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	      if(!currRunning) {
    	    	  ses.shutdown();
    	      }
    	   }
    	}, 0, 1, TimeUnit.SECONDS);
      
     
    }
    
    public void pauseGame()  {
    	state = off;
      startButton.removeActionListener(startButton.getActionListeners()[0]);
      startButton.setText("START");
      startButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e)  {  
            try {
				startGame();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
          } 
      });  
      currRunning = false;
      
      System.out.println("PAUSE"); // placeholder
      
      // Pause evolution
    }
    
    public void resetGame() throws IOException  {
      System.out.println("RESET"); // placeholder
      tm.resetTiles();
      displayTiles();
      // Pause evolution and reset all cells to dead
    }  
	
	public ArrayList<Integer> getLocOfClick(int x, int y){
		ArrayList<Integer> i = new ArrayList<Integer>();
		int X = (int) x / 20;
		int Y = (int) y / 20;
		i.add(X);
		i.add(Y);
		return i;
	}
	
	public void updateTiles() throws IOException {
		tm.calcNextGen();
		displayTiles();
	}
	
	public void displayTiles() throws IOException {
		panel.removeAll();
		BufferedImage alive = ImageIO.read(getClass().getResource("../images/alive.png"));
		BufferedImage dead = ImageIO.read(getClass().getResource("../images/dead.png"));
		for(int i = 0; i < x; i++) {
			for(int j = 0; j < y; j++)   {
				if(tm.tiles.get(i).get(j).get_current_state() == 0) {
					JLabel deadPic = new JLabel(new ImageIcon(dead));
					panel.add(deadPic);
	        	 }
				else {
					JLabel alivePic = new JLabel(new ImageIcon(alive));
					panel.add(alivePic);
				}
				
	         }
		}
		
		f.add(panel);
		f.setVisible(true);
		//tm.printTiles();
	}
	
	public void setupGUI() throws IOException {
		BufferedImage alive = ImageIO.read(getClass().getResource("../images/alive.png"));
		BufferedImage dead = ImageIO.read(getClass().getResource("../images/dead.png"));
		
      for(int i = 0; i < x; i++) {
         for(int j = 0; j < y; j++)   {
			JLabel deadPic = new JLabel(new ImageIcon(dead));
			panel.add(deadPic);
         }
		}
      
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int mouseX = e.getX();
				int mouseY = e.getY();
				if(mouseY < y*20) {
					int idx = getLocOfClick(mouseX, mouseY).get(0);
					int idy = getLocOfClick(mouseX, mouseY).get(1);
					
					if(state == off || state == paused) {
						tm.flipState(idy, idx);
						try {
							displayTiles();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					
					return;
				}
				
			}
		});
		
      startButton = new JButton("START");
      startButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e)  {  
            try {
				startGame();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
          } 
      });      
      resetButton = new JButton("RESET");
      resetButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e)  {  
            try {
				resetGame();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
          } 
      });    
      
      nextButton = new JButton("NEXT");
      nextButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e)  {  
            try {
				nextGen();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
          } 
      }); 
            
      controls.setLayout(new FlowLayout());
      controls.setLocation(0,600);
      controls.setSize(600,100);
      controls.add(startButton);
      controls.add(resetButton);
      controls.add(nextButton);
   
      panel.setSize(600, 600);
      f.setSize(600, 700);
   
      f.add(controls);
      f.add(panel);
		
      f.setResizable(false);
		f.setVisible(true);	
	}
	
	
	
    public static void main(String args[])  
    {  
    	try {
			GUI g = new GUI();
			g.setupGUI();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
    }  

}




