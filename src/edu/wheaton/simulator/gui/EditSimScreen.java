package edu.wheaton.simulator.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class EditSimScreen extends Screen {

	private static final long serialVersionUID = 3629462657811804434L;
	
	public EditSimScreen(ScreenManager sm) {
		super(sm);
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		JLabel label = new JLabel("Edit Simulation");
		label.setAlignmentX(CENTER_ALIGNMENT);
		label.setPreferredSize(new Dimension(500, 200));
		JButton newSimulation = new JButton("New Simulation");
		//newSimulation.setPreferredSize(new Dimension(175, 60));
		newSimulation.addActionListener(new GeneralButtonListener());
		JButton loadExisting = new JButton("Load Existing");
		//loadExisting.setPreferredSize(new Dimension(175, 60));
		loadExisting.setEnabled(false); //serialization not yet implemented
		loadExisting.addActionListener(new GeneralButtonListener());
		JButton save = new JButton("Save");
		//save.setPreferredSize(new Dimension(175, 60));
		save.setEnabled(false); //serialization not yet implemented
		save.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e){
				//TODO need serialization
			}
		});
		JButton entities = new JButton("Entities");
		//agents.setPreferredSize(new Dimension(175, 60));
		entities.addActionListener(new GeneralButtonListener());
		JButton fields = new JButton("Fields");
		//fields.setPreferredSize(new Dimension(175, 60));
		fields.addActionListener(new GeneralButtonListener());
		JButton statistics = new JButton("Statistics");
		//statistics.setPreferredSize(new Dimension(175, 60));
		statistics.addActionListener(new GeneralButtonListener());
		JButton gridSetup = new JButton("Grid Setup");
		//gridSetup.setPreferredSize(new Dimension(175, 60));
		final SetupScreen update = (SetupScreen) sm.getScreen("Grid Setup");
		final String name = sm.getGUIname();
		final int width = sm.getGUIwidth();
		final int height = sm.getGUIheight();
		final ScreenManager sm2 = sm;
		gridSetup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				update.updateSetUpScreen(name, width, height);
				sm2.update(update);
			}
		});
		
		JButton spawning = new JButton("Spawning");
		//spawning.setPreferredSize(new Dimension(175, 60));
		spawning.addActionListener(new GeneralButtonListener());
		JButton viewSimulation = new JButton("View Simulation");
		viewSimulation.setPreferredSize(new Dimension(400, 120));
		viewSimulation.addActionListener(new GeneralButtonListener());
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setMaximumSize(new Dimension(800, 1000));
		mainPanel.setPreferredSize(new Dimension(800, 1000));
		JPanel panel1 = new JPanel();
		panel1.setLayout(new GridLayout(1, 3));
		JPanel panel2 = new JPanel();
		panel2.setLayout(new GridLayout(1, 3));
		JPanel panel3 = new JPanel();
		panel3.setLayout(new GridLayout(1, 2));
		JPanel panel4 = new JPanel();
		panel1.add(newSimulation);
		panel1.add(loadExisting);
		panel1.add(save);
		panel2.add(entities);
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
		
	}

	private class GeneralButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			String action = e.getActionCommand();
			Screen update = sm.getScreen(action);
			sm.update(update);
		}
	}

}
