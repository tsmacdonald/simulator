/**
 * EditEntityScreen
 * 
 * Class representing the screen that allows users to edit properties of
 * grid entities, including triggers and appearance.
 * 
 * @author Willy McHie
 * Wheaton College, CSCI 335, Spring 2013
 */

package edu.wheaton.simulator.gui;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class EditEntityScreen extends Screen {

	private JLabel label;
	public EditEntityScreen(SimulatorMenu m) {
		this.menu = m;
		//placeholder screen components
		label = new JLabel("Entities");
		this.setVisible(true);
	}

	@Override
	public void addComponents(JPanel panel) {
		panel.add(label);
	}

}
