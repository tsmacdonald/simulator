package edu.wheaton.simulator.gui.screen;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import edu.wheaton.simulator.gui.Gui;
import edu.wheaton.simulator.gui.GuiConstants;
import edu.wheaton.simulator.gui.PrefSize;
import edu.wheaton.simulator.gui.ScreenManager;
import edu.wheaton.simulator.gui.SimulatorFacade;

public class LayerScreen extends Screen {

	private static final long serialVersionUID = -3839942858274589928L;

	private JComboBox agentComboBox;

	private JComboBox layerComboBox;

	private String[] entities;

	private JPanel agentsCBpanel;

	private JPanel fieldsCBpanel;
	
	private GridBagConstraints c;
	
	public LayerScreen(SimulatorFacade guiManager) {
		super(guiManager);
		entities = new String[0];
		
		JLabel agents = new JLabel("Agents");
		agentComboBox = Gui.makeComboBox(null, null);
		agentComboBox.setMinimumSize(GuiConstants.minComboBoxSize);

		JLabel layers = new JLabel("Fields");
		layerComboBox = Gui.makeComboBox(null, null);
		layerComboBox.setMinimumSize(GuiConstants.minComboBoxSize);

		final JColorChooser colorTool = Gui.makeColorChooser();

		JButton apply = Gui.makeButton("Apply", PrefSize.NULL,
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent ae) {
						gm.displayLayer(layerComboBox.getSelectedItem().toString(), colorTool.getColor());
						gm.getGridPanel().validate();
					}
				});

		JButton clear = Gui.makeButton("Clear", PrefSize.NULL,
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent ae) {
						gm.clearLayer();
						gm.getGridPanel().validate();
						ScreenManager.getInstance().getScreen("View Simulation").validate();
					}
				});

		agentsCBpanel = new JPanel();
		fieldsCBpanel = new JPanel();
		JPanel colorPanel = Gui.makeColorChooserPanel(colorTool);
		JPanel mainPanel = new JPanel(new GridBagLayout());
		
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(20,5,2,5);
		mainPanel.add(agents,c);
		agentsCBpanel.add(agentComboBox);
		
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(2,5,2,5);
		mainPanel.add(layers,c);
		fieldsCBpanel.add(layerComboBox);
		
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		c.insets = new Insets(2,5,2,5);
		mainPanel.add(apply,c);
		
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 2;
		c.insets = new Insets(2,5,2,5);
		mainPanel.add(clear,c);


		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 0;
		c.insets = new Insets(20,5,2,5);
		mainPanel.add(agentsCBpanel, c);

		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 1;
		c.insets = new Insets(2,5,2,5);
		mainPanel.add(fieldsCBpanel, c);

		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridy = 3;
		c.insets = new Insets(10,10,10,10);
		c.gridwidth = 4;
		c.gridheight = 4;
		mainPanel.add(colorPanel, c);
		
		this.add(mainPanel);
		this.validate();
	}

	@Override
	public void load() {
		entities = new String[0];
		entities = gm.getPrototypeNames().toArray(entities);
		agentComboBox = new JComboBox(entities);
		agentComboBox.setMinimumSize(GuiConstants.minComboBoxSize);
		agentComboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				// To ensure type safety with the "String" combo box, we need
				// to convert the objects to strings.
				String[] fields = new String[0];
				fields = gm
						.getPrototype(
								agentComboBox.getSelectedItem().toString())
						.getCustomFieldMap().keySet().toArray(fields);
				

				layerComboBox = new JComboBox(fields);
				layerComboBox.setMinimumSize(GuiConstants.minComboBoxSize);
				fieldsCBpanel.removeAll();
				
				fieldsCBpanel.add(layerComboBox);
				validate();
				repaint();
			}
		});
		if (agentComboBox.getSelectedItem() != null) {

			// To ensure type safety with the "String" combo box, we need to
			// convert the objects to strings.
			String[] fields = new String[0];
			fields = gm
					.getPrototype(
							agentComboBox.getSelectedItem().toString())
					.getCustomFieldMap().keySet().toArray(fields);

			layerComboBox = new JComboBox(fields);
			layerComboBox.setMinimumSize(GuiConstants.minComboBoxSize);
			fieldsCBpanel.removeAll();
			fieldsCBpanel.add(layerComboBox);
		}
		else{
			layerComboBox.removeAllItems();
		}
		agentsCBpanel.removeAll();
		agentsCBpanel.add(agentComboBox);
	}

}
