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

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

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
		JTabbedPane tabs = new JTabbedPane();
		JPanel lowerPanel = new JPanel();
		JPanel generalPanel = new JPanel();
		JPanel fieldPanel = new JPanel();
		JPanel fieldPanel2 = new JPanel();
		fieldPanel2.setLayout(new GridLayout(2, 4));
		JPanel triggerPanel = new JPanel();
		JPanel triggerPanel2 = new JPanel();
		triggerPanel2.setLayout(new GridLayout(2, 5));
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
		nameField = new JTextField();
		colorTool = new JColorChooser();
		JButton loadIconButton = new JButton("Load icon");
		JLabel fieldNameLabel = new JLabel("Field Name");
		JLabel fieldValueLabel = new JLabel("Field Initial Value");
		JLabel fieldTypeLabel = new JLabel("Field Type");
		fieldNames = new ArrayList<JTextField>();
		fieldNames.add(new JTextField());
		fieldValues = new ArrayList<JTextField>();
		fieldValues.add(new JTextField());
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
		generalPanel.add(generalLabel);
		generalPanel.add(nameLabel);
		generalPanel.add(nameField);
		//add the icon construction tool to the general panel here
		generalPanel.add(colorTool);
		generalPanel.add(loadIconButton);
		
		fieldPanel.add(fieldLabel);
		fieldPanel2.add(fieldNameLabel);
		fieldPanel2.add(fieldTypeLabel);
		fieldPanel2.add(fieldValueLabel);
		fieldPanel2.add(new JLabel()); //fill empty slot
		fieldPanel2.add(fieldNames.get(0));
		fieldPanel2.add(fieldTypes.get(0));
		fieldPanel2.add(fieldValues.get(0));
		fieldPanel2.add(fieldDeleteButtons.get(0));
		fieldPanel.add(fieldPanel2);
		fieldPanel.add(addFieldButton);
		
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
		
		this.add(tabs);
		this.add(lowerPanel);

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
