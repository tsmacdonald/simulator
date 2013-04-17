package edu.wheaton.simulator.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class NewSimulationScreen extends Screen {

	private static final long serialVersionUID = 1L;

	private JTextField name;

	private JTextField height;

	private JTextField width;

	public NewSimulationScreen(SimulatorGuiManager gm) {
		super(gm);
		this.setLayout(new GridBagLayout());

		initLabel();
		name = initNameField("Name: ", "Untitled Simulation");
		height = initHeightField("Grid Height: ", "10");
		width = initWidthField("Grid Width: ", "10");
		initButtonPanel();
		this.setVisible(true);
	}

	@Override
	public void load() {
		// TODO Auto-generated method stub

	}

	private void initLabel(){
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 4;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 50;

		JLabel label = new JLabel("New Simulation",SwingConstants.CENTER);
		this.add(label, c);
	}

	private JTextField initNameField(String label, String initText) {
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		JLabel nameLabel = new JLabel(label);
		this.add(nameLabel, c);

		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 3;
		c.fill = GridBagConstraints.HORIZONTAL;
		JTextField nameField = GuiUtility.makeTextField(initText,30,MaxSize.NULL,MinSize.NULL);
		this.add(nameField, c);
		return nameField;

	}

	private JTextField initHeightField(String label, String initText) {
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		JLabel heightLabel = GuiUtility.makeLabel(label,MaxSize.NULL,HorizontalAlignment.RIGHT);
		this.add(heightLabel, c);

		c.gridx = 1;
		c.gridy = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		JTextField heightField = GuiUtility.makeTextField(initText,10,MaxSize.NULL,new MinSize(70,30));
		this.add(heightField, c);
		return heightField;

	}

	private JTextField initWidthField(String label, String initText) {
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 2;
		c.gridy = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		JLabel widthLabel = new JLabel(label,SwingConstants.RIGHT);
		this.add(widthLabel, c);

		c.gridx = 3;
		c.gridy = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		JTextField widthField = GuiUtility.makeTextField(initText,10,MaxSize.NULL,new MinSize(70,30));
		this.add(widthField, c);
		return widthField;
	}

	private void initButtonPanel(){
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(3,1));
		
		JButton newSimButton = GuiUtility.makeButton("Blank Simulation",new NewSimScreenFinishListener(name, width, height, getGuiManager()));
		newSimButton.setPreferredSize(new Dimension(120, 40));
		//These simulation forms should eventually be loaded through normal serialization process
		JButton conwayButton = GuiUtility.makeButton("Conway's Game of Life",new ConwayFinishListener(name, width, height, getGuiManager()));
		conwayButton.setPreferredSize(new Dimension(120, 40));
		JButton rpsButton = GuiUtility.makeButton("Rock Paper Scissors",new RockPaperScissorsFinishListener(name, width, height, getGuiManager()));
		rpsButton.setPreferredSize(new Dimension(120, 40));
		
		buttonPanel.add(newSimButton);
		buttonPanel.add(conwayButton);
		buttonPanel.add(rpsButton);
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 4;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 40;
		
		this.add(buttonPanel, c);
	}
}
