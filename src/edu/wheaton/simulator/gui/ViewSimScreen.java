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

import edu.wheaton.simulator.simulation.Simulator;
import edu.wheaton.simulator.simulation.SimulationPauseException;

public class ViewSimScreen extends Screen {

	private static final long serialVersionUID = -6872689283286800861L;

	private SimulatorGuiManager gm;

	private GridPanel grid;

	private int stepCount;

	private long startTime;

	private boolean canSpawn;

	private JComboBox agentComboBox;

	private JComboBox layerComboBox;

	private String[] entities;

	private JPanel layerPanelAgents;

	private JPanel layerPanelLayers;

	public ViewSimScreen(final SimulatorGuiManager gm) {
		super(gm);
		canSpawn = true;
		entities = new String[0];
		this.setLayout(new BorderLayout());
		this.gm = gm;
		//gridRec = new SimulationRecorder(sm.getStatManager());
		stepCount = 0;
		JLabel label = new JLabel("View Simulation", SwingConstants.CENTER);

		JLabel agents = new JLabel("Agents", SwingConstants.CENTER);
		agentComboBox = Gui.makeComboBox(null,new MaxSize(200,50));

		JLabel layers = new JLabel("Layers", SwingConstants.CENTER);
		layerComboBox = Gui.makeComboBox(null, new MaxSize(200,50));

		final JColorChooser colorTool = Gui.makeColorChooser();

		JButton apply = Gui.makeButton("Apply",
				new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				gm.getFacade();
				Simulator.newLayer(layerComboBox.getSelectedItem().toString(), colorTool.getColor());
				try {
					gm.getFacade().setLayerExtremes();
				} catch (EvaluationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				grid.setLayers(true);
				grid.repaint();
			} 
		}
				);

		JButton clear = Gui.makeButton("Clear",
				new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				grid.setLayers(false);
				grid.repaint();
			} 
		}
				);



		layerPanelAgents = Gui.makePanel(BoxLayoutAxis.LINE_AXIS,null,null);
		layerPanelAgents.add(agents);
		layerPanelAgents.add(agentComboBox);

		layerPanelLayers = Gui.makePanel(BoxLayoutAxis.LINE_AXIS,null,null);
		layerPanelLayers.add(layers);
		layerPanelLayers.add(layerComboBox);

		JPanel layerPanelButtons = Gui.makePanel(BoxLayoutAxis.LINE_AXIS,null,null);
		layerPanelButtons.add(apply);
		layerPanelButtons.add(clear);

		JPanel upperLayerPanel = Gui.makePanel(BoxLayoutAxis.PAGE_AXIS, null, null);
		upperLayerPanel.add(layerPanelAgents);
		upperLayerPanel.add(layerPanelLayers);
		upperLayerPanel.add(layerPanelButtons);
		upperLayerPanel.setAlignmentX(LEFT_ALIGNMENT);

		JPanel colorPanel = Gui.makeColorChooserPanel(colorTool);

		grid = new GridPanel(gm);
		grid.setAlignmentY(CENTER_ALIGNMENT);
		grid.addMouseListener(
				new MouseListener() {

					@Override
					public void mouseClicked(MouseEvent me) {
						if(canSpawn){
							int x = me.getX();
							int y = me.getY();
							int height = grid.getHeight()/SimulatorGuiManager.getGUIheight();
							int width = grid.getWidth()/SimulatorGuiManager.getGUIwidth();
							int standardSize = Math.min(width, height);
							if(gm.getFacade().getAgent(x/standardSize, y/standardSize) == null){
								gm.getFacade().spiralSpawn(agentComboBox.getSelectedItem().toString(), x/standardSize, y/standardSize);
							}
							else{
								gm.getFacade().removeAgent(x/standardSize, y/standardSize);
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


		JPanel layerPanel = Gui.makePanel(BoxLayoutAxis.Y_AXIS,null,null);
		layerPanel.setAlignmentY(CENTER_ALIGNMENT);
		layerPanel.add(upperLayerPanel);
		layerPanel.add(colorPanel);



		JPanel mainPanel = Gui.makePanel(BoxLayoutAxis.LINE_AXIS,null,null);
		mainPanel.add(layerPanel);
		mainPanel.add(grid);

		this.add(label, BorderLayout.NORTH);
		this.add(makeButtonPanel(), BorderLayout.SOUTH);
		this.add(mainPanel, BorderLayout.CENTER);

		this.setVisible(true);	
	}

	private JPanel makeButtonPanel(){
		JPanel buttonPanel = Gui.makePanel((LayoutManager)null,new MaxSize(500,50),PrefSize.NULL);
		buttonPanel.add(makeStartButton());

		buttonPanel.add(Gui.makeButton("Pause",
				new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gm.setRunning(false);
				//add if loop for tabbed pane once implemented
				canSpawn = true;
			}
		}
				));

		//TODO most of these will become tabs, adding temporarily for navigation purposes
		buttonPanel.add(Gui.makeButton("Entities", new GeneralButtonListener("Entities", gm.getScreenManager())));
		buttonPanel.add(Gui.makeButton("Global Fields", new GeneralButtonListener("Fields", gm.getScreenManager())));
		buttonPanel.add(Gui.makeButton("Setup options", new GeneralButtonListener("Grid Setup", gm.getScreenManager())));
		buttonPanel.add(Gui.makeButton("Statistics", new GeneralButtonListener("Statistics", gm.getScreenManager())));
		return buttonPanel;
	}

	private JButton makeStartButton(){
		JButton b = Gui.makeButton("Start/Resume",new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!gm.hasStarted()) {
					ArrayList<SpawnCondition> conditions = gm.getSpawnConditions();
					for (SpawnCondition condition: conditions) {
						condition.addToGrid(gm.getFacade());
						System.out.println("spawning a condition");
					}
				}
				grid.repaint();
				gm.setRunning(true);
				gm.setStarted(true);
				canSpawn = false;
				startTime = System.currentTimeMillis();
				if (stepCount == 0) {
					//sm.getStatManager().setStartTime(startTime);
				}
				runSim();
			}
		}
				);
		return b;
	}

	private void runSim() {
		System.out.println("StepLimit = " + gm.getEnder().getStepLimit());
		//program loop yay!
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(gm.isRunning()) {
					try {
						gm.getFacade().updateEntities();
					} catch (SimulationPauseException e) {
						gm.setRunning(false);
						JOptionPane.showMessageDialog(null, e.getMessage());
						break;
					}
					long currentTime = System.currentTimeMillis();
					//gridRec.recordSimulationStep(gm.getFacade().getGrid(), stepCount, Prototype.getPrototypes());
					//gridRec.updateTime(currentTime, currentTime - startTime);
					startTime = currentTime;
					stepCount++;
					boolean shouldEnd = gm.getEnder().evaluate(stepCount, 
							gm.getFacade().getGrid());
					System.out.println("shouldEnd = " + shouldEnd);
					if (shouldEnd) {
						gm.setRunning(false);
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
		gm.getFacade();
		entities = Simulator.prototypeNames().toArray(entities);
		agentComboBox = new JComboBox(entities);
		agentComboBox.addItemListener(
				new ItemListener() {
					@Override
					public void itemStateChanged(ItemEvent e) {
						gm.getFacade();
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
			gm.getFacade();
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
