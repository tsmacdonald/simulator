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

	/**
	 * The trigger selected in the TriggerScreen
	 */
	private Trigger selectedTrigger;
	
	/**
	 * Size of the component cells
	 */
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
		validate();
		repaint();
	}

	/**
	 * Adds a label indicating where the name of the trigger is
	 * @param constraints
	 */
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

	/**
	 * Adds a label indicating that the spinner shows what the trigger priority is
	 * @param constraints
	 */
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

	/**
	 * Adds the text field where the name of the trigger is read
	 * @param constraints
	 */
	private void addNameField(GridBagConstraints constraints){
		nameField = new JTextField();
		nameField.setToolTipText("The name of the trigger");
		constraints.gridheight = 1; 
		constraints.gridwidth = 2; 
		constraints.gridx = 0; 
		constraints.gridy = 1; 
		constraints.ipadx = 150;
		constraints.anchor = GridBagConstraints.BASELINE_LEADING;
		this.add(nameField, constraints);
	}

	/**
	 * Adds a spinner that indicates the priority of trigger
	 * @param constraints
	 */
	private void addSpinner(GridBagConstraints constraints) {
		prioritySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1)); 
		prioritySpinner.setToolTipText("The priority level of the trigger");
		constraints.gridwidth = 1; 
		constraints.gridheight = 1;
		constraints.gridx = 2;
		constraints.gridy = 1;
		constraints.anchor = GridBagConstraints.BASELINE_LEADING;
		constraints.insets = new Insets(0, 50, 0, 0);
		add(prioritySpinner, constraints);
	}

	/**
	 * Indicates what the conditionals layout is
	 * @param constraints
	 */
	private void addIf(GridBagConstraints constraints) {
		JLabel ifLabel = new JLabel("If:"); 
		constraints.gridwidth = 1; 
		constraints.gridheight = 1;
		constraints.gridx = 0;
		constraints.gridy = 2; 
		constraints.ipadx = COMPONENT_SIZE.width;
		constraints.ipady = COMPONENT_SIZE.height;
		constraints.anchor = GridBagConstraints.BASELINE_LEADING;
		constraints.fill = GridBagConstraints.NONE;  
		add(ifLabel, constraints);
	}

	/**
	 * Adds the conditionals layout that displays the conditional expression
	 * @param constraints
	 */
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

	/**
	 * Adds a label that indicates what the behaviorLayout does
	 * @param constraints
	 */
	private void addThen(GridBagConstraints constraints) {
		JLabel thenLabel = new JLabel("Then:"); 
		constraints.gridwidth = 1; 
		constraints.gridheight = 1;
		constraints.gridx = 0;
		constraints.gridy = 6; 
		constraints.ipadx = COMPONENT_SIZE.width;
		constraints.ipady = COMPONENT_SIZE.height;
		constraints.anchor = GridBagConstraints.BASELINE_LEADING;
		constraints.fill = GridBagConstraints.NONE;
		add(thenLabel, constraints);
	}

	/**
	 * Adds the layout that displays the behavior expression
	 * @param constraints
	 */
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

	/**
	 * Adds the buttons that allow the user to interact with the conditional expression
	 * @param constraints
	 */
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

	/**
	 * Adds the buttons that allow the user to interact with the behavior expression
	 * @param constraints
	 */
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

	/**
	 * Makes a JComboBox with the conditional values in it
	 * @return the JComboBox
	 */
	private JComboBox makeConditionalDropdown(){
		JComboBox toReturn = new JComboBox(builder.conditionalValues().toArray());
		toReturn.setPreferredSize(COMPONENT_SIZE);
		return toReturn;
	}

	/**
	 * Makes a JComboBox with the behavior values in it
	 * @return the JComboBox
	 */
	private JComboBox makeBehaviorDropdown(){
		JComboBox toReturn = new JComboBox(builder.behavioralValues().toArray());
		toReturn.setPreferredSize(COMPONENT_SIZE);
		return toReturn;
	}	

	/**
	 * Adds a conditional JComboBox to the layout with no value selected
	 */
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

	/**
	 * Adds a JComboBox to the layout with a value selected
	 * @param s, the value to be selected
	 */
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

	/**
	 * Adds a behavior JComboBox to the layout with no value selected
	 */
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

	/**
	 * Adds a behavior JComboBox to the layout with a value selected
	 * @param s, the value to be selected
	 */
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

	/**
	 * Adds a JTextField to the conditional layout with the empty string
	 */
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

	/**
	 * Adds a JTextField to the conditional layout with the parameterized string as its content
	 * @param s, the string to be displayed in the text field
	 */
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

	/**
	 * Adds a JTextField to the behavior layout with the empty string
	 */
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

	/**
	 * Adds a JTextField to the behavior layout with the parameterized string as its content
	 * @param s, the string to be displayed in the text field
	 */
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

	/**
	 * Deletes a conditional component
	 */
	private void deleteConditional(){
		try{
			conditionalLayout.remove(conditionals.get(conditionals.size() - 1));
			conditionals.remove(conditionals.size() - 1);
			conditionalLayout.validate();
			conditionalLayout.repaint();
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "There is no conditional component to delete");
		}
	}

	/**
	 * Deletes a behavior component
	 */
	private void deleteBehavior(){
		try{
			behaviorLayout.remove(behaviors.get(behaviors.size() - 1));
			behaviors.remove(behaviors.size() - 1);
			behaviorLayout.validate();
			behaviorLayout.repaint();
		}catch(Exception e){
			JOptionPane.showMessageDialog(null,  "There is no behavior component to delete");
		}
	}

	/**
	 * Populates the conditional layout with JComponents from the conditional list
	 */
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

	/**
	 * Populates the behavior layout with JComponenets from the behavior list
	 */
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

	/**
	 * Adds a conditional JComboBox when its add button is clicked 
	 */
	private class AddConditionalBoxListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ae) {
			addConditionalBox();
		}
	}

	/**
	 * Adds a conditional text field when the add text button is clicked
	 */
	private class AddConditionalTextListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ae) {
			addConditionalText();
		}
	}

	/**
	 * Adds a behavior JComboBox when its add button is clicked
	 */
	private class AddBehaviorBoxListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ae) {
			addBehaviorBox();
		}
	}

	/**
	 * Adds a behavior text field when the add text button is clicked
	 */
	private class AddBehaviorTextListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ae) {
			addBehaviorText();
		}
	}

	/**
	 * Deletes the last conditional expression component
	 */
	private class DeleteConditionalListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			deleteConditional();			
		}

	}

	/**
	 * Deletes the last behavior expression component
	 */
	private class DeleteBehaviorListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			deleteBehavior();
		}
	}

	/**
	 * Loads the trigger information to be displayed
	 * @param b, the trigger builder
	 * @param t, the selected trigger
	 */
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

	/**
	 * Passes the trigger with the modified values
	 * @return The modified trigger
	 */
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

	/**
	 * Clears all values from this screen object
	 */
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
