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
		JLabel label = GuiUtility.makeLabel("Edit Simulation",new PrefSize(500, 200),null);
		label.setAlignmentX(CENTER_ALIGNMENT);
		JButton newSimulation = GuiUtility.makeButton("New Simulation",new GeneralButtonListener("New Simulation", sm));
		JButton loadExisting = GuiUtility.makeButton("Load Existing",new GeneralButtonListener("Load Existing", sm));
		//serialization not yet implemented
		loadExisting.setEnabled(false); 
		
		JButton save = GuiUtility.makeButton("Save",new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				//TODO need serialization
			}
		});
		save.setEnabled(false); //serialization not yet implemented
		
		
		JButton entities = GuiUtility.makeButton("Entities",new GeneralButtonListener("Entities", sm));
		JButton fields = GuiUtility.makeButton("Fields",new GeneralButtonListener("Fields", sm));
		//TODO fields getting refactored, temporarily disabling this button
		//fields.setEnabled(false);


		JButton statistics = GuiUtility.makeButton("Statistics",new GeneralButtonListener("Statistics", sm));
		JButton gridSetup = GuiUtility.makeButton("Grid Setup",new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				((SetupScreen)sm.getScreen("Grid Setup")).load();
				sm.update(sm.getScreen("Grid Setup"));
			}
		});
		
		JButton spawning = GuiUtility.makeButton("Spawning",new GeneralButtonListener("Spawning", sm));
		JButton viewSimulation = GuiUtility.makeButton("View Simulation",new GeneralButtonListener("View Simulation", sm));
		viewSimulation.setPreferredSize(new Dimension(400, 120));
		
		JPanel mainPanel = GuiUtility.makePanel(BoxLayoutAxis.Y_AXIS,new MaxSize(800,1000),null);
		mainPanel.setPreferredSize(new Dimension(800, 1000));
		JPanel panel1 = new JPanel(new GridLayout(1, 3));
		JPanel panel2 = new JPanel(new GridLayout(1, 3));
		JPanel panel3 = new JPanel(new GridLayout(1, 2));
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
