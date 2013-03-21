/**
 * TitleScreen.java
 * 
 * Class representing the opening screen of the simulation interface.
 * 
 * @author Willy McHie
 * Wheaton College, CSCI 335, Spring 2013
 */

package edu.wheaton.simulator.gui;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class TitleScreen extends Screen {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4901621402376078633L;

	private JLabel label;

	private JButton newSim;

	private JButton loadSim;

	public TitleScreen(SimulatorMenu m) {
		this.menu = m;
		this.label = new JLabel("Welcome to the Simulator!",
				SwingConstants.CENTER);
		label.setHorizontalTextPosition(SwingConstants.CENTER);
		this.newSim = new JButton("New Simulation");
		ActionEvent ns = new ActionEvent(newSim, 1, "new");
		this.loadSim = new JButton("Load a saved Simulation");
		loadSim.setEnabled(false); // since serialization is not yet
									// implemented
		// remaining setup, including adding components and defining layout
		ActionEvent ls = new ActionEvent(loadSim, 2, "load");
	}

	@Override
	public void addComponents(JPanel panel) {
		panel.add(label);
		panel.add(newSim);
		panel.add(loadSim);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equalsIgnoreCase("new")) {
			// TODO
		} else if (e.getActionCommand().equalsIgnoreCase("load")) {
			// TODO
		}
	}
}
