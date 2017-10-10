package TC;

import java.awt.*; 
import java.awt.geom.*; 
import java.awt.image.*; 
import java.io.*; 
import java.net.*; 
import java.util.*; 
import java.util.logging.*; 

import javax.imageio.*; 

import java.applet.Applet;
import java.applet.AudioClip;

/** 
 * The class that provides the mechanics for the whole game.
 *
 * @author isaac
 * @version 10/10/17
 */ 

public class game {
	static Thread currThread;
	
	//background images 
	public BufferedImage cloud; 
	public BufferedImage desert; 
	public background cloudmoving; 
	public background desertmoving;
 
	private Random random= new Random(); 
	public Robot robot;
 
	public playertank player;
	public updatescore highscore;  
	public ArrayList<powerup> poweruplist; //arraylist for powerups
	public ArrayList<bullet> bulletlist; //the arraylist for bullets 
	public ArrayList<superpower> superpowerlist; //the arraylist for superpower 
	public ArrayList<enemytank> enemylist; //arraylist for enemy "tanks"
	public ArrayList<enemyground> groundlist; //arraylist for ground enemies
	public ArrayList<enemybullet> enemybulletlist; //the arraylist for bullet given by enemy
	public ArrayList<bossground> bossgroundlist; //the arraylist for bosses on the ground
	public ArrayList<bossair> bossairlist; //the arraylist for bosses in the air
 
	public int runaway; //number of enemies that got away 
	public int killed; //number of enemies that the player killed
 
	AudioClip explode, attack, rocket, crash; //music and sound effects for game
	
	static Thread threadForInitGame;
	
	
	/** 
	 * Initialize everything for the game.
	 */

	private void initialize(){
		random = new Random(); 
		try{
			robot = new Robot(); 
		}catch (AWTException e){
			//Logger.getLogger(game.class.getName()).log(Level.SEVERE, null, e);
		}
		
		//making background moving 
		cloudmoving = new background(); 
		desertmoving = new background(); 
		
		player=new playertank(0); //set the initial position for player tank

		//set up
		bulletlist=new ArrayList<bullet>();  
		enemylist=new ArrayList<enemytank>(); 
		groundlist=new ArrayList<enemyground>();
		bossgroundlist = new ArrayList<bossground>();
		bossairlist = new ArrayList<bossair>();
		superpowerlist=new ArrayList<superpower>(); 
		enemybulletlist=new ArrayList<enemybullet>();
		poweruplist=new ArrayList<powerup>(); 
		highscore=new updatescore(0); 
		
		runaway=0; 
		killed=0; 
		if(Framework.musicplay){
		Framework.clip.loop();}
	}
	
	/**
	 *  Set images.
	 */
 
	private void load(){
		try {
			//music
			URL musicURL=this.getClass().getResource("/TC/resources/sound/explode.wav");
			explode=Applet.newAudioClip(musicURL);
			URL music1URL=this.getClass().getResource("/TC/resources/sound/tank.wav");
			attack=Applet.newAudioClip(music1URL);
			music1URL=this.getClass().getResource("/TC/resources/sound/rocket.wav");
			rocket=Applet.newAudioClip(music1URL);
			music1URL=this.getClass().getResource("/TC/resources/sound/crash.wav");
			crash=Applet.newAudioClip(music1URL);
			
			//pics
			URL cloudURL=this.getClass().getResource("/TC/resources/images/cloud_layer_1.png"); 
			cloud=ImageIO.read(cloudURL);
			URL desertURL=this.getClass().getResource("/TC/resources/images/desert.png"); 
			desert=ImageIO.read(desertURL);
			URL bulletURL=this.getClass().getResource("/TC/resources/images/bullet.png"); 
			bullet.bullet=ImageIO.read(bulletURL);     //read image for bullet 
			URL enemybulletURL=this.getClass().getResource("/TC/resources/images/enemybullet.png"); 
			enemybullet.enemybullet=ImageIO.read(enemybulletURL);     //read image for enemybullet
			URL powerupURL=this.getClass().getResource("/TC/resources/images/superpower.png"); 
			superpower.superpower=ImageIO.read(powerupURL);     //read image for bullet 
			URL enemytankURL=this.getClass().getResource("/TC/resources/images/enemy_plane.png"); 
			enemytank.enemytankimg=ImageIO.read(enemytankURL); //read image for enemy 
			URL enemygroundURL=this.getClass().getResource("/TC/resources/images/enemyground.png"); 
			enemyground.enemygroundimg=ImageIO.read(enemygroundURL); //read image for enemyground
			URL bossgroundURL=this.getClass().getResource("/TC/resources/images/bossground.png"); 
			bossground.bossgroundimg=ImageIO.read(bossgroundURL);
					
			URL bossairURL=this.getClass().getResource("/TC/resources/images/boss_plane.png"); 
			bossair.bossairimg=ImageIO.read(bossairURL); //read image for bossair
		
			
		} catch (IOException e) {
			Logger.getLogger(game.class.getName()).log(Level.SEVERE, null, e);
		} 
		
		//initialize moving 
		desertmoving.initialize(desert, -2, 0);//change data to modify background position
		cloudmoving.initialize(cloud, -2, 0);
	}
	
	/**
	 * Checks to see if the player is alive.
	 *
	 * @return a boolean that indicates whether the player is alive or not
	 */
 
	public boolean isplayeralive(){
		if (player.health<=0){
			Framework.clip.stop(); 
			if(Framework.musicplay){
			explode.play(); }
			return false; 
		}
		else 
			return true; 
	}
	
	/**
	 * When player left clicks, it sends out a bullet to attack enemies and plays sound effect.
	 */
 
	public void isplayershooting(long gametime){
		if(player.shooting(gametime)){
			if(Framework.musicplay){
			attack.play();}
			bullet.lastcreatbullet=gametime; 
			bullet bullet=new bullet(player.xgun-120, player.ygun-50);
			bulletlist.add(bullet);
			 
		}
	}
	
	
	/**
	 * When player right clicks, it sends out rockets that wipe out all enemies on the screen.
	 * Sound effect is played.
	 */

	public void isplayerusingsuperpower(long gametime){
		if(player.superpowering(gametime)){
			if(Framework.musicplay){
			rocket.play(); }
			superpower.lastcreatsuperpower=gametime; 
			superpower s=new superpower(); 
			superpowerlist.add(s); 
		}
	}
	
	
	/**
	 * Sends signal to let ground enemy shoot.
	 */

	public void isgroundenemyshooting(){
		for (int i =0; i<groundlist.size(); i++){
			if(groundlist.get(i).shooting()){
				enemybullet enb=new enemybullet(groundlist.get(i).x, groundlist.get(i).y, player.x, player.y+50); 
				enemybulletlist.add(enb); 
			}
		}
	}
	
	/**
	 * Sends signal to let ground boss shoot.
	 */

	public void isgroundbossshooting(long gametime){
		for (int i =0; i<bossgroundlist.size(); i++){
			if(bossgroundlist.get(i).shooting()){
				enemybullet enb=new enemybullet(bossgroundlist.get(i).x, bossgroundlist.get(i).y, player.x, player.y+50); 
				enemybulletlist.add(enb); 
			}
		}
	}
	
	public void isairbossshooting(long gametime){
		for (int i =0; i<bossairlist.size(); i++){
			if(bossairlist.get(i).shooting()){
				enemybullet enb=new enemybullet(bossairlist.get(i).x, bossairlist.get(i).y, player.x, player.y+50); 
				enemybulletlist.add(enb); 
			}
		}
	}
	
	/**
	 * Sends signal to let enemy "tank" shoot.
	 */

	public void isenemyshooting(){
		for (int i =0; i<enemylist.size(); i++){
			if(enemylist.get(i).shooting()){
				enemybullet enb=new enemybullet(enemylist.get(i).x, enemylist.get(i).y, player.x, player.y+50); 
				enemybulletlist.add(enb); 
			}
		}
	}
	

	/**
	 * Make bullet move.
	 */

	public void updatebullet(){
		for(int i=0; i<bulletlist.size(); i++){
			bullet bullet = bulletlist.get(i); 
			bullet.update();
			//check if bullet left screen 
			if(bullet.isleft()){
				bulletlist.remove(i);
				continue; 
			}
			//check if the bullet hit the enemy; 
			Rectangle b=new Rectangle((int)bullet.x, (int)bullet.y,bullet.bullet.getWidth(), bullet.bullet.getHeight()); 
			//then use for loop to check enemylist, if there is any enemy got hit or not? 
			for (int t=0; t<enemylist.size(); t++){
				enemytank r=enemylist.get(t); 
				Rectangle e=new Rectangle(r.x, r.y,r.enemytankimg.getWidth(), r.enemytankimg.getHeight()); 
				if (b.intersects(e)){
					r.health-=bullet.damage; 
				}
			}
			for (int t=0; t<groundlist.size(); t++){
				enemyground rr=groundlist.get(t); 
				Rectangle f=new Rectangle(rr.x, rr.y,rr.enemygroundimg.getWidth(), rr.enemygroundimg.getHeight()); 
				if (b.intersects(f)){
					rr.health-=bullet.damage; 
				}
			} 
			for (int t=0; t<bossgroundlist.size(); t++){
				bossground rr=bossgroundlist.get(t); 
				Rectangle f=new Rectangle(rr.x, rr.y,rr.bossgroundimg.getWidth(), rr.bossgroundimg.getHeight()); 
				if (b.intersects(f)){
					if(Framework.musicplay){
						crash.play();}
					rr.health-=bullet.damage; 
				}
			} 
			for (int t=0; t<bossairlist.size(); t++){
				bossair rr=bossairlist.get(t); 
				Rectangle f=new Rectangle(rr.x, rr.y,rr.bossairimg.getWidth(), rr.bossairimg.getHeight()); 
				if (b.intersects(f)){
					if(Framework.musicplay){
						crash.play();}
					rr.health-=bullet.damage; 
				}
			} 

			
		}
	}

	/**
	 * Make enemy bullet move.
	 */

	public void updateenemybullet(){
		for(int i=0; i<enemybulletlist.size(); i++){
			enemybullet enemybullet = enemybulletlist.get(i); 
			enemybullet.update();
			//check if bullet left screen 
			if(enemybullet.isleft()){
				enemybulletlist.remove(i);
				continue; 
			}
			//check if the bullet hit the enemy; 
			Rectangle b=new Rectangle((int)enemybullet.x, (int)enemybullet.y,enemybullet.enemybullet.getWidth(), enemybullet.enemybullet.getHeight()); 
			Rectangle p=new Rectangle(player.x+35, player.y+38,player.tank.getWidth()/2-10, player.tank.getHeight()/2-10); 
			if(p.intersects(b)){
				if(Framework.musicplay){
				crash.play();}
				player.health-=enemybullet.damage; 
				enemybulletlist.remove(i); 
			}
		} 

			
	}
	
	/**
	 * Updates a certain attribute when a powerup is gained.
	 */

	public void updatepowerup(){
		for (int i = 0; i<poweruplist.size(); i++){
			powerup po=poweruplist.get(i); 
			po.update();
			if(po.isleft()){
				poweruplist.remove(i); 
				continue; 
			}
			random=new Random(); 
			Rectangle b=new Rectangle((int)po.x, (int)po.y,powerup.powerupimg.getWidth(), powerup.powerupimg.getHeight()); 
			Rectangle p=new Rectangle(player.x+35, player.y+38,player.tank.getWidth()/2-10, player.tank.getHeight()/2-10); 
			if(p.intersects(b)){
				if(po.type==1){
					if (player.health+50>=100)
						{player.health=100;}
				    else 
				    	{player.health+=50;}
				}
				else 
					player.superpowerfinal+=1; 
				poweruplist.remove(i); 

			}
		}
	}

	/**
	 * Make superpower move.
	 */

	public void updatesuperpower(){
		for(int i=0; i<superpowerlist.size(); i++){
			superpower s = superpowerlist.get(i); 
			s.update();
			//check if bullet left screen 
			if(s.isleft()){
				superpowerlist.remove(i);
				continue; 
			}
			//check if the bullet hit the enemy; 
			Rectangle b=new Rectangle((int)s.x, (int)s.y,superpower.superpower.getWidth(), superpower.superpower.getHeight()); 
			//then use for loop to check enemylist, if there is any enemy got hit or not? 
			for (int t=0; t<enemylist.size(); t++){
				enemytank r=enemylist.get(t); 
				Rectangle e=new Rectangle(r.x, r.y,r.enemytankimg.getWidth(), r.enemytankimg.getHeight()); 

				if (b.intersects(e)){
					r.health-=s.superdamage; 
				}
			}
			
			for (int t=0; t<groundlist.size(); t++){
				enemyground rr=groundlist.get(t); 
				Rectangle f=new Rectangle(rr.x, rr.y,rr.enemygroundimg.getWidth(), rr.enemygroundimg.getHeight()); 
				if (b.intersects(f)){
					rr.health-=bullet.damage; 
				}
			}
			
			for (int t=0; t<bossgroundlist.size(); t++){
				bossground rr=bossgroundlist.get(t); 
				Rectangle f=new Rectangle(rr.x, rr.y,rr.bossgroundimg.getWidth(), rr.bossgroundimg.getHeight()); 
				if (b.intersects(f)){
					rr.health-=bullet.damage; 
				}
			}
			for (int t=0; t<bossairlist.size(); t++){
				bossair rr=bossairlist.get(t); 
				Rectangle f=new Rectangle(rr.x, rr.y,rr.bossairimg.getWidth(), rr.bossairimg.getHeight()); 
				if (b.intersects(f)){
					rr.health-=bullet.damage; 
				}
			}


			
		}
	}
	
	/**
	 * Draw images on screen.
	 */

	public void Draw(Graphics2D g2d){
		desertmoving.Draw(g2d);
		cloudmoving.Draw(g2d);

		//draw player 
		if(isplayeralive())
			player.Draw(g2d);
		
		//g2d.drawRect (player.x+35, player.y+38,player.tank.getWidth()/2-10, player.tank.getHeight()/2-10);//=====>show collision box 
		
		//draw enemytank 
		for (int i=0; i<enemylist.size(); i++){
			enemylist.get(i).Draw(g2d);
		}
		
		
		//draw enemyground
		for (int i=0; i<groundlist.size(); i++){
			groundlist.get(i).Draw(g2d);
		}
		
		//draw bossground
		for (int i=0; i<bossgroundlist.size(); i++){
			bossgroundlist.get(i).Draw(g2d);
		}
		
		//draw airboss
		for (int i=0; i<bossairlist.size(); i++){
			bossairlist.get(i).Draw(g2d);
		}
		
		//draw arraylist for bullet 
		for(int i=0; i<bulletlist.size(); i++){
			bulletlist.get(i).Draw(g2d);
		}
		
		//draw arraylist for enemybullet 
		for(int i=0; i<enemybulletlist.size(); i++){
			enemybulletlist.get(i).Draw(g2d);
		}
		
		//draw superpower 
		for(int i=0; i<superpowerlist.size(); i++){
			superpowerlist.get(i).Draw(g2d);
		}
		
		//draw powerups
		for (int i=0; i<poweruplist.size(); i++){
			poweruplist.get(i).Draw(g2d);
		}
		
		g2d.setFont(new Font("what wtahttttttt", Font.BOLD, 18));
		g2d.setColor(Color.gray );
		g2d.drawString("Killed: "+killed, 10, 20);
		g2d.drawString("Rocket: "+player.superpowerfinal, 10, 40);
		g2d.drawString("Escaped: "+runaway, 250, 20);
		g2d.drawString("Bullet Period: "+bullet.bulletperiod/1000000000+"s", 250, 40);
		if(player.health<0){
			player.health=0;
		}
		g2d.drawString("Player's health: "+player.health, 560, 20);
		
	}
	
	/**
	 * Update the logic of the game. Keep the game checking the whole logic.
	 */
	
	@SuppressWarnings("deprecation")
	public void updategame(long gametime){
		
		//restart the game if the player is dead. 
		if(!isplayeralive()){
			highscore.changescore(killed-runaway); 
			highscore.getscore();
			highscore.uploadscore();
			Framework.gamestate=Framework.gamestate.gameover; 
			return; //stop the game
		}
		
		//player is alive, the keep update. 
		if(isplayeralive()){
			isplayershooting(gametime);
			isplayerusingsuperpower(gametime);
			player.moving();
			player.update();
		}
		
		//go through all the groundenemy 
		isgroundenemyshooting();
		isenemyshooting(); 
		isgroundbossshooting(gametime); 
		isairbossshooting(gametime);
		//update bullet action
		updatebullet(); 
		updatesuperpower(); 
		updateenemybullet();
		//update the enemy 
		createenemytank(gametime); 
		updateenemy(); 
		//update groundboss
		createbossground(gametime); 
		createbossair(gametime);
		updatebossground(); 
		updatebossair();
		//update powerup 
		updatepowerup();
		//update the enemyground 
		createenemyground(gametime); 
		updateenemyground();
		currThread=Thread.currentThread();
		if(Framework.pause == true){
		try {
			Thread.currentThread().sleep(100000000);
			Framework.pause = false; 
		} catch (InterruptedException e) {
			e.printStackTrace();
		} }
	}
	
	
	/** 
	 * Creates the ground enemy when it comes to the right time.
	 */ 

	public void createenemyground(long gametime){
		if (gametime-enemyground.lastcreatedground>=enemyground.periodground){
			enemyground r = new enemyground(); 
			r.initialize(Framework.width);
			//System.out.println("1");
			groundlist.add(r); 
			//System.out.println("2");
			enemyground.speedup();
			enemyground.lastcreatedground=gametime; 
		}
	}
	
	
	/** 
	 * Creates the ground boss when it comes to the right time.
	 */ 

	public void createbossground(long gametime){
		if (gametime-bossground.lastcreatedground>=bossground.periodground && bossgroundlist.size()==0){
			bossground r = new bossground(); 
			r.initialize(Framework.width,1);
			//System.out.println("1");
			bossgroundlist.add(r); 
			//System.out.println("2");
			bossground.speedup();
			bossground.lastcreatedground=gametime; 
		}
	}
	
	/** 
	 * Creates the air boss when it comes to the right time.
	 */ 

	public void createbossair(long gametime){
		if (gametime-bossair.lastcreatedair>=bossair.periodair && bossairlist.size()==0){
			bossair r = new bossair(); 
			r.initialize(Framework.width,1);
			//System.out.println("1");
			bossairlist.add(r); 
			//System.out.println("2");
			bossair.speedup();
			bossair.lastcreatedair=gametime; 
		}
	}
	
	/** 
	 * Update all the ground enemy in the list(making the move and remove from the list if they do not exist)
	 */
 
	public void updateenemyground(){
		for (int i=0; i<groundlist.size(); i++){
			enemyground r = groundlist.get(i); 
			r.update(); 
			Rectangle p=new Rectangle(player.x+35, player.y+38,player.tank.getWidth()/2-10, player.tank.getHeight()/2-10); 
			Rectangle e=new Rectangle(r.x, r.y,r.enemygroundimg.getWidth(), r.enemygroundimg.getHeight()); 
			if(p.intersects(e)){
				if(Framework.musicplay){crash.play();}
                player.health-=30;
			    groundlist.remove(i);
			}
			else {
                if (r.health <= 0) {
                    //attack.play();
                    if (Framework.musicplay) {
                        explode.play();
                    }
                    groundlist.remove(i);
                    killed += 1;
                    continue;
                }
            }
			if(r.isleft()){
				groundlist.remove(i); 
				runaway+=1; }
			
		}
	}
	
	/** 
	 * Update all the ground boss in the list(making the move and remove from the list if they do not exist)
	 */
 
	public void updatebossground(){
		for (int i=0; i<bossgroundlist.size(); i++){
			bossground r = bossgroundlist.get(i); 
			r.update(); 
			Rectangle p=new Rectangle(player.x+35, player.y+38,player.tank.getWidth()/2-10, player.tank.getHeight()/2-10); 
			Rectangle e=new Rectangle(r.x, r.y,r.bossgroundimg.getWidth(), r.bossgroundimg.getHeight()); 
			if(p.intersects(e)){
				if(Framework.musicplay){crash.play();}
                player.health-=30;
                bossgroundlist.remove(i);
			}
			else {
                if (r.health <= 0) {
                    //attack.play();
                    if (Framework.musicplay) {
                        explode.play();
                    }
                    bossgroundlist.remove(i);
                    killed += 10;
                    continue;
                }
            }
		}
	}
	
	/** 
	 * Update all the ground boss in the list(making the move and remove from the list if they do not exist)
	 */
 
	public void updatebossair(){
		for (int i=0; i<bossairlist.size(); i++){
			bossair r = bossairlist.get(i); 
			r.update(); 
			Rectangle p=new Rectangle(player.x+35, player.y+38,player.tank.getWidth()/2-10, player.tank.getHeight()/2-10); 
			Rectangle e=new Rectangle(r.x, r.y,r.bossairimg.getWidth(), r.bossairimg.getHeight()); 
			if(p.intersects(e)){
				if(Framework.musicplay){crash.play();}
                player.health-=30;
                bossairlist.remove(i);
			}
			else {
                if (r.health <= 0) {
                    //attack.play();
                    if (Framework.musicplay) {
                        explode.play();
                    }
                    bossairlist.remove(i);
                    killed += 10;
                    continue;
                }
            }
		}
	}
	
	/**
	 * Creates the enemy "tank" when it comes to the right time.
	 */ 

	public void createenemytank(long gametime){
		if(gametime-enemytank.lastcreatedenemy>=enemytank.periodenemy){
			enemytank r=new enemytank(); 
			random = new Random(); 
			int x=Framework.width; 
			int y=random.nextInt(Framework.height-enemytank.enemytankimg.getHeight()-120); 
			r.initialize(x, y);
			//System.out.println("1");
			enemylist.add(r); 
			enemytank.speedup();
			//update the last created time!!!
			enemytank.lastcreatedenemy=gametime; 
		}
	}
	
	/**
	 * Update all the enemies in the list. 
	 */
	private void updateenemy(){
		for(int i=0; i<enemylist.size(); i++){
			enemytank r=enemylist.get(i); 
			r.update(); 
			//is crashed or not???
			//enemy die after crashing with player 
			Rectangle p=new Rectangle(player.x+35, player.y+38,player.tank.getWidth()/2-10, player.tank.getHeight()/2-10); 
			Rectangle e=new Rectangle(r.x, r.y,r.enemytankimg.getWidth(), r.enemytankimg.getHeight()); 
			if(p.intersects(e)){
				if(Framework.musicplay){
				crash.play();}
				player.health-=30; 
				enemylist.remove(i);    //<============================================================Doing collision. 
			}
			else {
                if (r.health <= 0) {
                    if (Framework.musicplay) {
                        explode.play();
                    }
                    random = new Random();
                    if (random.nextInt(12) == 4) { //<==================change the possibility to generate powerup
                        powerup pow = new powerup(r.x, r.y);
                        poweruplist.add(pow);
                    }
                    enemylist.remove(i);
                    killed += 1;
                    continue;
                }
            }
			if(r.isleft()){
				enemylist.remove(i); 
				runaway+=1; 
				//if runaway, remove from list 
			}
		}
	}
	
	/**
	 * Constructor for game class to set up the main mechanics of the game.
	 */
	public game(){
		Framework.gamestate=Framework.GameState.gameloading; 
	    	Thread threadForInitGame=new Thread(){
			@Override 
			public void run(){
				initialize(); 
				load(); 
				Framework.gamestate=Framework.GameState.playing;
			}
		};
		threadForInitGame.start(); 
	}
	
	
	/**
	 * When restarting, everything is set to default.
	 */

	public void restartgame(){
		player.reset(0,350);
        player.superpowerfinal = 5;
		enemytank.restartenemy();
		enemyground.restartenemyground();
		bossground.restartbossground();
		bossair.restartbossair();
		runaway=0; 
		killed=0; 
		superpower.lastcreatsuperpower=0; 
		bullet.lastcreatbullet=0; 
		enemylist.clear(); 
		bulletlist.clear(); 
		enemybulletlist.clear(); 
		superpowerlist.clear(); 
		groundlist.clear(); 
		poweruplist.clear(); 
		bossgroundlist.clear();
		bossairlist.clear();
		if(Framework.musicplay){Framework.clip.loop();}
	}
	
	/**
	 * Prints the result after game over.
	 */

	public void print(Graphics2D g2d, long gametime){
        	g2d.setFont(new Font("Results", Font.BOLD, 18));
        	g2d.setColor(Color.GRAY);
		    g2d.drawString("Time: "+gametime/1000000000+"s", 400, 530/2+20);
		    g2d.drawString("You Killed: "+killed+" enemies", 400, 530/2+70);
		    g2d.drawString("Escaped: "+runaway, 400, 530/2+120);
        	g2d.drawString("Total score: " + (killed - runaway), 400, 530/2 + 170);
        	
        	g2d.setFont(new Font("Results", Font.BOLD, 18));
        	g2d.setColor(Color.YELLOW);
        	if ((killed - runaway)>highscore.highestscore){	
        		g2d.setFont(new Font("Congrats", Font.BOLD, 40));
        		g2d.drawString("New High Score: " + (killed - runaway), 315, 530/2 - 30);
            	g2d.drawString("Congratulations!" , 315, 530/2 -85);
        	}
        	else 
            	g2d.drawString("Current High Score: " + highscore.highestscore, 400, 530/2 - 30);   	
        	
	}
}