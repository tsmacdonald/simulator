package edu.wheaton.simulator.gui.screen;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import edu.wheaton.simulator.gui.ConwayFinishListener;
import edu.wheaton.simulator.gui.FileMenu;
import edu.wheaton.simulator.gui.Gui;
import edu.wheaton.simulator.gui.HorizontalAlignment;
import edu.wheaton.simulator.gui.MaxSize;
import edu.wheaton.simulator.gui.MinSize;
import edu.wheaton.simulator.gui.NewSimScreenFinishListener;
import edu.wheaton.simulator.gui.PrefSize;
import edu.wheaton.simulator.gui.RockPaperScissorsFinishListener;
import edu.wheaton.simulator.gui.SimulatorFacade;


public class NewSimulationScreen extends Screen {

	private static final long serialVersionUID = 1L;

	private JTextField name;

	private JTextField height;

	private JTextField width;

	public NewSimulationScreen(SimulatorFacade gm) {
		super(gm);
		this.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 4;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 50;
		this.add(new JLabel(
				"New Simulation",SwingConstants.CENTER), c);
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		this.add(new JLabel("Name: "), c);

		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 3;
		c.fill = GridBagConstraints.HORIZONTAL;
		name = Gui.makeTextField("Untitled Simulation",30,MaxSize.NULL,MinSize.NULL);
		this.add(name, c);
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		this.add(Gui.makeLabel("Grid Height: ",MaxSize.NULL,HorizontalAlignment.RIGHT), c);

		c.gridx = 1;
		c.gridy = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		height = Gui.makeTextField("10",10,MaxSize.NULL,new MinSize(70,30));
		this.add(height, c);
		
		c = new GridBagConstraints();
		c.gridx = 2;
		c.gridy = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		this.add(new JLabel("Grid Width: ",SwingConstants.RIGHT), c);

		c.gridx = 3;
		c.gridy = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		width = Gui.makeTextField("10",10,MaxSize.NULL,new MinSize(70,30));
		this.add(width, c);
		
		PrefSize ps = new PrefSize(120,40);
		JPanel buttonPanel = Gui.makePanel(
			Gui.makeButton("Blank Simulation",ps,
					new NewSimScreenFinishListener(name, width, height, getGuiManager())),
			//These simulation forms should eventually be loaded through normal serialization process
			Gui.makeButton("Conway's Game of Life",ps,
					new ConwayFinishListener(name, width, height, getGuiManager())),
			Gui.makeButton("Rock Paper Scissors", ps,
					new RockPaperScissorsFinishListener(name, width, height, getGuiManager()))
		);
		buttonPanel.setLayout(new GridLayout(3,1));
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 4;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 40;
		this.add(buttonPanel, c);
		
		this.setVisible(true);
	}

	@Override
	public void load() {
		FileMenu fm = Gui.getFileMenu();
		fm.setNewSim(false);
		fm.setSaveSim(false);
	}
}
