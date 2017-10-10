package TC;

import java.awt.Graphics2D; 
import java.awt.image.*;

/**
 * Side-scrolling background class that makes it
 * look like the player is progressing through the map.
 *
 * @author isaac
 * @version 10/10/17
 */
public class background {
    /**
     * Background image.
     */
	public BufferedImage background;

    /**
     * Side-scrolling speed.
     */
	public double speed;

    /**
     * The background's various x positions.
     */
	public double x[];

    /**
     * The background's y position.
     */
	public int y;

    /**
     * Initialize the background's components
     *
     * @param i background image
     * @param a speed the background will be side-scrolling
     * @param b y-coordinate of background
     */
	public void initialize(BufferedImage i, double a, int b){
		this.background = i; 
		this.speed = a; 
		this.y=b; 
		//to calc how many times should we put image into the frame
		int number=(Framework.width/this.background.getWidth())+2; //in case of empty space +2
		x=new double[number]; 
		//set x to draw n times 
		for (int f = 0; f<x.length; f++){
			x[f]=f*background.getWidth(); 
		}
	}

    /**
     * Make image move and change coordinates as the background moves
     */
	private void update(){
		for (int i = 0; i<x.length; i++){
			
			//change picture coordinate by adding speed
			x[i]+=speed; 
			if (speed < 0){//move left
				//if move out of screen, it move to the other end of image. 
				if(x[i]<=-background.getWidth()){
					x[i]=background.getWidth()*(x.length-1); 
				}
			}
			else //move right
			{
				if(x[i]>=background.getWidth()*(x.length-1)){
					x[i]=-background.getWidth(); 
			}}
			
		}
	}

    /**
     * Draws the background.
     *
     * @param g2d helps draw the background in a way that the background is side-scrolling
     */
	public void Draw(Graphics2D g2d){
		this.update(); 
		for (int i = 0; i<x.length; i++){
			g2d.drawImage(background,  (int)x[i], y, null); 
		}
	}
	
	

}
