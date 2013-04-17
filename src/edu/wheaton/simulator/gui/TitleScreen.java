/**
 * TitleScreen.java
 * 
 * Class representing the opening screen of the simulation interface.
 * 
 * @author Willy McHie
 * Wheaton College, CSCI 335, Spring 2013
 */

package edu.wheaton.simulator.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class TitleScreen extends Screen {

	private static final long serialVersionUID = 4901621402376078633L;

	public TitleScreen(SimulatorGuiManager gm) {
		super(gm);
		this.setLayout(new GridBagLayout());
		
		initLabel();
		initButton("New Simulation", "New Simulation");
		initButton("Load a saved Simulation", "Load Existing");
		
		// Since serialization is not yet implemented.
		this.getComponent(2).setEnabled(false);
		
		this.setVisible(true);
	}
	
	private void initLabel(){
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 50;
		c.gridx = 0;
		c.gridy = 0;
		
		JLabel label = new JLabel("Welcome to the Simulator!",SwingConstants.CENTER);
		this.add(label, c);
	}
	
	private void initButton(String buttonName, String listenerName){
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = GridBagConstraints.RELATIVE;
		
		JButton button = GuiUtility.makeButton(buttonName,new GeneralButtonListener(listenerName, gm.getScreenManager()));
		button.setAlignmentX(CENTER_ALIGNMENT);
		button.setMinimumSize(new Dimension(200, 70));
		button.setPreferredSize(new Dimension(200, 70));
		this.add(button, c);
	}

	@Override
	public void load() {
		// Nothing to load
	}
}
