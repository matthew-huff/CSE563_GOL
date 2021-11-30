package gol;

class Tile  {
   public boolean alive;
   public boolean aliveNext;
}

public class TileManager {
	public Tile[][] tiles;
	int x;
	int y;
	
	public TileManager(int xSize, int ySize) {
		x = xSize;
		y = ySize;
      
      tiles = new Tile[x][y];
      for(int i = 0; i < x; i ++) {
			for(int j = 0; j < y; j++) {
				tiles[i][j] = new Tile();
			}
		}

	}
	
	public void resetTiles() {
		for(int i = 0; i < x; i ++) {
			for(int j = 0; j < y; j++) {
				tiles[i][j].alive = false;
			}
		}
	}
   
   public void calcNewState(int xCoord, int yCoord) {
		int liveAdjacent = liveNeighbors(xCoord, yCoord);
      
		if (tiles[xCoord][yCoord].alive) {
			if(liveAdjacent > 3 || liveAdjacent < 2) {
				tiles[xCoord][yCoord].aliveNext = false;
			}
         else {
				tiles[xCoord][yCoord].aliveNext = true;
			}
		
		}
      else {
			if(liveAdjacent == 3) {
				tiles[xCoord][yCoord].aliveNext = true;
			}
         else {
				tiles[xCoord][yCoord].aliveNext = false;
			}
		}
	}
	
	public void calcNextGen() {
		for(int i = 0; i < x; i ++) {
			for(int j = 0; j < y; j++) {
				calcNewState(i, j);
			}
		}
      
      for(int i = 0; i < x; i ++) {
			for(int j = 0; j < y; j++) {
				tiles[i][j].alive = tiles[i][j].aliveNext;
			}
		}
	}
	
	public void flipState(int i, int j) {
      tiles[i][j].alive = !tiles[i][j].alive;
	}
	
	public int liveNeighbors(int i, int j) {
      int count = 0;  
      
		for(int k = -1; k < 2; k++) {
			for(int l = -1; l < 2; l++) {
				if(i+k >= 0 && i+k < x && j+l >= 0  && j+l < y) {
					if(!(k == 0 && l == 0)) {
                  if (tiles[i+k][j+l].alive)
                     count++;
					}
				}
			}
		}
      
      return count;
	}
   
   public boolean noLife() {
      for(int i = 0; i < x; i ++) {
			for(int j = 0; j < y; j++) {
				if (tiles[i][j].alive)
               return false;
			}
		}
      return true;
	}

}
