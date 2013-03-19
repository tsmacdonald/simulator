/**
 * TitleScreen.java
 * 
 * Class representing the opening screen of the simulation interface.
 * 
 * @author Willy McHie
 * Wheaton College, CSCI 335, Spring 2013
 */

package edu.wheaton.simulator.gui;

import javax.swing.JButton;
import javax.swing.JLabel;

public class TitleScreen extends Screen {

	private JLabel label;
	
	private JButton newSim;
	
	private JButton loadSim;
	
	public TitleScreen() {
		
		this.label = new JLabel("Welcome to the Simulator!", JLabel.CENTER);
		label.setHorizontalTextPosition(JLabel.CENTER);
		this.newSim = new JButton("New Simulation");
		this.loadSim = new JButton("Load a saved Simulation");
		loadSim.setEnabled(false); //since serialization is not yet implemented
		//remaining setup, including adding components and defining layout
	}
}
