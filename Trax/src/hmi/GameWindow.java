package hmi;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import control.GameController;

public class GameWindow extends JFrame {
	
	private GamePanel gamePanel;
	private GameController gameController;
	private JPanel container = new JPanel();
	private JPanel tab = new JPanel();
	private JPanel cases = new JPanel();
	private JPanel command = new JPanel();
	
	public GameWindow(GamePanel gameP, GameController gameC) {
		this.setTitle("jeu Trax");
		this.setSize(new Dimension(410, 600));
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setLocationRelativeTo(null);
		this.setResizable(true);
		
		this.gamePanel = gameP;
		this.gameController = gameC;
		
		int h = this.gameController.getHeight();
		int w = this.gameController.getWidth();
		
		tab.setBackground(Color.darkGray);
	    tab.setLayout(new GridLayout(h, w, 5, 5));
	    //On ajoute le bouton au content pane de la JFrame
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				Box but = new Box();
				but.setPreferredSize(new Dimension(25, 25));
				but.addActionListener(new BouttonListener(i,j));
				this.gamePanel.table[j][i] = but;
//				but.addMouseListener(new BoxListener(i,j));
				tab.add(but);
			}
		}
		
		cases.setLayout(new GridLayout(2, 5, 5, 5));
	    //On ajoute le bouton au content pane de la JFrame
		for (int i = 0; i < 2; i++) {
			cases.add(new JPanel());
			for (int j = 0; j < 3; j++) {
				JButton but = new Bouton(j+3*i);
				but.setPreferredSize(new Dimension(50,50));
				but.addActionListener(new BouttonListener2(j+3*i));
//				but.addMouseListener(new BoxListener(i,j));
				cases.add(but);
			}
			cases.add(new JPanel());
		}
		
//		this.addKeyListener(this);
		
		this.gamePanel.setPreferredSize(new Dimension(410, 100));
		
		command.setBackground(Color.white);
		command.setLayout(new BorderLayout());
	    command.add(tab, BorderLayout.CENTER);
	    command.add(cases, BorderLayout.SOUTH);
		
		container.setBackground(Color.white);
	    container.setLayout(new BorderLayout());
	    container.add(this.gamePanel, BorderLayout.NORTH);
	    container.add(command, BorderLayout.CENTER);
		
		this.setContentPane(container);
		this.setVisible(true);
		if (gameController.mode[0]!=0) {
			do {
				gameController.nextStep();
				gamePanel.repaint();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} while(gameController.mode[gameController.player-1]!=0 && gameController.winner==0);
		}
	}
	
//	public void keyPressed(KeyEvent e) {
//		int c = (int) e.getKeyChar()-48;
//		System.out.println(c);
//			if (this.gameController.loc.size() < 2) { 
//				if (c >= 0 && c < this.gameController.getHeight()) {
//					this.gameController.loc.add(c);
//				}
//				else {
//					System.out.println("saisie incorecte");
//				}
//				this.gamePanel.repaint();
//		    }
//			else if (this.gameController.loc.size() == 2) {
//				//System.out.println(this.gameController.loc.get(0)+" , "+this.gameController.loc.get(1));
//				if (this.gameController.num.size() < 2) {
//					if (c >= 1 && c <= 4) {
//						this.gameController.num.add(c);
//					}
//					else {
//						System.out.println("saisie incorecte");
//					}
//					this.gamePanel.repaint();
//				}
//				if (this.gameController.num.size() == 2) {
//					int number[] = {this.gameController.num.get(0),this.gameController.num.get(1)};
// 					this.gameController.placeCell(this.gameController.loc.get(0), this.gameController.loc.get(1), number);
// 					this.gamePanel.repaint();
// 					this.gameController.num.remove(0); this.gameController.num.remove(0); this.gameController.loc.remove(0); this.gameController.loc.remove(0);
//				}
//			}
//		}
//	
//	public void keyTyped(KeyEvent e) { }
//
//	public void keyReleased(KeyEvent e) { }
	
//	class BoxListener implements MouseListener{
//		
//		int i;
//		int j;
//		
//		public BoxListener(int i, int j) {
//			this.i = i;
//			this.j = j;
//		}
//		
//		public void mouseClicked(MouseEvent e) {
//			gameController.loc.add(i);
//			gameController.loc.add(j);
//			repaint();
//		}
//		public void mousePressed(MouseEvent e) {}
//		public void mouseReleased(MouseEvent e) {}
//		public void mouseEntered(MouseEvent e) {}
//		public void mouseExited(MouseEvent e) {}
//	}
	
	class BouttonListener implements ActionListener{
		int i;
		int j;
		
		public BouttonListener(int i, int j) {
			this.i = i;
			this.j = j;
		}	
		
		public void actionPerformed(ActionEvent arg0) {
			if (gameController.loc.size() >= 2) {
				gameController.loc.remove(0);
				gameController.loc.remove(0);
			}
			gameController.loc.add(i);
			gameController.loc.add(j);
			
			if(gameController.num.size()>=2 && gameController.loc.size()>=2 && gameController.winner==0) {
				do {
					gameController.nextStep();
					gamePanel.repaint();
				} while(gameController.mode[gameController.player-1]!=0);
			}
		}
	}

	class BouttonListener2 implements ActionListener{
		int num;
		int[][] conv = {{1,2},{1,3},{1,4},{2,3},{2,4},{3,4}};
		
		public BouttonListener2(int num) {
			this.num =num;
		}	
		
		public void actionPerformed(ActionEvent arg0) {
			if (gameController.num.size() >= 2) {
				gameController.num.remove(0);
				gameController.num.remove(0);
			}
			gameController.num.add(conv[num][0]);
			gameController.num.add(conv[num][1]);
			
			if(gameController.num.size()>=2 && gameController.loc.size()>=2 && gameController.winner==0) {
				do {
					gameController.nextStep();
					gamePanel.repaint();
				} while(gameController.mode[gameController.player-1]!=0);

			}
		}
	}
}

