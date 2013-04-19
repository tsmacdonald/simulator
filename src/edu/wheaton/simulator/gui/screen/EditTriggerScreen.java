package edu.wheaton.simulator.gui.screen;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import edu.wheaton.simulator.entity.Prototype;
import edu.wheaton.simulator.entity.Trigger;
import edu.wheaton.simulator.gui.SimulatorGuiManager;

/**
 * 
 * @author daniel.davenport daniel.gill
 *
 */
public class EditTriggerScreen extends Screen {

	private static final long serialVersionUID = 3261558461232576081L;

	private JButton addConditionalBox;

	private JButton addConditionalText;

	private JButton addBehaviorBox;

	private JButton addBehaviorText;

	private JTextField nameField;

	private JSpinner prioritySpinner;

	private ArrayList<JComponent> conditionals;

	private ArrayList<JComponent> behaviors;

	private JScrollPane conditionalScrollLayout;

	private JPanel conditionalLayout;

	private JScrollPane behaviorScrollLayout;

	private JPanel behaviorLayout;

	private Trigger.Builder builder;

	private int numConditionals = 0;

	private int numBehaviors = 0;

	public EditTriggerScreen(SimulatorGuiManager sm) {
		super(sm);
		conditionals = new ArrayList<JComponent>();
		behaviors = new ArrayList<JComponent>();
		setLayout(new GridBagLayout());
		addNameLabel(new GridBagConstraints());
		addPriorityLabel(new GridBagConstraints());
		addNameField(new GridBagConstraints());
		addSpinner(new GridBagConstraints()); 
		addIf(new GridBagConstraints()); 
		addConditionsLayout(new GridBagConstraints());
		addThen(new GridBagConstraints());
		addBehaviorLayout(new GridBagConstraints());
		addConditionalButtons(new GridBagConstraints());
		addBehaviorButtons(new GridBagConstraints());
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
		conditionalScrollLayout = new JScrollPane();
		conditionalLayout = new JPanel();
		conditionalLayout.add(conditionalScrollLayout);
		conditionalLayout.setLayout(new GridBagLayout());
		constraints.gridwidth = 3; 
		constraints.gridheight = 2;
		constraints.gridx = 0;
		constraints.gridy = 3; 
		constraints.ipadx = 600;
		constraints.ipady = 150;
		constraints.anchor = GridBagConstraints.BASELINE_LEADING;
		constraints.fill = GridBagConstraints.BOTH;
		conditionalLayout.setBackground(Color.white);
		add(conditionalLayout, constraints);
	}

	private void addThen(GridBagConstraints constraints) {
		JLabel thenLabel = new JLabel("Then:"); 
		constraints.gridwidth = 1; 
		constraints.gridheight = 1;
		constraints.gridx = 0;
		constraints.gridy = 5; 
		constraints.insets = new Insets(10, 0, 0, 0);
		constraints.anchor = GridBagConstraints.BASELINE_LEADING;
		constraints.fill = GridBagConstraints.NONE;
		add(thenLabel, constraints);
	}

	private void addBehaviorLayout(GridBagConstraints constraints) {
		behaviorScrollLayout = new JScrollPane();
		behaviorLayout = new JPanel();
		behaviorLayout.add(behaviorScrollLayout);
		constraints.gridwidth = 3; 
		constraints.gridheight = 2;
		constraints.gridx = 0;
		constraints.gridy = 6; 
		constraints.ipadx = 600;
		constraints.ipady = 150;
		constraints.fill = GridBagConstraints.BOTH;
		behaviorLayout.setBackground(Color.white);
		add(behaviorLayout, constraints);
	}

	private void addConditionalButtons(GridBagConstraints constraints) {
		addConditionalBox = new JButton("Add Conditional");
		addConditionalText = new JButton("Add Text");
		addConditionalBox.addActionListener(new AddConditionalBoxListener());
		addConditionalText.addActionListener(new AddConditionalTextListener());
		constraints.gridx = 3;
		constraints.gridy = 3;
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		add(addConditionalBox, constraints);
		constraints.gridy = 4;
		add(addConditionalText, constraints);		
	}

	private void addBehaviorButtons(GridBagConstraints constraints) {
		addBehaviorBox = new JButton("Add Behavior");
		addBehaviorText = new JButton("Add Text");
		addBehaviorBox.addActionListener(new AddBehaviorBoxListener());
		addBehaviorText.addActionListener(new AddBehaviorTextListener());
		constraints.gridx = 3;
		constraints.gridy = 6;
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		add(addBehaviorBox, constraints);
		constraints.gridy = 7;
		add(addBehaviorText, constraints);
	}

	private JComboBox makeConditionalDropdown(){
		JComboBox toReturn = new JComboBox(builder.conditionalValues().toArray());
		toReturn.setPreferredSize(new Dimension(125, 20));
		return toReturn;
	}

	private JComboBox makeBehaviorDropdown(){
		JComboBox toReturn = new JComboBox(builder.behavioralValues().toArray());
		toReturn.setPreferredSize(new Dimension(125, 20));
		return toReturn;
	}	

	private class AddConditionalBoxListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try{
			conditionals.add(makeConditionalDropdown());
			GridBagConstraints constraints = new GridBagConstraints();
			constraints.gridwidth = 1; 
			constraints.gridheight = 1;
			constraints.gridx = numConditionals % 6;
			constraints.gridy = numConditionals / 6; 
			constraints.anchor = GridBagConstraints.NORTHWEST;
			conditionalLayout.add(conditionals.get(conditionals.size() - 1), constraints);	
			conditionalLayout.validate();
			}
			catch(Exception e){
				
			}
		}
	}

	private class AddConditionalTextListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			conditionals.add(new JTextField());
			GridBagConstraints constraints = new GridBagConstraints();
			constraints.gridwidth = 1; 
			constraints.gridheight = 1;
			constraints.gridx = numConditionals % 6;
			constraints.gridy = numConditionals / 6;
			constraints.anchor = GridBagConstraints.BASELINE_LEADING;
			conditionalLayout.add(conditionals.get(conditionals.size() - 1));
			conditionalLayout.validate();
			conditionalLayout.repaint();
		}
	}

	private class AddBehaviorBoxListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			behaviors.add(makeBehaviorDropdown());
			GridBagConstraints constraints = new GridBagConstraints();
			constraints.gridwidth = 1; 
			constraints.gridheight = 1;
			constraints.gridx = numBehaviors % 6;
			constraints.gridy = numBehaviors / 6;
			constraints.anchor = GridBagConstraints.BASELINE_LEADING;
			behaviorLayout.add(behaviors.get(behaviors.size() - 1), constraints);
			behaviorLayout.validate();
			behaviorLayout.repaint();
		}
	}

	private class AddBehaviorTextListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			behaviors.add(new JTextField());
			GridBagConstraints constraints = new GridBagConstraints();
			constraints.gridwidth = 1; 
			constraints.gridheight = 1;
			constraints.gridx = numBehaviors % 6;
			constraints.gridy = numBehaviors / 6;
			constraints.anchor = GridBagConstraints.BASELINE_LEADING;
			behaviorLayout.add(behaviors.get(behaviors.size() - 1), constraints);
			behaviorLayout.validate();
			behaviorLayout.repaint();
		}
	}

	public void load(Trigger.Builder b, Trigger t){
		builder = b;

	}

	public void load(){
	}

	public void sendInfo(){

	}

	public void reset() {
		nameField.setText("");
		prioritySpinner.setValue(0);
		numConditionals = 0;
		numBehaviors = 0;
		conditionals = new ArrayList<JComponent>();
		behaviors = new ArrayList<JComponent>();
		conditionalLayout.removeAll();
		behaviorLayout.removeAll();
		builder = null;
		validate();

	}


}
