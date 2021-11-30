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
	
<<<<<<< Updated upstream
	GUI() throws IOException{
		state = off;
=======
	GUI() {
>>>>>>> Stashed changes
		f = new JFrame("Game of Life");
		FlowLayout layout = new FlowLayout(0, 0, 0);
      controls = new JPanel(layout);
    }
	
<<<<<<< Updated upstream
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
=======
	public void nextGen() {
		updateTiles();	
	}
	
    public void startGame() {
      // Set "currRunning" to "true" so that main will
      // regularly update the tiles
      currRunning = true;
      nextButton.setEnabled(false);
      nextButton.setVisible(false);
      
      // Toggle the "start" button to become "pause"
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
    	state = off;
=======
    	// Set "currRunning" to "false" so that main will
      // not update the tiles
      currRunning = false;
      nextButton.setEnabled(true);
      nextButton.setVisible(true);
      
      // Toggle the "pause" button to become "start"
>>>>>>> Stashed changes
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
    
<<<<<<< Updated upstream
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
=======
    public void resetGame() {
      // Reset all cells to dead and pause evolution
      pauseGame();
      tm.resetTiles();
      displayTiles();
    }  
	
	public void updateTiles() {
>>>>>>> Stashed changes
		tm.calcNextGen();
		displayTiles();
	}
	
<<<<<<< Updated upstream
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
=======
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
>>>>>>> Stashed changes
		
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
      controls.setSize(600,100);
      controls.add(startButton);
      controls.add(resetButton);
      controls.add(nextButton);
   
<<<<<<< Updated upstream
      panel.setSize(600, 600);
      f.setSize(600, 700);
   
=======
      f.setSize(620, 700);
      
>>>>>>> Stashed changes
      f.add(controls);
      f.add(grid);
		
      f.setResizable(false);
<<<<<<< Updated upstream
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

=======
		f.setVisible(true);
      f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   } 
   
   public void run() {
      
      if (currRunning)  {
         updateTiles();
         if (detectNoLife())
            resetGame();        
      }
   }
>>>>>>> Stashed changes
}
