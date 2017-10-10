package TC;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * The main for our game.
 *
 * @author isaac
 * @version 10/10/17
 */
public class Window extends JFrame{
    /**
     * No-arg constructor to set up the Window of our game.
     */
	public Window(){
		this.setTitle("Tank Command"); 
		this.setSize(1000, 530);
		this.setLocationRelativeTo(null);
		this.setResizable(false); 
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		//create framework that extends the canvas and put it on the frame 
		this.setContentPane(new Framework());
		this.setVisible(true); 
	}

    /**
     * Game starts here.
     */
	public static void main(String[] args) {
		
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				new Window(); 
			}}); 
		
	}

}
