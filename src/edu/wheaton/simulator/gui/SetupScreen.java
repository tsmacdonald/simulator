package edu.wheaton.simulator.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Set;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.google.common.collect.ImmutableMap;

import edu.wheaton.simulator.entity.PrototypeID;
import edu.wheaton.simulator.simulation.end.SimulationEnder;

//TODO commented code for adding operators to ending conditions :
//     see if it should stay for future use or just be deleted
//TODO commented out code for changing width and height of grid :
//     causing too many problems and not providing any value atm.
public class SetupScreen extends Screen {

	private JTextField nameField;

	//	private JTextField width;
	//
	//	private JTextField height;

	private JTextField timeField;

	private String[] agentNames;

	private JComboBox updateBox;

	private ArrayList<JComboBox> agentTypes;

	private ArrayList<JTextField> values;

	//private String[] opNames = {">=", "=", "<="};

	//private ArrayList<JComboBox> operations;

	private ArrayList<JButton> deleteButtons;

	private ArrayList<JPanel> subPanels;

	private JPanel conListPanel;

	private JButton addConditionButton;

	private Component glue;

	private static final long serialVersionUID = -8347080877399964861L;

	public SetupScreen(final ScreenManager sm) {
		super(sm);
		this.setLayout(new BorderLayout());
		agentNames = new String[0];
		this.add(makeWindowLabel(), BorderLayout.NORTH);
		agentTypes = new ArrayList<JComboBox>();
		deleteButtons = new ArrayList<JButton>();
		subPanels = new ArrayList<JPanel>();
		glue = Box.createVerticalGlue();
		addConditionButton = makeAddConditionButton(this);
		conListPanel = makeConListPanel(addConditionButton);
		nameField = makeNameField(sm);
		timeField = makeTimeField();
		updateBox = makeUpdateBox();
		JPanel uberPanel = makeUberPanel(this, timeField, nameField, updateBox);
		this.add(uberPanel, BorderLayout.CENTER);
		agentTypes = new ArrayList<JComboBox>();
		values = new ArrayList<JTextField>();
		JPanel buttonPanel = makeButtonPanel(sm, nameField, timeField, updateBox,agentTypes,values);
		this.add(buttonPanel, BorderLayout.SOUTH);

		//		JLabel widthLabel = new JLabel("Width: ");
		//		widthLabel.setMaximumSize(new Dimension(100, 40));
		//		widthLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		//		width = new JTextField(sm.getGUIwidth()+"", 10);
		//		width.setMaximumSize(new Dimension(80, 30));
		//		JLabel heightLabel = new JLabel("Height: ");
		//		heightLabel.setMaximumSize(new Dimension(210, 40));
		//		heightLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		//		height = new JTextField(sm.getGUIheight()+"", 0);
		//		height.setMaximumSize(new Dimension(80, 30));

		//JLabel operationLabel = new JLabel("Operation");
		//operationLabel.setPreferredSize(new Dimension(350, 30));

		//operations = new ArrayList<JComboBox>();

		//addCondition();
		//TODO line up components
		//		subPanels.get(0).setLayout(
		//				new BoxLayout(subPanels.get(0), BoxLayout.X_AXIS)
		//				);

		//conLabelsPanel.add(operationLabel);
		//operationLabel.setHorizontalAlignment(SwingConstants.LEFT);

		//		subPanels.get(0).add(agentTypes.get(0));
		//		//subPanels.get(0).add(operations.get(0));
		//		subPanels.get(0).add(values.get(0));
		//		subPanels.get(0).add(deleteButtons.get(0));
		//		conListPanel.add(subPanels.get(0));



		//		JPanel panel2 = new JPanel();
		//		panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS));
		//		panel2.add(heightLabel);
		//		panel2.add(height);
		//		panel2.add(widthLabel);
		//		panel2.add(width);
	}

	private static JLabel makeWindowLabel(){
		JLabel label = new JLabel("Simulation Setup");
		label.setPreferredSize(new Dimension(300, 150));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		return label;
	}

	private static JLabel makeNameLabel(){
		JLabel nameLabel = new JLabel ("Name: ");
		nameLabel.setMaximumSize(new Dimension(100, 40));
		nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		return nameLabel;
	}

	private static JTextField makeNameField(final ScreenManager sm){
		JTextField nameField = new JTextField(sm.getGUIname(), 25);
		nameField.setMaximumSize(new Dimension(400, 30));
		return nameField;
	}

	private static JLabel makeUpdateLabel(){
		JLabel updateLabel = new JLabel("Update type: ");
		updateLabel.setMaximumSize(new Dimension(100, 40));
		return updateLabel;
	}

	private static JComboBox makeUpdateBox(){
		String[] updateTypes = {"Linear", "Atomic", "Priority"};
		JComboBox updateBox = new JComboBox(updateTypes);
		updateBox.setMaximumSize(new Dimension(200, 40));
		return updateBox;
	}

	private static JLabel makeEndingLabel(){
		JLabel endingLabel = new JLabel("Ending Conditions");
		endingLabel.setHorizontalAlignment(SwingConstants.CENTER);
		endingLabel.setPreferredSize(new Dimension(300, 100));
		return endingLabel;
	}

	private static JLabel makeAgentTypeLabel(){
		JLabel agentTypeLabel = new JLabel("Agent Type");
		agentTypeLabel.setPreferredSize(new Dimension(200, 30));
		agentTypeLabel.setHorizontalAlignment(SwingConstants.LEFT);
		agentTypeLabel.setAlignmentX(LEFT_ALIGNMENT);
		return agentTypeLabel;
	}

	private static JLabel makeValueLabel(){
		JLabel valueLabel = new JLabel("Population Limit");
		valueLabel.setPreferredSize(new Dimension(400, 30));
		valueLabel.setHorizontalAlignment(SwingConstants.CENTER);
		return valueLabel;
	}

	private static JTextField makeTimeField(){
		JTextField timeField = new JTextField(15);
		timeField.setMaximumSize(new Dimension(200, 40));
		return timeField;
	}

	private static JButton makeAddConditionButton(final SetupScreen screen){
		JButton addConditionButton = new JButton("Add Field");
		addConditionButton.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						screen.addCondition();
					}
				});
		return addConditionButton;
	}

	private static JPanel makeConMainPanel(SetupScreen screen, JTextField timeField){
		JPanel conMainPanel = new JPanel();
		conMainPanel.setLayout(
				new BorderLayout()
				);
		conMainPanel.add(makeEndingLabel(), BorderLayout.NORTH);

		JPanel conBodyPanel = makeConBodyPanel(screen, timeField);

		conMainPanel.add(conBodyPanel, BorderLayout.CENTER);

		return conMainPanel;
	}

	private static JPanel makeTimePanel(JTextField timeField){
		JPanel timePanel = new JPanel();
		timePanel.setLayout(new BoxLayout(timePanel, BoxLayout.X_AXIS));
		JLabel timeLabel = new JLabel("Time limit: ");
		timePanel.add(timeLabel);
		timePanel.add(timeField);
		timePanel.setAlignmentX(CENTER_ALIGNMENT);
		return timePanel;
	}

	private static JPanel makeConLabelsPanel(){
		JPanel conLabelsPanel = new JPanel();
		conLabelsPanel.setLayout(
				new BoxLayout(conLabelsPanel, BoxLayout.X_AXIS)
				);
		conLabelsPanel.add(Box.createHorizontalGlue());
		conLabelsPanel.add(makeAgentTypeLabel());
		conLabelsPanel.add(makeValueLabel());
		conLabelsPanel.add(Box.createHorizontalGlue());
		return conLabelsPanel;
	}

	private static JPanel makeConListPanel(JButton addConditionButton){
		JPanel conListPanel = new JPanel();
		conListPanel.setLayout(
				new BoxLayout(conListPanel, BoxLayout.Y_AXIS)
				);
		conListPanel.add(addConditionButton);
		conListPanel.add(Box.createVerticalGlue());
		return conListPanel;
	}

	private static JPanel makeConBodyPanel(JPanel conListPanel, JTextField timeField){
		JPanel conBodyPanel = new JPanel();
		conBodyPanel.setLayout(
				new BoxLayout(conBodyPanel, BoxLayout.Y_AXIS)
				);

		JPanel timePanel = makeTimePanel(timeField);

		JPanel conLabelsPanel = makeConLabelsPanel();

		conBodyPanel.add(timePanel);
		conBodyPanel.add(conLabelsPanel);
		conBodyPanel.add(conListPanel);

		return conBodyPanel;
	}

	private static JPanel makeUberPanel(SetupScreen screen, JTextField timeField, JTextField nameField, JComboBox updateBox){
		JPanel uberPanel = new JPanel();
		uberPanel.setLayout(new BoxLayout(uberPanel, BoxLayout.Y_AXIS));

		JPanel mainPanel = makeMainPanel(makeNameLabel(), nameField, makeUpdateLabel(), updateBox );
		uberPanel.add(mainPanel);

		JPanel conMainPanel = makeConMainPanel(screen, timeField);
		uberPanel.add(conMainPanel);
		return uberPanel;
	}

	private static JPanel makeButtonPanel(final ScreenManager sm, final JTextField nameField, final JTextField timeField, final JComboBox updateBox, ArrayList<JComboBox> agentTypes, ArrayList<JTextField> values){
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(makeBackButton(sm));
		buttonPanel.add( makeFinishButton(sm, nameField, timeField, updateBox, values, agentTypes) );
		return buttonPanel;
	}

	private static JButton makeBackButton(final ScreenManager sm){
		JButton backButton = new JButton("Back");
		backButton.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						sm.update(sm.getScreen("Edit Simulation"));
					}
				}
				);
		return backButton;
	}

	private static JButton makeFinishButton(final ScreenManager sm, final JTextField nameField, final JTextField timeField, final JComboBox updateBox, final ArrayList<JTextField> values, final ArrayList<JComboBox> agentTypes){
		JButton finishButton = new JButton("Finish");
		finishButton.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							if (nameField.getText().equals("")) {
								throw new Exception("All fields must have input");
							}
							//							if (Integer.parseInt(width.getText()) < 1 || Integer.parseInt(height.getText()) < 1) {
							//								throw new Exception("Width and height must be greater than 0");
							//							}
							for (int i = 0; i < values.size(); i++){
								if (values.get(i).getText().equals("")) {
									throw new Exception("All fields must have input.");
								}
							}
							//							sm.updateGUIManager(nameField.getText(), Integer.parseInt(width.getText()), Integer.parseInt(height.getText()));

							sm.getEnder().setStepLimit(Integer.parseInt(timeField.getText()));
							String str = (String)updateBox.getSelectedItem();
							if (str.equals("Linear")) {
								sm.getFacade().setLinearUpdate();
							}
							else if (str.equals("Atomic")) {
								sm.getFacade().setLinearUpdate();
							}
							else {
								sm.getFacade().setPriorityUpdate();
							}
							for (int i = 0; i < values.size(); i++) {
								sm.getEnder().setPopLimit(
										sm.getFacade().getPrototype(
												(String)(agentTypes.get(i).getSelectedItem())
												).getPrototypeID(), 
												Integer.parseInt(values.get(i).getText())
										);
							}
							sm.update(sm.getScreen("Edit Simulation"));
						}
						catch (NumberFormatException excep) {
							JOptionPane.showMessageDialog(null,
									"Width and Height fields must be integers greater than 0");
							excep.printStackTrace();
						}
						catch (Exception excep) {
							JOptionPane.showMessageDialog(null, excep.getMessage());
						} 
					}
				}
				);
		return finishButton;
	}

	private static JPanel makeMainPanel(JLabel nameLabel, JTextField nameField, JLabel updateLabel, JComboBox updateBox ){
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setAlignmentX(CENTER_ALIGNMENT);

		mainPanel.add( makePanel1(nameLabel,nameField) );
		//		mainPanel.add(panel2);
		mainPanel.add( makePanel3(updateLabel,updateBox) );

		return mainPanel;
	}

	private static JPanel makePanel1(JLabel nameLabel, JTextField nameField){
		JPanel panel1 = new JPanel();
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));
		panel1.add(nameLabel);
		panel1.add(nameField);
		return panel1;
	}

	private static JPanel makePanel3(JLabel updateLabel, JComboBox updateBox){
		JPanel panel3 = new JPanel();
		panel3.setLayout(new BoxLayout(panel3, BoxLayout.X_AXIS));
		panel3.add(updateLabel);
		panel3.add(updateBox);
		return panel3;
	}

	@Override
	public void load() {
		reset();
		nameField.setText(GUI.getNameOfSim());
		//		width.setText(GUI.getGridWidth() + "");
		//		height.setText(GUI.getGridHeight() + "");
		//		if (sm.hasStarted()) {
		//			width.setEditable(false);
		//			height.setEditable(false);
		//		}
		updateBox.setSelectedItem(sm.getFacade().currentUpdater());

		SimulationEnder se = sm.getEnder();
		Set<String> agents = sm.getFacade().prototypeNames();
		agentNames = agents.toArray(agentNames);
		timeField.setText(se.getStepLimit() + "");
		//to prevent accidental starting simulation with time limit of 0
		if (se.getStepLimit() <= 0) {
			timeField.setText(10 + "");
		}
		ImmutableMap<PrototypeID, Integer> popLimits = se.getPopLimits();
		if (popLimits.size() == 0) {
			conListPanel.add(addConditionButton);
			conListPanel.add(glue);

		}
		else {
			int i = 0;
			for (PrototypeID p : popLimits.keySet()) {
				addCondition();
				for (String s : agentNames) {
					if (sm.getFacade().getPrototype(s).getPrototypeID().equals(p)) {
						agentTypes.get(i).setSelectedItem(s);
					}
				}
				values.get(i).setText(popLimits.get(p) + "");
				i++;
			}
		}
		validate();

	}


	private void addCondition() {
		JPanel newPanel = new JPanel();
		newPanel.setLayout(
				new BoxLayout(newPanel, 
						BoxLayout.X_AXIS)
				);
		JComboBox newBox = new JComboBox(agentNames);
		newBox.setMaximumSize(new Dimension(300, 40));
		agentTypes.add(newBox);
		//		JComboBox newOps = new JComboBox(opNames);
		//		newOps.setMaximumSize(new Dimension(200, 40));
		//		operations.add(newOps);
		JTextField newValue = new JTextField(25);
		newValue.setMaximumSize(new Dimension(300, 40));
		values.add(newValue);
		JButton newButton = new JButton("Delete");
		newButton.addActionListener(new DeleteListener());
		deleteButtons.add(newButton);
		newButton.setActionCommand(deleteButtons.indexOf(newButton) + "");
		System.out.println(deleteButtons.indexOf(newButton) + "");
		newPanel.add(newBox);
		//newPanel.add(newOps);
		newPanel.add(newValue);
		newPanel.add(newButton);
		subPanels.add(newPanel);
		conListPanel.add(newPanel);
		conListPanel.add(addConditionButton);
		conListPanel.add(glue);
		conListPanel.validate();
		validate();	
	}

	private void reset() {
		nameField.setText("");
		//		width.setText("");
		//		height.setText("");
		conListPanel.removeAll();
		agentTypes.clear();
		values.clear();
		//operations.clear();
		deleteButtons.clear();
		subPanels.clear();
	}

	private class DeleteListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e){
			int n = Integer.parseInt(e.getActionCommand());
			String str = (String) agentTypes.get(n).getSelectedItem();
			if (str != null) {
				sm.getEnder().removePopLimit(
						sm.getFacade().getPrototype(str).getPrototypeID());
			}
			conListPanel.remove(subPanels.get(n));
			agentTypes.remove(n);
			values.remove(n);
			//operations.remove(n);
			deleteButtons.remove(n);
			for (int i = n; i < deleteButtons.size(); i++) {
				deleteButtons.get(i).setActionCommand(
						(Integer.parseInt(deleteButtons.get(i).getActionCommand()) - 1) + ""
						);
			}
			subPanels.remove(n);
			validate();
			repaint();
		}

	}
}
