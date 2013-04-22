package edu.wheaton.simulator.gui.screen;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import edu.wheaton.simulator.gui.Gui;
import edu.wheaton.simulator.gui.GuiConstants;
import edu.wheaton.simulator.gui.MaxSize;
import edu.wheaton.simulator.gui.MinSize;
import edu.wheaton.simulator.gui.PrefSize;
import edu.wheaton.simulator.gui.SimulatorFacade;

public class LayerScreen extends Screen {

	private static final long serialVersionUID = -3839942858274589928L;

	private JComboBox agentComboBox;

	private JComboBox layerComboBox;

	private String[] entities;

	private JPanel layerPanelAgents;

	private JPanel layerPanelLayers;
	
	private GridBagConstraints c;
	
	public LayerScreen(SimulatorFacade guiManager) {
		super(guiManager);
		this.setLayout(new GridBagLayout());
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
						gm.getGridPanel().repaint();
					}
				});

		JButton clear = Gui.makeButton("Clear", PrefSize.NULL,
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent ae) {
						//gm.getGridPanel().setLayers(true);
						gm.getGridPanel().validate();
						gm.getGridPanel().repaint();
					}
				});

		layerPanelAgents = Gui.makePanel(new GridBagLayout(), null, null);
		
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(5,5,5,5);
		layerPanelAgents.add(agents,c);
		
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 3;
		c.insets = new Insets(5,5,5,5);
		layerPanelAgents.add(agentComboBox,c);

		layerPanelLayers = Gui.makePanel(new GridBagLayout(), null, null);
		
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(5,5,5,5);
		layerPanelLayers.add(layers,c);
		
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 3;
		c.insets = new Insets(5,5,5,5);
		layerPanelLayers.add(layerComboBox,c);

		JPanel layerPanelButtons = Gui.makePanel(new GridBagLayout(),null, null);
		
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(5,5,5,5);
		layerPanelButtons.add(apply,c);
		
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 0;
		c.insets = new Insets(5,5,5,5);
		layerPanelButtons.add(clear,c);

		JPanel colorPanel = Gui.makeColorChooserPanel(colorTool);

		JPanel upperLayerPanel = new JPanel();
		upperLayerPanel.setLayout(new GridBagLayout());

		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridy = 0;
		c.insets = new Insets(5,5,5,5);
		upperLayerPanel.add(layerPanelAgents, c);

		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridy = 1;
		c.insets = new Insets(5,5,5,5);
		upperLayerPanel.add(layerPanelLayers, c);

		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridy = 2;
		c.insets = new Insets(5,5,5,5);
		upperLayerPanel.add(layerPanelButtons, c);

		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridy = 4;
		c.insets = new Insets(10,10,10,10);
		upperLayerPanel.add(colorPanel, c);
		
		c = new GridBagConstraints();
		c.insets = new Insets(30,10,10,10);
		this.add(upperLayerPanel,c);
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
				layerPanelLayers.remove(1);
				
				c = new GridBagConstraints();
				c.fill = GridBagConstraints.HORIZONTAL;
				c.gridx = 1;
				c.gridy = 0;
				c.gridwidth = 3;
				c.insets = new Insets(5,5,5,5);
				layerPanelLayers.add(layerComboBox,c);
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
			layerPanelLayers.remove(1);
			
			c = new GridBagConstraints();
			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridx = 1;
			c.gridy = 0;
			c.gridwidth = 3;
			c.insets = new Insets(5,5,5,5);
			layerPanelLayers.add(layerComboBox,c);
		}
		else{
			layerComboBox.removeAllItems();
		}
		layerPanelAgents.remove(1);
		
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 3;
		c.insets = new Insets(5,5,5,5);
		layerPanelAgents.add(agentComboBox,c);
	}

}
