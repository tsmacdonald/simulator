package edu.wheaton.simulator.gui.screen;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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

//TODO add components for resizing grid
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
		this.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		this.add(new JLabel("Name: "), c);

		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 3;
		nameField = Gui.makeTextField(gm.getSimName(), 25,new MaxSize(400,30),new MinSize(250,30));
		this.add(nameField, c);
		
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 2;
		JLabel updateLabel = Gui.makeLabel("Update type: ",new MaxSize(100,40),HorizontalAlignment.RIGHT);
		this.add(updateLabel,c);
		
		c.gridx = 2;
		c.gridy = 2;
		c.gridwidth = 2;
		String[] updateTypes = {"Linear", "Atomic", "Priority"};
		updateBox = Gui.makeComboBox(updateTypes, new MaxSize(200,40));
		this.add(updateBox,c);
		
		c.gridx = 1;
		c.gridy = 3;
		JLabel conHeader = Gui.makeLabel("Ending Conditions",new PrefSize(300,100),HorizontalAlignment.CENTER );
		this.add(conHeader,c);
		
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 1;
		JLabel timeLabel = new JLabel("Time limit: ");
		this.add(timeLabel,c);
		
		c.gridx = 1;
		c.gridy = 4;
		c.gridwidth = 3;
		timeField = Gui.makeTextField(null,15,new MaxSize(200,40),new MinSize(100,30));
		this.add(timeField,c);
		
		c.gridx = 0;
		c.gridy = 6;
		c.gridwidth = 4;
		c.gridheight = GridBagConstraints.REMAINDER;
		c.weighty = 1.0;
		conListPanel = Gui.makePanel(BoxLayoutAxis.Y_AXIS,null,null);
		this.add(conListPanel,c);
		
		c.gridx = 0;
		c.gridy = 6;
		JLabel agentTypeLabel = Gui.makeLabel("Agent Type",new PrefSize(200,30),HorizontalAlignment.LEFT);
		conListPanel.add(agentTypeLabel,c);
		
		c.gridx = 1;
		c.gridy = 6;
		JLabel valueLabel = Gui.makeLabel("Population Limit",new PrefSize(400,30),HorizontalAlignment.CENTER);
		conListPanel.add(valueLabel,c);
		
		c.gridx = 2;
		c.gridy = 6;
		addConditionButton = Gui.makeButton("Add Field",null,
				new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addCondition();
			}
		});
		
		c.gridwidth = 3;
		c.gridheight = 1;
		c.anchor = GridBagConstraints.PAGE_END;
		c.gridx = 2;
		c.gridy = 8;
		this.add(
			Gui.makePanel(
					Gui.makeButton("Revert",null,new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							load();
						}}),
						makeConfirmButton()
					), c);
		
		conListPanel.add(Gui.makePanel(addConditionButton),c);

		agentNames = new String[0];

		agentTypes = new ArrayList<JComboBox>();
		deleteButtons = new ArrayList<JButton>();
		subPanels = new ArrayList<JPanel>();

		agentTypes = new ArrayList<JComboBox>();
		values = new ArrayList<JTextField>();
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