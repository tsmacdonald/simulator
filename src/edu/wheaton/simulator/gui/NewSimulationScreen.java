package edu.wheaton.simulator.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.*;

public class NewSimulationScreen extends Screen {

	private static final long serialVersionUID = 1L;

	private JTextField name;

	private JTextField height;

	private JTextField width;

	private String nameString;

	private int heightInt;

	private int widthInt;

	public NewSimulationScreen(ScreenManager sm) {
		super(sm);
		JLabel label = new JLabel("New Simulation");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setHorizontalTextPosition(SwingConstants.CENTER);
		this.add(label);
		JPanel simPanel = new JPanel(new GridLayout(3, 2));
		JButton finishButton = new JButton("Finish");
		finishButton.addActionListener(this);
		this.add(finishButton);
		name = new JTextField(25);
		height = new JTextField(25);
		width = new JTextField(25);
		JLabel nameLabel = new JLabel("Name:");
		JLabel heightLabel = new JLabel("Height:");
		JLabel widthLabel = new JLabel("Width");
		simPanel.add(nameLabel);
		simPanel.add(name);
		simPanel.add(heightLabel);
		simPanel.add(height);
		simPanel.add(widthLabel);
		simPanel.add(width);
		simPanel.setVisible(true);
		this.setLayout(new GridLayout(3,1));
		this.add(simPanel);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		nameString = name.getText();
		try {
			heightInt = Integer.parseInt(height.getText());
			widthInt = Integer.parseInt(width.getText());
		} catch(java.lang.NumberFormatException nfe) { }
		sm.update(sm.getScreen("Edit Simulation"));
	}

	@Override
	public void sendInfo() {
		// not sure what to do with this yet
	}
}
