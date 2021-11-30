package gol;
import gol.GUI;
import java.util.concurrent.TimeUnit;

public class GameOfLife { 

   public static void main(String args[]) throws InterruptedException {
      GUI gui = new GUI();;
      
      gui.setupGUI();
      
      while (true)   {
         TimeUnit.MILLISECONDS.sleep(300);
         gui.run();   
      }   
   }
}