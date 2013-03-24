<<<<<<< HEAD
/**
 * SimulatorMenu.java
 * 
 * Class to store and manage window information for the user interface
 * 
 * @author Willy McHie
 * Wheaton College, CSCI 335, Spring 2013
 */

package edu.wheaton.simulator.gui;

import java.awt.*;

import javax.swing.*;

/**
 * This class will act as the singular JFrame window for the interface, with
 * different screens being displayed on it by using the setContentPane method
 * to switch to the "current" or "active" screen.
 */
public class Display extends JFrame {

	private static final long serialVersionUID = 8240039325787217431L;

	private JPanel panel;

	private boolean simulationIsRunning;

	public Display() {
		super("Simulator");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(1200, 800);
		this.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.setLocation(100, 100);
		panel = new JPanel();
		this.add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.setVisible(true);
	}

	public void updateDisplay(Screen s) {
		this.setContentPane(s);
		this.setVisible(true);
	}
	// getter methods for facades? or public methods to handle them?
}
=======
/**
 * SimulatorMenu.java
 * 
 * Class to store and manage window information for the user interface
 * 
 * @author Willy McHie
 * Wheaton College, CSCI 335, Spring 2013
 */

package edu.wheaton.simulator.gui;

import java.awt.*;

import javax.swing.*;

/**
 * This class will act as the singular JFrame window for the interface, with
 * different screens being displayed on it by using the setContentPane method
 * to switch to the "current" or "active" screen.
 */
public class Display extends JFrame {

	private static final long serialVersionUID = 8240039325787217431L;

	private JPanel panel;

	private boolean simulationIsRunning;

	public Display() {
		super("Simulator");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(1200, 800);
		this.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.setLocation(100, 100);
		panel = new JPanel();
		this.add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.setVisible(true);
	}

	public void updateDisplay(Screen s) {
		this.setContentPane(s);
		this.setVisible(true);
	}
	
}
>>>>>>> f9c788a45b8b22fa4087e4ee354f8dafcf6faf5b
