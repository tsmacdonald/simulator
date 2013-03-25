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

import javax.swing.GroupLayout.Alignment;

import com.sun.corba.se.impl.encoding.CodeSetConversion.BTCConverter;

import edu.wheaton.simulator.datastructure.ElementAlreadyContainedException;
import edu.wheaton.simulator.entity.Prototype;
import edu.wheaton.simulator.entity.Trigger;
import edu.wheaton.simulator.expression.Expression;

//TODO deletes prototype after editing
public class EditEntityScreen extends Screen {

	private static final long serialVersionUID = 4021299442173260142L;

	private Boolean editing;

	private Prototype agent;

	private JTextField nameField;

	private JColorChooser colorTool;

	private ArrayList<JTextField> fieldNames;

	private ArrayList<JTextField> fieldValues;

	private ArrayList<JComboBox> fieldTypes;

	private String[] typeNames = { "Integer", "Double", "String", "Boolean" };

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
		JLabel label = new JLabel("Edit Entities");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setHorizontalTextPosition(SwingConstants.CENTER);
		JTabbedPane tabs = new JTabbedPane();
		JPanel lowerPanel = new JPanel();
		JPanel generalPanel = new JPanel();
		JPanel mainPanel = new JPanel();
		JPanel iconPanel = new JPanel();
		JPanel fieldMainPanel = new JPanel();
		JPanel fieldLabelsPanel = new JPanel();
		fieldListPanel = new JPanel();
		JPanel triggerMainPanel = new JPanel();
		JPanel triggerLabelsPanel = new JPanel();
		triggerListPanel = new JPanel();
		removedFields = new HashSet<Integer>();
		removedTriggers = new HashSet<Integer>();

		JLabel generalLabel = new JLabel("General Info");
		generalLabel.setHorizontalAlignment(SwingConstants.CENTER);
		generalLabel.setPreferredSize(new Dimension(300, 80));
		JLabel nameLabel = new JLabel("Name: ");
		nameField = new JTextField(25);
		nameField.setMaximumSize(new Dimension(400, 40));
		colorTool = new JColorChooser();
		JPanel colorPanel = new JPanel();
		colorPanel.add(colorTool);
		colorPanel.setAlignmentX(LEFT_ALIGNMENT);
		iconPanel.setLayout(new GridLayout(7, 7));
		iconPanel.setMinimumSize(new Dimension(500, 500));
		iconPanel.setAlignmentX(RIGHT_ALIGNMENT);
		buttons = new JToggleButton[7][7];
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				buttons[i][j] = new JToggleButton();
				buttons[i][j].setOpaque(true);
				buttons[i][j].setBackground(Color.WHITE);
				buttons[i][j].setActionCommand(i + "" + j);
				buttons[i][j].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent ae) {
						String str = ae.getActionCommand();
						JToggleButton jb = buttons[Integer.parseInt(str
								.charAt(0) + "")][Integer.parseInt(str
										.charAt(1) + "")];
						if (jb.getBackground().equals(Color.WHITE))
							jb.setBackground(Color.BLACK);
						else
							jb.setBackground(Color.WHITE);
					}
				});
				iconPanel.add(buttons[i][j]);
			}
		}

		JButton loadIconButton = new JButton("Load icon");
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
		mainPanel.setMaximumSize(new Dimension(1200, 500));
		generalPanel
		.setLayout(new BoxLayout(generalPanel, BoxLayout.PAGE_AXIS));
		mainPanel.add(colorPanel);
		mainPanel.add(iconPanel);
		generalPanel.add(generalLabel);
		generalPanel.add(nameLabel);
		generalPanel.add(nameField);
		generalPanel.add(mainPanel);
		generalPanel.add(loadIconButton);

		JLabel fieldLabel = new JLabel("Field Info");
		fieldLabel.setHorizontalAlignment(SwingConstants.CENTER);
		fieldLabel.setPreferredSize(new Dimension(300, 100));
		JLabel fieldNameLabel = new JLabel("Field Name");
		fieldNameLabel.setPreferredSize(new Dimension(200, 30));
		JLabel fieldValueLabel = new JLabel("Field Initial Value");
		fieldValueLabel.setPreferredSize(new Dimension(400, 30));
		JLabel fieldTypeLabel = new JLabel("Field Type");
		fieldNameLabel.setPreferredSize(new Dimension(350, 30));
		fieldNames = new ArrayList<JTextField>();
		fieldValues = new ArrayList<JTextField>();
		fieldTypes = new ArrayList<JComboBox>();
		fieldDeleteButtons = new ArrayList<JButton>();
		fieldSubPanels = new ArrayList<JPanel>();
		addFieldButton = new JButton("Add Field");
		addFieldButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addField();
			}
		});
		glue = Box.createVerticalGlue();
		addField();
		// TODO make sure components line up
		fieldMainPanel.setLayout(new BorderLayout());
		JPanel fieldBodyPanel = new JPanel();
		fieldBodyPanel.setLayout(new BoxLayout(fieldBodyPanel,
				BoxLayout.Y_AXIS));
		fieldLabelsPanel.setLayout(new BoxLayout(fieldLabelsPanel,
				BoxLayout.X_AXIS));
		fieldListPanel.setLayout(new BoxLayout(fieldListPanel,
				BoxLayout.Y_AXIS));
		fieldSubPanels.get(0).setLayout(
				new BoxLayout(fieldSubPanels.get(0), BoxLayout.X_AXIS));
		fieldMainPanel.add(fieldLabel, BorderLayout.NORTH);
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
		fieldListPanel.add(glue);
		// fieldSubPanels.get(0).setAlignmentY(TOP_ALIGNMENT);
		fieldBodyPanel.add(fieldLabelsPanel);
		fieldBodyPanel.add(fieldListPanel);
		fieldMainPanel.add(fieldBodyPanel, BorderLayout.CENTER);

		JLabel triggerLabel = new JLabel("Trigger Info");
		triggerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		triggerLabel.setPreferredSize(new Dimension(300, 100));
		JLabel triggerNameLabel = new JLabel("Trigger Name");
		triggerNameLabel.setPreferredSize(new Dimension(130, 30));
		JLabel triggerPriorityLabel = new JLabel("Trigger Priority");
		triggerPriorityLabel.setPreferredSize(new Dimension(180, 30));
		JLabel triggerConditionLabel = new JLabel("Trigger Condition");
		triggerConditionLabel.setPreferredSize(new Dimension(300, 30));
		JLabel triggerResultLabel = new JLabel("Trigger Result");
		triggerResultLabel.setPreferredSize(new Dimension(300, 30));
		triggerNames = new ArrayList<JTextField>();
		triggerPriorities = new ArrayList<JTextField>();
		triggerConditions = new ArrayList<JTextField>();
		triggerResults = new ArrayList<JTextField>();
		triggerDeleteButtons = new ArrayList<JButton>();
		triggerSubPanels = new ArrayList<JPanel>();
		addTriggerButton = new JButton("Add Trigger");
		addTriggerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addTrigger();
			}
		});
		glue2 = Box.createVerticalGlue();
		addTrigger();
		// TODO make sure components line up
		triggerMainPanel.setLayout(new BorderLayout());
		JPanel triggerBodyPanel = new JPanel();
		triggerBodyPanel.setLayout(new BoxLayout(triggerBodyPanel,
				BoxLayout.Y_AXIS));
		triggerLabelsPanel.setLayout(new BoxLayout(triggerLabelsPanel,
				BoxLayout.X_AXIS));
		triggerListPanel.setLayout(new BoxLayout(triggerListPanel,
				BoxLayout.Y_AXIS));
		triggerSubPanels.get(0).setLayout(
				new BoxLayout(triggerSubPanels.get(0), BoxLayout.X_AXIS));
		triggerMainPanel.add(triggerLabel, BorderLayout.NORTH);
		triggerLabelsPanel.add(Box.createHorizontalGlue());
		triggerLabelsPanel.add(triggerNameLabel);
		triggerNameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		triggerLabelsPanel.add(triggerPriorityLabel);
		triggerLabelsPanel.add(triggerConditionLabel);
		triggerLabelsPanel.add(triggerResultLabel);
		triggerLabelsPanel.add(Box.createHorizontalGlue());
		triggerSubPanels.get(0).add(triggerNames.get(0));
		triggerSubPanels.get(0).add(triggerPriorities.get(0));
		triggerSubPanels.get(0).add(triggerConditions.get(0));
		triggerSubPanels.get(0).add(triggerResults.get(0));
		triggerSubPanels.get(0).add(triggerDeleteButtons.get(0));
		triggerListPanel.add(triggerSubPanels.get(0));
		triggerListPanel.add(addTriggerButton);
		triggerListPanel.add(glue2);
		triggerBodyPanel.add(triggerLabelsPanel);
		triggerLabelsPanel.setAlignmentX(CENTER_ALIGNMENT);
		triggerBodyPanel.add(triggerListPanel);
		triggerSubPanels.get(0).setAlignmentX(CENTER_ALIGNMENT);
		// triggerSubPanels.get(0).setAlignmentY(TOP_ALIGNMENT);
		triggerMainPanel.add(triggerBodyPanel, BorderLayout.CENTER);

		tabs.addTab("General", generalPanel);
		tabs.addTab("Fields", fieldMainPanel);
		tabs.addTab("Triggers", triggerMainPanel);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sm.update(sm.getScreen("Edit Simulation"));
				reset();
			}
		});
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

		lowerPanel.add(cancelButton);
		lowerPanel.add(finishButton);

		this.add(label, BorderLayout.NORTH);
		this.add(tabs, BorderLayout.CENTER);
		this.add(lowerPanel, BorderLayout.SOUTH);

	}

	public void load(String str) {
		reset();
		agent = sm.getFacade().getPrototype(str);
		nameField.setText(agent.getName());
		colorTool.setColor(agent.getColor());

		byte[] designBytes = agent.getDesign();
			for (byte b : designBytes) 
					System.out.println("lB:" + b);
		byte byter = Byte.parseByte("0000001", 2);
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				if ((designBytes[i] & (byter << j)) != Byte.parseByte("0000000", 2)) {
					buttons[i][6-j].doClick();
				}
			}
		}

		Map<String, String> fields = agent.getFieldMap();
		int i = 0;
		for (String s : fields.keySet()) {
			addField();
			fieldNames.get(i).setText(s);
			fieldValues.get(i).setText(s);
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
		fieldTypes.clear();
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
		boolean toReturn = false;
		try {
			for (int i = 0; i < fieldNames.size(); i++) {
				if (fieldNames.get(i).getText().equals("")
						|| fieldValues.get(i).getText().equals("")) {
					throw new Exception("All fields must have input");
				}
			}
			for (int j = 0; j < triggerNames.size(); j++) {
				if (triggerNames.get(j).getText().equals("")
						|| triggerConditions.get(j).getText().equals("")
						|| triggerResults.get(j).getText().equals("")) {
					throw new Exception("All fields must have input");
				}
				if (Integer.parseInt(triggerPriorities.get(j).getText()) < 0) {
					throw new Exception("Priority must be greater than 0");
				}
			}
			
			if (!editing) {
				sm.getFacade().createPrototype(nameField.getText(),
						sm.getFacade().getGrid(), colorTool.getColor(),
						generateBytes());
				agent = sm.getFacade().getPrototype(nameField.getText());
			}
			else {
				agent.setPrototypeName(agent.getName(),
						nameField.getText());
				agent.setColor(colorTool.getColor());
				agent.setDesign(generateBytes());
				Prototype.addPrototype(agent.getName(), agent);
			}
			
			for (int i = 0; i < fieldNames.size(); i++) {
				if (removedFields.contains(i)) {
					if (agent.hasField(fieldNames.get(i).getText()))
						agent.removeField(fieldNames.get(i));
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

				toReturn = true;
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null,
					"Priorities field must be an integer greater than 0.");
			e.printStackTrace();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		} finally {
			return toReturn;
		}
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
		JComboBox newType = new JComboBox(typeNames);
		newType.setMaximumSize(new Dimension(200, 40));
		fieldTypes.add(newType);
		JTextField newValue = new JTextField(25);
		newValue.setMaximumSize(new Dimension(300, 40));
		fieldValues.add(newValue);
		JButton newButton = new JButton("Delete");
		newButton.addActionListener(new DeleteFieldListener());
		fieldDeleteButtons.add(newButton);
		newButton.setActionCommand(fieldDeleteButtons.indexOf(newButton) + "");
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
					System.out.print("1");
					str += "1";
				} else {
					System.out.print("0");
					str += "0";
				}
			}
			str += ":";
			System.out.print(":");
		}
		str = str.substring(0, str.lastIndexOf(':'));
		String[] byteStr = str.split(":");
		System.out.println("BOO: " + str); 
				for (String s : byteStr) 
					System.out.println("genB:" +s);
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
		private String action;

		@Override
		public void actionPerformed(ActionEvent e) {
			removedFields.add(Integer.parseInt(e.getActionCommand()));
			fieldListPanel.remove(fieldSubPanels.get(Integer.parseInt(e
					.getActionCommand())));
			repaint();
		}
	}

	private class DeleteTriggerListener implements ActionListener {
		private String action;

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
