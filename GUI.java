import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;




class GUI extends JPanel{
	
	private JFrame f;
	private JPanel panel;
   private JPanel controls;
	private JLabel alivePic;
	private JLabel deadPic;
	private JButton startButton;
   private JButton resetButton;
	private ArrayList[][] test;
	
	
	GUI() throws IOException{
		f = new JFrame("Game of Life");
		FlowLayout layout = new FlowLayout(0, 0, 0);
		panel = new JPanel(layout);
      controls = new JPanel(layout);
	   BufferedImage alive = ImageIO.read(getClass().getResource("images/alive.png"));
		BufferedImage dead = ImageIO.read(getClass().getResource("images/dead.png"));
    }
    
    public void startGame()  {
            
      startButton.removeActionListener(startButton.getActionListeners()[0]);
      startButton.setText("PAUSE");
      startButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e)  {  
            pauseGame();
          } 
      });    
      
      System.out.println("START"); // placeholder
      
      // Resume evolution
    }
    
    public void pauseGame()  {
      startButton.removeActionListener(startButton.getActionListeners()[0]);
      startButton.setText("START");
      startButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e)  {  
            startGame();
          } 
      });  
      
      System.out.println("PAUSE"); // placeholder
      
      // Pause evolution
    }
    
    public void resetGame()  {
      System.out.println("RESET"); // placeholder
      
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
	
	public void setupGUI() throws IOException {
		BufferedImage alive = ImageIO.read(getClass().getResource("images/alive.png"));
		BufferedImage dead = ImageIO.read(getClass().getResource("images/dead.png"));
		
      for(int i = 0; i < 30; i++) {
         for(int j = 0; j < 30; j++)   {
			JLabel deadPic = new JLabel(new ImageIcon(dead));
			panel.add(deadPic);
         }
		}

		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				int idx = getLocOfClick(x, y).get(0);
				int idy = getLocOfClick(x, y).get(1);
				return;
			}
		});
    
      startButton = new JButton("START");
      startButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e)  {  
            startGame();
          } 
      });      
      resetButton = new JButton("RESET");
      resetButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e)  {  
            resetGame();
          } 
      });      
            
      controls.setLayout(new FlowLayout());
      controls.setLocation(0,600);
      controls.setSize(600,100);
      controls.add(startButton);
      controls.add(resetButton);
   
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




