package Game;

public class SinglePathRed extends SinglePath{
	
	public SinglePathRed(int[] start, int[] end, int startDir, int endDir, int h, int w) {
		super(start,end,startDir,endDir, h , w);
	}
	
	public SinglePathRed(int h, int w) {
		super(h,w);
	}
	
	public void addStart(int[] loc, int[] num) {
		int i = loc[0];
		int j = loc[1];
		int[][] voisin = {{i,mod(j-1,w)},{mod(i-1,h),j},{i,mod(j+1,w)},{mod(i+1,h),j}};
		if (this.startDir == (((num[0]-1)+2) % 4)+1) {
			this.startDir = num[1];
			i = voisin[num[1]-1][0];
			j = voisin[num[1]-1][1];
		}
		else {
			this.startDir = num[0];
			i = voisin[num[0]-1][0];
			j = voisin[num[0]-1][1];
		}
		this.start[0] = i;
		this.start[1] = j;
		this.score+=1;
	}
	
	public void addEnd(int[] loc, int[] num) {
		int i = loc[0];
		int j = loc[1];
		int[][] voisin = {{i,mod(j-1,w)},{mod(i-1,h),j},{i,mod(j+1,w)},{mod(i+1,h),j}};
		if (this.endDir == ((((num[0]-1)+2) % 4)+1)) {
			this.endDir = num[1];
			i = voisin[num[1]-1][0];
			j = voisin[num[1]-1][1];
		}
		else {
			this.endDir = num[0];
			i = voisin[num[0]-1][0];
			j = voisin[num[0]-1][1];
		}
		this.end[0] = i;
		this.end[1] = j;
		this.score+=1;
	}
	
	public void addNewPath(int[] loc, int[] num) {
		int i = loc[0];
		int j = loc[1];
		int voisin[][] = {{i, mod(j-1,w)}, {mod(i-1,h) ,j}, {i, mod(j+1,w)}, {mod(i+1,h) ,j} };
		for (int k = 1; k < 5;k++) {
			if (k == num[0]) {
				this.start = voisin[k-1];
				this.startDir = k;
				this.score = 1;
			}
			else if (k == num[1]) {
				this.end = voisin[k-1];
				this.endDir = k;
				this.score = 1;
			}
		}
	}
	
	@Override
	public Object clone() {
		return new SinglePathRed(this.start.clone(), this.end.clone(), this.startDir, this.endDir, this.h, this.w);
	}
	
}
