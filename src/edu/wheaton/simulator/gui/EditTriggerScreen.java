package edu.wheaton.simulator.gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

/**
 * 
 * @author daniel.davenport daniel.gill
 *
 */
public class EditTriggerScreen extends Screen {

	private static final long serialVersionUID = 3261558461232576081L;

	private JButton addConditional;
	
	private JButton addBehavior;
	
	private JTextField nameField;
	
	private JSpinner prioritySpinner;
	
	private ArrayList<JComboBox> conditionals;
	
	private ArrayList<JComboBox> behaviors;
	
	private JScrollPane conditionalLayout;
	
	private JScrollPane behaviorLayout;
	
	public EditTriggerScreen(Manager sm) {
		super(sm);
		this.setLayout(new GridBagLayout());
		addNameLabel(new GridBagConstraints());
		addPriorityLabel(new GridBagConstraints());
		addNameField(new GridBagConstraints());
		addSpinner(new GridBagConstraints()); 
		addIf(new GridBagConstraints()); 
		addConditionsLayout(new GridBagConstraints());
		addThen(new GridBagConstraints());
		addBehaviorLayout(new GridBagConstraints());		
	}

	private void addNameLabel(GridBagConstraints constraints) {
		JLabel name = new JLabel("Name:");
		constraints.gridwidth = 1; 
		constraints.gridheight = 1;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.anchor = GridBagConstraints.BASELINE_LEADING;
		constraints.fill = GridBagConstraints.BOTH;
		add(name, constraints);
	}
	
	private void addPriorityLabel(GridBagConstraints constraints) {
		JLabel name = new JLabel("Priority Level:");
		constraints.gridwidth = 1; 
		constraints.gridheight = 1;
		constraints.gridx = 2;
		constraints.gridy = 0;
		constraints.insets = new Insets(0, 50, 0, 0);
		constraints.fill = GridBagConstraints.BOTH;
		add(name, constraints);		
	}
	
	private void addNameField(GridBagConstraints constraints){
		nameField = new JTextField();
		constraints.gridheight = 1; 
		constraints.gridwidth = 2; 
		constraints.gridx = 0; 
		constraints.gridy = 1; 
		constraints.ipadx = 150;
		constraints.anchor = GridBagConstraints.BASELINE_LEADING;
		this.add(nameField, constraints);
	}
	
	private void addSpinner(GridBagConstraints constraints) {
		prioritySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1)); 
		constraints.gridwidth = 1; 
		constraints.gridheight = 1;
		constraints.gridx = 2;
		constraints.gridy = 1;
		constraints.anchor = GridBagConstraints.BASELINE_LEADING;
		constraints.insets = new Insets(0, 50, 0, 0);
		add(prioritySpinner, constraints);
	}

	private void addIf(GridBagConstraints constraints) {
		JLabel ifLabel = new JLabel("If:"); 
		constraints.gridwidth = 1; 
		constraints.gridheight = 1;
		constraints.gridx = 0;
		constraints.gridy = 2; 
		constraints.insets = new Insets(10, 0, 0, 0);
		constraints.anchor = GridBagConstraints.BASELINE_LEADING;
		constraints.fill = GridBagConstraints.NONE;  
		add(ifLabel, constraints);
	}
	
	private void addConditionsLayout(GridBagConstraints constraints) {
		conditionalLayout = new JScrollPane();
		constraints.gridwidth = 3; 
		constraints.gridheight = 1;
		constraints.gridx = 0;
		constraints.gridy = 3; 
		constraints.ipadx = 600;
		constraints.ipady = 150;
		constraints.anchor = GridBagConstraints.BASELINE_LEADING;
		constraints.fill = GridBagConstraints.BOTH;
		conditionalLayout.setBackground(Color.blue);
		add(conditionalLayout, constraints);
	}
	
	private void addThen(GridBagConstraints constraints) {
		JLabel thenLabel = new JLabel("Then:"); 
		constraints.gridwidth = 1; 
		constraints.gridheight = 1;
		constraints.gridx = 0;
		constraints.gridy = 4; 
		constraints.insets = new Insets(10, 0, 0, 0);
		constraints.anchor = GridBagConstraints.BASELINE_LEADING;
		constraints.fill = GridBagConstraints.NONE;
		add(thenLabel, constraints);
	}

	private void addBehaviorLayout(GridBagConstraints constraints) {
		behaviorLayout = new JScrollPane();
		constraints.gridwidth = 3; 
		constraints.gridheight = 1;
		constraints.gridx = 0;
		constraints.gridy = 5; 
		constraints.ipadx = 600;
		constraints.ipady = 150;
		constraints.fill = GridBagConstraints.BOTH;
		behaviorLayout.setBackground(Color.black);
		add(behaviorLayout, constraints);
	}

	@Override
	public void load() {
		
	}
	

}
