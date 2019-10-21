package Game;

public class SinglePathWhite extends SinglePath {
	
	public SinglePathWhite(int[] start, int[] end, int startDir, int endDir, int h, int w) {
		super(start,end,startDir,endDir,h,w);
	}
	
	public SinglePathWhite(int h, int w) {
		super(h,w);
	}
	
	public void addStart(int[] loc, int[] num) {
		int i = loc[0];
		int j = loc[1];
		int[][] voisin = {{i,mod(j-1,w)},{mod(i-1,h),j},{i,mod(j+1,w)},{mod(i+1,h),j}};
		int d = 0;
		for (int k = 1; k<=4; k++) {
			if (k!=num[0] && k!=num[1] && k!= ((((this.startDir-1)+2) % 4)+1)) {
				d = k;
				i = voisin[k-1][0];
				j = voisin[k-1][1];
			}
		}
		this.startDir = d;
		this.start[0] = i;
		this.start[1] = j;
		this.score+=1;
	}
	
	public void addEnd(int[] loc, int[] num) {
		int i = loc[0];
		int j = loc[1];
		int[][] voisin = {{i,mod(j-1,w)},{mod(i-1,h),j},{i,mod(j+1,w)},{mod(i+1,h),j}};
		int d = 0;
		for (int k = 1; k<=4; k++) {
			if (k!=num[0] && k!=num[1] && k!= (((this.endDir-1)+2) % 4)+1) {
				d = k;
				i = voisin[k-1][0];
				j = voisin[k-1][1];
			}
		}
		this.endDir = d;
		this.end[0] = i;
		this.end[1] = j;
		this.score+=1;
	}
	
	public void addNewPath(int[] loc, int[] num) {
		int i = loc[0];
		int j = loc[1];
		int voisin[][] = {{i, mod(j-1,w)}, {mod(i-1,h) ,j}, {i, mod(j+1,w)}, {mod(i+1,h) ,j} };
		for (int k = 1; k < 5;k++) {
			if (k == num[0]) {}
			else if (k == num[1]) {}
			else if (this.startDir==-1) {
				this.start = voisin[k-1];
				this.startDir = k;
				this.score = 1;
			}
			else {
				this.end = voisin[k-1];
				this.endDir = k;
				this.score = 1;
			}
		}
	}
	
	public Object clone() {
		return (SinglePath) new SinglePathWhite(this.start.clone(), this.end.clone(), this.startDir, this.endDir, this.h, this.w);
	}
	
}
