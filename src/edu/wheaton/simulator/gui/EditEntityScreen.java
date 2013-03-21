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

import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class EditEntityScreen extends Screen {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4021299442173260142L;
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

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
