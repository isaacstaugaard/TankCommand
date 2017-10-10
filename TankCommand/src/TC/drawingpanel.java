package TC;

import java.awt.*; 
import java.awt.event.*; 
import java.awt.image.*; 

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *  This acts just like JPanel, but it is tailored more for our game.
 *  We can draw on it and put it inside the window. (add keylistener.)
 *
 * @author isaac
 * @version 10/10/17
 */

public abstract class drawingpanel extends JPanel implements KeyListener, MouseListener {
	private static boolean[] keystate=new boolean[525]; //record the state of keys
	private static boolean[] mousestate=new boolean[3]; //record the state of mouse
	JButton b = new JButton();

    /**
     * No-arg constructor for drawingpanel.
     */
	public drawingpanel(){
		//using double buffer to draw on screen
		this.setDoubleBuffered(true);
		this.setFocusable(true); 
		this.setBackground(Color.black);
		this.addKeyListener(this); 
		this.addMouseListener(this); 
	}

	public abstract void Draw(Graphics2D g2d);

    /**
     * Overridden to be able to draw our components for our game.
     *
     * @param g casted as a Graphics2D so that we can use Graphics2D members
     */
	@Override 
	public void paintComponent(Graphics g){
		Graphics2D g2d = (Graphics2D) g; 
		Draw(g2d); 
	}

    /**
     * Check if the user presses any key or not.
     */
	public static boolean keystate(int key){
		return keystate[key];
	}

    /**
     * When a key is pressed, it's current state will be saved
     * and an event will happen when a key is pressed.
     *
     * @param e Some event
     */
	@Override
	public void keyPressed(KeyEvent e){
		keystate[e.getKeyCode()] = true; 
	}

    /**
     * When a key is released, it's current statue will be saved
     * and an event will stop when the key is released.
     *
     * @param e Some event
     */
	@Override
	public void keyReleased(KeyEvent e){
		keystate[e.getKeyCode()] = false;
		keyReleasedFramework(e);
	}

	public abstract void keyTyped(KeyEvent e);

	public abstract void keyReleasedFramework(KeyEvent e);


    /**
     * Check if the mouse was clicked or not.
     *
     * @param button is a value indicating which mouse button was pressed (i.e left click, right click, "scroll click")
     * @return a boolean that indicates whether a mouse button was pressed or not
     */
	public static boolean mouseButtonState(int button){
		return mousestate[button - 1]; 
	}

    /**
     * Stores each mouse button's current state.
     *
     * @param e Some event
     * @param status a boolean that indicates whether a mouse button is clicked or not
     */
	private void mouseKeyStatus(MouseEvent e, boolean status ){
		if(e.getButton()==MouseEvent.BUTTON1)
			mousestate[0]=status; 
		else if (e.getButton()==MouseEvent.BUTTON2)
			mousestate[1]=status; 
		else if(e.getButton() == MouseEvent.BUTTON3)
			mousestate[2]=status; 
	}

    /**
     * Checks to see if the mouse is pressed.
     *
     * @param e Some event
     */
	@Override
	public void mousePressed(MouseEvent e){
		mouseKeyStatus(e, true); 
	}

    /**
     * Checks to see if the mouse is not pressed anymore.
     *
     * @param e Some event
     */
	@Override
	public void mouseReleased(MouseEvent e) {
        mouseKeyStatus(e, false);
    }

	public abstract void mouseClicked(MouseEvent e);

	public abstract void mouseEntered(MouseEvent e);

	public abstract void mouseExited(MouseEvent e);
	
	
}
