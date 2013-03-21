/**
 * EditFieldScreen
 * 
 * Class representing the screen that allows users to edit
 * the properties of a local or global field.
 * 
 * @author Willy McHie
 * Wheaton College, CSCI 335, Spring 2013
 */

package edu.wheaton.simulator.gui;

import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class EditFieldScreen extends Screen {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8001531208716520432L;

	public EditFieldScreen(SimulatorMenu m) {
		this.menu = m;

		// placeholder screen components
		this.add(new JLabel("Edit a field"));
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
