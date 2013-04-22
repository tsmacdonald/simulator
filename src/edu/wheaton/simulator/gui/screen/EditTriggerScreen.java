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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import com.google.common.collect.ImmutableList;

import edu.wheaton.simulator.entity.Trigger;
import edu.wheaton.simulator.gui.FileMenu;
import edu.wheaton.simulator.gui.Gui;
import edu.wheaton.simulator.gui.SimulatorFacade;

/**
 * 
 * @author daniel.davenport daniel.gill
 *
 */
public class EditTriggerScreen extends Screen {

	private static final long serialVersionUID = 3261558461232576081L;

	/**
	 * Button that adds JComboBox to conditionalLayout
	 */
	private JButton addConditionalBox;

	/**
	 * Button that adds JTextField to conditionalLayout
	 */
	private JButton addConditionalText;

	/**
	 * Button that adds JComboBox to behaviorLayout
	 */
	private JButton addBehaviorBox;

	/**
	 * Button that adds JTextField to behaviorLayout
	 */
	private JButton addBehaviorText;

	/**
	 * Contains the name of the trigger
	 */
	private JTextField nameField;

	/**
	 * Contains the priority level of the trigger
	 */
	private JSpinner prioritySpinner;

	/**
	 * Holds a list of all parts of the conditional expression
	 */
	private ArrayList<JComponent> conditionals;

	/**
	 * Holds a list of all parts of the behavior expression
	 */
	private ArrayList<JComponent> behaviors;

	/**
	 * Adds scrolling capability to the conditionalLayout
	 */
	private JScrollPane conditionalScrollLayout;

	/**
	 * Holds the graphical representation of the conditional expression
	 */
	private JPanel conditionalLayout;

	/**
	 * Adds scrolling capability to the behaviorLayout
	 */
	private JScrollPane behaviorScrollLayout;

	/**
	 * Holds the graphical representation of the behavior expression
	 */
	private JPanel behaviorLayout;

	/**
	 * Object that builds a trigger from the user-generated expression
	 */
	private Trigger.Builder builder;

	/**
	 * Button that deletes components from the conditional expression
	 */
	private JButton deleteConditionalButton;
	
	/**
	 * Button that deletes components from the behavior expression
	 */
	private JButton deleteBehaviorButton;

	private Trigger selectedTrigger;
	
	private final Dimension COMPONENT_SIZE = new Dimension(140, 30);

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
		conditionalScrollLayout.setViewportView(conditionalLayout);
		conditionalLayout.setLayout(new GridBagLayout());
		constraints.gridwidth = 3; 
		constraints.gridheight = 3;
		constraints.gridx = 0;
		constraints.gridy = 3; 
		constraints.ipadx = COMPONENT_SIZE.width * 5;
		constraints.ipady = COMPONENT_SIZE.height * 5;
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
		constraints.ipadx = COMPONENT_SIZE.width * 5;
		constraints.ipady = COMPONENT_SIZE.height * 5;
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
		toReturn.setPreferredSize(COMPONENT_SIZE);
		return toReturn;
	}

	private JComboBox makeBehaviorDropdown(){
		JComboBox toReturn = new JComboBox(builder.behavioralValues().toArray());
		toReturn.setPreferredSize(COMPONENT_SIZE);
		return toReturn;
	}	

	private void addConditionalBox(){
		if(selectedTrigger == null)
			return;
		JComboBox toAdd = makeConditionalDropdown();
		conditionals.add(toAdd);
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridwidth = 1; 
		constraints.gridheight = 1;
		constraints.gridx = (conditionals.size() - 1) % 5;
		constraints.gridy = (conditionals.size() - 1) / 5; 
		conditionalLayout.add(toAdd, constraints);	
		conditionalLayout.validate();
	}

	private void addConditionalBox(String s){
		JComboBox toAdd = makeConditionalDropdown();
		toAdd.setSelectedItem(s);
		conditionals.add(toAdd);
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.gridx = (conditionals.size() - 1) % 5;
		constraints.gridy = (conditionals.size() - 1) / 5;
		conditionalLayout.add(toAdd, constraints);
	}

	private void addBehaviorBox(){
		if(selectedTrigger == null)
			return;
		JComboBox toAdd = makeBehaviorDropdown();
		behaviors.add(toAdd);
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridwidth = 1; 
		constraints.gridheight = 1;
		constraints.gridx = (behaviors.size() - 1) % 5;
		constraints.gridy = (behaviors.size() - 1) / 5;
		behaviorLayout.add(toAdd, constraints);
		behaviorLayout.validate();
	}

	private void addBehaviorBox(String s){
		JComboBox toAdd = makeBehaviorDropdown();
		toAdd.setSelectedItem(s);
		behaviors.add(toAdd);
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.gridx = (behaviors.size() - 1) % 5;
		constraints.gridy = (behaviors.size() - 1) / 5;
		behaviorLayout.add(toAdd, constraints);
	}

	private void addConditionalText(){
		if(selectedTrigger == null)
			return;
		JTextField toAdd = new JTextField();
		toAdd.setPreferredSize(COMPONENT_SIZE);
		conditionals.add(toAdd);
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridwidth = 1; 
		constraints.gridheight = 1;
		constraints.gridx = (conditionals.size() - 1) % 5;
		constraints.gridy = (conditionals.size() - 1) / 5;
		conditionalLayout.add(toAdd, constraints);
		conditionalLayout.validate();
	}

	private void addConditionalText(String s){
		JTextField toAdd = new JTextField(s);
		toAdd.setPreferredSize(COMPONENT_SIZE);
		conditionals.add(toAdd);
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridwidth = 1; 
		constraints.gridheight = 1;
		constraints.gridx = (conditionals.size() - 1) % 5;
		constraints.gridy = (conditionals.size() - 1) / 5;
		conditionalLayout.add(toAdd, constraints);
	}

	private void addBehaviorText(){
		if(selectedTrigger == null)
			return;
		JTextField toAdd = new JTextField();
		toAdd.setPreferredSize(COMPONENT_SIZE);
		behaviors.add(toAdd);
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridwidth = 1; 
		constraints.gridheight = 1;
		constraints.gridx = (behaviors.size() - 1) % 5;
		constraints.gridy = (behaviors.size() - 1) / 5;
		behaviorLayout.add(toAdd, constraints);
		behaviorLayout.validate();
	}

	private void addBehaviorText(String s){
		JTextField toAdd = new JTextField(s);
		toAdd.setPreferredSize(COMPONENT_SIZE);
		behaviors.add(toAdd);
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridwidth = 1; 
		constraints.gridheight = 1;
		constraints.gridx = (behaviors.size() - 1) % 5;
		constraints.gridy = (behaviors.size() - 1)/ 5;
		behaviorLayout.add(toAdd, constraints);
	}

	private void deleteConditional(){
		try{
			conditionalLayout.remove(conditionals.get(conditionals.size() - 1));
			conditionals.remove(conditionals.size() - 1);
			conditionalLayout.validate();
			conditionalLayout.repaint();
		}catch(Exception e){
		}
	}

	private void deleteBehavior(){
		try{
			behaviorLayout.remove(behaviors.get(behaviors.size() - 1));
			behaviors.remove(behaviors.size() - 1);
			behaviorLayout.validate();
			behaviorLayout.repaint();
		}catch(Exception e){
		}
	}

	private void populateConditionals(){
		ImmutableList<String> conditionalValues = builder.conditionalValues();
		String[] conditionals = builder.getConditionString().split(" ");
		for(String s: conditionals){
			boolean isInList = false;
			if(!s.equals("")){
				for(int i = 0; i < conditionalValues.size(); i++){
					if(s.equals(conditionalValues.get(i))){
						addConditionalBox(s);
						isInList = true;
						break;
					}
				}
				if(!isInList)
					addConditionalText(s);
			}
		}
	}

	private void populateBehaviors(){
		ImmutableList<String> behaviorValues = builder.behavioralValues();
		String[] behaviors = builder.getBehaviorString().split(" ");
		for(String s: behaviors){
			boolean isInList = false;
			if(!s.equals("")){
				for(int i = 0; i < behaviorValues.size(); i++){
					if(s.equals(behaviorValues.get(i))){
						addBehaviorBox(s);
						isInList = true;
						break;
					}
				}
				if(!isInList)
					addBehaviorText(s);
			}
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
		validate();
	}

	@Override
	public void load(){
		FileMenu fm = Gui.getFileMenu();
		fm.setSaveSim(false);
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
			if(builder.isValid())
				return builder.build();
		}catch(Exception e){
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
		return null;
	}

	public void reset() {
		nameField.setText("");
		prioritySpinner.setValue(0);
		conditionals = new ArrayList<JComponent>();
		behaviors = new ArrayList<JComponent>();
		conditionalLayout.removeAll();
		behaviorLayout.removeAll();
		builder = null;
		selectedTrigger = null;
		validate();
	}
}
