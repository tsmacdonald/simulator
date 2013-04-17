/**
 * ViewSimScreen
 * 
 * Class representing the screen that displays the grid as
 * the simulation runs.
 * 
 * @author Willy McHie and Ian Walling
 * Wheaton College, CSCI 335, Spring 2013
 */

package edu.wheaton.simulator.gui.screen;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import net.sourceforge.jeval.EvaluationException;

import edu.wheaton.simulator.gui.BoxLayoutAxis;
import edu.wheaton.simulator.gui.GeneralButtonListener;
import edu.wheaton.simulator.gui.GridPanel;
import edu.wheaton.simulator.gui.Gui;
import edu.wheaton.simulator.gui.MaxSize;
import edu.wheaton.simulator.gui.PrefSize;
import edu.wheaton.simulator.gui.ScreenManager;
import edu.wheaton.simulator.gui.SimulatorGuiManager;
import edu.wheaton.simulator.gui.SpawnCondition;
import edu.wheaton.simulator.simulation.Simulator;
import edu.wheaton.simulator.simulation.SimulationPauseException;

public class ViewSimScreen extends Screen {

	private static final long serialVersionUID = -6872689283286800861L;

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
		//gridRec = new SimulationRecorder(sm.getStatManager());
		stepCount = 0;

		agentComboBox = Gui.makeComboBox(null,new MaxSize(200,50));

		layerComboBox = Gui.makeComboBox(null, new MaxSize(200,50));

		final JColorChooser colorTool = Gui.makeColorChooser();

		layerPanelAgents = Gui.makePanel(BoxLayoutAxis.LINE_AXIS,null,null);
		layerPanelAgents.add(new JLabel("Agents", SwingConstants.CENTER));
		layerPanelAgents.add(agentComboBox);

		layerPanelLayers = Gui.makePanel(BoxLayoutAxis.LINE_AXIS,null,null);
		layerPanelLayers.add(new JLabel("Layers", SwingConstants.CENTER));
		layerPanelLayers.add(layerComboBox);

		JPanel layerPanelButtons = Gui.makePanel(BoxLayoutAxis.LINE_AXIS,null,null);
		
		layerPanelButtons.add(Gui.makeButton("Apply",null,
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
		}));
		
		layerPanelButtons.add(Gui.makeButton("Clear",null,
			new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ae) {
					grid.setLayers(false);
					grid.repaint();
				} 
		}));

		JPanel upperLayerPanel = Gui.makePanel(BoxLayoutAxis.PAGE_AXIS, null, null);
		upperLayerPanel.add(layerPanelAgents);
		upperLayerPanel.add(layerPanelLayers);
		upperLayerPanel.add(layerPanelButtons);
		upperLayerPanel.setAlignmentX(LEFT_ALIGNMENT);

		grid = new GridPanel(gm);
		grid.setAlignmentY(CENTER_ALIGNMENT);
		grid.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent me) {
				if(canSpawn){
					int standardSize = Math.min(
						grid.getWidth()/SimulatorGuiManager.getGUIwidth(),
						grid.getHeight()/SimulatorGuiManager.getGUIheight()
					);
					
					int x = me.getX()/standardSize;
					int y = me.getY()/standardSize;
					
					Simulator sim = gm.getFacade();
					
					if(sim.getAgent(x,y) == null)
						sim.spiralSpawn(agentComboBox.getSelectedItem().toString(), x, y);
					else
						sim.removeAgent(x,y);
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
		layerPanel.add(Gui.makeColorChooserPanel(colorTool));

		JPanel mainPanel = Gui.makePanel(BoxLayoutAxis.LINE_AXIS,null,null);
		mainPanel.add(layerPanel);
		mainPanel.add(grid);

		this.add(
			new JLabel("View Simulation", SwingConstants.CENTER), BorderLayout.NORTH
		);
		this.add(makeButtonPanel(), BorderLayout.SOUTH);
		this.add(mainPanel, BorderLayout.CENTER);
		this.setVisible(true);	
	}

	private JPanel makeButtonPanel(){
		JPanel buttonPanel = Gui.makePanel((LayoutManager)null,new MaxSize(500,50),PrefSize.NULL);
		buttonPanel.add(makeStartButton());

		buttonPanel.add(Gui.makeButton("Pause",null,
			new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					getGuiManager().setRunning(false);
					//add if loop for tabbed pane once implemented
					canSpawn = true;
				}
		}));

		//TODO most of these will become tabs, adding temporarily for navigation purposes
		ScreenManager sm = getScreenManager();
		buttonPanel.add(Gui.makeButton("Entities",null, new GeneralButtonListener("Entities",sm)));
		buttonPanel.add(Gui.makeButton("Global Fields",null, new GeneralButtonListener("Fields",sm)));
		buttonPanel.add(Gui.makeButton("Setup options",null, new GeneralButtonListener("Grid Setup",sm)));
		buttonPanel.add(Gui.makeButton("Statistics",null, new GeneralButtonListener("Statistics",sm)));
		return buttonPanel;
	}

	private JButton makeStartButton(){
		JButton b = Gui.makeButton("Start/Resume",null,new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SimulatorGuiManager gm = getGuiManager();
				if (!gm.hasStarted()) {
					for (SpawnCondition condition: gm.getSpawnConditions()) {
						condition.addToGrid(gm.getFacade());
						System.out.println("spawning a condition");
					}
				}
				grid.repaint();
				gm.setRunning(true);
				gm.setStarted(true);
				canSpawn = false;
				startTime = System.currentTimeMillis();
//				if (stepCount == 0)
//					;//sm.getStatManager().setStartTime(startTime);
				runSim();
			}
		});
		return b;
	}

	private void runSim() {
		System.out.println("StepLimit = " + getGuiManager().getEnder().getStepLimit());
		//program loop yay!
		new Thread(new Runnable() {
			@Override
			public void run() {
				SimulatorGuiManager gm = getGuiManager();
				while(gm.isRunning()) {
					Simulator sim = gm.getFacade();
					try {
						sim.updateEntities();
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
							sim.getGrid());
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
		entities = Simulator.prototypeNames().toArray(entities);
		agentComboBox = new JComboBox(entities);
		agentComboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				layerComboBox = new JComboBox(Simulator.getPrototype
						(agentComboBox.getSelectedItem().toString())
						.getCustomFieldMap().keySet().toArray());
				layerComboBox.setMaximumSize(new Dimension(200, 50));
				layerPanelLayers.remove(1);
				layerPanelLayers.add(layerComboBox);
				validate();
				repaint();
			}
		});
		if(entities.length != 0){
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
