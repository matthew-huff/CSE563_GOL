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
   private MouseAdapter mouseListener;
	
	public boolean currRunning = false;
	
   private int x = 30;
	private int y = 30;
	private TileManager tm = new TileManager(x, y);
	
	GUI() throws IOException{
		f = new JFrame("Game of Life");
		FlowLayout layout = new FlowLayout(0, 0, 0);
		panel = new JPanel(layout);
      controls = new JPanel(layout);
	   BufferedImage alive = ImageIO.read(getClass().getResource("../images/alive.png"));
		BufferedImage dead = ImageIO.read(getClass().getResource("../images/dead.png"));
    }
    
	
	public void nextGen() throws IOException {
		try {
			updateTiles();
		}
		catch(IOException e1) {
			e1.printStackTrace();
		}
		
	}
	
    public void startGame() {
      // Set "currRunning" to "true" so that main will
      // regularly update the tiles
      currRunning = true;
      panel.removeMouseListener(mouseListener);
      nextButton.setEnabled(false);
      nextButton.setVisible(false);
      
      // Toggle the "start" button to become "pause"
      startButton.removeActionListener(startButton.getActionListeners()[0]);
      startButton.setText("PAUSE");
      startButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e)  {  
            pauseGame();
          } 
      });
    }
    
    public void pauseGame()  {
    	// Set "currRunning" to "false" so that main will
      // not update the tiles
      currRunning = false;
      panel.addMouseListener(mouseListener);
      nextButton.setEnabled(true);
      nextButton.setVisible(true);
      
      // Toggle the "pause" button to become "start"
      startButton.removeActionListener(startButton.getActionListeners()[0]);
      startButton.setText("START");
      startButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e)  {
				startGame();
          }
      });  
    }
    
    public void resetGame() throws IOException  {
      // Reset all cells to dead and pause evolution
      tm.resetTiles();
      displayTiles();
      pauseGame();
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
				if(!tm.tiles[i][j].alive) {
					JLabel deadPic = new JLabel(new ImageIcon(dead));
					panel.add(deadPic);
	        	 }
				else {
					JLabel alivePic = new JLabel(new ImageIcon(alive));
					panel.add(alivePic);
				}
				
	         }
		}
      panel.revalidate();
	}
   
   public boolean detectNoLife() {
      return tm.noLife();
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
      
      mouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int mouseX = e.getX() / 20;
				int mouseY = e.getY() / 20;
				if (mouseY < y && mouseX < x) {			
					tm.flipState(mouseY, mouseX);
				   try {
						displayTiles();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}	
			}
      };		
      
		panel.addMouseListener(mouseListener);
		
      startButton = new JButton("START");
      startButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e)  {  
            startGame();
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
      controls.setSize(620,100);
      controls.add(startButton);
      controls.add(resetButton);
      controls.add(nextButton);
   
      panel.setSize(620, 600);
      panel.setLocation(0, 0);
      f.setSize(620, 700);
   
      f.add(controls);
      f.add(panel);
		
      f.setResizable(false);
		f.setVisible(true);
      f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   } 

}




