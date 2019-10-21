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
import javax.swing.JPanel;

public class Box extends JButton {
	
	private Image img;
	
	public Box() {
	    try {
	        img = ImageIO.read(new File("00.png"));
	      } catch (IOException e) {
	        e.printStackTrace();
	      }
	}
	
	public void paintComponent(Graphics g){
//		Graphics2D g2d = (Graphics2D)g;
		g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
	}
	
	public void setImage(Image img) {
		this.img = img;
	}
}
