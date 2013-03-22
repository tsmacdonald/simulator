/**
 * TitleScreen.java
 * 
 * Class representing the opening screen of the simulation interface.
 * 
 * @author Willy McHie
 * Wheaton College, CSCI 335, Spring 2013
 */

package edu.wheaton.simulator.gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class TitleScreen extends Screen {

	private static final long serialVersionUID = 4901621402376078633L;

	public TitleScreen(ScreenManager sm) {
		super(sm);
		JLabel label = new JLabel("Welcome to the Simulator!", SwingConstants.CENTER);
		label.setHorizontalTextPosition(SwingConstants.CENTER);
		this.setLayout(new FlowLayout());
		JButton newSim = new JButton("New Simulation");
		newSim.addActionListener(this);
		JButton loadSim = new JButton("Load a saved Simulation");
		// Since serialization is not yet implemented.
		loadSim.setEnabled(false);
		loadSim.addActionListener(this);
	}

	@Override
	public void sendInfo() {
		return;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equalsIgnoreCase("New Simulation")) {
			sm.update(sm.getScreen("New Simulation"));
		} else if (e.getActionCommand().equalsIgnoreCase("Load Simulation")) {
			// TODO
		}
	}
}
