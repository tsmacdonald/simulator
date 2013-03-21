package edu.wheaton.simulator.gui;

import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class EditSimScreen extends Screen {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3629462657811804434L;

	public EditSimScreen(SimulatorMenu m) {
		this.menu = m;

		// placeholder screen components
		this.add(new JLabel("edit simulation"));
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
