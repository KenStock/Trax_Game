package Game;

import java.util.ArrayList;

public class Cell {

	int number[];
	ArrayList<Integer> red = new ArrayList<Integer>();
	ArrayList<Integer> white = new ArrayList<Integer>();
	
	public Cell(int[] num) {
		this.number = num;
	}
	
	@Override
	public Object clone() {
		try {
			return (Cell) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			Cell cell = new Cell(this.number.clone());
			for (int p : this.red) {
				cell.red.add(p);
			}
			for (int p : this.white) {
				cell.white.add(p);
			}
			return cell;
		}
	}
	
	public int[] getNum() {
		return this.number;
	}
	
	public boolean isEmpty() {
		if (this.number[0] == 0 && this.number[1] == 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public int redNeighbour() {
		return red.size();
	}
	
	public int whiteNeighbour() {
		return white.size();
	}
}
