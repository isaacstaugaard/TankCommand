package TC;

import java.awt.*; 
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException; 
import java.io.InterruptedIOException;
import java.net.URL; 
import java.util.logging.*; 

import javax.imageio.ImageIO; 
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JPanel;

import java.applet.Applet;
import java.applet.AudioClip;

/** 
 * The class that provides the basis for the whole game.
 *
 * @author isaac
 * @version 10/10/17
 */ 

public class Framework extends drawingpanel{
	
	/** 
 	 * A boolean that tests whether the pause menu is up or not.
 	 */ 
	public static boolean pause=false;
	
	/** 
 	 * used for debugging restart. 
 	 */ 
	public int gamestatenumber=0; 
	
	/** 
 	 * A boolean that tests if music is playing.
 	 */ 
	public static boolean musicplay=true; 

	/** 
 	 * Width of the framework.
 	 */
	public static int width;

	/** 
 	 * Height of the framework.
 	 */ 
	public static int height;
 
	/** 
 	 * Nanoseconds that are used for the game time.
 	 */
	public static long nanosecond=1000000000L;
 
	/** 
 	 * Milliseconds that are used for the game time.
 	 */ 
	public static long milisecond=1000000L; 
	
	private int fps=60;

	/** 
 	 * A value that represents the highscore of the game.
 	 */ 
	public int highscore;
	
	/** 
 	 * A temporary updatescore object that sets up the highscore file.
 	 */ 
	public updatescore tmp = new updatescore(1); 
	
	private long period = nanosecond/fps; 
	
	/** 
 	 * Enumeration of each game state.
 	 */
	public static enum GameState{starting, gameloading, main_menu, options, playing, gameover, destroyed, visualizing, pause} 
	
	/** 
 	 * Current game state.
 	 */
	public static GameState gamestate; 
	
	/** 
 	 * Game running time.
 	 */ 
	public long gametime;

	/** 
 	 * Game's last running time.
 	 */ 
	public long lasttime;

	/** 
 	 * The game.
 	 */ 	
	public game game;

	private Font font; 
	
	private JButton startButton,soundChangeButton,musicButton,restartButton1,pauseButton,restartButton2,mainMenuButton; 
	
	private BufferedImage b1;

	private BufferedImage title;
  
	private BufferedImage menuborder;
  
	private BufferedImage cloud;

	private BufferedImage black; 	
	
	/** 
 	 * Number control.
 	 */ 
	public int count=0; 
		
	static AudioClip clip;
	
	/** 
 	 * Background music.
 	 */  
	public URL music; 
	
	/**
	 * Shows the mouse positon.
	 *
	 * @return a Point object that has the x and y coordinates of the mouse's current position
	 */

	private Point mouseposition(){
		try {
			Point a=this.getMousePosition(); 
			if(a != null)
				return this.getMousePosition(); 
			else 
				return new Point(0, 0); 
		}catch (Exception e){
			return new Point(0,0); 
		}
	}
	
	
	/** 
	 * Start a new game.
	 */ 

	private void newgame(){
		gametime = 0; 
		lasttime=System.nanoTime();
		game = new game(); 
		/*//--------------------------------------------------------test 
		b=new JButton("whatttever?"); 
		b.setVerticalTextPosition(AbstractButton.CENTER); 
		b.setHorizontalTextPosition(AbstractButton.CENTER);
		add(b); 
		//--------------------------------------------------------test */
	}
	
	
	/**
	 * Restart a game.
	 */
 
	private void restartgame(){
		gametime = 0; 
		lasttime=System.nanoTime();
		game.restartgame();
		gamestate=GameState.playing; //changing the state of the game 
		mainMenuButton.setVisible(false);
	}
	
	
	/**
	 * Initialize the variables and objects. 
	 */
	
	private void initialize(){
		font = new Font ("Tank Command", Font.ITALIC, 50); 
	}
	
	/**
	 * Load images for starting menu
	 */ 
	
	private void load(){
		try {
			URL music=this.getClass().getResource("/TC/resources/sound/firework.wav");
			clip = Applet.newAudioClip(music);      //----------------------------------->>background music 
		
			URL burl=this.getClass().getResource("/TC/resources/images/desert.png");
			b1 = ImageIO.read(burl); 
			
			URL turl=this.getClass().getResource("/TC/resources/images/title.png");
			title = ImageIO.read(turl); 
			
			URL murl=this.getClass().getResource("/TC/resources/images/menu_border.png");
			menuborder = ImageIO.read(murl); //--------------------------------------------------------------<<<<<<<<<<<<<
			
			URL curl=this.getClass().getResource("/TC/resources/images/cloud_layer_1.png");
			cloud = ImageIO.read(curl); 
			
			URL blackurl=this.getClass().getResource("/TC/resources/images/black.jpg");
			black = ImageIO.read(blackurl);
		}
		catch (IOException ex){
			//System.err.println("............"); 
			Logger.getLogger(Framework.class.getName()).log(Level.SEVERE, null, ex);
		}		
	}
	
	
	
	/**
	 * The game runs in this loop(). Does different actions depending on different gamestates.
	 */
	
	private void loop(){
		
		//need time to load the correct frame and window content. 
		long visualizingtime=0, lastvisualizingtime=System.nanoTime(); 
		
		//calc for how long we need to put threat to sleep to match with fps. 
		long begintime, timetaken, timeleft; 
		
		while(true){
			begintime = System.nanoTime(); 
			
			switch (gamestate){
			case playing : 
				gametime +=System.nanoTime() - lasttime; 
				game.updategame(gametime);
				lasttime = System.nanoTime(); 
				break; 
				
			case gameover:
				break; 
			case main_menu:
				break; 
			case options: 
				break; 
			case gameloading: 
				break; 
			case starting:
				initialize(); 
				load(); 
				gamestate=GameState.main_menu; 
				break; 
			case visualizing: 
				if(this.getWidth()>1 && visualizingtime > nanosecond){
					width = this.getWidth(); 
					height=this.getHeight(); 
					
					gamestate=GameState.starting; 
				}
				else 
				{
					visualizingtime += System.nanoTime() - lastvisualizingtime; 
					lastvisualizingtime = System.nanoTime(); 
				}
				
				break; 
			}
			
			//update 
			//check pause status here...???pause before repaint ...-----------------------------------------<<<<<<<<<<<<<<<
			repaint(); 
			
			//calc how long should we put thread into sleep to satisfy fps; 
			timetaken = System.nanoTime() - begintime; 
			timeleft=(period - timetaken)/milisecond; 
			if (timeleft<10){
				timeleft=10; 
			}
			try {
				Thread.sleep(timeleft); 
			} catch (InterruptedException ex){
			}
			
		}
		
	}
	
	
	
	/**
	 * Draw the main menu.
	 */
 
	private void drawmenu(Graphics2D g2d){
		g2d.setBackground(Color.green);
		g2d.drawImage(b1, 0, 0, width, height, null); 
		g2d.setColor(Color.YELLOW);
	}
	
	
	
	/** 
	 * Draw onto the screen.
	 */ 
	@Override
	public void Draw(Graphics2D g2d){
		
		switch (gamestate){
			case playing: 
				game.Draw(g2d);
				startButton.setVisible(false); 
				soundChangeButton.setVisible(false); 
				musicButton.setVisible(false); 
				restartButton1.setVisible(false);
				pauseButton.setVisible(true);
				break; 
			case gameover: 
				drawmenu(g2d); 
				g2d.setColor(Color.GRAY);
				g2d.drawString("Press  ESC to exit." , width/2-55, height/4-75);
				game.print(g2d, gametime); //important!!!!
				g2d.setFont(font);
				g2d.setColor(Color.BLACK);
				g2d.drawString("Game over", width/2-125, height/4);
				startButton.setVisible(false); 
				soundChangeButton.setVisible(false); 
				musicButton.setVisible(false); 
				restartButton1.setVisible(true);
				pauseButton.setVisible(false);
				mainMenuButton.setVisible(true);
				
				break; 
			case main_menu: 
				tmp.getscore(); 
				int i = tmp.highestscore; 
				drawmenu(g2d); 
				
				g2d.drawImage(title, width/2-title.getWidth()/2+30, height/4-50, null);
				g2d.setFont(new Font("whatevereverever", Font.BOLD, 18));
				g2d.setColor(Color.YELLOW); 
				g2d.drawString("High Score: "+String.valueOf(i), width/2-80, height/8);
				g2d.setColor(Color.GRAY); 
				g2d.drawString("Use A, W, D or the arrow keys to move the tank." , width/4 + 30,  height/2);
				g2d.drawString("Press left mouse button to fire bullet and right mouse button to use rocket.", width / 10 + 20, height / 2 + 30);
				g2d.drawString("Press K end the game instantly", (width/10)*3 + 30, height/2+60);
				g2d.drawString("Press ESC to exit.", (width/8)*3 + 30, height/2+90);
				startButton.setVisible(true);
				soundChangeButton.setVisible(true); 
				musicButton.setVisible(true);
				restartButton1.setVisible(false);
				restartButton2.setVisible(false);
				pauseButton.setVisible(false);
				

				break; 
			case options:
				break; 
			case pause: 
				break; 
			case gameloading: 
				g2d.drawImage(black, 0, 0,null); 
				g2d.setColor(Color.white); 
				g2d.setFont(new Font("what wtahttttttt", Font.ITALIC, 20));
				g2d.drawString("Game is Loading!", 800/2, 530/2); 
				startButton.setVisible(false); 
				soundChangeButton.setVisible(false); 
				musicButton.setVisible(false); 
				restartButton1.setVisible(false);
				break; 
		}
	}

	/**
	 * Exits game upon pressing the escape key.
	 */

	@Override
	public void keyReleasedFramework(KeyEvent e) {
		if (e.getKeyCode()==KeyEvent.VK_ESCAPE) {
            		System.exit(0);
        	}
	}


    /**
     * Does nothing.
     * @param e Some event
     */
    public void keyTyped(KeyEvent e){}

    /**
     * Does nothing.
     * @param e Some event
     */
    public void mouseClicked(MouseEvent e){}

    /**
     * Does nothing.
     * @param e Some event
     */
    public void mouseEntered(MouseEvent e){}

    /**
     * Does nothing.
     * @param e Some event
     */
    public void mouseExited(MouseEvent e){}
	
	/**
	 * Constructor to set up framework of the game.
	 */

	public Framework(){
		super();
  
		//Construct new thread. Running the thread on the same time. 
		final Thread gameThread = new Thread(){
			@Override 
			public void run(){
				loop(); 
			}
		}; 
		gameThread.start(); 
			

		gamestate = GameState.visualizing; 
		startButton=new JButton("       Start       "); 
		startButton.setFocusable(false);
		add(startButton);
		startButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(gamestatenumber==0){
				newgame(); 
				gamestatenumber=1; 
				}
				else 
					restartgame(); 
			}
		});
		
		
		//Change the background music 
		soundChangeButton=new JButton("   Sound Change   "); 
		soundChangeButton.setFocusable(false);
		add(soundChangeButton);  
		soundChangeButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				count++; 
				if(count % 3 == 0){
					music=this.getClass().getResource("/TC/resources/sound/firework1.wav");
					clip = Applet.newAudioClip(music);
					soundChangeButton.setText("   \"Firework\"   ");
				}
				else if(count%3 == 1){
					music=this.getClass().getResource("/TC/resources/sound/firework.wav");
					clip = Applet.newAudioClip(music);
					soundChangeButton.setText("   \"Desperate\"   ");
				}
				else if(count%3 == 2){
					music=this.getClass().getResource("/TC/resources/sound/Space.wav");
					clip = Applet.newAudioClip(music);
					soundChangeButton.setText("     \"grave\"     ");	
				}
			}
		}); 
		
		
		musicButton=new JButton("   Music On   "); 
		musicButton.setFocusable(false);
		add(musicButton); 
		musicButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				musicplay=!musicplay;
				if(musicButton.getText()=="   Music On   "){
				musicButton.setText("   Music Off   "); }
				else 
					musicButton.setText("   Music On   ");
			}
		});
		
		
		restartButton1=new JButton("    Restart    "); 
		restartButton1.setFocusable(false);
		restartButton1.setVisible(false);
		add(restartButton1); 
		restartButton1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				restartgame(); 
			}
		});
		
		restartButton2=new JButton("    Restart   ");
		restartButton2.setFocusable(false); 
		restartButton2.setVisible(false);
		add(restartButton2); 
		restartButton2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				pause=false; 
				game.currThread.interrupt();
				pauseButton.setText("    Pause    ");
				restartButton2.setVisible(false); 
				restartgame(); 
			}
		});
			
		//pause menu 
		pauseButton=new JButton("    Pause    "); 
		pauseButton.setFocusable(false);
		pauseButton.setVisible(false);
		add(pauseButton); 
		pauseButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				pause=!pause; 
				System.out.println(pause); 
				pauseButton.setText("  Resume  ");
				clip.stop(); 
				restartButton2.setVisible(pause);
				mainMenuButton.setVisible(pause);
				if (pause == false && game.currThread.getState()==Thread.State.TIMED_WAITING){
					game.currThread.interrupt();
					clip.loop(); 
					pauseButton.setText("    Pause    ");
				}
				System.out.println(game.currThread.getName()+" "+game.currThread.getState());
			}
		});
		
		mainMenuButton=new JButton("    Main Menu   ");
		mainMenuButton.setFocusable(false);
		mainMenuButton.setVisible(false);
		add(mainMenuButton);
		mainMenuButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				pause=false;
				Framework.clip.stop();
				game.currThread.interrupt();
				mainMenuButton.setVisible(false);
				pauseButton.setText("  Pause  ");
				gamestate=gamestate.main_menu;
			}
		});
	}
	

	
	
	
	
}
