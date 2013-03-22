/**
 * TitleScreen.java
 * 
 * Class representing the opening screen of the simulation interface.
 * 
 * @author Willy McHie
 * Wheaton College, CSCI 335, Spring 2013
 */

package edu.wheaton.simulator.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class TitleScreen extends Screen {

	private static final long serialVersionUID = 4901621402376078633L;

	public TitleScreen(ScreenManager sm) {
		super(sm);
		this.setLayout(new BorderLayout());
		JLabel label = new JLabel("Welcome to the Simulator!");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setPreferredSize(new Dimension(300, 150));
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		JButton newSim = new JButton("New Simulation");
		newSim.setAlignmentX(CENTER_ALIGNMENT);
		newSim.setPreferredSize(new Dimension(100, 60));
		newSim.addActionListener(this);
		JButton loadSim = new JButton("Load a saved Simulation");
		loadSim.setAlignmentX(CENTER_ALIGNMENT);
		loadSim.setPreferredSize(new Dimension(100, 60));
		// Since serialization is not yet implemented.
		loadSim.setEnabled(false);
		loadSim.addActionListener(this);
		this.add(label, BorderLayout.NORTH);
		panel.add(newSim);
		panel.add(loadSim);
		this.add(panel, BorderLayout.CENTER);

		this.setVisible(true);
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
			// TODO empty else-if
		}
	}
}
