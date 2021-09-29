package display;

import javax.swing.JFrame;

public class MasterFrame extends JFrame {

	/**
	 * Serial number. 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Create master JFrame. 
	 */
	public MasterFrame() {
		UI userInterface = new UI();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Cellular Automata");
		this.add(userInterface);
		this.setSize(600, 465);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
}
