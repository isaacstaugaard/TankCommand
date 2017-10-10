package TC;

import java.awt.*; 
import java.awt.image.*;

/**
 * A weapon that the player uses to attack enemies.
 *
 * @author isaac
 * @version 10/10/17
 */

public class bullet {

    /**
     * This is the time between two bullets.
     */
	public static long bulletperiod = (long) (Framework.nanosecond/.5); //time between the two bullet <------changed the
	                                                                   // data to change the frequency of the bullet.

    /**
     * Keeps track of how many bullets were shot.
     */
	public static long lastcreatbullet=0;

    /**
     * Bullet image.
     */
	public static BufferedImage bullet;

    /**
     * The amount of damage the bullet can do.
     */
	public static int damage=20;

    /**
     * The bullet's position.
     */
	public double x,y;

    /**
     * The bullet's overall speed.
     */
	public static double speed=8;

    /**
     * The bullet's horizontal speed.
     */
	public double xspeed;

    /**
     * The bullet's vertical speed.
     */
	public double yspeed;

    /**
     * A temporary variable for testing purposes.
     */
	public  int tmp=0;

    /**
     * Constructor for bullet.
     *
     * @param x initial x-coordinate
     * @param y initial y-coordinate
     */
	public bullet (int x, int y){
		x+=115;
		y-=65;
		this.x=x; 
		this.y=y;
		xspeed=speed; 
		yspeed=speed;

	}

    /**
     * Detects if the bullet left the screen or hit the ground.
     *
     * @return a boolean indicating whether left the screen or hit the ground
     */
	public boolean isleft(){
		if (x>0 && x<Framework.width  && y<Framework.height ) //bullet can in the air(no boundary on the top. )
			return false; 
		else 
			return true; 
	}


    /**
     * Makes bullet move according to the function in bullet constructor.
     */
	public void update(){
		x+=xspeed; 
		y-=28;
		tmp+=1;
		y+=tmp;
		System.out.println(tmp+" "+x+" "+y);
	}

    /**
     * Draws the bullet onto the screen.
     *
     * @param g2d helps draw bullet on screen so that it makes it look like it's moving
     */
	public void Draw(Graphics2D g2d){
		g2d.drawImage(bullet, (int)x, (int)y, null); 
	}
	
	

}
