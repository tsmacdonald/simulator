/**
 * EditEntityScreen
 * 
 * Class representing the screen that allows users to edit properties of
 * grid entities, including triggers and appearance.
 * 
 * @author Willy McHie
 * Wheaton College, CSCI 335, Spring 2013
 */

package edu.wheaton.simulator.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

public class EditEntityScreen extends Screen {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4021299442173260142L;
	
	//TODO make sure this is set to true if we're editing an existing agent, 
	//     and change the finish behavior based on what this is.
	private Boolean editing;

	private JTextField nameField;

	private JColorChooser colorTool;

	private ArrayList<JTextField> fieldNames; 

	private ArrayList<JTextField> fieldValues;

	private ArrayList<JComboBox> fieldTypes;

	private String[] typeNames =  {"Integer", "Double", "String", "Boolean"};

	private ArrayList<JButton> fieldDeleteButtons;

	private ArrayList<JPanel> fieldSubPanels;

	private Component glue;

	private Component glue2;

	private JButton addFieldButton;

	private JPanel fieldListPanel;

	private ArrayList<JTextField> triggerNames;

	private ArrayList<JTextField> triggerPriorities;

	private ArrayList<JTextField> triggerConditions;

	private ArrayList<JTextField> triggerResults;

	private ArrayList<JButton> triggerDeleteButtons;

	private ArrayList<JPanel> triggerSubPanels;
	
	private JButton addTriggerButton;
	
	private JPanel triggerListPanel;

	//TODO clean/organize this whole damn thing- Willy
	public EditEntityScreen(final ScreenManager sm) {
		super(sm);
		this.setLayout(new BorderLayout());
		JLabel label = new JLabel("Edit Entities");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setHorizontalTextPosition(SwingConstants.CENTER);
		JTabbedPane tabs = new JTabbedPane();
		JPanel lowerPanel = new JPanel();
		JPanel generalPanel = new JPanel();
		JPanel fieldMainPanel = new JPanel();
		JPanel fieldLabelsPanel = new JPanel();
		fieldListPanel = new JPanel();
		JPanel triggerMainPanel = new JPanel();
		triggerListPanel = new JPanel();
		JPanel triggerLabelsPanel = new JPanel();
		JLabel generalLabel = new JLabel("General Info");
		generalLabel.setHorizontalAlignment(SwingConstants.CENTER);
		generalLabel.setPreferredSize(new Dimension(300, 80));
		JLabel fieldLabel = new JLabel("Field Info");
		fieldLabel.setHorizontalAlignment(SwingConstants.CENTER);
		fieldLabel.setPreferredSize(new Dimension(300, 100));
		JLabel triggerLabel = new JLabel("Trigger Info");
		triggerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		triggerLabel.setPreferredSize(new Dimension(300, 100));
		JLabel nameLabel = new JLabel("Name: ");
		nameField = new JTextField(25);
		nameField.setMaximumSize(new Dimension(400, 40));
		colorTool = new JColorChooser();
		JButton loadIconButton = new JButton("Load icon");

		JLabel fieldNameLabel = new JLabel("Field Name");
		fieldNameLabel.setPreferredSize(new Dimension(200, 30));
		JLabel fieldValueLabel = new JLabel("Field Initial Value");
		fieldValueLabel.setPreferredSize(new Dimension(400, 30));
		JLabel fieldTypeLabel = new JLabel("Field Type");
		fieldNameLabel.setPreferredSize(new Dimension(350, 30));

		fieldNames = new ArrayList<JTextField>();
		fieldNames.add(new JTextField(25));
		fieldNames.get(0).setMaximumSize(new Dimension(300, 40));
		fieldValues = new ArrayList<JTextField>();
		fieldValues.add(new JTextField(25));
		fieldValues.get(0).setMaximumSize(new Dimension(300, 40));
		fieldTypes = new ArrayList<JComboBox>();
		fieldTypes.add(new JComboBox(typeNames));
		fieldTypes.get(0).setMaximumSize(new Dimension(200, 40));
		fieldDeleteButtons = new ArrayList<JButton>();
		fieldDeleteButtons.add(new JButton("Delete"));
		fieldDeleteButtons.get(0).setActionCommand("Delete Field 0");
		fieldDeleteButtons.get(0).addActionListener(this);
		fieldSubPanels = new ArrayList<JPanel>();
		fieldSubPanels.add(new JPanel());
		addFieldButton = new JButton("Add Field");
		//TODO should this be merged with this.actionPerformed?
		addFieldButton.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						addField();
					}
				}
				);
		JLabel triggerNameLabel = new JLabel("Trigger Name");
		triggerNameLabel.setPreferredSize(new Dimension(130, 30));
		JLabel triggerPriorityLabel = new JLabel("Trigger Priority");
		triggerPriorityLabel.setPreferredSize(new Dimension(180, 30));
		JLabel triggerConditionLabel = new JLabel("Trigger Condition");
		triggerConditionLabel.setPreferredSize(new Dimension(300, 30));
		JLabel triggerResultLabel = new JLabel("Trigger Result");
		triggerResultLabel.setPreferredSize(new Dimension(300, 30));
		
		triggerNames = new ArrayList<JTextField>();
		triggerNames.add(new JTextField(25));
		triggerNames.get(0).setMaximumSize(new Dimension(200, 40));
		triggerPriorities = new ArrayList<JTextField>();
		triggerPriorities.add(new JTextField(15));
		triggerPriorities.get(0).setMaximumSize(new Dimension(150, 40));
		//conditions and results: objects may change based on how those are finished
		triggerConditions = new ArrayList<JTextField>();
		triggerConditions.add(new JTextField(50));
		triggerConditions.get(0).setMaximumSize(new Dimension (300, 40));
		triggerResults = new ArrayList<JTextField>();
		triggerResults.add(new JTextField(50));
		triggerResults.get(0).setMaximumSize(new Dimension (300, 40));
		triggerDeleteButtons = new ArrayList<JButton>();
		triggerDeleteButtons.add(new JButton("Delete"));
		triggerDeleteButtons.get(0).setActionCommand("Delete Trigger 0");
		triggerDeleteButtons.get(0).addActionListener(this);
		triggerSubPanels = new ArrayList<JPanel>();
		triggerSubPanels.add(new JPanel());
		addTriggerButton = new JButton("Add Trigger");
		addTriggerButton.addActionListener(this);

	
		JButton cancelButton = new JButton("Cancel");
		//TODO should this be merged with this.actionPerformed?
		cancelButton.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						sm.update(sm.getScreen("Edit Simulation")); 
					} 
				}
				);
		JButton finishButton = new JButton("Finish");
		//TODO should this be merged with this.actionPerformed?
		//TODO finishbutton needs to pull information from the screen
		//     and update the simulation data accordingly
		//     INCLUDING erasing anything that was erased
		finishButton.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						sm.update(sm.getScreen("Edit Simulation")); 
					} 
				}
				);

		lowerPanel.add(cancelButton);
		lowerPanel.add(finishButton);

		generalPanel.setLayout(
				new BoxLayout(generalPanel, BoxLayout.PAGE_AXIS)
				);
		generalPanel.add(generalLabel);
		generalPanel.add(nameLabel);
		generalPanel.add(nameField);
		//TODO add the icon construction tool to the general panel here
		generalPanel.add(colorTool);
		generalPanel.add(loadIconButton);

		//TODO make sure components line up
		fieldMainPanel.setLayout(
				new BorderLayout());
		JPanel fieldBodyPanel = new JPanel();
		fieldBodyPanel.setLayout(
				new BoxLayout(fieldBodyPanel, BoxLayout.Y_AXIS)
				);
		fieldLabelsPanel.setLayout(
				new BoxLayout(fieldLabelsPanel, BoxLayout.X_AXIS)
				);
		fieldListPanel.setLayout(
				new BoxLayout(fieldListPanel, BoxLayout.Y_AXIS)
				);
		fieldSubPanels.get(0).setLayout(
				new BoxLayout(fieldSubPanels.get(0), BoxLayout.X_AXIS)
				);
		fieldMainPanel.add(fieldLabel, BorderLayout.NORTH);
		fieldLabel.setAlignmentX(CENTER_ALIGNMENT);
		fieldLabelsPanel.add(Box.createHorizontalGlue());
		fieldLabelsPanel.add(fieldNameLabel);
		fieldNameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		fieldNameLabel.setAlignmentX(LEFT_ALIGNMENT);
		fieldLabelsPanel.add(fieldTypeLabel);
		fieldTypeLabel.setHorizontalAlignment(SwingConstants.LEFT);
		fieldLabelsPanel.add(fieldValueLabel);
		fieldLabelsPanel.add(Box.createHorizontalGlue());
		fieldValueLabel.setHorizontalAlignment(SwingConstants.CENTER);
		fieldSubPanels.get(0).add(fieldNames.get(0));
		fieldSubPanels.get(0).add(fieldTypes.get(0));
		fieldSubPanels.get(0).add(fieldValues.get(0));
		fieldSubPanels.get(0).add(fieldDeleteButtons.get(0));
		fieldListPanel.add(fieldSubPanels.get(0));
		fieldListPanel.add(addFieldButton);
		glue = Box.createVerticalGlue();
		fieldListPanel.add(glue);
		fieldSubPanels.get(0).setAlignmentY(TOP_ALIGNMENT);
		fieldBodyPanel.add(fieldLabelsPanel);
		fieldLabelsPanel.setAlignmentX(CENTER_ALIGNMENT);
		fieldBodyPanel.add(fieldListPanel);
		fieldMainPanel.add(fieldBodyPanel, BorderLayout.CENTER);

		triggerMainPanel.setLayout(new BorderLayout());
		triggerListPanel.setLayout(
				new BoxLayout(triggerListPanel, BoxLayout.Y_AXIS)
				);
		triggerListPanel.setAlignmentX(CENTER_ALIGNMENT);
		triggerLabelsPanel.setLayout(
				new BoxLayout(triggerLabelsPanel, BoxLayout.X_AXIS)
				);
		triggerSubPanels.get(0).setLayout(
				new BoxLayout(triggerSubPanels.get(0), BoxLayout.X_AXIS)
				);
		triggerMainPanel.add(triggerLabel, BorderLayout.NORTH);
		triggerLabelsPanel.add(Box.createHorizontalGlue());
		triggerLabelsPanel.add(triggerNameLabel);
		triggerNameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		triggerLabelsPanel.add(triggerPriorityLabel);
		triggerLabelsPanel.add(triggerConditionLabel);
		triggerLabelsPanel.add(triggerResultLabel);
		triggerLabelsPanel.add(Box.createHorizontalGlue());
		triggerListPanel.add(triggerLabelsPanel);
		triggerSubPanels.get(0).add(triggerNames.get(0));
		triggerSubPanels.get(0).add(triggerPriorities.get(0));
		triggerSubPanels.get(0).add(triggerConditions.get(0));
		triggerSubPanels.get(0).add(triggerResults.get(0));
		triggerSubPanels.get(0).add(triggerDeleteButtons.get(0));
		triggerListPanel.add(triggerSubPanels.get(0));
		triggerSubPanels.get(0).setAlignmentX(CENTER_ALIGNMENT);
		triggerSubPanels.get(0).setAlignmentY(TOP_ALIGNMENT);
		triggerListPanel.add(addTriggerButton);
		glue2 = Box.createVerticalGlue();
		triggerListPanel.add(glue2);
		triggerMainPanel.add(triggerListPanel, BorderLayout.CENTER);

		tabs.addTab("General", generalPanel);
		tabs.addTab("Fields", fieldMainPanel);
		tabs.addTab("Triggers", triggerMainPanel);

		this.add(label, BorderLayout.NORTH);
		this.add(tabs, BorderLayout.CENTER);
		this.add(lowerPanel, BorderLayout.SOUTH);

	}



	@Override
	//TODO should other action listeners be merged into this? 
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		Screen update = this;
		if (action.equals("Add Field")) {
			addField();
		}
		else if (action.equals("Add Trigger")) {
			addTrigger();
		}
		else if (action.substring(0, 14).equals("Delete Trigger")) {
			deleteTrigger(Integer.parseInt(action.substring(15)));
		}
		else if (action.substring(0, 12).equals("Delete Field")) {
			deleteField(Integer.parseInt(action.substring(13)));
		}
	}

	//TODO need a public void load(GridEntity g) { }
	//     which will set the fields to display the values of that entity,
	//     and will be called when the page is to be displayed
	
	//TODO probably need a reset() method as well, to clear the screen to being empty
	
	@Override
	public void sendInfo() {
		// TODO Auto-generated method stub

	}

	private void addField() {
		JPanel newPanel = new JPanel();
		newPanel.setLayout(
				new BoxLayout(newPanel, 
						BoxLayout.X_AXIS)
				);
		JTextField newName = new JTextField(25);
		newName.setMaximumSize(new Dimension(300, 40));
		fieldNames.add(newName);
		JComboBox newType = new JComboBox(typeNames);
		newType.setMaximumSize(new Dimension(200, 40));
		fieldTypes.add(newType);
		JTextField newValue = new JTextField(25);
		newValue.setMaximumSize(new Dimension(300, 40));
		JButton newButton = new JButton("Delete");
		newButton.addActionListener(this);
		fieldDeleteButtons.add(newButton);
		newButton.setActionCommand("Delete Field " + 
									fieldDeleteButtons.indexOf(newButton));
		newPanel.add(newName);
		newPanel.add(newType);
		newPanel.add(newValue);
		newPanel.add(newButton);
		fieldSubPanels.add(newPanel);
		fieldListPanel.add(newPanel);
		fieldListPanel.add(addFieldButton);
		fieldListPanel.add(glue);
		repaint();	
	}

	private void addTrigger(){
		JPanel newPanel = new JPanel();
		newPanel.setLayout(
				new BoxLayout(newPanel, 
						BoxLayout.X_AXIS)
				);
		JTextField newName = new JTextField(25);
		newName.setMaximumSize(new Dimension(200, 40));
		triggerNames.add(newName);
		JTextField newPriority = new JTextField(15);
		newPriority.setMaximumSize(new Dimension(150, 40));
		triggerPriorities.add(newPriority);
		JTextField newCondition = new JTextField(50);
		newCondition.setMaximumSize(new Dimension(300, 40));
		triggerConditions.add(newCondition);
		JTextField newResult = new JTextField(50);
		newResult.setMaximumSize(new Dimension(300, 40));
		triggerResults.add(newResult);
		JButton newButton = new JButton("Delete");
		newButton.addActionListener(this);
		triggerDeleteButtons.add(newButton);
		newButton.setActionCommand("Delete Trigger " + 
				triggerDeleteButtons.indexOf(newButton));
		newPanel.add(newName);
		newPanel.add(newPriority);
		newPanel.add(newCondition);
		newPanel.add(newResult);
		newPanel.add(newButton);
		triggerSubPanels.add(newPanel);
		triggerListPanel.add(newPanel);
		triggerListPanel.add(addTriggerButton);
		triggerListPanel.add(glue2);
		repaint();
	}
	
	//TODO I think I have a better way of setting up these methods up 
	//     to make it easier to delete things from the agents.
	private void deleteField(int n) {
		fieldNames.remove(n);
		fieldTypes.remove(n);
		fieldDeleteButtons.remove(n);
		for (int i = n; i < fieldDeleteButtons.size(); i++) {
			fieldDeleteButtons.get(i).setActionCommand("Delete Field " + i);
		}
		fieldListPanel.remove(fieldSubPanels.get(n));
		fieldSubPanels.remove(n);
		repaint();
	}
	
	private void deleteTrigger(int n) {
		triggerNames.remove(n);
		triggerPriorities.remove(n);
		triggerConditions.remove(n);
		triggerResults.remove(n);
		triggerDeleteButtons.remove(n);
		for (int i = n; i < triggerDeleteButtons.size(); i++) {
			triggerDeleteButtons.get(i).setActionCommand(
					"Delete Trigger " + i
					);
		}
		triggerListPanel.remove(triggerSubPanels.get(n));
		triggerSubPanels.remove(n);
		repaint();
	}
}
