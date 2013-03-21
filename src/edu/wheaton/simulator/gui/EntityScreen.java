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

import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class EntityScreen extends Screen {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8471925846048875713L;

	public EntityScreen(SimulatorMenu m) {
		this.menu = m;
		
		//placeholder screen components
		this.add(new JLabel("Fields"));
		this.setVisible(true);
	}

	@Override
	public void addComponents(JPanel panel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}