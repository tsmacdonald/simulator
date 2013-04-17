/**
 * TitleScreen.java
 * 
 * Class representing the opening screen of the simulation interface.
 * 
 * @author Willy McHie
 * Wheaton College, CSCI 335, Spring 2013
 */

package edu.wheaton.simulator.gui.screen;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import edu.wheaton.simulator.gui.GeneralButtonListener;
import edu.wheaton.simulator.gui.Gui;
import edu.wheaton.simulator.gui.PrefSize;
import edu.wheaton.simulator.gui.SimulatorGuiManager;

public class TitleScreen extends Screen {

	private static final long serialVersionUID = 4901621402376078633L;

	public TitleScreen(SimulatorGuiManager gm) {
		super(gm);
		this.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 50;
		c.gridx = 0;
		c.gridy = 0;
		add(new JLabel("Welcome to the Simulator!",
			SwingConstants.CENTER), c);
		
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = GridBagConstraints.RELATIVE;
		add(makeButton("New Simulation", "New Simulation"),c);
		add(makeButton("Load a saved Simulation", "Load Existing"),c);
		
		// Since serialization is not yet implemented.
		this.getComponent(2).setEnabled(false);
		this.setVisible(true);
	}
	
	private JButton makeButton(String buttonName, String listenerName){
		PrefSize ps = new PrefSize(200,70);
		JButton button = Gui.makeButton(buttonName,ps,
			new GeneralButtonListener(listenerName, getScreenManager()));
		button.setAlignmentX(CENTER_ALIGNMENT);
		button.setMinimumSize(ps);
		return button;
	}

	@Override
	public void load() {
	}
}
