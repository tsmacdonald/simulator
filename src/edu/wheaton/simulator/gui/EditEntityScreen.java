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
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class EditEntityScreen extends Screen {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4021299442173260142L;
	
	private JTextField nameField;
	
	private JColorChooser colorTool;
	
	private ArrayList<JTextField> fieldNames; 
	
	private ArrayList<JTextField> fieldValues;
	
	private ArrayList<JComboBox> fieldTypes;
	
	private String[] typeNames =  {"Integer", "Double", "String"};
	
	private ArrayList<JButton> fieldDeleteButtons;
	
	private ArrayList<JTextField> triggerNames;
	
	private ArrayList<JTextField> triggerPriorities;
	
	private ArrayList<JTextField> triggerConditions;
	
	private ArrayList<JTextField> triggerResults;
	
	private ArrayList<JButton> triggerDeleteButtons;
	
	public EditEntityScreen(ScreenManager sm) {
		super(sm);
		this.setLayout(new BorderLayout());
		JLabel label = new JLabel("Edit Entities");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setHorizontalTextPosition(SwingConstants.CENTER);
		JTabbedPane tabs = new JTabbedPane();
		JPanel lowerPanel = new JPanel();
		JPanel generalPanel = new JPanel();
		JPanel fieldPanel = new JPanel();
		JPanel fieldPanel2 = new JPanel();
		JPanel fieldPanel3 = new JPanel();
		JPanel fieldSubPanel = new JPanel();
		JPanel triggerPanel = new JPanel();
		JPanel triggerPanel2 = new JPanel();
		JLabel generalLabel = new JLabel("General Info");
		generalLabel.setHorizontalAlignment(SwingConstants.CENTER);
		generalLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		JLabel fieldLabel = new JLabel("Field Info");
		fieldLabel.setHorizontalAlignment(SwingConstants.CENTER);
		fieldLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		JLabel triggerLabel = new JLabel("Trigger Info");
		triggerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		triggerLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		JLabel nameLabel = new JLabel("Name: ");
		nameField = new JTextField(25);
		//set max size of name field
		nameField.setMaximumSize(new Dimension(400, 50));
		colorTool = new JColorChooser();
		JButton loadIconButton = new JButton("Load icon");
		JLabel fieldNameLabel = new JLabel("Field Name");
		//fieldNameLabel.setMinimumSize(new Dimension(500, 40));
		JLabel fieldValueLabel = new JLabel("Field Initial Value");
		//fieldValueLabel.setMinimumSize(new Dimension(500, 40));
		JLabel fieldTypeLabel = new JLabel("Field Type");
		//fieldTypeLabel.setMinimumSize(new Dimension(500, 40));
		fieldNames = new ArrayList<JTextField>();
		fieldNames.add(new JTextField(25));
		//set max size of field name text box
		fieldNames.get(0).setMaximumSize(new Dimension(300, 50));
		fieldValues = new ArrayList<JTextField>();
		fieldValues.add(new JTextField(25));
		fieldValues.get(0).setMaximumSize(new Dimension(300, 50));
		fieldTypes = new ArrayList<JComboBox>();
		fieldTypes.add(new JComboBox(typeNames));
		fieldDeleteButtons = new ArrayList<JButton>();
		fieldDeleteButtons.add(new JButton("Delete"));
		JButton addFieldButton = new JButton("Add Field");
		JLabel triggerNameLabel = new JLabel("Trigger Name");
		JLabel triggerPriorityLabel = new JLabel("Trigger Priority");
		JLabel triggerConditionLabel = new JLabel("Trigger Condition");
		JLabel triggerResultLabel = new JLabel("Trigger Result");
		triggerNames = new ArrayList<JTextField>();
		triggerNames.add(new JTextField());
		triggerPriorities = new ArrayList<JTextField>();
		triggerPriorities.add(new JTextField());
		//conditions and results: this will probably change
		triggerConditions = new ArrayList<JTextField>();
		triggerConditions.add(new JTextField());
		triggerResults = new ArrayList<JTextField>();
		triggerResults.add(new JTextField());
		triggerDeleteButtons = new ArrayList<JButton>();
		triggerDeleteButtons.add(new JButton("Delete"));
		JButton addTriggerButton = new JButton("Add Trigger");
		JButton cancelButton = new JButton("Cancel");
		JButton finishButton = new JButton("Finish");
		
		lowerPanel.add(cancelButton);
		lowerPanel.add(finishButton);
		
		generalPanel.setLayout(
					new BoxLayout(generalPanel, BoxLayout.PAGE_AXIS)
					);
		generalPanel.add(generalLabel);
		generalPanel.add(nameLabel);
		generalPanel.add(nameField);
		//add the icon construction tool to the general panel here
		generalPanel.add(colorTool);
		generalPanel.add(loadIconButton);
		
		//TODO mess with sizes of components
		fieldPanel.setLayout(
				new BoxLayout(fieldPanel, BoxLayout.PAGE_AXIS)
				);
		fieldPanel2.setLayout(
				new BoxLayout(fieldPanel2, BoxLayout.LINE_AXIS)
				);
		fieldPanel3.setLayout(
				new BoxLayout(fieldPanel3, BoxLayout.PAGE_AXIS)
				);
		fieldSubPanel.setLayout(
				new BoxLayout(fieldSubPanel, BoxLayout.LINE_AXIS)
				);
		fieldPanel.add(fieldLabel);
		fieldPanel2.add(fieldNameLabel);
		fieldPanel2.add(fieldTypeLabel);
		fieldPanel2.add(fieldValueLabel);
		fieldPanel2.add(new JLabel()); //fill empty slot
		fieldSubPanel.add(fieldNames.get(0));
		fieldSubPanel.add(fieldTypes.get(0));
		fieldSubPanel.add(fieldValues.get(0));
		fieldSubPanel.add(fieldDeleteButtons.get(0));
		fieldPanel3.add(fieldSubPanel);
		fieldPanel.add(fieldPanel2);
		fieldPanel.add(fieldPanel3);
		fieldPanel.add(addFieldButton);
		
		triggerPanel.setLayout(
				new BoxLayout(triggerPanel, BoxLayout.PAGE_AXIS)
				);
		triggerPanel2.setLayout(new GridLayout(15, 5));
		triggerPanel.add(triggerLabel);
		triggerPanel2.add(triggerNameLabel);
		triggerPanel2.add(triggerPriorityLabel);
		triggerPanel2.add(triggerConditionLabel);
		triggerPanel2.add(triggerResultLabel);
		triggerPanel.add(triggerPanel2);
		triggerPanel.add(addTriggerButton);
		
		tabs.addTab("General", generalPanel);
		tabs.addTab("Fields", fieldPanel);
		tabs.addTab("Triggers",  triggerPanel);
		
		this.add(label, BorderLayout.NORTH);
		this.add(tabs, BorderLayout.CENTER);
		this.add(lowerPanel, BorderLayout.SOUTH);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendInfo() {
		// TODO Auto-generated method stub
		
	}

}
