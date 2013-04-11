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
		

		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();

		
		initLabel();
		initMainPanel(panel1,panel2);
		initButton(panel1,"New Simulation", "New Simulation");
		initButton(panel2,"Load a saved Simulation", "Load Existing");
		
		// Since serialization is not yet implemented.
		panel2.getComponent(0).setEnabled(false);
		this.setVisible(true);

	}
	
	private void initLabel(){
		JLabel label = new JLabel("Welcome to the Simulator!");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setPreferredSize(new Dimension(300, 150));
		// Since serialization is not yet implemented.
		this.add(label, BorderLayout.NORTH);
		//
	}
	
	private void initMainPanel(JPanel panel1, JPanel panel2){
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		// Since serialization is not yet implemented.
		mainPanel.add(Box.createVerticalGlue());
		mainPanel.add(panel1);
		mainPanel.add(panel2);
		mainPanel.add(Box.createVerticalGlue());
		this.add(mainPanel, BorderLayout.CENTER);
		//
	}
	
	private void initButton(JPanel panel, String buttonName, String listenerName){
		JButton button = new JButton(buttonName);
		button.setAlignmentX(CENTER_ALIGNMENT);
		button.setPreferredSize(new Dimension(200, 70));
		button.addActionListener(new GeneralButtonListener(listenerName, sm));
		panel.add(button);
	}

	@Override
	public void load() {
		// Nothing to load
		
	}
}
