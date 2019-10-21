package control;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import Game.Cell;
import Game.Engine;
import Game.Paths;

public class GameController {
	
	public Engine engine;
	public List<Integer> loc = new ArrayList<Integer>();
	public List<Integer> num = new ArrayList<Integer>();
	public int player = 1;
	public int winner = 0;
	public int[] mode; 
	
	public GameController(int[] mode) {
		this.engine = new Engine();
		this.mode = mode;
	}
	
	int[] arrange (int[] num) {
		int[] num1 = {num[0],num[1]};
		int[] num2 = {num[1],num[0]};
		return num[0] < num[1] ? num1 : num2;
	}
	
	int indexMax(ArrayList<Integer> list) {
	    int max = Integer.MIN_VALUE;
	    int max_i = 0;
	    for(int i=0; i<list.size(); i++){
	        if(list.get(i) > max){
	            max = list.get(i);
	            max_i = i;
	        }
	    }
	    return max_i;
	}
	
	public int[] getCellValue(int i, int j) {
		return this.engine.getValue(i, j);
	}
	
	public int getHeight() {
		return this.engine.getHeight();
	}
	
	public int getWidth() {
		return this.engine.getWidth();
	}
	
	public boolean placeCell(int i, int j, int[] num, Engine engine) {
		if (engine.isBoardEmpty()) {
			engine.addFirstCell(i, j, num);
			return true;
		}
		else {
			return engine.addNewCell(i, j, num);
		}
	}
	
	public void win(Engine engine) {
		int w = 0;
		int r = 0;
		if (engine.isOver(engine.paths.pathsWhite)) { w = 1 ; this.winner = 2; } //System.out.println("pbm au blanc");}
		if (engine.isOver(engine.paths.pathsRed)) { r = 1 ; this.winner = 1; } //System.out.println("pbm au rouge");}
		if (r==1 && w==1) { this.winner = player; } //System.out.println("pbm au rouge et blanc");}
	}
	
	public void nextHumanStep(Engine engine) {
		int number[] = {num.get(0), num.get(1)};
		boolean place = placeCell( loc.get(0),  loc.get(1), arrange(number), engine);
		if (place) {
			engine.forcedMoove();
			engine.forcedMoove();
			engine.forcedMoove();
			engine.forcedMoove();
			this.win(engine);
//			System.out.println("And the winner is " +winner);
			player =  player%2 +1;
		}
		num.remove(0);  num.remove(0);  loc.remove(0);  loc.remove(0);
	}
	
	/*****************************************************************************************
	 * IMPLEMENTATION DE L'IA
	 *****************************************************************************************/
	
	public void RandomStep(Engine engine) {
		ArrayList<int[]> list = engine.getPossibleLoc2(); //liste des emplaement où il est possible de jouer
		int length = list.size();
		
		//choix de la position
		if (length == 0) {
			loc.add((int) (Math.random()*this.getHeight()));
			loc.add((int) (Math.random()*this.getWidth()));
		}
		else {
			int[] e = list.get((int) (Math.random()*length));
			loc.add(e[0]);
			loc.add(e[1]);
		}
		
		//choix de la case
		this.num = randomNum(loc.get(0),loc.get(1),engine);
	}
	
	
	public ArrayList<Integer> randomNum(int i, int j, Engine engine) {
		ArrayList<Integer> obligRed = engine.getObligationRed(i, j);
		ArrayList<Integer> obligWhite = engine.getObligationWhite(i, j);
		ArrayList<Integer> num = new ArrayList<Integer>();
		
		if (obligRed.size() == 1) {  //face rouge obligée ?
			num.add(obligRed.get(0)); 
		}
		if (obligWhite.size() == 1) { //face rouge à éviter ?
			int n =0;
			int l = num.size();
			for (int k = 0; k<2-l ; k++) {
				do {
					n = (int) (1+ Math.random()*3);
				} while((n == obligWhite.get(0) || n == (num.size()==1 ? num.get(0) : 0) ));
				num.add(n);
			}
		}
		int l = num.size();
		for (int k = 0; k<2-l ; k++) { //reste un numéro à pourvoir ?
			int n =0;
			do {
				n = (int) (1+ Math.random()*3);
			} while(n == (num.size()==1 ? num.get(0) : 0) );
			num.add(n);
		}
		return num;
	}
	
	
	public void aiStep() {
		ArrayList<int[]> listLoc = engine.getPossibleLoc2(); //liste des emplaement où il est possible de jouer
		ArrayList<int[]> listNum = new ArrayList<int[]>();
		ArrayList<Integer> score = new ArrayList<Integer>();
		Engine engineTest = (Engine) engine.clone();
		
//		System.out.println(engine.toString());
		for (int[] locTest : listLoc) {
			ArrayList<int[]> listNumTest = engine.getPossibleNum(locTest[0], locTest[1]);
			ArrayList<Integer> scoreNum = new ArrayList<Integer>();
			int playerTest = this.player;
			int urg=1;
			
//			System.out.println("\nON PLACE PREMIER DANS ENGINE FICTIF ");
			for (int[] numTest : listNumTest) {
				int it = 0;
				int s_win = 0;
				int n = 100;
				
				while (it< n ) {
					int it2 =0;
					loc.add(locTest[0]); loc.add(locTest[1]);
					num.add(numTest[0]); num.add(numTest[1]);
					this.nextHumanStep(engine);
					int w =winner;
					
					while(w==0 && it2 <20) {
						this.RandomStep(engine);
		//				System.out.println("\nDANS LA BOUCLE "+it2+" numéro "+num.get(0)+ "," + num.get(0) +" et position "+loc.get(0)+","+loc.get(1) );
						this.nextHumanStep(engine);
						w = this.winner;
						it2++;
//						System.out.println(engine.toString());
					}
//					System.out.println("on est sorti de la petite boucle");
//					if (it2 >=60) {
//						System.out.println("c'est kassé de ouf ---------------------------------------------------------------------------------");
//					}
		//			System.out.println("And the winner is :"+w);
					if (winner==playerTest) {
		//				System.out.println("before s_win : "+s_win);
						s_win= s_win+(20-it2);
//						System.out.println("s_win+ : "+(20-it2));
					}
					if (it2==0 || (it2==1 && winner==playerTest)){
		//				System.out.println("before s_loose : "+s_loose);
						urg+=(2-it2)*n;
//						System.out.println("urg+ : "+(2-it2)*10);
					}
					if (it2==1 && winner!=playerTest) {
						s_win=Integer.MIN_VALUE;
//						System.out.println("s_win- : "+500);
					}
					it++;
					engine = (Engine) engineTest.clone();
					this.player = playerTest;
					this.winner = 0;
					w=0;
				}
				
//				System.out.println("on arrive à sortir de la boucle et s = "+s);
				
//				System.out.println("Pour loc = "+locTest[0]+","+locTest[1] + "et num = "+numTest[0]+","+numTest[1]);
//				System.out.println("\ts_win : "+s_win + ", urg : "+urg+"\n");
				
				scoreNum.add(s_win);
			}
//			System.out.println("\t\tPour loc = "+locTest[0]+","+locTest[1] + " on a urg = " +urg+"\n");
			int maxId = indexMax(scoreNum);
			listNum.add(listNumTest.get(maxId));
			score.add(scoreNum.get(maxId)*urg);
		}
		
		this.winner = 0;
		int m = indexMax(score);
//		System.out.println("OOO loc jouée :" +listLoc.get(m)[0]+","+listLoc.get(m)[1]+" ; num joué :"+listNum.get(m)[0]+","+listNum.get(m)[1] +"; avec un score de "+ score.get(m));
		loc.add(listLoc.get(m)[0]); loc.add(listLoc.get(m)[1]);
		num.add(listNum.get(m)[0]); num.add(listNum.get(m)[1]);
	}
	
	/*****************************************************************************************
	 * TOUR DE JEU
	 *****************************************************************************************/
	
	public void nextStep() {
		if (mode[this.player - 1] == 0) {
			this.nextHumanStep(this.engine);
		}
		else if (mode[this.player - 1] == 1) {
			if (engine.isBoardEmpty())
				this.RandomStep(this.engine);
			else
				this.aiStep();
			this.nextHumanStep(this.engine);
		}
		else if (mode[this.player - 1] == 2) {
			this.RandomStep(this.engine);
			this.nextHumanStep(this.engine);
		}
	}
	
	public void raz() {
		this.engine = new Engine();
		this.player = 1;
		this.winner = 0;
	}
}
