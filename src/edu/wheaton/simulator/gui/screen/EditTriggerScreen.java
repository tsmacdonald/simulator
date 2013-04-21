package edu.wheaton.simulator.gui.screen;

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

import edu.wheaton.simulator.gui.SimulatorFacade;

/**
 * 
 * @author daniel.davenport daniel.gill
 *
 */
public class EditTriggerScreen extends Screen {

	private static final long serialVersionUID = 3261558461232576081L;

	private JButton addConditional; //TODO fix this warning
	
	private JButton addBehavior; //TODO fix this warning
	
	private JTextField nameField;
	
	private JSpinner prioritySpinner;
	
	private ArrayList<JComboBox> conditionals; //TODO fix this warning
	
	private ArrayList<JComboBox> behaviors; //TODO fix this warning
	
	private JScrollPane conditionalLayout;
	
	private JScrollPane behaviorLayout;
	
	public EditTriggerScreen(SimulatorFacade gm) {
		super(gm);
		this.setLayout(new GridBagLayout());
		addNameLabel();
		addPriorityLabel();
		addNameField();
		addSpinner(); 
		addIf(); 
		addConditionsLayout();
		addThen();
		addBehaviorLayout();		
	}

	private void addNameLabel() {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridwidth = 1; 
		constraints.gridheight = 1;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.anchor = GridBagConstraints.BASELINE_LEADING;
		constraints.fill = GridBagConstraints.BOTH;
		
		JLabel name = new JLabel("Name:");
		add(name, constraints);
	}
	
	private void addPriorityLabel() {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridwidth = 1; 
		constraints.gridheight = 1;
		constraints.gridx = 2;
		constraints.gridy = 0;
		constraints.insets = new Insets(0, 50, 0, 0);
		constraints.fill = GridBagConstraints.BOTH;
		
		JLabel name = new JLabel("Priority Level:");
		add(name, constraints);		
	}
	
	private void addNameField(){
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridheight = 1; 
		constraints.gridwidth = 2; 
		constraints.gridx = 0; 
		constraints.gridy = 1; 
		constraints.ipadx = 150;
		constraints.anchor = GridBagConstraints.BASELINE_LEADING;
		
		nameField = new JTextField();
		this.add(nameField, constraints);
	}
	
	private void addSpinner() {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridwidth = 1; 
		constraints.gridheight = 1;
		constraints.gridx = 2;
		constraints.gridy = 1;
		constraints.anchor = GridBagConstraints.BASELINE_LEADING;
		constraints.insets = new Insets(0, 50, 0, 0);
		
		prioritySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1)); 
		add(prioritySpinner, constraints);
	}

	private void addIf() {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridwidth = 1; 
		constraints.gridheight = 1;
		constraints.gridx = 0;
		constraints.gridy = 2; 
		constraints.insets = new Insets(10, 0, 0, 0);
		constraints.anchor = GridBagConstraints.BASELINE_LEADING;
		constraints.fill = GridBagConstraints.NONE;  
		
		JLabel ifLabel = new JLabel("If:"); 
		add(ifLabel, constraints);
	}
	
	private void addConditionsLayout() {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridwidth = 3; 
		constraints.gridheight = 1;
		constraints.gridx = 0;
		constraints.gridy = 3; 
		constraints.ipadx = 600;
		constraints.ipady = 150;
		constraints.anchor = GridBagConstraints.BASELINE_LEADING;
		constraints.fill = GridBagConstraints.BOTH;
		
		conditionalLayout = new JScrollPane();
		conditionalLayout.setBackground(Color.blue);
		add(conditionalLayout, constraints);
	}
	
	private void addThen() {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridwidth = 1; 
		constraints.gridheight = 1;
		constraints.gridx = 0;
		constraints.gridy = 4; 
		constraints.insets = new Insets(10, 0, 0, 0);
		constraints.anchor = GridBagConstraints.BASELINE_LEADING;
		constraints.fill = GridBagConstraints.NONE;
		
		JLabel thenLabel = new JLabel("Then:"); 
		add(thenLabel, constraints);
	}

	private void addBehaviorLayout() {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridwidth = 3; 
		constraints.gridheight = 1;
		constraints.gridx = 0;
		constraints.gridy = 5; 
		constraints.ipadx = 600;
		constraints.ipady = 150;
		constraints.fill = GridBagConstraints.BOTH;
		
		behaviorLayout = new JScrollPane();
		behaviorLayout.setBackground(Color.black);
		add(behaviorLayout, constraints);
	}

	@Override
	public void load() {
	}
}
