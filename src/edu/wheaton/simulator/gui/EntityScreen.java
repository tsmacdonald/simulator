/**
 * EntityScreen
 * 
 * Class representing the screen that manages user interactions 
 * pertaining to grid entities, including triggers and appearance.
 * 
 * @author Willy McHie
 * Wheaton College, CSCI 335, Spring 2013
 */

package edu.wheaton.simulator.gui;

import javax.swing.JLabel;

public class EntityScreen extends Screen {

	public EntityScreen(SimulatorMenu m) {
		this.menu = m;
		
		//placeholder screen components
		this.add(new JLabel("Fields"));
		this.setVisible(true);
	}

}