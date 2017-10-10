package TC;

import java.awt.*; 
import java.awt.image.*;

/**
 * An enemy can shoot the player using an enemy bullet.
 * 
 * @author isaac
 * @version 10/10/17
 */ 

public class enemybullet {
    /**
     * Enemy bullet image.
     */
	public static BufferedImage enemybullet;

    /**
     * The amount of damage the enemy bullet can give to the player.
     */
	public static int damage=10;

    /**
     * The position of the enemy bullet.
     */
	public double x,y;

    /**
     * The overall speed of the enemy bullet.
     */
	public static double speed=-4;

    /**
     * The horizontal speed of the enemy bullet.
     */
	public double xspeed;

    /**
     * The vertical speed of the enemy bullet.
     */
	public double yspeed; 
	
	/**
	 * Constructor to create an enemy bullet with respect to the enemy.
	 *
	 * @param x initial x position
	 * @param y initial y position
	 * @param xtank player tank's x position
	 * @param ytank player tank's y position
	 */
 
	public enemybullet(int x, int y, int xtank, int ytank) {
		this.x=x; 
		this.y=y; 
		xspeed = speed; 
		yspeed = (y-ytank)*speed/(x-xtank); 
		System.out.println(xspeed+" "+yspeed);
	}
	
	/**
	 * Checks to see if the enemy bullet has left the screen.
	 *
	 * @return a boolean that indicates whether the enemy bullet is still on the screen or not
	 */

	public boolean isleft(){
		if (x>0 && x<Framework.width  && y<Framework.height )//bullet can in the air(no boundary on the top. )
			return false; 
		else 
			return true; 
	}
	
	/**
	 * Makes the enemy bullet move.
	 */

	public void update(){
		x+=xspeed; 
		y+=yspeed;	
	}
	
	/**
	 * Draws enemy bullet.
	 */

	public void Draw(Graphics2D g2d){
		g2d.drawImage(enemybullet, (int)x, (int)y, null); 
	}
	
	
}
