package TC;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * @author isaac
 * @version 10/10/17
 * class for a boss enemy that moves on the air
 */
public class bossair {
	
	//the time between enemies show 
	public static long periodairinit=14*Framework.nanosecond;
	public static long periodair=periodairinit; 
	public static long lastcreatedair=0; 
	private Random random= new Random(); 
		
	//img for enemyair.java
	public static BufferedImage bossairimg; 
		
	//health of enemy 
	public int health; 
			
	//position of enemy 
	public int x;
	public int y;
	private int tmp=random.nextInt(300)+600; 
	private int tmp1=random.nextInt(300)+400; 
	private int tmp2=random.nextInt(300)+300; 
	private int tmp3=random.nextInt(300)+200; 


			
	//moving speed:
	public static double xmovinginit=-.03; 
	public static double xmoving=xmovinginit; 
		
	/**
 	 * Speeds the boss up in case side scroller moves faster than boss
 	 */
	public static void speedup(){
		if(bossair.periodairinit > Framework.nanosecond){
				bossair.periodair-=Framework.nanosecond/18; 
				bossair.xmoving-=0.03; 
		}
			
	}
		
	/**
 	 * restarts state of the boss 
 	 */
	public static void restartbossair(){
				bossair.periodair=periodairinit;
				bossair.lastcreatedair=0; 
				bossair.xmoving=xmovinginit; 
	}
		
	/**
	 * overrides the initialized position 
	 * @param x x coordinate of new position
	 * @param y y coordinate of new position
 	 */
	public void initialize(int x, int y){
			health = 300; 
			this.x=x; 
			this.y=100; 
			this.xmoving=-.03; 
			//this.tmp=random.nextInt(200)+700; 
	}

	/**
 	 * controls the movement of the boss 
 	 */
	public void update(){
			if (x<200){
				xmoving=1; 
			}
			if (x>900){     
				xmoving=-.1; 
			} 

			x+=xmoving;

	}
		
	/**
 	 * Uses random numbers to decide whether or not the boss is shooting
 	 * @return true if the boss is shooting
 	 */

	public boolean shooting(){
			if(x==tmp || x==tmp1 || x==tmp2 || x == tmp3){
				return true; 
			}
			else 
				return false; 
	}

		
	/**
 	 * @param g2d allows the boss to be drawn with side-scrolling
 	 */
	public void Draw(Graphics2D g2d){
			g2d.drawImage(bossairimg, x, y, null); 
	}



}