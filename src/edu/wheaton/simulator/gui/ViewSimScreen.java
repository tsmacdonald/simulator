/**
 * ViewSimScreen
 * 
 * Class representing the screen that displays the grid as
 * the simulation runs.
 * 
 * @author Willy McHie and Ian Walling
 * Wheaton College, CSCI 335, Spring 2013
 */

package edu.wheaton.simulator.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.sourceforge.jeval.EvaluationException;

import edu.wheaton.simulator.simulation.Simulator;
import edu.wheaton.simulator.simulation.SimulationPauseException;

public class ViewSimScreen extends Screen {

	private static final long serialVersionUID = -6872689283286800861L;

	private ScreenManager sm;

	private GridPanel grid;

	private int stepCount;

	private boolean canSpawn;

	private JComboBox agentComboBox;

	private JComboBox layerComboBox;

	private String[] entities;

	private JPanel layerPanelAgents;

	private JPanel layerPanelLayers;

	private GridBagConstraints c;

	private final EntityScreen entitiesScreen;

	private JTabbedPane tabs;

	public ViewSimScreen(final ScreenManager sm) {
		super(sm);
		canSpawn = true;
		entities = new String[0];
		this.setLayout(new GridBagLayout());
		this.sm = sm;
		stepCount = 0;
		JLabel label = new JLabel("View Simulation", SwingConstants.CENTER);

		JLabel agents = new JLabel("Agents", SwingConstants.CENTER);
		agentComboBox = GuiUtility.makeComboBox(null,new MaxSize(200,50));

		JLabel layers = new JLabel("Layers", SwingConstants.CENTER);
		layerComboBox = GuiUtility.makeComboBox(null, new MaxSize(200,50));

		final JColorChooser colorTool = GuiUtility.makeColorChooser();

		JButton apply = GuiUtility.makeButton("Apply",
				new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				sm.getFacade();
				Simulator.newLayer(layerComboBox.getSelectedItem().toString(), colorTool.getColor());
				try {
					sm.getFacade().setLayerExtremes();
				} catch (EvaluationException e) {
					e.printStackTrace();
				}
				grid.setLayers(true);
				grid.repaint();
			} 
		}
				);

		JButton clear = GuiUtility.makeButton("Clear",
				new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				grid.setLayers(false);
				grid.repaint();
			} 
		}
				);

		layerPanelAgents = GuiUtility.makePanel(BoxLayoutAxis.LINE_AXIS,null,null);
		layerPanelAgents.add(agents);
		layerPanelAgents.add(agentComboBox);

		layerPanelLayers = GuiUtility.makePanel(BoxLayoutAxis.LINE_AXIS,null,null);
		layerPanelLayers.add(layers);
		layerPanelLayers.add(layerComboBox);

		JPanel layerPanelButtons = GuiUtility.makePanel(BoxLayoutAxis.LINE_AXIS,null,null);
		layerPanelButtons.add(apply);
		layerPanelButtons.add(clear);

		JPanel colorPanel = GuiUtility.makeColorChooserPanel(colorTool);

		JPanel upperLayerPanel = new JPanel();
		upperLayerPanel.setLayout(new GridBagLayout());

		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridy = 0;
		upperLayerPanel.add(layerPanelAgents, c);

		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridy = 1;
		upperLayerPanel.add(layerPanelLayers, c);

		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridy = 2;
		upperLayerPanel.add(layerPanelButtons, c);

		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridy = 3;
		upperLayerPanel.add(colorPanel, c);
		upperLayerPanel.setAlignmentX(LEFT_ALIGNMENT);



		tabs = new JTabbedPane();
		entitiesScreen = (EntityScreen)sm.getScreen("Entities");
		tabs.add("Agent", entitiesScreen);
		tabs.add("Layers", upperLayerPanel);
		tabs.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent ce) {
				entitiesScreen.load();
			}

		});

		grid = new GridPanel(sm);
		grid.setPreferredSize(new Dimension(550,550));
		grid.addMouseListener(
				new MouseListener() {
					@Override
					public void mouseClicked(MouseEvent me) {
						if(canSpawn && entitiesScreen.getSelectedAgent() != ""){
							int x = me.getX();
							int y = me.getY();
							int height = grid.getHeight()/ScreenManager.getGUIheight();
							int width = grid.getWidth()/ScreenManager.getGUIwidth();
							int standardSize = Math.min(width, height);
							if(sm.getFacade().getGrid().emptyPos(x/standardSize, y/standardSize)){
								sm.getFacade().addAgent(entitiesScreen.getSelectedAgent(), x/standardSize, y/standardSize);
							}
							else{
								sm.getFacade().removeAgent(x/standardSize, y/standardSize);
							}
							grid.repaint();
						}
					}

					@Override
					public void mouseEntered(MouseEvent arg0) {}

					@Override
					public void mouseExited(MouseEvent arg0) {}

					@Override
					public void mousePressed(MouseEvent arg0) {}

					@Override
					public void mouseReleased(MouseEvent arg0) {}

				});


		JPanel layerPanel = GuiUtility.makePanel(BoxLayoutAxis.Y_AXIS,null,null);
		layerPanel.setAlignmentY(CENTER_ALIGNMENT);

		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.PAGE_START;
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 1;
		this.add(label, c);

		c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 2;
		c.weighty = 1;
		c.weightx = .5;
		c.insets = new Insets(1,1,1,1);
		this.add(tabs, c);

		c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 1;
		c.gridy = 1;
		c.ipadx = 600;
		c.ipady = 600;
		c.gridwidth = 2;
		c.weighty = 1;
		c.weightx = .7;
		c.insets = new Insets(1,1,1,1);
		this.add(grid, c);

		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 2;
		this.add(makeButtonPanel(), c);

		this.setVisible(true);	
	}

	private JPanel makeButtonPanel(){
		JPanel buttonPanel = GuiUtility.makePanel((LayoutManager)null,new MaxSize(500,50),PrefSize.NULL);
		buttonPanel.add(makeStartButton());

		buttonPanel.add(GuiUtility.makeButton("Pause",
				new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sm.setRunning(false);
				if(tabs.getSelectedIndex() != 0){
					canSpawn = false;
				}
				else {canSpawn = true;}
			}
		}
				));

		//TODO most of these will become tabs, adding temporarily for navigation purposes
		//	buttonPanel.add(GuiUtility.makeButton("Entities", new GeneralButtonListener("Entities", sm)));
		buttonPanel.add(GuiUtility.makeButton("Global Fields", new GeneralButtonListener("Fields", sm)));
		buttonPanel.add(GuiUtility.makeButton("Setup options", new GeneralButtonListener("Grid Setup", sm)));
		buttonPanel.add(GuiUtility.makeButton("Statistics", new GeneralButtonListener("Statistics", sm)));

		//Make new clear button
		
		return buttonPanel;
	}

	private JButton makeStartButton(){
		JButton b = GuiUtility.makeButton("Start/Resume",new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!sm.hasStarted()) {
					ArrayList<SpawnCondition> conditions = sm.getSpawnConditions();
					for (SpawnCondition condition: conditions) {
						condition.addToGrid(sm.getFacade());
						System.out.println("spawning a condition");
					}
				}
				grid.repaint();
				sm.setRunning(true);
				sm.setStarted(true);
				canSpawn = false;
				runSim();
			}
		}
				);
		return b;
	}

	private void runSim() {
		System.out.println("StepLimit = " + sm.getEnder().getStepLimit());
		//program loop yay!
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(sm.isRunning()) {
					try {
						sm.getFacade().updateEntities();
					} catch (SimulationPauseException e) {
						sm.setRunning(false);
						JOptionPane.showMessageDialog(null, e.getMessage());
						break;
					}
					boolean shouldEnd = sm.getEnder().evaluate(stepCount, 
							sm.getFacade().getGrid());
					System.out.println("shouldEnd = " + shouldEnd);
					if (shouldEnd) {
						sm.setRunning(false);
					}

					SwingUtilities.invokeLater(
							new Thread (new Runnable() {
								@Override
								public void run() {
									grid.repaint();
								}
							}));

					System.out.println(stepCount);
					try {
						System.out.println("Sleep!");
						Thread.sleep(500);
					} catch (InterruptedException e) {
						System.err.println("ViewSimScreen.java: 'Thread.sleep(500)' was interrupted");
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	@Override
	public void load() {
		sm.getFacade();
		entitiesScreen.load();
		entities = Simulator.prototypeNames().toArray(entities);
		agentComboBox = new JComboBox(entities);
		agentComboBox.addItemListener(
				new ItemListener() {
					@Override
					public void itemStateChanged(ItemEvent e) {
						sm.getFacade();
						layerComboBox = new JComboBox(Simulator.getPrototype
								(agentComboBox.getSelectedItem().toString())
								.getCustomFieldMap().keySet().toArray());
						layerComboBox.setMaximumSize(new Dimension(200, 50));
						layerPanelLayers.remove(1);
						layerPanelLayers.add(layerComboBox);
						validate();
						repaint();
					}
				}
				);
		if(entities.length != 0){
			sm.getFacade();
			layerComboBox = new JComboBox(Simulator.getPrototype
					(agentComboBox.getItemAt(0).toString())
					.getCustomFieldMap().keySet().toArray());
			layerComboBox.setMaximumSize(new Dimension(200, 50));
			layerPanelLayers.remove(1);
			layerPanelLayers.add(layerComboBox);
		}
		agentComboBox.setMaximumSize(new Dimension(200, 50));
		layerPanelAgents.remove(1);
		layerPanelAgents.add(agentComboBox);
		validate();
		grid.repaint();

	}
}
