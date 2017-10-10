package TC;

import java.awt.*; 
import java.awt.image.*; 
import java.io.IOException;
import java.net.URL;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * Defeated enemies will drop powerups at random times
 * and the player can pick these up to gain an extra superpower.
 *
 * @author isaac
 * @version 10/10/17
 */
public class powerup {
    /**
     * The powerup image.
     */
	public static BufferedImage powerupimg; //image of powerup

    /**
     * The powerup's position.
     */
	public double x,y; //powerup location

    /**
     * Powerup's horizontal speed because the powerup will be
     * bouncing around when dropped by the enemy.
     */
	public double xspeed = 3;

    /**
     * Powerup's vertical speed because the powerup will be bouncing
     * around when dropped by the enemy.
     */
	public double yspeed = 7;

    /**
     * The type of powerup that can be dropped:
     *   -Type 1: Extra superpower (rocket)
     *   -Type 2: Extra health (+50)
     */
	public int type;

    /**
     * Random variable used to randomize powerup drops.
     */
	public Random random=new Random();

    /**
     * The variable that refers to the location of the powerup image.
     */
	public URL URL; 

    /**
     * Powerup constructor that initializes all powerup components.
     *
     * @param x x position of powerup
     * @param y y position of powerup
     */
	public powerup(int x, int y) {
		this.x=x; 
		this.y=y; 
		type=random.nextInt(2); 
		if(type==0){
			URL=this.getClass().getResource("/TC/resources/images/powerup.png"); }
		else {
			URL=this.getClass().getResource("/TC/resources/images/powerup1.png");
		}
		try {
			powerupimg=ImageIO.read(URL);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    /**
     * Checks to see if the powerup left the screen.
     *
     * @return a boolean that indicates whether the powerup left the screen
     */
	public boolean isleft(){
		if (x>0 && x<Framework.width  && y<Framework.height )
			return false; 
		else 
			return true; 
	}

    /**
     * Creates the motion of the powerup.
     */
	public void update(){
		x-=xspeed; 
		if (y>Framework.height-50){
			yspeed=-Math.abs(yspeed); 
		}
		else if(y<50){
			yspeed=Math.abs(yspeed); 
		} 
		y+=yspeed; 
	}

    /**
     * Draws the powerup.
     *
     * @param g2d
     */
	public void Draw(Graphics2D g2d){
		g2d.drawImage(powerupimg, (int)x, (int)y, null); 
	}

}
