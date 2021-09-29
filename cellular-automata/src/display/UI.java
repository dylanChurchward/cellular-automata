package display;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

import structures.Cell;

public class UI extends JPanel {

	/**
	 * Serial number.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Number of pixels the matrix of cells is inset from the edge of the frame. 
	 */
	private final int myInset = 15;
	
	/**
	 * Width of the array of cells in pixels.
	 */
	private final int myBoxWidth = 400;
	
	/**
	 * Width of each square cell. 
	 */
	private final int myCellWidth = 20;
	
	/**
	 * Total cell count. 
	 */
	private final int myCellCount = myBoxWidth / myCellWidth;
	
	/**
	 * Reference used for each cell as the array of cells is built.
	 */
	private Cell myCell;
	
	/**
	 * Array of cells. 
	 */
	private Cell[][] myCellArr;
	
	/**
	 * Timer used for animation. 
	 */
	private Timer myTimer;
	
	
	/**
	 * Start and stop animation.
	 */
	private JButton myRunButton;
	
	/**
	 * Set all cells to dead & white. 
	 */
	private JButton myClearButton;
	
	
	/**
	 * Create UI, instantiate cell array, Timer, & call setupButtons(). 
	 */
	public UI() {
		this.setLayout(null);
		myCellArr = new Cell[myCellCount][myCellCount];
		myTimer = new Timer(350, new Run());
		setupButtons();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
	
	/**
	 * Create matrix of cells, instantiate & setup run and clear buttons. 
	 */
	private void setupButtons() {
		
		// Set up all Cell buttons.
		for (int i = 0; i < myCellCount; i++) {
			for (int j = 0; j < myCellCount; j++) {
				myCell = new Cell(myCellWidth, myInset + 20 * i, myInset + j * 20);
				myCellArr[i][j] = myCell;
				this.add(myCell);
			}
		}
		
		myRunButton = new JButton("Run");
		myRunButton.setSize(105, 32);
		myRunButton.setLocation(450, 15);
		myRunButton.addActionListener(new RunButtonListener());
		this.add(myRunButton);
		myRunButton.setVisible(true);
		
		myClearButton = new JButton("Clear");
		myClearButton.setSize(105, 32);
		myClearButton.setLocation(450, 55);
		myClearButton.addActionListener(new ClearButtonListener());
		this.add(myClearButton);
		myClearButton.setVisible(true);
	}
	
	/**
	 * Iterate through each cell & use compare method to determine whether each
	 * cell should live or die on the next call to myTimer. 
	 */
	private class Run implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) { 
			for (int i = 0; i < myCellCount; i++) {
				for (int j = 0; j < myCellCount; j++) {
					if (compare(i, j)) {
						// Mark appropriate cells to live or die.
						myCellArr[i][j].setMyHealthFlag(true); 
					}
				}
			}
			
			for (int i = 0; i < myCellCount; i++) {
				for (int j = 0; j < myCellCount; j++) {
					if (myCellArr[i][j].getMyHealthFlag()) {
						// Change health status of appropriate cells. 
						myCellArr[i][j].changeMyHealth();
					}
				}
			}
			repaint();			
		}
	}
	
	/**
	 * Control the run button, which controls the timer. 
	 * @author Dylan
	 *
	 */
	private class RunButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (myTimer.isRunning()) {
				myTimer.stop();
				myRunButton.setText("Run");
			} else {
				myTimer.start();
				myRunButton.setText("Stop");
			}
		}
		
	}
	
	/**
	 * Control the clear button, which sets each cell to dead and white. 
	 * @author Dylan
	 *
	 */
	private class ClearButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			myTimer.stop();
			myRunButton.setText("Run");
			for (int i = 0; i < myCellCount; i++) {
				for (int j = 0; j < myCellCount; j++) {
					if (myCellArr[i][j].getMyHealth()) {
						myCellArr[i][j].changeMyHealth();
					}
				}
			}
			repaint();
		}
		
	}
	
	/**
	 * Compare each cell to the eight (or less) cells around it to determine whether it should 
	 * live or die upon the next call to myTimer. A living cell with two or three living neighbors
	 * survives another round. A living cell with one or four or more living neighbors will die
	 * in the next round. A dead cell with exactly three living neighbors will come to life in the next round. 
	 * All other cells stay dead. 
	 * 
	 * @param theI
	 * @param theJ
	 * @return
	 */
	private boolean compare(final int theI, final int theJ) {
		int count = 0;
		boolean flag = false; 
		if (theI - 1 >= 0 && myCellArr[theI - 1][theJ].getMyHealth() == true) { // left 
			count++;
		}
		if (theI - 1 >= 0 && theJ - 1 >= 0 && myCellArr[theI - 1][theJ - 1].getMyHealth() == true) { // left & up
			count++;
		}
		if (theJ - 1 >= 0 && myCellArr[theI][theJ - 1].getMyHealth() == true) { // up
			count++;
		}
		if (theI + 1 < myCellCount && theJ - 1 >= 0 && myCellArr[theI + 1][theJ - 1].getMyHealth() == true) { // up & right
			count++;
		}
		if (theI + 1 < myCellCount && myCellArr[theI + 1][theJ].getMyHealth() == true) { // right
			count++;
		}
		if (theI + 1 < myCellCount && theJ + 1 < myCellCount && myCellArr[theI + 1][theJ + 1].getMyHealth() == true) { // right and down
			count++;
		}
		if (theJ + 1 < myCellCount && myCellArr[theI][theJ + 1].getMyHealth() == true) { // down
			count++;
		}
		if (theI - 1 >= 0 && theJ + 1 < myCellCount && myCellArr[theI - 1][theJ + 1].getMyHealth() == true) { //  down and left
			count++;
		}
		
		if (myCellArr[theI][theJ].getMyHealth() == true) { // Living cell
			if (count == 2 || count == 3) {
				flag = false; // Living cell stays living
			} else {
				flag = true; // Living cell dies 
			}
		} else if (myCellArr[theI][theJ].getMyHealth() == false && count == 3) { // Dead cell with 3 live neighbors
			flag = true; // Dead cell comes to life
		}
		
		return flag;
	}
}
