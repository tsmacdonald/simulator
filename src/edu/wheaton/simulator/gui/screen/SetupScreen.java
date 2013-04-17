package edu.wheaton.simulator.gui.screen;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Set;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import com.google.common.collect.ImmutableMap;

import edu.wheaton.simulator.entity.PrototypeID;
import edu.wheaton.simulator.gui.BoxLayoutAxis;
import edu.wheaton.simulator.gui.Gui;
import edu.wheaton.simulator.gui.HorizontalAlignment;
import edu.wheaton.simulator.gui.MaxSize;
import edu.wheaton.simulator.gui.MinSize;
import edu.wheaton.simulator.gui.PrefSize;
import edu.wheaton.simulator.gui.ScreenManager;
import edu.wheaton.simulator.gui.SimulatorGuiManager;
import edu.wheaton.simulator.simulation.Simulator;
import edu.wheaton.simulator.simulation.end.SimulationEnder;

//TODO commented code for adding operators to ending conditions :
//     see if it should stay for future use or just be deleted
//TODO commented out code for changing width and height of grid :
//     causing too many problems and not providing any value atm.
public class SetupScreen extends Screen {

	private JTextField nameField;

	private JTextField timeField;

	private String[] agentNames;

	private JComboBox updateBox;

	private ArrayList<JComboBox> agentTypes;

	private ArrayList<JTextField> values;

	private ArrayList<JButton> deleteButtons;

	private ArrayList<JPanel> subPanels;

	private JPanel conListPanel;

	private JButton addConditionButton;

	private static final long serialVersionUID = -8347080877399964861L;

	public SetupScreen(final SimulatorGuiManager gm) {
		super(gm);
		this.setLayout(new BorderLayout());
		
		agentNames = new String[0];
		
		JLabel windowLabel = Gui.makeLabel("Simulation Setup",new PrefSize(300,150),HorizontalAlignment.CENTER );
		this.add(windowLabel, BorderLayout.NORTH);
		
		agentTypes = new ArrayList<JComboBox>();
		
		deleteButtons = new ArrayList<JButton>();
		
		subPanels = new ArrayList<JPanel>();
		
		addConditionButton = makeAddConditionButton(this);
		
		conListPanel = Gui.makePanel(BoxLayoutAxis.Y_AXIS,null,null);
		conListPanel.add(addConditionButton);
		conListPanel.add(Box.createVerticalGlue());
		
		nameField = Gui.makeTextField(SimulatorGuiManager.getGUIname(), 25,new MaxSize(400,30),MinSize.NULL);
		
		timeField = Gui.makeTextField(null,15,new MaxSize(200,40),MinSize.NULL);
		
		String[] updateTypes = {"Linear", "Atomic", "Priority"};
		updateBox = Gui.makeComboBox(updateTypes, new MaxSize(200,40));
		
		JPanel uberPanel = makeUberPanel(conListPanel, timeField, nameField, updateBox);
		this.add(uberPanel, BorderLayout.CENTER);
		
		agentTypes = new ArrayList<JComboBox>();
		
		values = new ArrayList<JTextField>();
		
		JPanel buttonPanel = new JPanel();
		
		JButton backButton = Gui.makeButton("Back",new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ScreenManager sm = getScreenManager();
				sm.update(sm.getScreen("View Simulation"));
			}
		});
		buttonPanel.add(backButton);
		
		buttonPanel.add( makeFinishButton(gm, nameField, timeField, updateBox, values, agentTypes) );
		
		this.add(buttonPanel, BorderLayout.SOUTH);
	}

	private static JButton makeAddConditionButton(final SetupScreen screen){
		JButton addConditionButton = Gui.makeButton("Add Field",new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						screen.addCondition();
					}
				});
		return addConditionButton;
	}

	private static JPanel makeConBodyPanel(JPanel conListPanel, JTextField timeField){
		JPanel conBodyPanel = Gui.makePanel(BoxLayoutAxis.Y_AXIS,null,null);

		JPanel timePanel = Gui.makePanel(BoxLayoutAxis.X_AXIS,null,null);
		JLabel timeLabel = new JLabel("Time limit: ");
		timePanel.add(timeLabel);
		timePanel.add(timeField);
		timePanel.setAlignmentX(CENTER_ALIGNMENT);

		JPanel conLabelsPanel = Gui.makePanel(BoxLayoutAxis.X_AXIS,null,null);
		conLabelsPanel.add(Box.createHorizontalGlue());
		
		JLabel agentTypeLabel = Gui.makeLabel("Agent Type",new PrefSize(200,30),HorizontalAlignment.LEFT);
		agentTypeLabel.setAlignmentX(LEFT_ALIGNMENT);
		conLabelsPanel.add(agentTypeLabel);
		
		JLabel valueLabel = Gui.makeLabel("Population Limit",new PrefSize(400,30),HorizontalAlignment.CENTER);
		conLabelsPanel.add(valueLabel);
		
		conLabelsPanel.add(Box.createHorizontalGlue());

		conBodyPanel.add(timePanel);
		conBodyPanel.add(conLabelsPanel);
		conBodyPanel.add(conListPanel);

		return conBodyPanel;
	}

	private static JPanel makeUberPanel(JPanel conListPanel, JTextField timeField, JTextField nameField, JComboBox updateBox){
		JPanel uberPanel = Gui.makePanel(BoxLayoutAxis.Y_AXIS,null,null);

		JLabel nameLabel = Gui.makeLabel("Name: ",new MaxSize(100,40), HorizontalAlignment.RIGHT);
		JLabel updateLabel = Gui.makeLabel("Update type: ",new MaxSize(100,40),null);
		
		JPanel mainPanel = makeMainPanel(nameLabel, nameField, updateLabel, updateBox );
		uberPanel.add(mainPanel);

		JLabel endingLabel = Gui.makeLabel("Ending Conditions",new PrefSize(300,100),HorizontalAlignment.CENTER );
		
		JPanel conMainPanel = Gui.makePanel(new BorderLayout(),MaxSize.NULL,PrefSize.NULL);
		conMainPanel.add(endingLabel, BorderLayout.NORTH);

		JPanel conBodyPanel = makeConBodyPanel(conListPanel, timeField);
		conMainPanel.add(conBodyPanel, BorderLayout.CENTER);
		
		uberPanel.add(conMainPanel);
		return uberPanel;
	}

	private static JButton makeFinishButton(final SimulatorGuiManager guiManager, final JTextField nameField, final JTextField timeField, final JComboBox updateBox, final ArrayList<JTextField> values, final ArrayList<JComboBox> agentTypes){
		JButton finishButton = Gui.makeButton("Finish",new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							if (nameField.getText().equals(""))
								throw new Exception("All fields must have input");
							
							for (int i = 0; i < values.size(); i++)
								if (values.get(i).getText().equals(""))
									throw new Exception("All fields must have input.");

							guiManager.getEnder().setStepLimit(Integer.parseInt(timeField.getText()));
							String str = (String)updateBox.getSelectedItem();
							
							if (str.equals("Linear"))
								guiManager.getFacade().setLinearUpdate();
							else if (str.equals("Atomic"))
								guiManager.getFacade().setLinearUpdate();
							else
								guiManager.getFacade().setPriorityUpdate(0, 50);

							for (int i = 0; i < values.size(); i++) {
								guiManager.getEnder().setPopLimit(
										Simulator.getPrototype(
												(String)(agentTypes.get(i).getSelectedItem())
												).getPrototypeID(), 
												Integer.parseInt(values.get(i).getText())
										);
							}
							guiManager.getScreenManager().update(guiManager.getScreenManager().getScreen("View Simulation"));
						}
						catch (NumberFormatException excep) {
							JOptionPane.showMessageDialog(null,
									"Width and Height fields must be integers greater than 0");
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
		JPanel mainPanel = Gui.makePanel(BoxLayoutAxis.Y_AXIS,null,null);
		mainPanel.setAlignmentX(CENTER_ALIGNMENT);
		
		JPanel panel1 = Gui.makePanel(BoxLayoutAxis.X_AXIS,null,null);
		panel1.add(nameLabel);
		panel1.add(nameField);
		mainPanel.add(panel1);
		
		JPanel panel3 = Gui.makePanel(BoxLayoutAxis.X_AXIS,null,null);
		panel3.add(updateLabel);
		panel3.add(updateBox);
		mainPanel.add(panel3);

		return mainPanel;
	}

	@Override
	public void load() {
		reset();
		nameField.setText(SimulatorGuiManager.getNameOfSim());
		updateBox.setSelectedItem(getGuiManager().getFacade().currentUpdater());

		SimulationEnder se = getGuiManager().getEnder();
		Set<String> agents = Simulator.prototypeNames();
		agentNames = agents.toArray(agentNames);
		timeField.setText(se.getStepLimit() + "");
		//to prevent accidental starting simulation with time limit of 0
		if (se.getStepLimit() <= 0) 
			timeField.setText(10 + "");
		
		ImmutableMap<PrototypeID, Integer> popLimits = se.getPopLimits();
		
		if (popLimits.size() == 0) {
			conListPanel.add(addConditionButton);
			conListPanel.add(Box.createVerticalGlue());
		}
		
		else {
			int i = 0;
			for (PrototypeID p : popLimits.keySet()) {
				addCondition();
				
				for (String s : agentNames) 
					if (Simulator.getPrototype(s).getPrototypeID().equals(p)) 
						agentTypes.get(i).setSelectedItem(s);

				values.get(i).setText(popLimits.get(p) + "");
				i++;
			}
		}
		validate();
	}

	private void addCondition() {
		JComboBox newBox = Gui.makeComboBox(agentNames,new MaxSize(300,40));
		agentTypes.add(newBox);
		
		JTextField newValue = Gui.makeTextField(null,25,new MaxSize(300,40),MinSize.NULL);
		values.add(newValue);
		
		JButton newButton = Gui.makeButton("Delete",new DeleteListener());
		newButton.setActionCommand(deleteButtons.indexOf(newButton) + "");
		deleteButtons.add(newButton);
		
		JPanel newPanel = Gui.makePanel( BoxLayoutAxis.X_AXIS,null,null);
		newPanel.add(newBox);
		newPanel.add(newValue);
		newPanel.add(newButton);
		subPanels.add(newPanel);
		
		conListPanel.add(newPanel);
		conListPanel.add(addConditionButton);
		conListPanel.add(Box.createVerticalGlue());
		conListPanel.validate();
		
		validate();	
	}

	private void reset() {
		nameField.setText("");
		conListPanel.removeAll();
		agentTypes.clear();
		values.clear();
		deleteButtons.clear();
		subPanels.clear();
	}

	private class DeleteListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e){
			int n = Integer.parseInt(e.getActionCommand());
			String str = (String) agentTypes.get(n).getSelectedItem();
			
			if (str != null) 
				getGuiManager().getEnder().removePopLimit(
						Simulator.getPrototype(str).getPrototypeID());
			
			conListPanel.remove(subPanels.get(n));
			agentTypes.remove(n);
			values.remove(n);
			deleteButtons.remove(n);
			
			for (int i = n; i < deleteButtons.size(); i++)
				deleteButtons.get(i).setActionCommand(
						(Integer.parseInt(deleteButtons.get(i).getActionCommand()) - 1) + "");
			
			subPanels.remove(n);
			validate();
			repaint();
		}
	}
}
