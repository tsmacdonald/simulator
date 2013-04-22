package edu.wheaton.simulator.gui.screen;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import com.google.common.collect.ImmutableList;

import edu.wheaton.simulator.entity.Trigger;
import edu.wheaton.simulator.gui.SimulatorFacade;

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

	private JButton deleteConditionalButton;

	private JButton deleteBehaviorButton;

	private JLabel isValidText;

	private Trigger selectedTrigger;

	public EditTriggerScreen(SimulatorFacade sm) {
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
		addValidLabel(new GridBagConstraints());
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

	private void addValidLabel(GridBagConstraints constraints){
		isValidText = new JLabel("Invalid");
		isValidText.setToolTipText("Tells whether or not the trigger created is valid" +
				"\nUpdates when save button is pressed");
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.gridx = 0;
		constraints.gridy = 10;
		constraints.anchor = GridBagConstraints.BASELINE_TRAILING;
		add(isValidText, constraints);		
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
		conditionalScrollLayout.setViewportView(conditionalLayout);
		conditionalLayout.setLayout(new GridBagLayout());
		constraints.gridwidth = 3; 
		constraints.gridheight = 3;
		constraints.gridx = 0;
		constraints.gridy = 3; 
		constraints.ipadx = 600;
		constraints.ipady = 150;
		constraints.anchor = GridBagConstraints.BASELINE_LEADING;
		constraints.fill = GridBagConstraints.BOTH;
		conditionalLayout.setBackground(Color.white);
		add(conditionalScrollLayout, constraints);
	}

	private void addThen(GridBagConstraints constraints) {
		JLabel thenLabel = new JLabel("Then:"); 
		constraints.gridwidth = 1; 
		constraints.gridheight = 1;
		constraints.gridx = 0;
		constraints.gridy = 6; 
		constraints.insets = new Insets(10, 0, 0, 0);
		constraints.anchor = GridBagConstraints.BASELINE_LEADING;
		constraints.fill = GridBagConstraints.NONE;
		add(thenLabel, constraints);
	}

	private void addBehaviorLayout(GridBagConstraints constraints) {
		behaviorScrollLayout = new JScrollPane();
		behaviorLayout = new JPanel();
		behaviorScrollLayout.setViewportView(behaviorLayout);
		behaviorLayout.setLayout(new GridBagLayout());
		constraints.gridwidth = 3; 
		constraints.gridheight = 3;
		constraints.gridx = 0;
		constraints.gridy = 7; 
		constraints.ipadx = 600;
		constraints.ipady = 150;
		constraints.fill = GridBagConstraints.BOTH;
		behaviorLayout.setBackground(Color.white);
		add(behaviorScrollLayout, constraints);
	}

	private void addConditionalButtons(GridBagConstraints constraints) {
		addConditionalBox = new JButton("Add Conditional");
		addConditionalText = new JButton("Add Text");
		deleteConditionalButton = new JButton("Delete Conditional");
		addConditionalBox.addActionListener(new AddConditionalBoxListener());
		addConditionalText.addActionListener(new AddConditionalTextListener());
		deleteConditionalButton.addActionListener(new DeleteConditionalListener());
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.gridx = 3;
		constraints.gridy = 3;
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		add(addConditionalBox, constraints);
		constraints.gridy = 4;
		add(addConditionalText, constraints);		
		constraints.gridy = 5;
		add(deleteConditionalButton, constraints);
	}

	private void addBehaviorButtons(GridBagConstraints constraints){
		addBehaviorBox = new JButton("Add Behavior");
		addBehaviorText = new JButton("Add Text");
		deleteBehaviorButton = new JButton("Delete Behavior");
		addBehaviorBox.addActionListener(new AddBehaviorBoxListener());
		addBehaviorText.addActionListener(new AddBehaviorTextListener());
		deleteBehaviorButton.addActionListener(new DeleteBehaviorListener());
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.gridx = 3;
		constraints.gridy = 7;
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		add(addBehaviorBox, constraints);
		constraints.gridy = 8;
		add(addBehaviorText, constraints);
		constraints.gridy = 9;
		add(deleteBehaviorButton, constraints);
	}

	private JComboBox makeConditionalDropdown(){
		JComboBox toReturn = new JComboBox(builder.conditionalValues().toArray());
		toReturn.setPreferredSize(new Dimension(120, 20));
		return toReturn;
	}

	private JComboBox makeBehaviorDropdown(){
		JComboBox toReturn = new JComboBox(builder.behavioralValues().toArray());
		toReturn.setPreferredSize(new Dimension(120, 20));
		return toReturn;
	}	

	private void addConditionalBox(){
		if(selectedTrigger == null)
			return;
		conditionals.add(makeConditionalDropdown());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridwidth = 1; 
		constraints.gridheight = 1;
		constraints.gridx = numConditionals % 5;
		constraints.gridy = numConditionals / 5; 
		conditionalLayout.add(conditionals.get(conditionals.size() - 1), constraints);	
		conditionalLayout.validate();
		numConditionals++;
	}

	private void addConditionalBox(String s){
		System.out.println(s);
		JComboBox toAdd = makeConditionalDropdown();
		toAdd.setSelectedItem(s);
		conditionals.add(toAdd);
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.gridx = numConditionals % 5;
		constraints.gridy = numConditionals / 5;
		conditionalLayout.add(conditionals.get(conditionals.size() - 1), constraints);
		numConditionals++;
	}

	private void addBehaviorBox(){
		if(selectedTrigger == null)
			return;
		behaviors.add(makeBehaviorDropdown());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridwidth = 1; 
		constraints.gridheight = 1;
		constraints.gridx = numBehaviors % 5;
		constraints.gridy = numBehaviors / 5;
		behaviorLayout.add(behaviors.get(behaviors.size() - 1), constraints);
		behaviorLayout.validate();
		numBehaviors++;
	}
	
	private void addBehaviorBox(String s){
		System.out.println(s);
		JComboBox toAdd = makeBehaviorDropdown();
		toAdd.setSelectedItem(s);
		behaviors.add(toAdd);
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.gridx = numBehaviors % 5;
		constraints.gridy = numBehaviors / 5;
		behaviorLayout.add(behaviors.get(behaviors.size() - 1), constraints);
		numBehaviors++;
	}

	private void addConditionalText(){
		if(selectedTrigger == null)
			return;
		JTextField toAdd = new JTextField();
		toAdd.setPreferredSize(new Dimension(120, 20));
		conditionals.add(toAdd);
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridwidth = 1; 
		constraints.gridheight = 1;
		constraints.gridx = numConditionals % 5;
		constraints.gridy = numConditionals / 5;
		conditionalLayout.add(conditionals.get(conditionals.size() - 1), constraints);
		conditionalLayout.validate();
		numConditionals++;
	}
	
	private void addConditionalText(String s){
		JTextField toAdd = new JTextField(s);
		toAdd.setPreferredSize(new Dimension(120, 20));
		conditionals.add(toAdd);
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridwidth = 1; 
		constraints.gridheight = 1;
		constraints.gridx = numConditionals % 5;
		constraints.gridy = numConditionals / 5;
		conditionalLayout.add(conditionals.get(conditionals.size() - 1), constraints);
		numConditionals++;
	}

	private void addBehaviorText(){
		if(selectedTrigger == null)
			return;
		JTextField toAdd = new JTextField();
		toAdd.setPreferredSize(new Dimension(120, 20));
		behaviors.add(toAdd);
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridwidth = 1; 
		constraints.gridheight = 1;
		constraints.gridx = numBehaviors % 5;
		constraints.gridy = numBehaviors / 5;
		behaviorLayout.add(behaviors.get(behaviors.size() - 1), constraints);
		behaviorLayout.validate();
		numBehaviors++;
	}
	
	private void addBehaviorText(String s){
		JTextField toAdd = new JTextField(s);
		toAdd.setPreferredSize(new Dimension(120, 20));
		behaviors.add(toAdd);
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridwidth = 1; 
		constraints.gridheight = 1;
		constraints.gridx = numBehaviors % 5;
		constraints.gridy = numBehaviors / 5;
		behaviorLayout.add(conditionals.get(conditionals.size() - 1), constraints);
		numBehaviors++;
	}

	private void deleteConditional(){
		try{
			conditionalLayout.remove(conditionals.size() - 1);
			conditionals.remove(conditionals.size() - 1);
			numConditionals--;
			conditionalLayout.validate();
			repaint();
		}catch(Exception e){
		}

	}

	private void deleteBehavior(){
		try{
			behaviorLayout.remove(behaviors.size() - 1);
			behaviors.remove(behaviors.size() - 1);
			numBehaviors--;
			behaviorLayout.validate();
			repaint();
		}catch(Exception e){
		}
	}

	private void populateConditionals(){
		StringTokenizer conditionalTokenizer = new StringTokenizer(builder.getConditionString(), " ");
		ImmutableList<String> conditionalValues = builder.conditionalValues();
		while(conditionalTokenizer.hasMoreTokens()){
			String token = conditionalTokenizer.nextToken();
			for(int i = 0; i < conditionalValues.size(); i++)
				if(token.equals(conditionalValues.get(i))){
					addConditionalBox(token);
					return;
				}
			addConditionalText(token);
		}
	}

	private void populateBehaviors(){
		StringTokenizer behaviorTokenizer = new StringTokenizer(builder.getBehaviorString(), " ");
		ImmutableList<String> behaviorValues = builder.behavioralValues();
		while(behaviorTokenizer.hasMoreTokens()){
			String token = behaviorTokenizer.nextToken();
			for(int i = 0; i < behaviorValues.size(); i++)
				if(token.equals(behaviorValues.get(i))){
					addConditionalBox(token);
					return;
				}
			addConditionalText(token);
		}
	}

	private class AddConditionalBoxListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ae) {
			addConditionalBox();
		}
	}

	private class AddConditionalTextListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ae) {
			addConditionalText();
		}
	}

	private class AddBehaviorBoxListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ae) {
			addBehaviorBox();
		}
	}

	private class AddBehaviorTextListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ae) {
			addBehaviorText();
		}
	}

	private class DeleteConditionalListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			deleteConditional();			
		}

	}

	private class DeleteBehaviorListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			deleteBehavior();
		}
	}

	public void load(Trigger.Builder b, Trigger t){
		reset();
		builder = b;
		selectedTrigger = t;
		nameField.setText(selectedTrigger.getName());
		prioritySpinner.setValue(selectedTrigger.getPriority());
		populateConditionals();
		populateBehaviors();
		try{
			if(builder.isValid())
				isValidText.setText("Valid");
			else
				isValidText.setText("Invalid");
		}
		catch(Exception e){
		}
		validate();
	}

	public void load(){
	}

	public Trigger sendInfo(){
		String expression = "";
		for(int i = 0; i < conditionals.size(); i++){
			JComponent current = conditionals.get(i);
			if(current instanceof JComboBox){
				expression += ((JComboBox) current).getSelectedItem().toString();
			}
			else{
				expression += ((JTextField) current).getText();
			}
			if(i < conditionals.size() - 1)
				expression += " ";
		}
		builder.addConditional(expression);
		expression = "";
		for(int i = 0; i < behaviors.size(); i++){
			JComponent current = behaviors.get(i);
			if(current instanceof JComboBox){
				expression += ((JComboBox) current).getSelectedItem().toString();
			}
			else{
				expression += ((JTextField) current).getText();
			}
			if(i < behaviors.size() - 1)
				expression += " ";
		}
		builder.addBehavioral(expression);
		builder.addName(nameField.getText());
		builder.addPriority((Integer) prioritySpinner.getValue());
		try{
			if(builder.isValid()){
				isValidText.setText("Valid");
				return builder.build();
			}
		}catch(Exception e){
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
		isValidText.setText("Invalid");
		return null;
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
		selectedTrigger = null;
		validate();
	}
}
