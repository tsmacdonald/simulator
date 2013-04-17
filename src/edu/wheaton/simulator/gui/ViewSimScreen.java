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

import java.awt.BorderLayout;
import java.awt.Dimension;
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
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import net.sourceforge.jeval.EvaluationException;

import edu.wheaton.simulator.entity.Prototype;
import edu.wheaton.simulator.simulation.Simulator;
import edu.wheaton.simulator.simulation.SimulationPauseException;
import edu.wheaton.simulator.statistics.SimulationRecorder;

public class ViewSimScreen extends Screen {

	private static final long serialVersionUID = -6872689283286800861L;

	private ScreenManager sm;

	private GridPanel grid;

	private int stepCount;

	private long startTime;

	private boolean canSpawn;

	private SimulationRecorder gridRec;

	private JComboBox agentComboBox;

	private JComboBox layerComboBox;

	private String[] entities;

	private JPanel layerPanelAgents;

	private JPanel layerPanelLayers;

	public ViewSimScreen(final ScreenManager sm) {
		super(sm);
		canSpawn = true;
		entities = new String[0];
		this.setLayout(new BorderLayout());
		this.sm = sm;
		gridRec = new SimulationRecorder(sm.getStatManager());
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
					// TODO Auto-generated catch block
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

		JPanel upperLayerPanel = GuiUtility.makePanel(BoxLayoutAxis.PAGE_AXIS, null, null);
		upperLayerPanel.add(layerPanelAgents);
		upperLayerPanel.add(layerPanelLayers);
		upperLayerPanel.add(layerPanelButtons);
		upperLayerPanel.setAlignmentX(LEFT_ALIGNMENT);

		JPanel colorPanel = GuiUtility.makeColorChooserPanel(colorTool);

		grid = new GridPanel(sm);
		grid.setAlignmentY(CENTER_ALIGNMENT);
		grid.addMouseListener(
				new MouseListener() {

					@Override
					public void mouseClicked(MouseEvent me) {
						if(canSpawn){
							int x = me.getX();
							int y = me.getY();
							int height = grid.getHeight()/ScreenManager.getGUIheight();
							int width = grid.getWidth()/ScreenManager.getGUIwidth();
							int standardSize = Math.min(width, height);
							if(sm.getFacade().getAgent(x/standardSize, y/standardSize) == null){
								sm.getFacade().spiralSpawn(agentComboBox.getSelectedItem().toString(), x/standardSize, y/standardSize);
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
		layerPanel.add(upperLayerPanel);
		layerPanel.add(colorPanel);



		JPanel mainPanel = GuiUtility.makePanel(BoxLayoutAxis.LINE_AXIS,null,null);
		mainPanel.add(layerPanel);
		mainPanel.add(grid);

		this.add(label, BorderLayout.NORTH);
		this.add(makeButtonPanel(), BorderLayout.SOUTH);
		this.add(mainPanel, BorderLayout.CENTER);

		this.setVisible(true);	
	}

	private JPanel makeButtonPanel(){
		JPanel buttonPanel = GuiUtility.makePanel((LayoutManager)null,new MaxSize(500,50),PrefSize.NULL);
		buttonPanel.add(makeStartButton());

		buttonPanel.add(GuiUtility.makeButton("Pause",
				new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sm.setRunning(false);
				//add if loop for tabbed pane once implemented
				canSpawn = true;
			}
		}
				));

		//TODO most of these will become tabs, adding temporarily for navigation purposes
		buttonPanel.add(GuiUtility.makeButton("Entities", new GeneralButtonListener("Entities", sm)));
		buttonPanel.add(GuiUtility.makeButton("Global Fields", new GeneralButtonListener("Fields", sm)));
		buttonPanel.add(GuiUtility.makeButton("Setup options", new GeneralButtonListener("Grid Setup", sm)));
		buttonPanel.add(GuiUtility.makeButton("Statistics", new GeneralButtonListener("Statistics", sm)));
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
				startTime = System.currentTimeMillis();
				if (stepCount == 0) {
					sm.getStatManager().setStartTime(startTime);
				}
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
					long currentTime = System.currentTimeMillis();
					gridRec.recordSimulationStep(sm.getFacade().getGrid(), stepCount, Prototype.getPrototypes());
					gridRec.updateTime(currentTime, currentTime - startTime);
					startTime = currentTime;
					stepCount++;
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
