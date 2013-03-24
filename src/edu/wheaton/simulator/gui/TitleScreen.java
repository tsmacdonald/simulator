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

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class TitleScreen extends Screen {

	private static final long serialVersionUID = 4901621402376078633L;

	public TitleScreen(ScreenManager sm) {
		super(sm);
		this.setLayout(new BorderLayout());
		JLabel label = new JLabel("Welcome to the Simulator!");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setPreferredSize(new Dimension(300, 150));
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JButton newSim = new JButton("New Simulation");
		newSim.setAlignmentX(CENTER_ALIGNMENT);
		newSim.setPreferredSize(new Dimension(200, 70));
		newSim.addActionListener(new GeneralButtonListener("New Simulation", sm));
		JButton loadSim = new JButton("Load a saved Simulation");
		loadSim.setAlignmentX(CENTER_ALIGNMENT);
		loadSim.setPreferredSize(new Dimension(200, 70));
		// Since serialization is not yet implemented.
		loadSim.setEnabled(false);
		loadSim.addActionListener(new GeneralButtonListener("Load Existing", sm));
		this.add(label, BorderLayout.NORTH);
		panel1.add(newSim);
		panel2.add(loadSim);
		mainPanel.add(Box.createVerticalGlue());
		mainPanel.add(panel1);
		mainPanel.add(panel2);
		mainPanel.add(Box.createVerticalGlue());
		this.add(mainPanel, BorderLayout.CENTER);

		this.setVisible(true);
	}

	@Override
	public void load() {
		// Nothing to load
		
	}
}
