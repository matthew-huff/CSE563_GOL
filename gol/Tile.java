package gol;
import java.util.ArrayList;

public class Tile {
	
	private ArrayList<Tile> neighbors;
	// 0 stand for dead, 1 stand for live
	private int current_state;
	private int next_state;
	
	//x and y represent location within grid
	private int x;
	private int y;
	
	public Tile(int c_state, int x, int y) {
		current_state = c_state;
		next_state = 0;
		neighbors = new ArrayList<Tile>();
		this.x = x;
		this.y = y;
	}
	
	public int get_current_state() {
		return current_state;
	}
	
	public ArrayList<Tile> getNeighborList(){
		return this.neighbors;
	}
	
	public void set_current_state(int state){
		current_state = state;
	}
	
	public void flip_current_state() {
		if(current_state == 0) {
			this.current_state = 1;
		}
		else {
			this.current_state = 0;
		}
	}
	
	public void update_state() {
		current_state = next_state;
	}
	
	public void add_new_neighbor(Tile neighbor) {
		neighbors.add(neighbor);
	}
	
	// get all the living neighbors
	private int get_all_neighbor() {
		int neighbor = 0;
		
		for(Tile i: neighbors) {
			if(i.get_current_state() == 1) {
				neighbor += 1;
			}
		}
		return neighbor;
	}
	
	// determine if this tile is dead in next round
	private boolean Die() {
		int neighbor = get_all_neighbor();
		
		// if neighbor is > 3 or < 2, the tile should be die
		if (neighbor > 3 || neighbor < 2) {
			return true;
		}
		// else the tile should stay alive
		return false;
	}
	
	// determine if a dead cell become new born or not
	private boolean new_live() {
		int neighbor = get_all_neighbor();
		
		if(neighbor == 3) {
			return true;
		}
		return false;
	}
	
	public String toString() {
		return String.format("(%d, %d)", x, y);
		//return Integer.toString(current_state);
	}
	
	public void cal_new_state() {
		// living state
		if (get_current_state() == 1) {
			if(Die() == true) {
				next_state = 0;
			}else {
				next_state = 1;
			}
		// dead state
		}else {
			if(new_live() == true) {
				next_state = 1;
			}else {
				next_state = 0;
			}
		}
	}
}