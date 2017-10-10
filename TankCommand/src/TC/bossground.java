package TC;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * @author isaac
 * @version 10/10/17
 * class for a boss enemy that moves on the ground
 */
public class bossground {
	
	//the time between enemies show 
	public static long periodgroundinit=20*Framework.nanosecond; //change to 16
	public static long periodground=periodgroundinit; 
	public static long lastcreatedground=0; 
	private Random random= new Random(); 
		
	//img for enemyground.java
	public static BufferedImage bossgroundimg; 
		
	//health of enemy 
	public int health; 
			
	//position of enemy 
	public int x;
	public int y;
	private int tmp=random.nextInt(300)+700; 
	private int tmp1=random.nextInt(300)+600; 
	private int tmp2=random.nextInt(300)+500; 
	private int tmp3=random.nextInt(300)+350; 


			
	//moving speed:
	public static double xmovinginit=-0.05; 
	public static double xmoving=xmovinginit; 
		
	/**
 	 * Speeds the boss up in case side scroller moves faster than boss
 	 */
	public static void speedup(){
		if(bossground.periodgroundinit > Framework.nanosecond){
				bossground.periodground-=Framework.nanosecond/28; 
				bossground.xmoving-=0.05; 
		}
			
	}
		
	/**
 	 * restarts state of the boss 
 	 */
	public static void restartbossground(){
				bossground.periodground=periodgroundinit;
				bossground.lastcreatedground=0; 
				bossground.xmoving=xmovinginit; 
	}
		
	/**
	 * overrides the initialized position 
	 * @param x x coordinate of new position
	 * @param y y coordinate of new position
 	 */
	public void initialize(int x, int y){
			health = 200; 
			this.x=x; 
			this.y=400; 
			this.xmoving=-1; 
			//this.tmp=random.nextInt(200)+700; 
	}

	/**
 	 * controls the movement of the boss 
 	 */
	public void update(){
			if (x<200){
				xmoving=2; 
			}
			if (x>900){     
				xmoving=-0.1; 
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
     * Draws the ground boss.
     *
 	 * @param g2d allows the boss to be drawn with side-scrolling
 	 */
	public void Draw(Graphics2D g2d){
			g2d.drawImage(bossgroundimg, x, 380, null); 
	}



}
