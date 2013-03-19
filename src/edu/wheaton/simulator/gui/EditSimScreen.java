package edu.wheaton.simulator.gui;

import javax.swing.JLabel;

public class EditSimScreen extends Screen {

	
	public EditSimScreen(SimulatorMenu m) {
		this.menu = m;
		
		//placeholder screen components
		this.add(new JLabel("edit simulation"));
	}
}
