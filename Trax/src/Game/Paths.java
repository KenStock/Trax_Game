package Game;

import java.util.ArrayList;

public class Paths {
	
	public ArrayList<SinglePath> pathsRed;
	public ArrayList<SinglePath> pathsWhite;
	public int h;
	public int w;
	
	public Paths(int h, int w) {
		this.pathsRed = new ArrayList<SinglePath>();
		this.pathsWhite = new ArrayList<SinglePath>();
		this.h = h;
		this.w = w;
	}
	
	@Override
	public Object clone() {
		Paths paths = null;
		try {
			paths = (Paths) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			paths = new Paths( this.h, this.w);
		}
		for (SinglePath p : this.pathsRed) {
			paths.pathsRed.add((SinglePath) p.clone());
		}
		for (SinglePath p : this.pathsWhite) {
			paths.pathsWhite.add((SinglePath) p.clone());
		}
		return paths;
	}
	
	int mod ( int x , int y ) {
		return x >= 0 ? x % y : y - 1 - ((-x-1) % y) ;
    }
	
	public boolean isEmpty(ArrayList<SinglePath> paths) {
		return paths.isEmpty();
	}
	
	public int length(ArrayList<SinglePath> paths) {
		return paths.size();
	}
	
	public int[] occurance(int[] loc, ArrayList<SinglePath> paths) {
		int[] occ = new int[paths.size()+1];
		int i = 0;
		for (int k=0; k<paths.size();k++) {
			if ( (loc[0]==paths.get(k).start[0] && loc[1]==paths.get(k).start[1]) || (loc[0]==paths.get(k).end[0] && loc[1]==paths.get(k).end[1])) {
				occ[i]=k;
				i++;
			}
		}
		occ[paths.size()]=i;
		return occ;
	}
	
	public void addCell(int[] loc, int[] num, ArrayList<SinglePath> paths) {

		int[] occ = this.occurance(loc, paths);

		if (occ[paths.size()] == 0) {
			this.newPath(loc, num, paths);
		}
		
		else if (occ[paths.size()] == 1) {
			SinglePath pathNew = paths.get(occ[0]);
			if (pathNew.start[0]==loc[0] && pathNew.start[1]==loc[1]) {
				pathNew.addStart(loc, num);
			}
			else {
				pathNew.addEnd(loc, num);
			}
		}
		
		else {
			SinglePath pathNew1 = paths.get(occ[0]);
			SinglePath pathNew2 = paths.get(occ[1]);
			if (pathNew1.start[0] == pathNew2.end[0] && pathNew1.start[1] == pathNew2.end[1]) {
				pathNew1.start = pathNew2.start;
				pathNew1.startDir = pathNew2.startDir;
				pathNew1.score += pathNew2.score+1;
				paths.remove(occ[1]);
			}
			else if (pathNew1.end[0] == pathNew2.start[0] && pathNew1.end[1] == pathNew2.start[1]){
				pathNew1.end = pathNew2.end;
				pathNew1.endDir = pathNew2.endDir;
				pathNew1.score += pathNew2.score+1;
				paths.remove(occ[1]);
			}
			else if (pathNew1.end[0] == pathNew2.end[0] && pathNew1.end[1] == pathNew2.end[1]){
				pathNew1.end = pathNew2.start;
				pathNew1.endDir = pathNew2.startDir;
				pathNew1.score += pathNew2.score+1;
				paths.remove(occ[1]);
			}
			else {
				pathNew1.start = pathNew2.end;
				pathNew1.startDir = pathNew2.endDir;
				pathNew1.score += pathNew2.score+1;
				paths.remove(occ[1]);
			}
//			System.out.println("fusion de deux chemin");
		}
	}
	
	public void newPath(int[] loc, int[] num, ArrayList<SinglePath> paths) {
		if (paths.toString().equals(this.pathsRed.toString())) {
			SinglePath newP = new SinglePathRed(h,w);
			newP.addNewPath(loc, num);
			paths.add(newP);
		}
		else {
			SinglePath newP = new SinglePathWhite(h,w);
			newP.addNewPath(loc, num);
			paths.add(newP);
		}
		
	}
	
	public int score(ArrayList<SinglePath> paths) {
		int sc = 0;
		for (int i = 0; i < paths.size(); i++ ) {
			if (paths.get(i).score > sc) {
				sc =paths.get(i).score;
			}
		}
		return sc;
	}
	
	public void upDate(int[] loc, int[] num) {
		if (this.pathsRed.isEmpty() || this.pathsWhite.isEmpty()) {
			this.newPath(loc, num, pathsRed);
			this.newPath(loc, num, pathsWhite);
		}
		else {
//			System.out.println("----------------------------------------------------------");
//			System.out.println("POUR LES ROUGES");
			this.addCell(loc, num, pathsRed);
			
//			System.out.println("----------------------------------------------------------");
//			System.out.println("POUR LES BLANC");
			this.addCell(loc, num, pathsWhite);
		}
	}
}
