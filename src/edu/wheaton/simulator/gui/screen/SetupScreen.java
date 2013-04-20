package edu.wheaton.simulator.gui.screen;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.google.common.collect.ImmutableMap;

import edu.wheaton.simulator.gui.BoxLayoutAxis;
import edu.wheaton.simulator.gui.Gui;
import edu.wheaton.simulator.gui.HorizontalAlignment;
import edu.wheaton.simulator.gui.MaxSize;
import edu.wheaton.simulator.gui.MinSize;
import edu.wheaton.simulator.gui.PrefSize;
import edu.wheaton.simulator.gui.SimulatorFacade;

//TODO add elements for step delay
public class SetupScreen extends Screen {

	private JTextField nameField;
	private JTextField timeField;

	private JTextField widthField;
	private JTextField heightField;
	
	private JTextField delayField;

	private String[] agentNames;
	private JComboBox updateBox;

	private ArrayList<JComboBox> agentTypes;
	private ArrayList<JTextField> values;
	private ArrayList<JButton> deleteButtons;
	private ArrayList<JPanel> subPanels;

	private JPanel conListPanel;
	private JButton addConditionButton;

	private static final long serialVersionUID = -8347080877399964861L;

	public SetupScreen(final SimulatorFacade gm) {
		super(gm);
		this.setLayout(new GridBagLayout());

		JPanel upperPanel = makeUpperPanel();
		upperPanel.setMinimumSize(new MinSize(420,114));
		
		JPanel lowerPanel = makeLowerPanel();
		lowerPanel.setMinimumSize(new MinSize(398,70));
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		this.add(upperPanel, c);

		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = c.RELATIVE;
		c.insets = new Insets(20,20,20,20);
		this.add(lowerPanel, c);

		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.PAGE_END;
		c.gridx =0;
		c.gridy = 8;
		this.add(
				Gui.makePanel(
						Gui.makeButton("Revert", null, new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								load();
							}
						}), makeConfirmButton()), c);

		agentNames = new String[0];

		agentTypes = new ArrayList<JComboBox>();
		deleteButtons = new ArrayList<JButton>();
		subPanels = new ArrayList<JPanel>();

		agentTypes = new ArrayList<JComboBox>();
		values = new ArrayList<JTextField>();
	}

	private JButton makeConfirmButton() {
		return Gui.makeButton("Confirm", null, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					SimulatorFacade gm = getGuiManager();

					int newWidth = Integer.parseInt(widthField.getText());
					int newHeight = Integer.parseInt(widthField.getText());
					int newTime = Integer.parseInt(timeField.getText());
					int newDelay = Integer.parseInt(delayField.getText());
					if (newWidth <= 0 || newHeight <= 0 || newTime <= 0 || newDelay <= 0)
						throw new NumberFormatException();
					if (newWidth < gm.getGridWidth()
							|| newHeight < gm.getGridHeight()) {
						int selection = JOptionPane
								.showConfirmDialog(
										null,
										"The new grid size you provided"
												+ "\nis smaller than its current value."
												+ "\nThis may result in the deletion of objects placed in the grid that"
												+ "\ncannot fit within these new dimensions."
												+ "\nFurthermore, agent data that depended on specific coordinates may"
												+ "\nneed to be checked for bugs after resizing."
												+ "\n\nIf you are sure you want to apply these changes, click 'Ok', otherwise click 'No' or 'Cancel'");
						if (selection == JOptionPane.YES_OPTION)
							gm.resizeGrid(newWidth, newHeight);
						else
							return;
					}

					if (nameField.getText().equals(""))
						throw new Exception("All fields must have input");
					gm.setName(nameField.getText());

					for (int i = 0; i < values.size(); i++)
						if (values.get(i).getText().equals(""))
							throw new Exception("All fields must have input.");

					gm.resizeGrid(newWidth, newHeight);

					gm.setStepLimit(newTime);
					
					gm.setSleepPeriod(newDelay);

					String str = (String) updateBox.getSelectedItem();

					if (str.equals("Linear"))
						gm.setLinearUpdate();
					else if (str.equals("Atomic"))
						gm.setAtomicUpdate();
					else
						gm.setPriorityUpdate(0, 50);

					for (int i = 0; i < values.size(); i++) {
						gm.setPopLimit(
								(String) (agentTypes.get(i).getSelectedItem()),
								Integer.parseInt(values.get(i).getText()));
					}
					load();
				} catch (NumberFormatException excep) {
					JOptionPane
							.showMessageDialog(null,
									"Width, Height, and Time fields must be integers greater than 0");
				} catch (Exception excep) {
					JOptionPane.showMessageDialog(null, excep.getMessage());
				}
			}
		});
	}

	@Override
	public void load() {
		reset();
		nameField.setText(getGuiManager().getSimName());
		updateBox.setSelectedItem(getGuiManager().getCurrentUpdater());
		widthField.setText(gm.getGridWidth().toString());
		heightField.setText(gm.getGridHeight().toString());
		delayField.setText(gm.getSleepPeriod().toString());

		SimulatorFacade gm = getGuiManager();
		int stepLimit = gm.getStepLimit();
		agentNames = gm.getPrototypeNames().toArray(agentNames);
		timeField.setText(stepLimit + "");
		// to prevent accidental starting simulation with time limit of 0
		if (stepLimit <= 0)
			timeField.setText(10 + "");

		ImmutableMap<String, Integer> popLimits = gm.getPopLimits();

		if (popLimits.size() == 0) {
			conListPanel.add(addConditionButton);
		} else {
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

	private JPanel makeUpperPanel() {
		JPanel upperPanel = Gui.makePanel(new GridBagLayout(), MaxSize.NULL,
				PrefSize.NULL, (Component[]) null);

		JLabel nameLabel = Gui.makeLabel("Name:", MaxSize.NULL,
				null);
		
		nameField = Gui.makeTextField(gm.getSimName(), 25,
				MaxSize.NULL, MinSize.NULL);
		
		JLabel widthLabel = Gui.makeLabel("Width:", MaxSize.NULL,
				null);
		
		widthField = Gui.makeTextField("10", 5, MaxSize.NULL,
				MinSize.NULL);
		
		JLabel yLabel = Gui.makeLabel("Height:", MaxSize.NULL,
				null);
		
		heightField = Gui.makeTextField("10", 5, MaxSize.NULL,
				MinSize.NULL);
		
		JLabel updateLabel = Gui.makeLabel("Update type:", MaxSize.NULL, null);
		
		updateBox = Gui.makeComboBox(new String[] { "Linear", "Atomic",
				"Priority" }, MaxSize.NULL);
		
		//TODO working on adding step delay components
		JLabel delayLabel = Gui.makeLabel("Step delay:", MaxSize.NULL, null);
		
		delayField = Gui.makeTextField("1.0", 5, MaxSize.NULL,
				MinSize.NULL);

		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(0, 0, 0, 0);
		upperPanel.add(nameLabel, c);

		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.fill = c.HORIZONTAL;
		c.insets = new Insets(0, 0, 0, 0);
		upperPanel.add(nameField, c);

		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(0, 0, 0, 0);
		upperPanel.add(widthLabel, c);

		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 1;
		upperPanel.add(widthField, c);

		c = new GridBagConstraints();
		c.gridx = 2;
		c.gridy = 1;
		c.insets = new Insets(0, 0, 0, 0);
		upperPanel.add(yLabel, c);

		c = new GridBagConstraints();
		c.gridx = 3;
		c.gridy = 1;
		c.insets = new Insets(0, 0, 0, 0);
		upperPanel.add(heightField, c);

		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 2;
		upperPanel.add(delayLabel, c);
		
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.fill = c.HORIZONTAL;
		upperPanel.add(delayField, c);
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 3;
		upperPanel.add(updateLabel, c);

		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.fill = c.HORIZONTAL;
		upperPanel.add(updateBox, c);
		
		

		upperPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		return upperPanel;
	}

	private JPanel makeLowerPanel() {
		JPanel lowerPanel = Gui.makePanel(new GridBagLayout(), MaxSize.NULL,
				PrefSize.NULL, (Component[]) null);

		JLabel conHeader = Gui.makeLabel("Ending Conditions", MaxSize.NULL, null);
		JLabel timeLabel = Gui.makeLabel("Time Limit", MaxSize.NULL,
				null);

		timeField = Gui.makeTextField(null, 15, MaxSize.NULL,
				MinSize.NULL);

		JLabel agentTypeLabel = Gui.makeLabel("Agent Type", MaxSize.NULL, null);
		JLabel valueLabel = Gui.makeLabel("Population Limit", MaxSize.NULL, null);
		
		conListPanel = makeConditionListPanel();

		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 1;
		c.gridwidth = 3;
		c.gridy = 0;
		c.insets = new Insets(0, 0, 0, 0);
		lowerPanel.add(conHeader, c);

		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(0, 0, 0, 0);
		lowerPanel.add(timeLabel, c);

		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 1;
		c.insets = new Insets(0, 0, 0, 0);
		lowerPanel.add(timeField, c);

		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 2;
		c.insets = new Insets(0, 0, 0, 0);
		lowerPanel.add(agentTypeLabel, c);

		c = new GridBagConstraints();
		c.gridx = 2;
		c.gridy = 2;
		c.insets = new Insets(0, 0, 0, 0);
		lowerPanel.add(valueLabel, c);
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 4;
		this.add(conListPanel, c);

		lowerPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		return lowerPanel;
	}

	private JPanel makeConditionListPanel() {
		JPanel conListPanel = Gui.makePanel((BoxLayoutAxis)null, null, null);

		addConditionButton = Gui.makeButton("Add Field", null,
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						addCondition();
					}
				});

		conListPanel.add(addConditionButton);
		
		return conListPanel;
	}

	private void addCondition() {
		JComboBox newBox = Gui.makeComboBox(agentNames, MaxSize.NULL);
		agentTypes.add(newBox);

		JTextField newValue = Gui.makeTextField(null, 25,
				MaxSize.NULL, MinSize.NULL);
		values.add(newValue);

		JButton newButton = Gui.makeButton("Delete", null,
				new DeleteListener());
		deleteButtons.add(newButton);
		newButton.setActionCommand(deleteButtons.indexOf(newButton) + "");

		JPanel newPanel = Gui.makePanel(newBox,newValue,newButton);
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
		public void actionPerformed(ActionEvent e) {
			int n = Integer.parseInt(e.getActionCommand());
			String str = (String) agentTypes.get(n).getSelectedItem();

			if (str != null)
				getGuiManager().removePopLimit(str);

			conListPanel.remove(subPanels.get(n));
			agentTypes.remove(n);
			values.remove(n);
			deleteButtons.remove(n);

			for (int i = n; i < deleteButtons.size(); i++)
				deleteButtons.get(i).setActionCommand(
						(Integer.parseInt(deleteButtons.get(i)
								.getActionCommand()) - 1) + "");

			subPanels.remove(n);
			validate();
			repaint();
		}
	}
}
