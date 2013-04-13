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
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import edu.wheaton.simulator.datastructure.ElementAlreadyContainedException;
import edu.wheaton.simulator.entity.Prototype;
import edu.wheaton.simulator.entity.Trigger;
import edu.wheaton.simulator.expression.Expression;
import edu.wheaton.simulator.simulation.GUIToAgentFacade;

public class EditEntityScreen extends Screen {

	private static final long serialVersionUID = 4021299442173260142L;

	private Boolean editing;

	private Prototype agent;

	private JPanel cards;
	//private JTabbedPane tabs;

	private String currentCard;

	private JPanel generalPanel;

	private JTextField nameField;

	private JColorChooser colorTool;

	private ArrayList<JTextField> fieldNames;

	private ArrayList<JTextField> fieldValues;

	//private ArrayList<JComboBox> fieldTypes;

	//private String[] typeNames = { "Integer", "Double", "String", "Boolean" };

	private ArrayList<JButton> fieldDeleteButtons;

	private ArrayList<JPanel> fieldSubPanels;

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
	
	private JButton nextButton;
	
	private JButton previousButton;
	
	private JButton finishButton;

	
	//TODO addField/Trigger buttons don't work, some polishing to do on next/previous buttons
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

		currentCard = "General";

		fieldListPanel = GuiUtility.makeBoxPanel(BoxLayout.Y_AXIS);

		triggerListPanel = GuiUtility.makeBoxPanel(BoxLayout.Y_AXIS);

		cards = new JPanel(new CardLayout());
		//tabs = new JTabbedPane();

		generalPanel = GuiUtility.makeBoxPanel(BoxLayout.PAGE_AXIS);

		addFieldButton = makeAddFieldButton(this);

		addTriggerButton = makeAddTriggerButton(this);

		JPanel iconPanel = makeIconPanel();

		initIconDesignObject(iconPanel);

		//serialization not yet implemented
		//JButton loadIconButton = new JButton("Load icon");


		initGeneralPanel(iconPanel);

		//JLabel fieldTypeLabel = new JLabel("Field Type");

		addField();

		// TODO make sure components line up

		initFieldSubPanels();

		//fieldTypeLabel.setHorizontalAlignment(SwingConstants.LEFT);

		//fieldLabelsPanel.add(fieldTypeLabel);

		initFieldListPanel();


		// fieldSubPanels.get(0).setAlignmentY(TOP_ALIGNMENT);

		addTrigger();

		initTriggerSubPanels();
		initTriggerListPanel();

		initCards();

		this.add(makeScreenLabel(), BorderLayout.NORTH);
		this.add(cards, BorderLayout.CENTER);
		this.add(makeLowerPanel(this,sm), BorderLayout.SOUTH);

	}

	private void initCards(){
		cards.add(generalPanel, "General");
		cards.add(makeFieldMainPanel(fieldListPanel), "Fields");
		cards.add(makeTriggerMainPanel(triggerListPanel), "Triggers");
	}

	private void initTriggerListPanel(){
		triggerListPanel.add(triggerSubPanels.get(0));
		triggerListPanel.add(addTriggerButton);
		triggerListPanel.add(Box.createVerticalGlue());
	}

	private void initTriggerSubPanels(){
		triggerSubPanels.get(0).setLayout(
				new BoxLayout(triggerSubPanels.get(0), BoxLayout.X_AXIS));
		triggerSubPanels.get(0).add(triggerNames.get(0));
		triggerSubPanels.get(0).add(triggerPriorities.get(0));
		triggerSubPanels.get(0).add(triggerConditions.get(0));
		triggerSubPanels.get(0).add(triggerResults.get(0));
		triggerSubPanels.get(0).add(triggerDeleteButtons.get(0));
		triggerSubPanels.get(0).setAlignmentX(CENTER_ALIGNMENT);
		// triggerSubPanels.get(0).setAlignmentY(TOP_ALIGNMENT);
	}

	private void initFieldListPanel(){
		fieldListPanel.add(fieldSubPanels.get(0));
		fieldListPanel.add(addFieldButton);
		fieldListPanel.add(Box.createVerticalGlue());
	}

	private void initFieldSubPanels(){
		fieldSubPanels.get(0).setLayout(
				new BoxLayout(fieldSubPanels.get(0), BoxLayout.X_AXIS));
		fieldSubPanels.get(0).add(fieldNames.get(0));
		//fieldSubPanels.get(0).add(fieldTypes.get(0));
		fieldSubPanels.get(0).add(fieldValues.get(0));
		fieldSubPanels.get(0).add(fieldDeleteButtons.get(0));
	}

	private void initGeneralPanel(JPanel iconPanel){
		generalPanel.add(makeGeneralLabel());
		generalPanel.add(makeNameLabel());
		generalPanel.add(nameField);
		generalPanel.add(makeMainPanel(colorTool,iconPanel));
		//generalPanel.add(loadIconButton);
	}

	private void initIconDesignObject(JPanel iconPanel){
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
	}

	private static JButton makeAddFieldButton(final EditEntityScreen screen){
		JButton addFieldButton = GuiUtility.makeButton("Add Field",new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				screen.addField();
			}
		});
		return addFieldButton;
	}

	private static JButton makeAddTriggerButton(final EditEntityScreen screen){
		JButton addTriggerButton = GuiUtility.makeButton("Add Trigger",new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				screen.addTrigger();
			}
		});
		return addTriggerButton;
	}

	private static JLabel makeNameLabel(){
		return new JLabel("Name: ");
	}

	private static JLabel makeGeneralLabel(){
		JLabel generalLabel = GuiUtility.makeLabel("General Info",new PrefSize(300,80),HorizontalAlignment.CENTER);
		return generalLabel;
	}

	private static JPanel makeMainPanel(JColorChooser colorTool, JPanel iconPanel){
		JPanel mainPanel = GuiUtility.makeBoxPanel(BoxLayout.X_AXIS);
		mainPanel.setMaximumSize(new Dimension(1200, 500));
		mainPanel.add(makeColorPanel(colorTool));
		mainPanel.add(iconPanel);
		return mainPanel;
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

		
		JLabel triggerNameLabel = GuiUtility.makeLabel("Trigger Name",new PrefSize(130,30),HorizontalAlignment.LEFT);
		
		JLabel triggerPriorityLabel = GuiUtility.makeLabel("Trigger Priority",new PrefSize(180,30),null);
		
		JLabel triggerConditionLabel = GuiUtility.makeLabel("Trigger Condition",new PrefSize(300,30),null);
		
		JLabel triggerResultLabel = GuiUtility.makeLabel("Trigger Result",new PrefSize(300,30),null);
		
		JPanel triggerLabelsPanel = GuiUtility.makeBoxPanel(BoxLayout.X_AXIS);

		triggerLabelsPanel.add(Box.createHorizontalGlue());
		triggerLabelsPanel.add(triggerNameLabel);
		triggerLabelsPanel.add(triggerPriorityLabel);
		triggerLabelsPanel.add(triggerConditionLabel);
		triggerLabelsPanel.add(triggerResultLabel);
		triggerLabelsPanel.add(Box.createHorizontalGlue());
		triggerLabelsPanel.setAlignmentX(CENTER_ALIGNMENT);

		
		JLabel triggerLabel = GuiUtility.makeLabel("Trigger Info",new PrefSize(300,100),HorizontalAlignment.CENTER);
		
		JPanel triggerBodyPanel = makeTriggerBodyPanel();
		triggerBodyPanel.add(triggerLabelsPanel);
		triggerBodyPanel.add(triggerListPanel);
		
		JPanel triggerMainPanel = GuiUtility.makeBorderPanel(new BorderLayout());

		triggerMainPanel.add(triggerLabel, BorderLayout.NORTH);
		triggerMainPanel.add(triggerBodyPanel, BorderLayout.CENTER);
		return triggerMainPanel;
	}

	private static JPanel makeFieldMainPanel(JPanel fieldListPanel){
		JLabel fieldNameLabel = GuiUtility.makeLabel("Field Name",new PrefSize(350,30),HorizontalAlignment.LEFT);
		fieldNameLabel.setAlignmentX(LEFT_ALIGNMENT);

		
		JLabel fieldValueLabel = GuiUtility.makeLabel("Field Initial Value",new PrefSize(400,30),HorizontalAlignment.CENTER);
		
		JPanel fieldLabelsPanel = GuiUtility.makeBoxPanel(BoxLayout.X_AXIS);

		fieldLabelsPanel.add(Box.createHorizontalGlue());
		fieldLabelsPanel.add(fieldNameLabel);
		fieldLabelsPanel.add(fieldValueLabel);
		fieldLabelsPanel.add(Box.createHorizontalGlue());

		JPanel fieldBodyPanel = makeFieldBodyPanel(fieldLabelsPanel,fieldListPanel);

		
		JLabel fieldLabel = GuiUtility.makeLabel("Field Info",new PrefSize(300,100),HorizontalAlignment.CENTER);
		
		JPanel fieldMainPanel = GuiUtility.makeBorderPanel(new BorderLayout());

		fieldMainPanel.add(fieldLabel, BorderLayout.NORTH);
		fieldMainPanel.add(fieldBodyPanel, BorderLayout.CENTER);
		return fieldMainPanel;
	}

	private JPanel makeLowerPanel(final EditEntityScreen screen, final ScreenManager sm){
		JPanel lowerPanel = new JPanel();
		lowerPanel.add(makeCancelButton(screen,sm));
		finishButton = makeFinishButton(screen,sm);
		lowerPanel.add(finishButton);
		previousButton = makePreviousButton();
		lowerPanel.add(previousButton);
		nextButton = makeNextButton();
		lowerPanel.add(nextButton);
		return lowerPanel;
	}

	private static JPanel makeFieldBodyPanel(JPanel fieldLabelsPanel, JPanel fieldListPanel){
		JPanel fieldBodyPanel = GuiUtility.makeBoxPanel(BoxLayout.Y_AXIS);
		fieldBodyPanel.add(fieldLabelsPanel);
		fieldBodyPanel.add(fieldListPanel);
		return fieldBodyPanel;
	}

	private static JPanel makeTriggerBodyPanel(){
		JPanel triggerBodyPanel = GuiUtility.makeBoxPanel(BoxLayout.Y_AXIS);
		return triggerBodyPanel;
	}

	private static JLabel makeScreenLabel(){
		JLabel label = new JLabel("Edit Entities");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setHorizontalTextPosition(SwingConstants.CENTER);
		return label;
	}

	private static JButton makeCancelButton(final EditEntityScreen screen, final ScreenManager sm){
		JButton cancelButton = GuiUtility.makeButton("Cancel",new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sm.update(sm.getScreen("Entities"));
				screen.reset();
			}
		});
		return cancelButton;
	}

	private static JButton makeFinishButton(final EditEntityScreen screen, final ScreenManager sm){
		JButton finishButton = GuiUtility.makeButton("Finish",new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (screen.sendInfo()) {
					sm.update(sm.getScreen("Edit Simulation"));
					screen.reset();
				}
			}
		});
		return finishButton;
	}

	public void load(String str) {
		reset();
		((CardLayout)cards.getLayout()).next(cards);
		sm.getFacade();
		agent = GUIToAgentFacade.getPrototype(str);
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
			sm.getFacade();
			GUIToAgentFacade.createPrototype(nameField.getText(),
					sm.getFacade().getGrid(), colorTool.getColor(),	generateBytes());
			sm.getFacade();
			agent = GUIToAgentFacade.getPrototype(nameField.getText());
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
		JPanel newPanel = GuiUtility.makeBoxPanel(BoxLayout.X_AXIS);
		JTextField newName = new JTextField(25);
		newName.setMaximumSize(new Dimension(300, 40));
		fieldNames.add(newName);
		//		JComboBox newType = new JComboBox(typeNames);
		//		newType.setMaximumSize(new Dimension(200, 40));
		//		fieldTypes.add(newType);
		JTextField newValue = new JTextField(25);
		newValue.setMaximumSize(new Dimension(300, 40));
		fieldValues.add(newValue);
		JButton newButton = GuiUtility.makeButton("Delete",new DeleteFieldListener());
		fieldDeleteButtons.add(newButton);
		newButton.setActionCommand(fieldDeleteButtons.indexOf(newButton) + "");
		newPanel.add(newName);
		//		newPanel.add(newType);
		newPanel.add(newValue);
		newPanel.add(newButton);
		fieldSubPanels.add(newPanel);
		fieldListPanel.add(newPanel);
		fieldListPanel.add(addFieldButton);
		fieldListPanel.add(Box.createVerticalGlue());
		repaint();
	}

	private void addTrigger() {
		JPanel newPanel = GuiUtility.makeBoxPanel(BoxLayout.X_AXIS);
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
		JButton newButton = GuiUtility.makeButton("Delete",new DeleteTriggerListener());
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
		triggerListPanel.add(Box.createVerticalGlue());
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

	private class NextListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			CardLayout c1 = (CardLayout)cards.getLayout();
			if (currentCard == "General"){
				sendGeneralInfo();
				previousButton.setEnabled(true);
				c1.next(cards);
				currentCard = "Fields";
			}
			else if (currentCard == "Fields"){
				sendFieldInfo();
				c1.next(cards);
				nextButton.setEnabled(false);
				finishButton.setEnabled(true);
				currentCard = "Triggers";
			}
		}
	}
	
private class PreviousListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			CardLayout c1 = (CardLayout)cards.getLayout();
			if (currentCard == "Fields"){
				sendFieldInfo();
				previousButton.setEnabled(false);
				c1.previous(cards);
				currentCard = "General";
			}
			else if (currentCard == "Triggers"){
				sendTriggerInfo();
				c1.previous(cards);
				nextButton.setEnabled(true);
				finishButton.setEnabled(false);
				currentCard = "Fields";
			}
		}
	}
	
	//TODO where does this need to go?
	private JButton makeNextButton(){
		JButton next = new JButton("Next");
		next.addActionListener(new NextListener());
		return next;
	}
	
	private JButton makePreviousButton(){
		JButton previous = new JButton("Previous");
		previous.addActionListener(new PreviousListener());
		return previous;
	}

	@Override
	public void load() {
		reset();
		addField();
		addTrigger();
	}

}
