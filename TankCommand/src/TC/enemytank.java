package TC;

import java.awt.*; 
import java.awt.event.*; 
import java.awt.image.*; 
import java.io.*; 
import java.net.*; 
import java.util.Random;
import java.util.logging.*; //useless

import javax.imageio.*; 

/**
 * A type of enemy that attacks in the air. (Name of class is misleading.)
 * 
 * @author isaac
 * @version 10/10/17
 */

public class enemytank {
	//the time between enemies show 
	public static long periodenemyinit=8*Framework.nanosecond; 
	public static long periodenemy=periodenemyinit; 
	public static long lastcreatedenemy=0; 
	
	//construct a random function for flying 
	public Random random= new Random();  
	private int tmp=random.nextInt(300)+700;
	private int tmp1=random.nextInt(300)+600;
	private int tmp2=random.nextInt(300)+400;


	//health of enemy 
	public int health; 
	
	//position of enemy 
	public int x;
	public int y;
	
	//moving speed:
	public static double xmovinginit=-4; 
	public static double xmoving=xmovinginit; 
	
	//image of enemy "tank"
	public static BufferedImage enemytankimg; 
	
	/**
	 * Initialize the enemy.
	 *
	 * @param x initial x position
	 * @param y initial y position
	 */ 
	public void initialize(int x, int y){
		health = 40; 
		this.x=x; 
		this.y=y; 
		this.xmoving=-1; 
	}
	
	/**
	 * Upon restarting the game, the enemy "tank's" properties are set to default.
	 */
 
	public static void restartenemy(){
		enemytank.periodenemy=periodenemyinit;
		enemytank.lastcreatedenemy=0; 
		enemytank.xmoving=xmovinginit; 
	}
	
	
	/** 
	 * Modify the time, frequency, and the difficulty of the enemy "tank"
	 */
 
	public static void speedup(){
		if(enemytank.periodenemy > Framework.nanosecond){
			enemytank.periodenemy-=Framework.nanosecond/10; 
			enemytank.xmoving-=0.25; 
		}
		
	}
	
	/**
	 * Checks to see of the enemy "tank" left the screen.
	 *
	 * @return a boolean that indicates whether the enemy "tank" left the screen or not
	 */

	public boolean isleft(){
		if(x<0 - enemytankimg.getWidth())
			return true; 
		else
			return false; 
	}
	
	/**
	 * Checks to see if the enemy "tank" is shooting.
	 */

	public boolean shooting(){
		if(x==tmp || x==tmp1 || x==tmp2){
			return true; 
		}
		else 
			return false; 
	}

	
	/**
	 * Makes the enemy "tank" move.
	 */

	public void update(){
		Random random = new Random();
		int i =random.nextInt(999); 
		if(i%4==1){
		    x+=(xmoving+1); 
		    y+=(xmoving-2);}
		if(i%4==2){
			x+=(xmoving+1);
			y-=(xmoving-2);}
		if(i%4==0){
			x+=(xmoving); 
			//y+=xmoving;
		}
		if(i%4==3){
			x+=(xmoving); 
			//y+=xmoving;
		}
		if(y<40)
			y=40; 
		if(y>400)
			y=400;
		
	}
	
	/**
	 * Draws the enemy "tank."
	 */

	public void Draw(Graphics2D g2d){
		g2d.drawImage(enemytankimg, x, y, null); 
	}
	
	
	

}