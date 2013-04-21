/**
 * TitleScreen.java
 * 
 * Class representing the opening screen of the simulation interface.
 * 
 * @author Willy McHie
 * Wheaton College, CSCI 335, Spring 2013
 */

package edu.wheaton.simulator.gui.screen;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import edu.wheaton.simulator.gui.GeneralButtonListener;
import edu.wheaton.simulator.gui.Gui;
import edu.wheaton.simulator.gui.PrefSize;
import edu.wheaton.simulator.gui.SimulatorFacade;

public class TitleScreen extends Screen {

	private static final long serialVersionUID = 4901621402376078633L;

	public TitleScreen(SimulatorFacade gm) {
		super(gm);
		this.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 50;
		c.gridx = 0;
		c.gridy = 0;
		add(new JLabel("Welcome to the Simulator!", SwingConstants.CENTER), c);

		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = GridBagConstraints.RELATIVE;
		add(makeButton("New Simulation", "New Simulation"), c);
		// add(makeButton("Load a saved Simulation", "Load Existing"),c);
		JButton loadButton = new JButton("Load a Saved Simulation");
		loadButton.setPreferredSize(new Dimension(200, 70));
		loadButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// should bring up a dialog with names of simulations to load
			}
		});
		add(loadButton, c);
		JButton importButton = new JButton("Import Simulation from file");
		importButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				getGuiManager().load();
			}
		});
		loadButton.setPreferredSize(new Dimension(200, 70));
		add(importButton, c);
		this.setVisible(true);
	}

	private JButton makeButton(String buttonName, String listenerName) {
		PrefSize ps = new PrefSize(200, 70);
		JButton button = Gui.makeButton(buttonName, ps,
				new GeneralButtonListener(listenerName, getScreenManager()));
		button.setAlignmentX(CENTER_ALIGNMENT);
		button.setMinimumSize(ps);
		return button;
	}

	@Override
	public void load() { }
}
