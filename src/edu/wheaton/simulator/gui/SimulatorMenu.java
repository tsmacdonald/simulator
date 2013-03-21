/**
 * SimulatorMenu.java
 * 
 * Class to store and manage window information for the user interface
 * 
 * @author Willy McHie
 * Wheaton College, CSCI 335, Spring 2013
 */

package edu.wheaton.simulator.gui;

import java.awt.FlowLayout;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * This class will act as the singular JFrame window for the interface, with
 * different screens being displayed on it by using the setContentPane method
 * to switch to the "current" or "active" screen.
 */
public class SimulatorMenu extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8240039325787217431L;

	private HashMap<String, Screen> screens;

	private JPanel panel;

	private boolean simulationIsRunning;

	// references to facades for internal code

	public SimulatorMenu() {
		super("Simulator");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(1200, 800);
		this.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.setLocation(100, 100);
		// this.setExtendedState(MAXIMIZED_BOTH);
		panel = new JPanel();
		this.add(panel);
		// create and add screens to HashMap
		screens = new HashMap<String, Screen>();
		screens.put("title", new TitleScreen(this));
		screens.put("editSim", new EditSimScreen(this));
		screens.put("fields", new FieldScreen(this));
		screens.put("editField", new EditFieldScreen(this));
		screens.put("entities", new EntityScreen(this));
		screens.put("editEntity", new EditEntityScreen(this));
		screens.put("spawning", new SpawningScreen(this));
		screens.put("viewSim", new ViewSimScreen(this));
		screens.put("statistics", new StatisticsScreen(this));

		panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		panel.add(screens.get("title"));
		screens.get("title").addComponents(panel);
		panel.setVisible(true);
		this.setVisible(true);

	}

	public Screen getScreen(String s) {
		return screens.get(s);
	}

	public void setScreen(Screen s) {
		this.setContentPane(s);
		this.setVisible(true);
	}

	// getter methods for facades? or public methods to handle them?
}
