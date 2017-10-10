package TC;

import java.awt.*; 
import java.awt.image.*;

/**
 * Player is able to use a superpower, in which it wipes out all enemies currently on the map.
 *
 * @author isaac
 * @version 10/10/17
 */

public class superpower {
    /**
     * The time between the appearance of two superpowers (rockets).
     */
	public static long superpowerperiod = (long) (Framework.nanosecond/1); //time between the two superpower <------changed the data
	                                                                        // to change the frequency of the bullet.

    /**
     * Keeps track of how many superpowers (rockets) were used.
     */
	public static long lastcreatsuperpower=0;

    /**
     * The superpower image.
     */
	public static BufferedImage superpower;

    /**
     * The amount of damage the superpower (rocket) can give to the enemies.
     */
	public static int superdamage=100;

    /**
     * The superpower's (rocket's) x position.
     */
	public double x;

    /**
     * The superpower's (rocket's) y position.
     */
	public double y;

    /**
     * The speed of the superpower (rocket).
     */
	public static double speed=4;

    /**
     * No-arg Constructor for superpower.
     */

	public superpower(){
		this.x=-5; 
		this.y=0; 
	}

    /**
     * Checks to see if the superpower has left the screen or not.
     *
     * @return a boolean that tells whether the superpower has left the screen or not
     */
	public boolean isleft(){
		if (x<Framework.width )
			return false; 
		else 
			return true; 
 
	}

    /**
     * Update the superpower class(make it move to the right).
     */
	public void update(){
		x+=speed; //only move to the right 

	}

    /**
     * Draw the superpower onto the screen.
     *
     * @param g2d helps draw the superpower onto the screen in a way that it moves to the right
     */
	public void Draw(Graphics2D g2d){
		g2d.drawImage(superpower, (int)x, (int)y, null); 
	}
	
	
	
	
	
	
	

}
