package Game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Engine {

		private int height;
		private int width;
		public Cell[][] board;
		public Paths paths;
		
		
		public Engine() {
			this.height = 6;
			this.width = 6;
			this.paths = new Paths(height,width);
			this.board = new Cell[height][width];
			for (int i = 0; i < this.height; i++) {
				for (int j = 0; j < this.width; j++) {
					int[] nul = {0,0};
					board[i][j] = new Cell(nul);
				}
			}
		}
		
		@Override
		public Object clone() {
			Engine engine = null;
			try {
				engine = (Engine) super.clone();
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				engine = new Engine();
			}
			engine.paths = (Paths) this.paths.clone();
			for (int i = 0; i < this.height; i++) {
				for (int j = 0; j < this.width; j++) {
					engine.board[i][j] = (Cell) this.board[i][j].clone();
				}
			}
			return engine;
		}
		
		public String toString() {
			String str = "";
			for (int i = 0; i < this.height; i++) {
				for (int j = 0; j < this.width; j++) {
					str += "\t"+ board[i][j].getNum()[0] + "," + board[i][j].getNum()[1];
				}
				str+="\n";
			}
			return str;
		}
		
		int mod ( int x , int y ) {
			return x >= 0 ? x % y : y - 1 - ((-x-1) % y) ;
	    }
		
		int[] arrange (int[] num) {
			int[] num1 = {num[0],num[1]};
			int[] num2 = {num[1],num[0]};
			return num[0] < num[1] ? num1 : num2;
		}
		
		public int getWidth() {
			return this.width;
		}
		
		public int getHeight() {
			return this.height;
		}
		
		public int[] getValue(int i, int j) {
			return this.board[i][j].getNum();
		}
		
		public ArrayList<Integer> getObligationRed(int i, int j) {
				return this.board[i][j].red;
		}
		
		public ArrayList<Integer> getObligationWhite(int i, int j) {
				return this.board[i][j].white;
		}
		
		/***********************************************************************************************
		 * AJOUT D'UNE NOUVELLE CASE
		 ***********************************************************************************************/
		
		public boolean isPossible(int i, Cell cellBoard, int[] numNew) {
			//i corresponds to the position of the old cell comparded with the new comming one
			// 0 = left ; 1 = up ; 2 = right ; 3 = down
			int iOpp = (((i-1)+2) % 4)+1;
			int[] numBoard = cellBoard.getNum();
			
			// On verrifie qu'il 'y a pas de cas avec 3 r/b faisant face au même côté
			if (numBoard[0]==0 && numBoard[1]==0) {
				if (cellBoard.redNeighbour() <=2 && (i != numNew[0] || i != numNew[1])) {
					return true;
				}
				else if (cellBoard.whiteNeighbour() <=2 && (i == numNew[0] || i == numNew[1])) {
					return true;
				}
				else {
//					System.out.println("Trop de rouge/blanc vers " + i );
					return false;
				}
			}
			
			// On verrifie que la jonction des deux tuille soit rouge-rouge
			else if ((iOpp == numBoard[0]) || (iOpp==numBoard[1])){
				if ((i == numNew[0]) || (i == numNew[1])){
//					System.out.println("rouge se touchent vers "+ i);
					return true;
				}
			}
			
			// On verrifie que la jonction des deux tuille soit blanc-blanc
			else if ((iOpp != numBoard[0]) && (iOpp != numBoard[1])) {
				if ((i != numNew[0]) && (i != numNew[1])){
//					System.out.println("blanc se touchent vers " + i);
					return true;
				}
			}
			
//			System.out.println("blanc et rouge se touchent vers " + i);
			return false;
		}
		
		public boolean isEmpty(int i, int j) {
			return this.board[i][j].isEmpty();
		}
		
		public boolean onFrontier(Cell cell) {
			if (cell.redNeighbour()==0 && cell.whiteNeighbour()==0) {
				return false;
			}
			else {
				return true;
			}
		}
		
		public boolean featsIn(int i, int j, int[] num) {
			Cell voisin[] = {board[i][ mod(j-1,this.width)], board[ mod(i-1,this.height) ][j], board[i][ mod(j+1,this.width)],board[ mod(i+1,this.height) ][j] };
			boolean verif = this.isPossible(1, voisin[0], num)
							&& this.isPossible(2, voisin[1], num)
							&& this.isPossible(3, voisin[2], num)
							&& this.isPossible(4, voisin[3], num)
							&& this.onFrontier(this.board[i][j])
							&& isEmpty(i,j) ;
			return verif;
		}
		
		public void updateVoisin(int i, int j, int[] num) {
			Cell voisin[] = {board[i][ mod(j-1,this.width)], board[ mod(i-1,this.height) ][j], board[i][ mod(j+1,this.width)],board[ mod(i+1,this.height) ][j] };
			if ((1 == num[0]) || (1 == num[1])) { voisin[0].red.add(3);}// System.out.println("+1 pour "+i+","+mod(j-1,this.width)+" : "+ board[i][ mod(j-1,this.width) ].number[2]);}
			else { voisin[0].white.add(3);}
			if ((2 == num[0]) || (2 == num[1])) { voisin[1].red.add(4);}// System.out.println("+1 pour "+mod(i-1,this.height)+","+j+" : "+ board[ mod(i-1,this.height) ][j].number[2]);}
			else { voisin[1].white.add(4);}
			if ((3 == num[0]) || (3 == num[1])) { voisin[2].red.add(1);}// System.out.println("+1 pour "+i+","+mod(j+1,this.width)+" : "+ board[i][ mod(j+1,this.width) ].number[2]);}
			else { voisin[2].white.add(1);}
			if ((4 == num[0]) || (4 == num[1])) { voisin[3].red.add(2);}// System.out.println("+1 pour "+mod(i+1,this.height)+","+j+" : "+ board[ mod(i+1,this.height) ][j].number[2]);}
			else { voisin[3].white.add(2);}
		}
		
		public void addFirstCell(int i, int j, int[] num) {
			this.updateVoisin(i, j, num);
			this.board[i][j].number[0] = num[0];
			this.board[i][j].number[1] = num[1];
			int[] loc = {i,j};
			this.paths.upDate(loc, num);
		}
		
		public boolean addNewCell(int i, int j, int[] num) {
			if (this.featsIn(i, j, num)) {
				this.updateVoisin(i, j, num);
//				System.out.println("position "+i+","+j+ " numero " + num[0] +"," +num[1]);
				this.board[i][j].number[0] = num[0];
				this.board[i][j].number[1] = num[1];
				int[] loc = {i,j};
				
				//update les paths
				this.paths.upDate(loc, num);
//				System.out.println("\nListe des chemins :");
//				for (int k =0; k<this.paths.pathsRed.size(); k++) {
//					System.out.println("      (rouge): la boucle démare à " + this.paths.pathsRed.get(k).start[0]+","+ this.paths.pathsRed.get(k).start[1]+" avec dir "+this.paths.pathsRed.get(k).startDir+"et finit à " + this.paths.pathsRed.get(k).end[0]+","+this.paths.pathsRed.get(k).end[1]+" et dir "+this.paths.pathsRed.get(k).endDir+ ", score "+this.paths.pathsRed.get(k).score);
//				}
//				for (int k =0; k<this.paths.pathsWhite.size(); k++) {
//					System.out.println("      (blanc): la boucle démare à " + this.paths.pathsWhite.get(k).start[0]+","+ this.paths.pathsWhite.get(k).start[1]+" avec dir "+this.paths.pathsWhite.get(k).startDir+"et finit à " + this.paths.pathsWhite.get(k).end[0]+","+this.paths.pathsWhite.get(k).end[1]+" et dir "+this.paths.pathsWhite.get(k).endDir+ ", score "+this.paths.pathsWhite.get(k).score);
//				}
				
				return true;
			}
				
			else {
//				System.out.println("Choisir une autre tuile");
				return false;
			}
		}
		
		/***********************************************************************************************
		 * MOUVEMENT FORCÉS
		 ***********************************************************************************************/
		
		public boolean isBoardEmpty(){
			for (int i = 0 ; i < this.height ; i++) {
				for (int j = 0 ; j < this.width ; j++) {
					if (!this.board[i][j].isEmpty())
						return false;
				}
			}
			return true;
		}
		
		public int[] forcedNum(int i,int j) {
			if (this.board[i][j].redNeighbour() == 2 && this.isEmpty(i, j)) {
				int[] num = {this.board[i][j].red.get(0), this.board[i][j].red.get(1)};
				return arrange(num);}
			else if (this.board[i][j].whiteNeighbour() == 2 && this.isEmpty(i, j)) {
				int[] num = {this.board[i][j].white.get(0), this.board[i][j].white.get(1)};
				int num1 = num[0];
				int num2 = num[1];
				if (num1%4 +1 == num2 || num2%4 +1 == num1) {
					num[0] = (num1+1) %4 +1;
					num[1] = (num2+1) %4 +1;}
				else {
					num[0] = (num1) %4 +1;
					num[1] = (num2) %4 +1;}
				return arrange(num);
			}
			int val[] = {};
			return val;
		}
		
		public void forcedMoove() {
			for (int i = 0 ; i < this.getHeight(); i++) {
				for (int j = 0 ; j < this.getWidth(); j++) {
					int[] fNum = this.forcedNum(i, j);
					if (fNum.length > 0) {
//						System.out.println(fNum[0]+","+fNum[1]);
						this.addNewCell(i , j ,fNum);
					}
				}
			}
		}

		/***********************************************************************************************
		 * CONDITION D'ARRÊT
		 ***********************************************************************************************/
		
		public boolean isOver(ArrayList<SinglePath> paths){
			int n = paths.size();
			boolean b = false;
			for (int i = 0; i < n; i++) {
				int[] reveal = paths.get(i).end;
				if (!board[reveal[0]][reveal[1]].isEmpty()) {
					b = true;
				}
			}
//			System.out.println(b);
			return b;
		}
		
		public int[] score() {
			int[] sc = new int[2];
			if (paths.pathsRed.size()==0) {
				return sc;
			}
			else {
				sc[0] = this.paths.score(paths.pathsRed);
				sc[1] = this.paths.score(paths.pathsWhite);
				return sc;
			}
		}
		
		public boolean isOver() {
			return (this.isOver(this.paths.pathsRed) || this.isOver(this.paths.pathsWhite));
		}
		
		/***********************************************************************************************
		 * INTELLIGENCE ARTIFICIELLE
		 ***********************************************************************************************/
		
		public Set<int[]> getPossibleLoc() {
			Set<int[]> possible = new HashSet<int[]>();
			for (SinglePath sP : this.paths.pathsRed) {
				possible.add(sP.end);
				possible.add(sP.start);
			}
			for (SinglePath sP : this.paths.pathsWhite) {
				possible.add(sP.end);
				possible.add(sP.start);
			}
			return possible;
		}
		
		public ArrayList<int[]> getPossibleLoc2() {
			ArrayList<int[]> possible = new ArrayList<int[]>();
			for (int i=0; i<height; i++) {
				for (int j=0; j<width; j++) {
					if (this.isEmpty(i, j) && this.onFrontier(board[i][j])) {
						int[] loc = {i,j};
						possible.add(loc);
					}
				}
			}
			return possible;
		}
		
		public ArrayList<int[]> getPossibleNum(int i,int j){
			ArrayList<int[]> possible = new ArrayList<int[]>();
			ArrayList<Integer> obligRed = getObligationRed(i, j);
			ArrayList<Integer> obligWhite = getObligationWhite(i, j);
			int blanc = ((obligWhite.size() == 1) ? obligWhite.get(0) : 0);
			
			for (int n1 = 1; n1<5 ; n1++) {
				for (int n2 = 1; n2<5 ; n2++) {
					if (n1 != blanc  && ((obligRed.size() == 1)? n1==obligRed.get(0) : true)) {
						if (n2 != n1 && n2 != blanc) {
							int[] num = {n1,n2};
							possible.add(arrange(num));
						}
					}
				}
			}
			
			return possible;
		}
		
		
//		public void oneStep() {
			//first, the player choose the position of the cell
//			int i = -1;
//			int j = -1;
//			do {
//				Scanner sin = new Scanner(System.in);
//				i = sin.nextInt();
//				j = sin.nextInt();
//			} while( !(i>=0 && i<this.height && j>=0 && j<this.width && this.board[i][j].isEmpty()) );
//			
//			//then, the player choose the value he will give to the cell
//			int num1 = -1;
//			int num2 = -1;
//			int num[] = {num1, num2};
//			do {
//				Scanner sin = new Scanner(System.in);
//				num[0] = sin.nextInt();
//				num[1] = sin.nextInt();
//			} while( !( num[0]>=0 && num[0]<4 && num[1]>=0 && num[1]<4 && this.featsIn(i, j, num) ) )  ;
//			
//			this.board[i][j].number = num;
//		}
		

		

}
