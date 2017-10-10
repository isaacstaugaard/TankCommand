package TC;

import java.io.*;
import java.util.Scanner;

/**
 * Class that updates the high score of the game if the player's score
 * happens to be bigger than the current high score.
 *
 * @author isaac
 * @version 10/10/17
 */

public class updatescore {

	/**
	 * Player's current score.
	 */
	public int score; 
	
	/**
	 * Overall high score.
	 */
	public int highestscore; 
	
	/**
	 * Used to read a line from the high score file.
	 */
	String line;

	/**
	 * High score file.
	 */ 
	File file = new File("HighScore.txt");

	/**
	 * Constructor that creates a high score file if the file did not exist already
	 * and sets the default value of the high score to 0. It also stores the player's score
	 * to score.
	 *
	 * @param score Player's score
	 */

	public updatescore(int score) {
		try {
			if(file.createNewFile()){
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw); 
			bw.write("0");
			bw.close();}
			else {}; 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		this.score=score; 
	}
	
	/**
	 * Sets score to the Player's score.
	 */
	
	public void changescore(int score){
		this.score=score; 
	}

	/**
	 * Get the score from the high score file.
	 */
	
	public void getscore(){
		try {
            Scanner scanner = new Scanner(file);
            line = scanner.nextLine();
            //System.out.println(line);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
		highestscore = Integer.parseInt(line);
	}
	

	/**
	 * Overwrites file if player's score is higher than the current high score.
	 */
	public void uploadscore(){
		if(score>highestscore){
			try {
				FileWriter fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw); 
				bw.write(String.valueOf(score));
				bw.close(); 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
		}
	}

}
