package Game;

public abstract class SinglePath {

	public int[] start;
	public int[] end;
	public int startDir;
	public int endDir;
	public int score = 0;
	public int h;
	public int w;
	
	public SinglePath( int[] start, int[] end, int startDir, int endDir, int h, int w) {
		this.start = start;
		this.end = end;
		this.startDir = startDir;
		this.endDir = endDir;
		this.h= h;
		this.w = w;
	}
	
	public SinglePath(int h,int w) {
		this.startDir = -1;
		this.h= h;
		this.w = w;
	}
	
	int mod ( int x , int y ) {
		return x >= 0 ? x % y : y - 1 - ((-x-1) % y) ;
    }
	
	public abstract void addStart(int[] loc, int[] num);
	public abstract void addEnd(int[] loc, int[] num);
	public abstract void addNewPath(int[] loc, int[] num);
	
	public abstract Object clone();
	
}
