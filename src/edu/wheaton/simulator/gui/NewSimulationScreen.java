package edu.wheaton.simulator.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class NewSimulationScreen extends Screen{

	private static final long serialVersionUID = 1L;

	private JLabel label;
	
	private JPanel simPanel;
	
	private JButton finishButton;
	
	private JTextField name;
	
	private JTextField height;
	
	private JTextField width;
	
	private JLabel nameLabel;
	
	private JLabel heightLabel;
	
	private JLabel widthLabel;
	
	public String nameString;
	
	public int heightInt;
	
	public int widthInt;
	
	public NewSimulationScreen(ScreenManager sm){
		super(sm);
		label = new JLabel("New Simulation");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setHorizontalTextPosition(SwingConstants.CENTER);
		simPanel = new JPanel(new GridLayout(3, 2));
		finishButton = new JButton("Finish");
		finishButton.addActionListener(this);
		name = new JTextField(25);
		height = new JTextField(25);
		width = new JTextField(25);
		nameLabel = new JLabel("Name:");
		heightLabel = new JLabel("Height:");
		widthLabel = new JLabel("Width");
		simPanel.add(nameLabel);
		simPanel.add(name);
		simPanel.add(heightLabel);
		simPanel.add(height);
		simPanel.add(widthLabel);
		simPanel.add(width);
		simPanel.setVisible(true);
		this.layout = new GridLayout(3, 1);
		components = new JComponent[3];
		components[0] = label;
		components[1] = simPanel;
		components[2] = finishButton;
	}
	
	public void actionPerformed(ActionEvent e){
		nameString = name.getText();
		heightInt = Integer.parseInt(height.getText());
		widthInt = Integer.parseInt(width.getText());
		sendInfo();
	}
	
	public void sendInfo(){
		//not sure what to do with this yet
	}

}
