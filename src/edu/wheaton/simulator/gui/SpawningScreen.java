/**
 * SpawningScreen
 * 
 * Class representing the screen that allows to determine spawning conditions,
 * or spawn new entities during a simulation.
 * 
 * @author Willy McHie
 * Wheaton College, CSCI 335, Spring 2013
 */

package edu.wheaton.simulator.gui;

import javax.swing.JLabel;

public class SpawningScreen extends Screen {

	public SpawningScreen(SimulatorMenu m) {
		this.menu = m;
		
		//placeholder screen components
		this.add(new JLabel("Spawning"));
		this.setVisible(true);
	}

}