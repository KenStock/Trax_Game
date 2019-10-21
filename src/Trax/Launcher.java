package Trax;

import control.GameController;
import hmi.GamePanel;
import hmi.GameWindow;

public class Launcher {

	/*
	 * Pour choisir un mode de jeu, veuillez changer la variable mode.
	 * Le premier �lement de ce tableau correspond au caract�re du joueur 1 (rouge) et le second correspond � celui du joueur 2 (blanc)
	 * 0 = manuel
	 * 1 = automatique
	 * 2 = al�atoire
	 */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] mode = {0,0};
		GameController ctrl = new GameController(mode);
		GamePanel p = new GamePanel(ctrl);
		new GameWindow(p,ctrl);
	}

}
