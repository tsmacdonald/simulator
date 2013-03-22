package edu.wheaton.simulator.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.*;

public class EditSimScreen extends Screen {

	private static final long serialVersionUID = 3629462657811804434L;
	
	private JButton[] buttons;
	
	public EditSimScreen(ScreenManager sm) {
		super(sm);
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		JLabel label = new JLabel("Edit Simulation");
		label.setAlignmentX(CENTER_ALIGNMENT);
		label.setPreferredSize(new Dimension(500, 200));
		buttons = new JButton[9];
		JButton newSimulation = new JButton("New Simulation");
		//newSimulation.setPreferredSize(new Dimension(175, 60));
		buttons[0] = newSimulation;
		JButton loadExisting = new JButton("Load Existing");
		//loadExisting.setPreferredSize(new Dimension(175, 60));
		loadExisting.setEnabled(false); //serialization not yet implemented
		buttons[1] = loadExisting;
		JButton save = new JButton("Save");
		//save.setPreferredSize(new Dimension(175, 60));
		save.setEnabled(false); //serialization not yet implemented
		buttons[2] = save;
		JButton agents = new JButton("Agents");
		//agents.setPreferredSize(new Dimension(175, 60));
		buttons[3] = agents;
		JButton fields = new JButton("Fields");
		//fields.setPreferredSize(new Dimension(175, 60));
		buttons[4] = fields;
		JButton statistics = new JButton("Statistics");
		//statistics.setPreferredSize(new Dimension(175, 60));
		buttons[5] = statistics;
		JButton gridSetup = new JButton("Grid Setup");
		//gridSetup.setPreferredSize(new Dimension(175, 60));
		buttons[6] = gridSetup;
		JButton spawning = new JButton("Spawning");
		//spawning.setPreferredSize(new Dimension(175, 60));
		buttons[7] = spawning;
		JButton viewSimulation = new JButton("View Simulation");
		viewSimulation.setPreferredSize(new Dimension(400, 120));
		buttons[8] = viewSimulation;
		
		for(JButton j : buttons)
			j.addActionListener(this);
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setMaximumSize(new Dimension(800, 1000));
		mainPanel.setPreferredSize(new Dimension(800, 1000));
		JPanel panel1 = new JPanel();
		//panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));
		panel1.setLayout(new GridLayout(1, 3));
		JPanel panel2 = new JPanel();
		//panel2.setLayout(new BoxLayout(panel2, BoxLayout.LINE_AXIS));
		panel2.setLayout(new GridLayout(1, 3));
		JPanel panel3 = new JPanel();
		//panel3.setLayout(new BoxLayout(panel3, BoxLayout.LINE_AXIS));
		panel3.setLayout(new GridLayout(1, 2));
		JPanel panel4 = new JPanel();
		panel1.add(newSimulation);
		panel1.add(loadExisting);
		panel1.add(save);
		panel2.add(agents);
		panel2.add(fields);
		panel2.add(statistics);
		panel3.add(gridSetup);
		panel3.add(spawning);
		panel4.add(viewSimulation);
		mainPanel.add(panel1);
		mainPanel.add(panel2);
		mainPanel.add(panel3);
		mainPanel.add(Box.createVerticalGlue());
		mainPanel.add(panel4);
		this.add(label);
		this.add(mainPanel);
		//this.add(panel4, BorderLayout.SOUTH);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		Screen update = this;
		for(JButton j : buttons)
			if(j.getText().equals(action))
				update = sm.getScreen(action);
		sm.update(update);
	}

	@Override
	public void sendInfo() {
		// TODO Auto-generated method stub

	}
}
