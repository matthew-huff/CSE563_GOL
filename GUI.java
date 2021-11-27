import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;




class GUI extends JPanel{
	
	private JFrame f;
	private JPanel panel;
	private JLabel alivePic;
	private JLabel deadPic;
	private JButton button1;
	private ArrayList[][] test;
	
	
	GUI() throws IOException{
		f = new JFrame("Game of Life");
		FlowLayout layout = new FlowLayout(0, 0, 0);
		panel = new JPanel(layout);
		panel.setSize(800, 800);
		BufferedImage alive = ImageIO.read(getClass().getResource("images/alive.png"));
		BufferedImage dead = ImageIO.read(getClass().getResource("images/dead.png"));

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
		
		
		
		f.setSize(800, 1200);
		for(int i = 0; i < 40; i++) {
			for(int j = 0; j < 40; j++) {
				JLabel deadPic = new JLabel(new ImageIcon(dead));
				panel.add(deadPic);
			}
		}
		panel.setSize(800, 800);
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
		Dimension x = new Dimension(800,800);
		panel.setMinimumSize(x);
		panel.setMaximumSize(x);
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




