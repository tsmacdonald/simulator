package edu.wheaton.simulator.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class EditSimScreen extends Screen {

	private static final long serialVersionUID = 3629462657811804434L;
	
	//TODO throughout the menu: figure out which buttons/windows need to 
	//     disabled while simulation is running.
	public EditSimScreen(final ScreenManager sm) {
		super(sm);
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		JLabel label = makeLabelPreferredSize("Edit Simulation",500, 200);
		label.setAlignmentX(CENTER_ALIGNMENT);
		JButton newSimulation = makeButton("New Simulation",new GeneralButtonListener("New Simulation", sm));
		JButton loadExisting = makeButton("Load Existing",new GeneralButtonListener("Load Existing", sm));
		//serialization not yet implemented
		loadExisting.setEnabled(false); 
		
		JButton save = makeButton("Save",new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				//TODO need serialization
			}
		});
		save.setEnabled(false); //serialization not yet implemented
		
		
		JButton entities = makeButton("Entities",new GeneralButtonListener("Entities", sm));
		JButton fields = makeButton("Fields",new GeneralButtonListener("Fields", sm));
		//TODO fields getting refactored, temporarily disabling this button
		//fields.setEnabled(false);


		JButton statistics = makeButton("Statistics",new GeneralButtonListener("Statistics", sm));
		JButton gridSetup = makeButton("Grid Setup",new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				((SetupScreen)sm.getScreen("Grid Setup")).load();
				sm.update(sm.getScreen("Grid Setup"));
			}
		});
		
		JButton spawning = makeButton("Spawning",new GeneralButtonListener("Spawning", sm));
		JButton viewSimulation = makeButton("View Simulation",new GeneralButtonListener("View Simulation", sm));
		viewSimulation.setPreferredSize(new Dimension(400, 120));
		
		JPanel mainPanel = makeBoxPanel(BoxLayout.Y_AXIS);
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

	@Override
	public void load() {
		return;
	}
	
}
