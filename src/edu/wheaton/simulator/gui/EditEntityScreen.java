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
	
	private JTabbedPane tabs;
	
	private JPanel lowerPanel;
	
	private JPanel generalPanel;
	
	private JLabel generalLabel;
	
	private JPanel fieldPanel;
	
	private JPanel fieldPanel2;
	
	private JLabel fieldLabel;
	
	private JPanel triggerPanel;
	
	private JPanel triggerPanel2;

	private JLabel triggerLabel;
	
	private JLabel nameLabel;
	
	private JTextField nameField;
	
	private JColorChooser colorTool;
	
	private JButton loadIconButton;
	
	private ArrayList<JTextField> fieldNames; 
	
	private ArrayList<JTextField> fieldValues;
	
	private ArrayList<JComboBox> fieldTypes;
	
	private String[] typeNames =  {"Integer", "Double", "String"};
	
	private ArrayList<JButton> fieldDeleteButtons;
	
	private JLabel fieldNameLabel;
	
	private JLabel fieldValueLabel;
	
	private JLabel fieldTypeLabel;
	
	private JButton addFieldButton;
	
	private ArrayList<JTextField> triggerNames;
	
	private ArrayList<JTextField> triggerPriorities;
	
	private ArrayList<JTextField> triggerConditions;
	
	private ArrayList<JTextField> triggerResults;
	
	private ArrayList<JButton> triggerDeleteButtons;
	
	private JLabel triggerNameLabel;
	
	private JLabel triggerPriorityLabel;
	
	private JLabel triggerConditionLabel;
	
	private JLabel triggerResultLabel;
	
	private JButton addTriggerButton;
	
	private JButton cancelButton;
	
	private JButton finishButton;
	
	public EditEntityScreen(ScreenManager sm) {
		super(sm);
		tabs = new JTabbedPane();
		lowerPanel = new JPanel();
		generalPanel = new JPanel();
		fieldPanel = new JPanel();
		fieldPanel2 = new JPanel();
		fieldPanel2.setLayout(new GridLayout(2, 4));
		triggerPanel = new JPanel();
		triggerPanel2 = new JPanel();
		triggerPanel2.setLayout(new GridLayout(2, 5));
		generalLabel = new JLabel("General Info");
		generalLabel.setHorizontalAlignment(SwingConstants.CENTER);
		generalLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		fieldLabel = new JLabel("Field Info");
		fieldLabel.setHorizontalAlignment(SwingConstants.CENTER);
		fieldLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		triggerLabel = new JLabel("Trigger Info");
		triggerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		triggerLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		nameLabel = new JLabel("Name: ");
		nameField = new JTextField();
		colorTool = new JColorChooser();
		loadIconButton = new JButton("Load icon");
		//single label at the top of each column \/
		fieldNameLabel = new JLabel("Field Name");
		fieldValueLabel = new JLabel("Field Initial Value");
		fieldTypeLabel = new JLabel("Field Type");
		fieldNames = new ArrayList<JTextField>();
		fieldNames.add(new JTextField());
		fieldValues = new ArrayList<JTextField>();
		fieldValues.add(new JTextField());
		fieldTypes = new ArrayList<JComboBox>();
		fieldTypes.add(new JComboBox(typeNames));
		fieldDeleteButtons = new ArrayList<JButton>();
		fieldDeleteButtons.add(new JButton("Delete"));
		addFieldButton = new JButton("Add Field");
		triggerNameLabel = new JLabel("Trigger Name");
		triggerPriorityLabel = new JLabel("Trigger Priority");
		triggerConditionLabel = new JLabel("Trigger Condition");
		triggerResultLabel = new JLabel("Trigger Result");
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
		addTriggerButton = new JButton("Add Trigger");
		cancelButton = new JButton("Cancel");
		finishButton = new JButton("Finish");
		
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
		
		components = new JComponent[2];
		components[0] = tabs;
		components[1] = lowerPanel;
	}


	public void addComponents(JPanel panel) {
		panel.add(label);
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
