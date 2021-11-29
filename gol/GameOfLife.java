package gol;
import gol.GUI;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class GameOfLife { 

   public static void main(String args[]) throws IOException, InterruptedException {
      GUI gui = new GUI();;
      
      gui.setupGUI();
      int waitTime = 50;
      
      while (true)   {
         TimeUnit.MILLISECONDS.sleep(waitTime);
         if (gui.currRunning) {
            gui.updateTiles();
            waitTime = 300;
            if (gui.detectNoLife())
               gui.resetGame();
         }
         else
            waitTime = 50;
      }
      
   }
}