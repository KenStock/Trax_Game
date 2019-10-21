package hmi;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;

public class Bouton extends JButton{

	private Image img;
	int num;
	private String[] conv = {"12.png","13.png","14.png","23.png","24.png","34.png"};
	
	public Bouton(int num){
	    super(""+num);
	    this.num = num;
	    try {
	      img = ImageIO.read(new File(conv[num]));
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	  }
	
	  public void paintComponent(Graphics g){
//		    Graphics2D g2d = (Graphics2D)g;
		    g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
		  }
}
