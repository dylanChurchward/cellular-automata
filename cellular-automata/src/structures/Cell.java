package structures;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 * Each cell behaves as a controllable button that one can click to either
 * kill or bring to life in order to alter the game board into a desirable
 * state before running the program. 
 * 
 * @author Dylan
 *
 */
public class Cell extends JButton {
	
	/**
	 * Serial number. 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * True is alive, false is dead. 
	 */
	private boolean myHealth;
	
	/**
	 * True means the cell will have it's health status changed 
	 * on the next turn. From dead to alive, or vice versa. 
	 */
	private boolean myHealthFlag;
	
	/**
	 * If alive, the color is black. If dead, the color is black. 
	 */
	private Color myColor;
	
	/**
	 * Create a cell. 
	 * @param theSize - dimensions of the square cell/button. 
	 * @param theX - the location in pixels along the horizontal plane 
	 * relative to the upper left corner of the JFrame. 
	 * @param theY - the location in pixels along the vertical plane
	 * relative to the upper left corner of the JFrame. 
	 */
	public Cell(final int theSize, final int theX, final int theY) {
		myHealth = false; 
		myHealthFlag = false;
		myColor = Color.white;
		this.setSize(theSize, theSize);
		this.setLocation(theX, theY);
		this.setBackground(myColor);
		this.addActionListener(new Clicked());
	}
	
	/**
	 * Controls the cell & allows user to kill or revive the cell. 
	 * @author Dylan
	 *
	 */
	private class Clicked implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (myColor == Color.white) {
				changeMyHealth();
			} else if (myColor == Color.black){
				changeMyHealth();
			}
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBackground(myColor);
	}
	
	/**
	 * Get whether cell is alive or dead (true or false). 
	 * @return boolean
	 */
	public boolean getMyHealth() {
		return myHealth;
	}
	
	/**
	 * Change the health status of the cell. From alive to
	 * dead or vice versa. 
	 * @param theBoolean
	 */
	public void changeMyHealth() {
		myHealth = !myHealth;
		myHealthFlag = false;
		if (myColor == Color.white) {
			myColor = Color.black;
		} else {
			myColor = Color.white;
		}
	}
	
	/**
	 * Signal that the health status of the cell will be changed
	 * upon the next time. 
	 * @param theBoolean - true if the health status will be changed 
	 * on the next turn. 
	 */
	public void setMyHealthFlag(final boolean theBoolean) {
		myHealthFlag = theBoolean;
	}
	
	/**
	 * Get myHealthFlag. 
	 * @return boolean - myHealthFlag
	 */
	public boolean getMyHealthFlag() {
		return myHealthFlag;
	}
	
	
}
