package edu.wheaton.simulator.gui;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.*;

public class EditSimScreen extends Screen {

	private static final long serialVersionUID = 3629462657811804434L;
	
	private JButton[] buttons;
	
	public EditSimScreen(ScreenManager sm) {
		super(sm);
		JLabel label = new JLabel("Edit Simulation");
		label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		label.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		this.add(label);
		buttons = new JButton[9];
		JButton newSimulation = new JButton("New Simulation");
		buttons[0] = newSimulation;
		JButton loadExisting = new JButton("Load Existing");
		loadExisting.setEnabled(false); //serialization not yet implemented
		buttons[1] = loadExisting;
		JButton save = new JButton("Save");
		save.setEnabled(false); //serialization not yet implemented
		buttons[2] = save;
		JButton statistics = new JButton("Statistics");
		buttons[3] = statistics;
		JButton agents = new JButton("Agents");
		buttons[4] = agents;
		JButton gridSetup = new JButton("Grid Setup");
		buttons[5] = gridSetup;
		JButton startSimulation = new JButton("View Simulation");
		buttons[6] = startSimulation;
		JButton fields = new JButton("Fields");
		buttons[7] = fields;
		JButton spawning = new JButton("Spawning");
		buttons[8] = spawning;
		
		for(JButton j : buttons)
			j.addActionListener(this);
		this.setLayout(new FlowLayout());
		JPanel panel1 = new JPanel(new GridLayout(4,1));
		JPanel panel2 = new JPanel(new GridLayout(5,1));
		panel1.add(newSimulation);
		panel1.add(loadExisting);
		panel1.add(save);
		panel1.add(statistics);
		panel2.add(agents);
		panel2.add(gridSetup);
		panel2.add(startSimulation);
		panel2.add(fields);
		panel2.add(spawning);
		this.add(panel1);
		this.add(panel2);
		
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
