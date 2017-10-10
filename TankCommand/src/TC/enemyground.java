package TC;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * A type of enemy that attacks on the ground.
 * 
 * @author isaac
 * @version 10/10/17
 */ 

public class enemyground {
	//the time between enemies show 
	public static long periodgroundinit=10*Framework.nanosecond; 
	public static long periodground=periodgroundinit; 
	public static long lastcreatedground=0; 
	private Random random= new Random(); 
	
	//img for enemyground.java
	public static BufferedImage enemygroundimg; 
	
	//health of enemy 
	public int health; 
		
	//position of enemy 
	public int x;
	public int y;
	private int tmp=random.nextInt(300)+700; 
	private int tmp1=random.nextInt(300)+600; 
	private int tmp2=random.nextInt(300)+450; 


		
	//moving speed:
	public static double xmovinginit=-0.1; 
	public static double xmoving=xmovinginit; 
	
	/**
	 * Modify the time, frequency, and the difficulty of the ground enemy. 
	 */ 
	public static void speedup(){
		if(enemyground.periodgroundinit > Framework.nanosecond){
			enemyground.periodground-=Framework.nanosecond/18; 
			enemyground.xmoving-=0.1; 
		}
		
	}
	
	/** 
	 * Upon restarting the game, the ground enemy's properties are set back to default.
	 */ 
	public static void restartenemyground(){
			enemyground.periodground=periodgroundinit;
			enemyground.lastcreatedground=0; 
			enemyground.xmoving=xmovinginit; 
		}
	
	/**
	 * Initialize ground enemy properties.
	 *
	 * @param x initial x position
	 */

	public void initialize(int x){
		health = 40; 
		this.x=x; 
		this.y=400; 
		this.xmoving=-1; 
	}

	
	/**
	 * Makes the ground enemy move.
	 */

	public void update(){
		x+=xmoving;
	}
	
	/**
	 * Checks to see of the ground enemy left the screen.
	 *
	 * @return a boolean that indicates whether the ground enemy left the screen or not
	 */

	public boolean isleft(){
		if(x<0 - enemygroundimg.getWidth())
			return true; 
		else
			return false; 
	}
	
	/**
	 * Checks to see if the ground enemy is shooting.
	 */

	public boolean shooting(){
		if(x==tmp || x==tmp1 || x==tmp2){
			return true; 
		}
		else 
			return false; 
	}

	
	/**
	 * Draws the ground enemy. For the ground enemy, the y coordinate doesnt change.
	 */

	public void Draw(Graphics2D g2d){
		g2d.drawImage(enemygroundimg, x, 389, null); 
	}



}