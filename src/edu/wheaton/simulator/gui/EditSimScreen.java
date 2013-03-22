package edu.wheaton.simulator.gui;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class EditSimScreen extends Screen {

	private static final long serialVersionUID = 3629462657811804434L;
	
	private JButton newSimulation;
	private JButton loadExisting;
	private JButton save;
	private JButton statistics;
	private JButton agents;
	private JButton gridSetup;
	private JButton startSimulation;
	private JButton fields;
	private JPanel panel1;
	private JPanel panel2;
	private JButton[] buttons;
	
	public EditSimScreen(ScreenManager sm) {
		super(sm);
		label = new JLabel("Edit Simulation");
		label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		label.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		
		buttons = new JButton[8];
		newSimulation = new JButton("New Simulation");
		buttons[0] = newSimulation;
		loadExisting = new JButton("Load Existing");
		buttons[1] = loadExisting;
		save = new JButton("Save");
		buttons[2] = save;
		statistics = new JButton("Statistics");
		buttons[3] = statistics;
		agents = new JButton("Agents");
		buttons[4] = agents;
		gridSetup = new JButton("Grid Setup");
		buttons[5] = gridSetup;
		startSimulation = new JButton("Start Simulation");
		buttons[6] = startSimulation;
		fields = new JButton("Fields");
		buttons[7] = fields;
		for(JButton j : buttons)
			j.addActionListener(this);
		
		layout = new FlowLayout();
		panel1 = new JPanel(new GridLayout(4,1));
		panel2 = new JPanel(new GridLayout(4,1));
		panel1.add(newSimulation);
		panel1.add(loadExisting);
		panel1.add(save);
		panel1.add(statistics);
		panel2.add(agents);
		panel2.add(gridSetup);
		panel2.add(startSimulation);
		panel2.add(fields);
		
		components = new JComponent[3];
		components[0] = label;
		components[1] = panel1;
		components[2] = panel2;
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
