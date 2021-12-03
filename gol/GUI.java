package gol;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import gol.TileManager;

class TileGrid extends JPanel {
    private int width;
    private int height;
    private int x;
    private int y;
    private Tile[][] tiles;
    
    public TileGrid(int width, int height, Tile[][] tiles) {
      this.width = width;
      this.height = height;
      x = width / 20;
      y = height / 20;
      this.tiles = tiles;
    }

    public void paintComponent(Graphics g) {
       super.paintComponent(g);
   
       Color[] colors = {Color.GRAY, Color.YELLOW};
       int lengthUnit = 20;
       
       for (int row = 0; row < y; row++) {
           for (int col = 0; col < x; ++col) {
               if (tiles[col][row].alive)
                  g.setColor(colors[1]);
               else
                  g.setColor(colors[0]);
               g.fillRect(row * lengthUnit, col * 20, 20, 20);
           }
       }
       
       g.setColor(Color.BLACK);
       for (int row = 0; row < y; row++) {
           g.drawLine(0, (row * 20), width, (row * 20));
           for (int col = 0; col < x; ++col) {
               g.drawLine((col * 20), 0, (col * 20), height);
           }
       }

   }
}

class GUI extends JPanel{
	
	private JFrame f;
   private TileGrid grid;
   private JPanel controls;
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
      controls = new JPanel(layout);
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

    
    public void pauseGame()  {
    	// Set "currRunning" to "false" so that main will
      // not update the tiles
      currRunning = false;


      nextButton.setEnabled(true);
      nextButton.setVisible(true);
      
      startButton.removeActionListener(startButton.getActionListeners()[0]);
      startButton.setText("START");
      startButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e)  {  
            startGame();
          } 
      });  
    }
	
    public void resetGame() {
      // Reset all cells to dead and pause evolution
      pauseGame();
      tm.resetTiles();
      displayTiles();
    }  
	
	public void updateTiles() {
		tm.calcNextGen();
    displayTiles();
	}


	public void displayTiles() {
      // Repain the grid with the updated tile information
      f.repaint();
      f.revalidate();
	}
   
   public boolean detectNoLife() {
      return tm.noLife();
   }


	public void setupGUI() {
      
    grid = new TileGrid(600, 600, tm.tiles);	
      
		grid.addMouseListener(new MouseAdapter() {

  
			public void mouseClicked(MouseEvent e) {
				int mouseX = e.getX() / 20;
				int mouseY = e.getY() / 20;
				if (mouseY < y && mouseX < x) {			
					tm.flipState(mouseY, mouseX);
				   displayTiles();
				}	
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
      
      nextButton = new JButton("NEXT");
      nextButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e)  {  
				nextGen();
          } 
      }); 
            
      controls.setLayout(new FlowLayout());
      controls.setLocation(0,600);
      controls.setSize(620,100);
      controls.add(startButton);
      controls.add(resetButton);
      controls.add(nextButton);


      f.setSize(620, 700);
      f.add(controls);
      f.add(grid);
      f.setVisible(true);
      f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		
      f.setResizable(false);

		f.setVisible(true);	
	}

   
   public void run() {
      
      if (currRunning)  {
         updateTiles();
         if (detectNoLife())
            resetGame();        
      }
   }
}
