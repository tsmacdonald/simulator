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
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import edu.wheaton.simulator.datastructure.ElementAlreadyContainedException;
import edu.wheaton.simulator.entity.Prototype;
import edu.wheaton.simulator.entity.Trigger;
import edu.wheaton.simulator.expression.Expression;

public class EditEntityScreen extends Screen {

	private static final long serialVersionUID = 4021299442173260142L;

	private Boolean editing;

	private Prototype agent;

	private JTabbedPane tabs;
	
	private String currentTab;

	private JPanel generalPanel;

	private JTextField nameField;

	private JColorChooser colorTool;

	private ArrayList<JTextField> fieldNames;

	private ArrayList<JTextField> fieldValues;

	//private ArrayList<JComboBox> fieldTypes;

	//private String[] typeNames = { "Integer", "Double", "String", "Boolean" };

	private ArrayList<JButton> fieldDeleteButtons;

	private ArrayList<JPanel> fieldSubPanels;

	private Component glue;

	private Component glue2;

	private JButton addFieldButton;

	private JToggleButton[][] buttons;

	private JPanel fieldListPanel;

	private ArrayList<JTextField> triggerNames;

	private ArrayList<JTextField> triggerPriorities;

	private ArrayList<JTextField> triggerConditions;

	private ArrayList<JTextField> triggerResults;

	private ArrayList<JButton> triggerDeleteButtons;

	private ArrayList<JPanel> triggerSubPanels;

	private JButton addTriggerButton;

	private JPanel triggerListPanel;

	private HashSet<Integer> removedFields;

	private HashSet<Integer> removedTriggers;

	public EditEntityScreen(final ScreenManager sm) {
		super(sm);
		this.setLayout(new BorderLayout());
		
		removedFields = new HashSet<Integer>();
		removedTriggers = new HashSet<Integer>();
		
		nameField = new JTextField(25);
		nameField.setMaximumSize(new Dimension(400, 40));
		
		colorTool = new JColorChooser();
		
		buttons = new JToggleButton[7][7];
		
		fieldNames = new ArrayList<JTextField>();
		fieldValues = new ArrayList<JTextField>();
		
		//fieldTypes = new ArrayList<JComboBox>();
		
		
		fieldSubPanels = new ArrayList<JPanel>();
		triggerSubPanels = new ArrayList<JPanel>();
		
		triggerNames = new ArrayList<JTextField>();
		triggerPriorities = new ArrayList<JTextField>();
		triggerConditions = new ArrayList<JTextField>();
		triggerResults = new ArrayList<JTextField>();
		
		fieldDeleteButtons = new ArrayList<JButton>();
		triggerDeleteButtons = new ArrayList<JButton>();
		
		glue = Box.createVerticalGlue();
		glue2 = Box.createVerticalGlue();
		
		currentTab = "General";
		
		fieldListPanel = new JPanel();
		
		triggerListPanel = new JPanel();
		
		tabs = new JTabbedPane();
		
		generalPanel = new JPanel();
		generalPanel
		.setLayout(new BoxLayout(generalPanel, BoxLayout.PAGE_AXIS));
		
		addFieldButton = new JButton("Add Field");
		addFieldButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addField();
			}
		});
		
		addTriggerButton = new JButton("Add Trigger");
		addTriggerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addTrigger();
			}
		});
		
		JPanel iconPanel = makeIconPanel();
	
		//Creates the icon design object.
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				buttons[i][j] = new JToggleButton();
				buttons[i][j].setOpaque(true);
				buttons[i][j].setBackground(Color.WHITE);
				buttons[i][j].setActionCommand(i + "" + j);
				//When the button is pushed it switches colors.
				buttons[i][j].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent ae) {
						String str = ae.getActionCommand();
						JToggleButton jb = buttons[Integer.parseInt(str
								.charAt(0) + "")][Integer.parseInt(str
										.charAt(1) + "")];
						if (jb.getBackground().equals(Color.WHITE)) {
							jb.setBackground(Color.BLACK);
							jb.setForeground(Color.BLACK);
						}
						else {
							jb.setBackground(Color.WHITE);
							jb.setForeground(Color.WHITE);
						}
					}
				});
				iconPanel.add(buttons[i][j]);
			}
		}

		//serialization not yet implemented
		//JButton loadIconButton = new JButton("Load icon");
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
		mainPanel.setMaximumSize(new Dimension(1200, 500));
		mainPanel.add(makeColorPanel(colorTool));
		mainPanel.add(iconPanel);
		
		JLabel generalLabel = makeLabel("General Info",300,80);
		generalLabel.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel nameLabel = new JLabel("Name: ");
		
		generalPanel.add(generalLabel);
		generalPanel.add(nameLabel);
		generalPanel.add(nameField);
		generalPanel.add(mainPanel);
		//generalPanel.add(loadIconButton);

		//JLabel fieldTypeLabel = new JLabel("Field Type");
		
		addField();
		
		// TODO make sure components line up
		
		fieldSubPanels.get(0).setLayout(
				new BoxLayout(fieldSubPanels.get(0), BoxLayout.X_AXIS));
		fieldSubPanels.get(0).add(fieldNames.get(0));
		//fieldSubPanels.get(0).add(fieldTypes.get(0));
		fieldSubPanels.get(0).add(fieldValues.get(0));
		fieldSubPanels.get(0).add(fieldDeleteButtons.get(0));
		
		//fieldTypeLabel.setHorizontalAlignment(SwingConstants.LEFT);
		
		//fieldLabelsPanel.add(fieldTypeLabel);
		
		fieldListPanel.setLayout(new BoxLayout(fieldListPanel,
				BoxLayout.Y_AXIS));
		fieldListPanel.add(fieldSubPanels.get(0));
		fieldListPanel.add(addFieldButton);
		fieldListPanel.add(glue);
		
		// fieldSubPanels.get(0).setAlignmentY(TOP_ALIGNMENT);
		
		addTrigger();
		
		// TODO make sure components line up
		triggerSubPanels.get(0).setLayout(
				new BoxLayout(triggerSubPanels.get(0), BoxLayout.X_AXIS));
		triggerSubPanels.get(0).add(triggerNames.get(0));
		triggerSubPanels.get(0).add(triggerPriorities.get(0));
		triggerSubPanels.get(0).add(triggerConditions.get(0));
		triggerSubPanels.get(0).add(triggerResults.get(0));
		triggerSubPanels.get(0).add(triggerDeleteButtons.get(0));
		triggerSubPanels.get(0).setAlignmentX(CENTER_ALIGNMENT);
		// triggerSubPanels.get(0).setAlignmentY(TOP_ALIGNMENT);
		
		
		triggerListPanel.setLayout(new BoxLayout(triggerListPanel,
				BoxLayout.Y_AXIS));
		triggerListPanel.add(triggerSubPanels.get(0));
		triggerListPanel.add(addTriggerButton);
		triggerListPanel.add(glue2);

		tabs.addTab("General", generalPanel);
		tabs.addTab("Fields", makeFieldMainPanel(fieldListPanel));
		tabs.addTab("Triggers", makeTriggerMainPanel(triggerListPanel));
		tabs.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				
				if (currentTab == "General")
					sendGeneralInfo();
				else if (currentTab == "Fields")
					sendFieldInfo();
				else
					sendTriggerInfo();
				currentTab = tabs.getTitleAt(tabs.getSelectedIndex());
				
				
			}
			
		});
		
		this.add(makeScreenLabel(), BorderLayout.NORTH);
		this.add(tabs, BorderLayout.CENTER);
		this.add(makeLowerPanel(), BorderLayout.SOUTH);

	}
	
	private static JPanel makeIconPanel(){
		JPanel iconPanel = new JPanel();
		iconPanel.setLayout(new GridLayout(7, 7));
		iconPanel.setMinimumSize(new Dimension(500, 500));
		iconPanel.setAlignmentX(RIGHT_ALIGNMENT);
		return iconPanel;
	}
	
	private static JPanel makeColorPanel(JColorChooser colorTool){
		JPanel colorPanel = new JPanel();
		colorPanel.add(colorTool);
		colorPanel.setAlignmentX(LEFT_ALIGNMENT);
		return colorPanel;
	}
	
	private static JPanel makeTriggerMainPanel(JPanel triggerListPanel){
		
		JLabel triggerNameLabel = makeLabel("Trigger Name",130,30);
		triggerNameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		
		JLabel triggerPriorityLabel = makeLabel("Trigger Priority",180,30);
		
		JLabel triggerConditionLabel = makeLabel("Trigger Condition",300,30);
		
		JLabel triggerResultLabel = makeLabel("Trigger Result",300,30);
		
		JPanel triggerLabelsPanel = new JPanel();
		triggerLabelsPanel.setLayout(new BoxLayout(triggerLabelsPanel,
				BoxLayout.X_AXIS));
		triggerLabelsPanel.add(Box.createHorizontalGlue());
		triggerLabelsPanel.add(triggerNameLabel);
		triggerLabelsPanel.add(triggerPriorityLabel);
		triggerLabelsPanel.add(triggerConditionLabel);
		triggerLabelsPanel.add(triggerResultLabel);
		triggerLabelsPanel.add(Box.createHorizontalGlue());
		triggerLabelsPanel.setAlignmentX(CENTER_ALIGNMENT);
		
		JLabel triggerLabel = makeLabel("Trigger Info",300,100);
		triggerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel triggerBodyPanel = makeTriggerBodyPanel();
		triggerBodyPanel.add(triggerLabelsPanel);
		triggerBodyPanel.add(triggerListPanel);
		
		JPanel triggerMainPanel = new JPanel();
		triggerMainPanel.setLayout(new BorderLayout());
		triggerMainPanel.add(triggerLabel, BorderLayout.NORTH);
		triggerMainPanel.add(triggerBodyPanel, BorderLayout.CENTER);
		return triggerMainPanel;
	}
	
	private static JPanel makeFieldMainPanel(JPanel fieldListPanel){
		JLabel fieldNameLabel = makeLabel("Field Name",350,30);
		fieldNameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		fieldNameLabel.setAlignmentX(LEFT_ALIGNMENT);
		
		JLabel fieldValueLabel = makeLabel("Field Initial Value",400,30);
		fieldValueLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel fieldLabelsPanel = new JPanel();
		fieldLabelsPanel.setLayout(new BoxLayout(fieldLabelsPanel,
				BoxLayout.X_AXIS));
		fieldLabelsPanel.add(Box.createHorizontalGlue());
		fieldLabelsPanel.add(fieldNameLabel);
		fieldLabelsPanel.add(fieldValueLabel);
		fieldLabelsPanel.add(Box.createHorizontalGlue());
		
		JPanel fieldBodyPanel = makeFieldBodyPanel(fieldLabelsPanel,fieldListPanel);
		
		JLabel fieldLabel = makeLabel("Field Info",300,100);
		fieldLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel fieldMainPanel = new JPanel();
		fieldMainPanel.setLayout(new BorderLayout());
		fieldMainPanel.add(fieldLabel, BorderLayout.NORTH);
		fieldMainPanel.add(fieldBodyPanel, BorderLayout.CENTER);
		return fieldMainPanel;
	}
	
	private JPanel makeLowerPanel(){
		JPanel lowerPanel = new JPanel();
		lowerPanel.add(makeCancelButton());
		lowerPanel.add(makeFinishButton());
		return lowerPanel;
	}
	
	private static JPanel makeFieldBodyPanel(JPanel fieldLabelsPanel, JPanel fieldListPanel){
		JPanel fieldBodyPanel = new JPanel();
		fieldBodyPanel.setLayout(new BoxLayout(fieldBodyPanel,
				BoxLayout.Y_AXIS));
		
		fieldBodyPanel.add(fieldLabelsPanel);
		fieldBodyPanel.add(fieldListPanel);
		return fieldBodyPanel;
	}
	
	private static JPanel makeTriggerBodyPanel(){
		JPanel triggerBodyPanel = new JPanel();
		triggerBodyPanel.setLayout(new BoxLayout(triggerBodyPanel,
				BoxLayout.Y_AXIS));
		return triggerBodyPanel;
	}
	
	private static JLabel makeLabel(String name, int width, int height){
		JLabel label = new JLabel(name);
		label.setPreferredSize(new Dimension(width, height));
		return label;
	}
	
	private static JLabel makeScreenLabel(){
		JLabel label = new JLabel("Edit Entities");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setHorizontalTextPosition(SwingConstants.CENTER);
		return label;
	}
	
	private JButton makeCancelButton(){
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sm.update(sm.getScreen("Entities"));
				reset();
			}
		});
		return cancelButton;
	}
	
	private JButton makeFinishButton(){
		JButton finishButton = new JButton("Finish");
		finishButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (sendInfo()) {
					sm.update(sm.getScreen("Edit Simulation"));
					reset();
				}
			}
		});
		return finishButton;
	}

	public void load(String str) {
		reset();
		tabs.setSelectedComponent(generalPanel);
		agent = sm.getFacade().getPrototype(str);
		nameField.setText(agent.getName());
		colorTool.setColor(agent.getColor());

		byte[] designBytes = agent.getDesign();
		byte byter = Byte.parseByte("0000001", 2);
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				if ((designBytes[i] & (byter << j)) != Byte.parseByte("0000000", 2)) {
					buttons[i][6-j].doClick();
				}
			}
		}

		Map<String, String> fields = agent.getCustomFieldMap();
		int i = 0;
		for (String s : fields.keySet()) {
			addField();
			fieldNames.get(i).setText(s);
			fieldValues.get(i).setText(fields.get(s));
			i++;
		}
		List<Trigger> triggers = agent.getTriggers();
		int j = 0;
		for (Trigger t : triggers) {
			addTrigger();
			triggerNames.get(j).setText(t.getName());
			triggerConditions.get(j).setText(t.getConditions().toString());
			triggerResults.get(j).setText(t.getBehavior().toString());
			triggerPriorities.get(j).setText(t.getPriority() +"");
			j++;
		}
	}

	public void reset() {
		agent = null;
		nameField.setText("");
		colorTool.setColor(Color.WHITE);
		for (int x = 0; x < 7; x++) {
			for (int y = 0; y < 7; y++) {
				buttons[x][y].setSelected(false);
				buttons[x][y].setBackground(Color.WHITE);
			}
		}
		fieldNames.clear();
		//fieldTypes.clear();
		fieldValues.clear();
		fieldDeleteButtons.clear();
		fieldSubPanels.clear();
		removedFields.clear();
		fieldListPanel.removeAll();
		fieldListPanel.add(addFieldButton);
		triggerNames.clear();
		triggerPriorities.clear();
		triggerConditions.clear();
		triggerResults.clear();
		triggerDeleteButtons.clear();
		triggerSubPanels.clear();
		removedTriggers.clear();
		triggerListPanel.removeAll();
		triggerListPanel.add(addTriggerButton);
	}

	public boolean sendInfo() {
		sendGeneralInfo();
		sendFieldInfo();
		return sendTriggerInfo();
	}

	public void sendGeneralInfo(){
		if (!editing) {
			sm.getFacade().createPrototype(nameField.getText(),
					sm.getFacade().getGrid(), colorTool.getColor(),	generateBytes());
			agent = sm.getFacade().getPrototype(nameField.getText());
		}
		else {
			agent.setPrototypeName(agent.getName(),
					nameField.getText());
			agent.setColor(colorTool.getColor());
			agent.setDesign(generateBytes());
			Prototype.addPrototype(agent);
		}
	}

	public void sendFieldInfo(){
		try{
			for (int i = 0; i < fieldNames.size(); i++) {
				if (!removedFields.contains(i)) {
					if (fieldNames.get(i).getText().equals("")
							|| fieldValues.get(i).getText().equals("")) {
						throw new Exception("All fields must have input");
					}
				}
			}

			for (int i = 0; i < fieldNames.size(); i++) {
				if (removedFields.contains(i)) {
					if (agent.hasField(fieldNames.get(i).getText()))
						agent.removeField(fieldNames.get(i).toString());
				} else {
					if (agent.hasField(fieldNames.get(i).getText())) {
						agent.updateField(fieldNames.get(i).getText(),
								fieldValues.get(i).getText());
					} else
						try {
							agent.addField(fieldNames.get(i).getText(),
									fieldValues.get(i).getText());
						} catch (ElementAlreadyContainedException e) {
							e.printStackTrace();
						}
				}
			}
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		} 
	}

	public boolean sendTriggerInfo(){
		boolean toReturn = false;
		try{
			for (int j = 0; j < triggerNames.size(); j++) {
				if (!removedTriggers.contains(j)) {
					if (triggerNames.get(j).getText().equals("")
							|| triggerConditions.get(j).getText().equals("")
							|| triggerResults.get(j).getText().equals("")) {
						throw new Exception("All fields must have input");
					}
				}
				if (Integer.parseInt(triggerPriorities.get(j).getText()) < 0) {
					throw new Exception("Priority must be greater than 0");
				}
			}

			for (int i = 0; i < triggerNames.size(); i++) {
				if (removedTriggers.contains(i)) {
					if (agent.hasTrigger(triggerNames.get(i).getText()))
						agent.removeTrigger(triggerNames.get(i).getText());
				} else {
					if (agent.hasTrigger(triggerNames.get(i).getText()))
						agent.updateTrigger(triggerNames.get(i).getText(),
								generateTrigger(i));
					else
						agent.addTrigger(generateTrigger(i));
				}
			}
			toReturn = true;
		}
		catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null,
					"Priorities field must be an integer greater than 0.");
			e.printStackTrace();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		} 

		return toReturn;
	}

	public void setEditing(Boolean b) {
		editing = b;
	}

	private void addField() {
		JPanel newPanel = new JPanel();
		newPanel.setLayout(new BoxLayout(newPanel, BoxLayout.X_AXIS));
		JTextField newName = new JTextField(25);
		newName.setMaximumSize(new Dimension(300, 40));
		fieldNames.add(newName);
		//		JComboBox newType = new JComboBox(typeNames);
		//		newType.setMaximumSize(new Dimension(200, 40));
		//		fieldTypes.add(newType);
		JTextField newValue = new JTextField(25);
		newValue.setMaximumSize(new Dimension(300, 40));
		fieldValues.add(newValue);
		JButton newButton = new JButton("Delete");
		newButton.addActionListener(new DeleteFieldListener());
		fieldDeleteButtons.add(newButton);
		newButton.setActionCommand(fieldDeleteButtons.indexOf(newButton) + "");
		newPanel.add(newName);
		//		newPanel.add(newType);
		newPanel.add(newValue);
		newPanel.add(newButton);
		fieldSubPanels.add(newPanel);
		fieldListPanel.add(newPanel);
		fieldListPanel.add(addFieldButton);
		fieldListPanel.add(glue);
		repaint();
	}

	private void addTrigger() {
		JPanel newPanel = new JPanel();
		newPanel.setLayout(new BoxLayout(newPanel, BoxLayout.X_AXIS));
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
		newButton.addActionListener(new DeleteTriggerListener());
		triggerDeleteButtons.add(newButton);
		newButton.setActionCommand(triggerDeleteButtons.indexOf(newButton)
				+ "");
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

	private byte[] generateBytes() {
		String str = "";
		byte[] toReturn = new byte[7];
		for (int column = 0; column < 7; column++) {
			for (int row = 0; row < 7; row++) {
				if (buttons[column][row].getBackground().equals(Color.BLACK)) {
					str += "1";
				} else {
					str += "0";
				}
			}
			str += ":";
		}
		str = str.substring(0, str.lastIndexOf(':'));
		String[] byteStr = str.split(":");
		for (int i = 0; i < 7; i++) {
			toReturn[i] = Byte.parseByte(byteStr[i], 2);
		}

		return toReturn;
	}

	private Trigger generateTrigger(int i) {
		return new Trigger(triggerNames.get(i).getText(),
				Integer.parseInt(triggerPriorities.get(i).getText()),
				new Expression(triggerConditions.get(i).getText()),
				new Expression(triggerResults.get(i).getText()));
	}

	private class DeleteFieldListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			removedFields.add(Integer.parseInt(e.getActionCommand()));
			fieldListPanel.remove(fieldSubPanels.get(Integer.parseInt(e
					.getActionCommand())));
			repaint();
		}
	}

	private class DeleteTriggerListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			removedTriggers.add(Integer.parseInt(e.getActionCommand()));
			triggerListPanel.remove(triggerSubPanels.get(Integer.parseInt(e
					.getActionCommand())));
			repaint();
		}
	}

	@Override
	public void load() {
		reset();
		addField();
		addTrigger();
	}

}
