package gol;

import gol.Tile;
public class TileManager {
	ArrayList<ArrayList<Tile>> tiles;
	int x;
	int y;
	int genNum = 0;
	
	public TileManager(int x, int y) {
		tiles = new ArrayList<ArrayList<Tile>>();
		this.x = x;
		this.y = y;
		
		for(int i = 0; i < x; i ++) {
			ArrayList<Tile> e = new ArrayList<Tile>();
			tiles.add(e);
		}
		
		for(int i = 0; i < x; i ++) {
			for(int j = 0; j < y; j++) {
				Tile t = new Tile(0, i, j);
				tiles.get(i).add(t);
			}
		}
		
		for(int i = 0; i < x; i ++) {
			for(int j = 0; j < y; j++) {
				findNeighbors(i, j);	
			}
		}
	}
	
	public void resetTiles() {
		genNum = 0;
		for(int i = 0; i < x; i ++) {
			for(int j = 0; j < y; j++) {
				tiles.get(i).get(j).set_current_state(0);
			}
		}
	}
	
	public void calcNextGen() {
		for(int i = 0; i < x; i ++) {
			for(int j = 0; j < y; j++) {
				tiles.get(i).get(j).cal_new_state();
			}
		}
		for(int i = 0; i < x; i ++) {
			for(int j = 0; j < y; j++) {
				tiles.get(i).get(j).update_state();
			}
		}
		genNum++;	
	}
	
	public void flipState(int i, int j) {
		tiles.get(i).get(j).flip_current_state();
	}
	
	public void findNeighbors(int i, int j) {
		for(int k = -1; k < 2; k++) {
			for(int l = -1; l < 2; l++) {
				if(i+k >= 0 && i+k < x && j+l >= 0  && j+l < y) {
					if(!(k == 0 && l == 0)) {
						tiles.get(i).get(j).add_new_neighbor(tiles.get(i+k).get(j+l));
					}
				}
			}
		}
	}
	
	public void printTiles() {
		for(int i = 0; i < x; i ++) {
			System.out.println(tiles.get(i).toString());
		}
		System.out.println("--------------------");
	}
	
	public void printNeighbors() {
		for(int i = 0; i < x; i ++) {
			for(int j = 0; j < y; j++) {
				System.out.println(String.format("%d, %d: ", i, j) + tiles.get(i).get(j).getNeighborList());
			}
		}
		System.out.println("--------------------");
	}
	
	
}
