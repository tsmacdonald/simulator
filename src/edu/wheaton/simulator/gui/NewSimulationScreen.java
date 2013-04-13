package edu.wheaton.simulator.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
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

	public NewSimulationScreen(ScreenManager sm) {
		super(sm);
		this.setLayout(new BorderLayout());
		JLabel label = makeLabelPreferredSize("New Simulation",300, 150);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		JPanel simPanel = makeBoxPanel(BoxLayout.PAGE_AXIS);
		JPanel panel1 = makeBoxPanel(BoxLayout.LINE_AXIS);
		JPanel panel2 = makeBoxPanel(BoxLayout.LINE_AXIS);
		JPanel panel3 = makeBoxPanel(BoxLayout.LINE_AXIS);
		JPanel buttonPanel = new JPanel();
		name = new JTextField(40);
		name.setMaximumSize(new Dimension(400, 30));
		height = new JTextField(10);
		height.setMaximumSize(new Dimension(100, 30));
		width = new JTextField(10);
		width.setMaximumSize(new Dimension(100, 30));
		JLabel nameLabel = new JLabel("Name: ");
		nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		JLabel heightLabel = new JLabel("Height:");
		heightLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		JLabel widthLabel = new JLabel("Width:");
		widthLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		JButton newSimButton = makeButton("Blank Simulation",new NewSimScreenFinishListener(name, width, height, sm));
		
		//These simulations forms should eventually be loaded through normal serialization process
		newSimButton.setPreferredSize(new Dimension(200, 75));
		JButton conwayButton = makeButton("Conway's Game of Life",new ConwayFinishListener(name, width, height, sm));
		conwayButton.setPreferredSize(new Dimension(200, 75));
		JButton rpsButton = makeButton("Rock Paper Scissors",new RockPaperScissorsFinishListener(name, width, height, sm));
		rpsButton.setPreferredSize(new Dimension(200, 75));
		
		panel1.add(nameLabel);
		panel1.add(name);
		panel2.add(heightLabel);
		panel2.add(height);
		panel2.add(Box.createRigidArea(new Dimension(20, 0)));
		panel2.add(widthLabel);
		panel2.add(width);
		simPanel.add(panel1);
		simPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		simPanel.add(panel2);
		simPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		buttonPanel.add(newSimButton);
		buttonPanel.add(conwayButton);
		buttonPanel.add(rpsButton);
		simPanel.add(buttonPanel);
		this.add(label, BorderLayout.NORTH);
		this.add(simPanel, BorderLayout.CENTER);
		name.setText("New Simulation");
		height.setText("10");
		width.setText("10");
		this.setVisible(true);
	}

	@Override
	public void load() {
		// TODO Auto-generated method stub
		
	}
}
