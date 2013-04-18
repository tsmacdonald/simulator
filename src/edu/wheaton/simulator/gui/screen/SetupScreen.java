package edu.wheaton.simulator.gui.screen;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import com.google.common.collect.ImmutableMap;

import edu.wheaton.simulator.gui.BoxLayoutAxis;
import edu.wheaton.simulator.gui.Gui;
import edu.wheaton.simulator.gui.HorizontalAlignment;
import edu.wheaton.simulator.gui.MaxSize;
import edu.wheaton.simulator.gui.MinSize;
import edu.wheaton.simulator.gui.PrefSize;
import edu.wheaton.simulator.gui.SimulatorGuiManager;
import edu.wheaton.simulator.simulation.Simulator;

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

		this.add(
				Gui.makeLabel("Simulation Options",new PrefSize(300,150),HorizontalAlignment.CENTER ),
				BorderLayout.NORTH
				);

		agentTypes = new ArrayList<JComboBox>();
		deleteButtons = new ArrayList<JButton>();
		subPanels = new ArrayList<JPanel>();

		addConditionButton = Gui.makeButton("Add Field",null,
				new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addCondition();
			}
		});

		conListPanel = Gui.makePanel(BoxLayoutAxis.Y_AXIS,null,null);
		conListPanel.add(addConditionButton);
		conListPanel.add(Box.createVerticalGlue());

		nameField = Gui.makeTextField(gm.getSimName(), 25,new MaxSize(400,30),MinSize.NULL);

		timeField = Gui.makeTextField(null,15,new MaxSize(200,40),MinSize.NULL);

		String[] updateTypes = {"Linear", "Atomic", "Priority"};
		updateBox = Gui.makeComboBox(updateTypes, new MaxSize(200,40));

		this.add(makeUberPanel(conListPanel, timeField, nameField, updateBox),
				BorderLayout.CENTER);

		agentTypes = new ArrayList<JComboBox>();
		values = new ArrayList<JTextField>();

		this.add(
				Gui.makePanel(
						Gui.makeButton("Revert",null,new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								load();
							}}),
							makeConfirmButton()
						), BorderLayout.SOUTH
				);
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

		uberPanel.add(makeMainPanel(
				Gui.makeLabel("Name: ",new MaxSize(100,40), HorizontalAlignment.RIGHT),
				nameField,
				Gui.makeLabel("Update type: ",new MaxSize(100,40),null),
				updateBox 
				));

		JPanel conMainPanel = Gui.makePanel(new BorderLayout(),MaxSize.NULL,PrefSize.NULL);
		conMainPanel.add(
				Gui.makeLabel("Ending Conditions",new PrefSize(300,100),HorizontalAlignment.CENTER ),
				BorderLayout.NORTH
				);

		conMainPanel.add(
				makeConBodyPanel(conListPanel, timeField), 
				BorderLayout.CENTER
				);

		uberPanel.add(conMainPanel);
		return uberPanel;
	}

	private JButton makeConfirmButton(){
		return Gui.makeButton("Confirm",null,new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					SimulatorGuiManager gm = getGuiManager();
					if (nameField.getText().equals(""))
						throw new Exception("All fields must have input");
					gm.setSimName(nameField.getText());

					for (int i = 0; i < values.size(); i++)
						if (values.get(i).getText().equals(""))
							throw new Exception("All fields must have input.");

					gm.setSimStepLimit(Integer.parseInt(timeField.getText()));
					String str = (String)updateBox.getSelectedItem();

					if (str.equals("Linear"))
						gm.setSimLinearUpdate();
					else if (str.equals("Atomic"))
						gm.setSimAtomicUpdate();
					else
						gm.setSimPriorityUpdate(0, 50);

					for (int i = 0; i < values.size(); i++) {
						gm.setSimPopLimit(
								(String)(agentTypes.get(i).getSelectedItem()), 
								Integer.parseInt(values.get(i).getText())
								);
					}
					load();
				}
				catch (NumberFormatException excep) {
					JOptionPane.showMessageDialog(null,
							"Width and Height fields must be integers greater than 0");
				}
				catch (Exception excep) {
					JOptionPane.showMessageDialog(null, excep.getMessage());
				} 
			}
		});
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
		nameField.setText(getGuiManager().getSimName());
		updateBox.setSelectedItem(getGuiManager().getCurrentSimUpdater());

		SimulatorGuiManager gm = getGuiManager();
		int stepLimit = gm.getSimStepLimit();
		agentNames = Simulator.prototypeNames().toArray(agentNames);
		timeField.setText(stepLimit + "");
		//to prevent accidental starting simulation with time limit of 0
		if (stepLimit <= 0) 
			timeField.setText(10 + "");

		ImmutableMap<String, Integer> popLimits = gm.getSimPopLimits();

		if (popLimits.size() == 0) {
			conListPanel.add(addConditionButton);
			conListPanel.add(Box.createVerticalGlue());
		}
		else {
			int i = 0;
			for (String p : popLimits.keySet()) {
				addCondition();
				agentTypes.get(i).setSelectedItem(p);

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

		JButton newButton = Gui.makeButton("Delete",null,new DeleteListener());
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
				getGuiManager().removeSimPopLimit(str);

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