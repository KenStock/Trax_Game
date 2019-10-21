package hmi;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import control.GameController;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javax.swing.JPanel;

public class GamePanel extends JPanel{

	private GameController gameController;
	public Box[][] table;
	private int h ;
	private int w;

	public GamePanel(GameController controle) {
		this.gameController = controle;
		this.h = this.gameController.getHeight();
		this.w = this.gameController.getWidth();
		this.table = new Box[h][w];
	}

	public void paint(Graphics graphics) {
		// begin-user-code
		// TODO Auto-generated method stub
//		int carre = 75;
//		int margeCarre = 5;
		
		int plateau = this.getWidth()-10;
		int margePlateauX = 5;
		int margePlateauY = 5;
		
//		graphics.setColor(Color.darkGray);
//		graphics.fillRect(margePlateauX, margePlateauY, plateau, plateau);
	
		try {
			Image[] img = {ImageIO.read(new File("12.png")),
					               ImageIO.read(new File("13.png")),
					               ImageIO.read(new File("14.png")),
					               ImageIO.read(new File("23.png")),
					               ImageIO.read(new File("24.png")),
					               ImageIO.read(new File("34.png")),};
		
			for (int i = 0; i < h; i++) {
				for (int j = 0; j < w; j++) {
				
//					int margeX = margePlateauX + margeCarre + i* (margeCarre + carre);
//					int margeY = margePlateauY + margeCarre + j* (margeCarre + carre);
					
					int[] num = gameController.getCellValue(j, i);
					if (num[0]==0 && num[1]==0) {
//						graphics.setColor(Color.lightGray);
//						graphics.fillRect(margeX, margeY, carre, carre);
					}
					else if (num[0]==1 && num[1]==2) {
//						graphics.drawImage(img[0], margeX, margeY, carre, carre, this);
						this.table[i][j].setImage(img[0]);
					}
					else if (num[0]==1 && num[1]==3) {
//						graphics.drawImage(img[1], margeX, margeY, carre, carre, this);
						this.table[i][j].setImage(img[1]);
					}
					else if (num[0]==1 && num[1]==4) {
//						graphics.drawImage(img[2], margeX, margeY, carre, carre, this);
						this.table[i][j].setImage(img[2]);
					}
					else if (num[0]==2 && num[1]==3) {
//						graphics.drawImage(img[3], margeX, margeY, carre, carre, this);
						this.table[i][j].setImage(img[3]);
					}
					else if (num[0]==2 && num[1]==4) {
//						graphics.drawImage(img[4], margeX, margeY, carre, carre, this);
						this.table[i][j].setImage(img[4]);
					}
					else if (num[0]==3 && num[1]==4) {
//						graphics.drawImage(img[5], margeX, margeY, carre, carre, this);
						this.table[i][j].setImage(img[5]);
					}
					this.table[i][j].repaint();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
		}
		
		
		int pl = this.gameController.player;
		graphics.setColor(Color.darkGray);
		graphics.fillRect(margePlateauX, margePlateauY, plateau, 80);
		graphics.setFont(graphics.getFont().deriveFont(Font.BOLD, 23.0f));
		if (pl == 1) {
			graphics.setColor(Color.RED);
//			graphics.drawString("r : "+ sc[0] + " and w : "+ sc[1] , margePlateauX+margePlateauY, 6*margePlateauY);
			graphics.drawString("It's red player turn to play", 30, 6*margePlateauY);
		}
		else {
			graphics.setColor(Color.WHITE);
//			graphics.drawString("r : "+ sc[0] + " and w : "+ sc[1] , margePlateauX+margePlateauY, 6*margePlateauY);
			graphics.drawString("It's white player turn to play", 30, 6*margePlateauY);

		}
		
		if (this.gameController.winner != 0) {
			graphics.setFont(graphics.getFont().deriveFont(Font.BOLD, 25.0f));
			if (this.gameController.winner==1) {
				graphics.setColor(Color.red);
				graphics.fillRect(margePlateauX, margePlateauY, plateau, (int) plateau/4);
				graphics.setColor(Color.BLACK);
				graphics.drawString("Les rouges ont gagnés !" , 30, 6*margePlateauY);
			}
			else if (this.gameController.winner==2){
				graphics.setColor(Color.blue);
				graphics.fillRect(margePlateauX, margePlateauY, plateau, (int) plateau/4);
				graphics.setColor(Color.BLACK);
				graphics.drawString("Les blanc ont gagnés !" , 30, 6*margePlateauY);
			}
				
		}
		
//		graphics.setColor(Color.darkGray);
//		graphics.fillRect(margePlateauX, 5*margePlateauY+plateau*5/4, plateau, (int) plateau/4);
//		graphics.setColor(Color.BLACK);
//		graphics.setFont(graphics.getFont().deriveFont(Font.BOLD, 20.0f));
//		List<Integer> loc = this.gameController.loc;
//		if (loc.size() == 0) {
//			graphics.drawString("localisation choisie ... , ...",margePlateauX+margePlateauY, plateau+(int) plateau/2);
//		}
//		else if (loc.size() == 1) {
//			graphics.drawString("localisation choisie "+ loc.get(0) +", ..." ,margePlateauX+margePlateauY, plateau+(int) plateau/2);
//		}
//		else {
//			graphics.drawString("localisation choisie "+ loc.get(0) +" , "+ loc.get(1) ,margePlateauX+margePlateauY, plateau+(int) plateau/2);
//		}
	}

}
