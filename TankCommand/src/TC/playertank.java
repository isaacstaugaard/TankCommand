package TC;

import java.awt.*; 
import java.awt.event.*; 
import java.awt.image.*; 
import java.io.*; 
import java.net.*; 
import java.util.logging.*; 

import javax.imageio.*; 

/**
 * This is the tank that the player will be controlling.
 * 
 * @author isaac
 * @version 10/10/17
 */

public class playertank {
    /**
     * The player's initial health.
     */
	public int healthinit=100;

    /**
     * The current health of the player.
     */
	public int health;

    /**
     * The player's x position on the screen.
     */
	public int x=0;

    /**
     * The player's y position on the screen.
     */
	public int y=350; //tank is always on the ground except when jumping

    /**
     * The player's horizontal speed.
     */
	public double xspeed;

    /**
     * The player's vertical speed (used for jumping).
     */
	public double yspeed;

    /**
     * Number of superpowers (rockets) the player has initially.
     */
	public int numberofsuperpower = 5;

    /**
     * The final amount of superpowers (rockets) the player has after game over or
     * after every use of a superpower (rocket).
     */
	public int superpowerfinal;


    /**
     * Player tank image with health bar.
     */
	public BufferedImage tank, healthbar, healthbar1;

    /**
     * The player's bullet's x position.
     */
	public int xgunontank;

    /**
     * The player's bullet's y position.
     */
	public int ygunontank;

    /**
     * The player's horizontal acceleration.
     */
	public int xacc=4;

    /**
     * The player's vertical acceleration. (Never used...)
     */
	public int yacc=1;

    /**
     * The player's gun's x position.
     */
	public int xgun;

    /**
     * The player's gun's y position.
     */
	public int ygun; 
	
	/**
	 * Constructor to create the player tank.
	 *
	 * @param x initial x position
	 */
 
	public playertank(int x){
        	this.x = x;
		loadimage(); 
		initialize(); 
	}
	
	
	/**
	 * Initialize the player tank.
	 */ 

	public void initialize(){
		this.health=healthinit;
		this.superpowerfinal=numberofsuperpower; 
		this.xspeed=0; 
		this.yspeed=0; //-(10*(xspeed-0.89))*(10*(xspeed-0.89))+80; //the data is not accurate yspeed set up for jumping. 
		//set gun position 
		this.xgunontank=tank.getWidth()-40; //try to changed the position of bullet we shoot!!!!
		this.ygunontank=tank.getHeight(); 
		this.xgun=this.x+this.xgunontank; 
		this.ygun=this.y+this.ygunontank;//update the position of gun  
	}
	
	/**
	 * Load the image for player tank body.
	 */
 
	public void loadimage(){
		try {
			URL tankurl=this.getClass().getResource("/TC/resources/images/tank.png"); 
			tank=ImageIO.read(tankurl);
			tankurl=this.getClass().getResource("/TC/resources/images/healthbar5.jpg"); 
			healthbar=ImageIO.read(tankurl);
			tankurl=this.getClass().getResource("/TC/resources/images/healthbar2.png"); 
			healthbar1=ImageIO.read(tankurl);
		}catch(IOException ex){
			//do something. 
		}    //no set up for animination 
	}
	
	/**
	 * Reset the position for player tank for every restart.
	 */
 
	public void reset(int x, int y){
		this.health = healthinit;
		this.x=x;
        	this.y=y;
		this.xgun=this.x+this.xgunontank; 
		this.ygun=this.x+this.ygunontank;
		this.xspeed=0; 
		this.yspeed=0; //jumping initial speed;
        	this.loadimage();
		
	}
	
	
	/**
	 * Set player tank shooting and the period between shooting.
	 *
	 * @return a boolean that indicates a bullet can be shot when there's a left click. returns false otherwise.
	 */
 
	public boolean shooting(long gametime){
		if(drawingpanel.mouseButtonState(MouseEvent.BUTTON1) && ((gametime-bullet.lastcreatbullet)>=bullet.bulletperiod)){
			return true; 
		}
		else 
			return false; 
	}
	
	/**
	 * Set player tank using superpower and the period between using the superpower.
	 *
	 * @return a boolean that indicates the superpower is used when there's a right click. returns false otherwise.
	 */

	public boolean superpowering(long gametime){
		if(drawingpanel.mouseButtonState(MouseEvent.BUTTON3) && superpowerfinal>0 && ((gametime-superpower.lastcreatsuperpower)>=superpower.superpowerperiod)){
			superpowerfinal-=1; 
			return true; 
		}
		else 
			return false; 
	}
	
	/**
	 * Controls to move the player tank.
	 */

	public void moving(){
        	//boundary for tank
		if(y>350){
			y=350;
		}
		if(y<0){
		        y=0;
		}
		if(x<0){
			x=0;
		}
		if(x>900){
			x=900; 
		}

        	//tank controls
		if(drawingpanel.keystate(KeyEvent.VK_D) ||drawingpanel.keystate(KeyEvent.VK_RIGHT) ){
			xspeed=xacc; 
		}
		else if (drawingpanel.keystate(KeyEvent.VK_A) ||drawingpanel.keystate(KeyEvent.VK_LEFT) ){
			xspeed=-xacc; 
		}
		else if(!drawingpanel.keystate(KeyEvent.VK_D) ||!drawingpanel.keystate(KeyEvent.VK_RIGHT) ){
			xspeed=0; 
		}
		else if (!drawingpanel.keystate(KeyEvent.VK_A) ||!drawingpanel.keystate(KeyEvent.VK_LEFT) ){ 
			xspeed=-0; 
		}
		if(drawingpanel.keystate(KeyEvent.VK_W) ||drawingpanel.keystate(KeyEvent.VK_UP) ){
			yspeed=-5;
			try {
			    URL tankurl=this.getClass().getResource("/TC/resources/images/rocket_tank.png");
			    tank=ImageIO.read(tankurl);
			}catch(IOException ex){
			    //do something.
			}
		}
		else if(!drawingpanel.keystate(KeyEvent.VK_W) ||!drawingpanel.keystate(KeyEvent.VK_UP) ){
			yspeed=8;
			if(y<=348){     //tank will not shake when we dont move it. 
			    try {
				URL tankurl=this.getClass().getResource("/TC/resources/images/tank.png");
				tank=ImageIO.read(tankurl);
			    }catch(IOException ex){                        
				//do something.
			    }
			}
		}
		if(drawingpanel.keystate(KeyEvent.VK_K)){
				this.health = 0;
		}

	}
	
	
	/**
	 * Moves player tank according to the change of coordinates.
	 */
 
	public void update(){
		x+=xspeed; 
		y+=yspeed; 
		this.xgun=this.x+this.xgunontank; 
		this.ygun=this.y+this.ygunontank; 
	}
	
	/**
	 * Draw the player tank.
	 */
 
	public void Draw(Graphics2D g2d){
		g2d.drawImage(tank, x, y, null);
        	int h = healthbar.getHeight();
        	int w = healthbar.getWidth();
        	g2d.drawImage(healthbar1, x+27, y+15, (w/4)/100*106, h/3, null); 
		g2d.drawImage(healthbar,x+30, y+18, (w/4)/100*health + 1, h/5 - 1, null); 
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
