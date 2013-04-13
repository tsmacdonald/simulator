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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
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
import edu.wheaton.simulator.simulation.GUIToAgentFacade;
import edu.wheaton.simulator.simulation.SimulationPauseException;
import edu.wheaton.simulator.statistics.SimulationRecorder;

public class ViewSimScreen extends Screen {

	//private JPanel gridPanel;

	//private int height;

	//private int width;

	private ScreenManager sm;
	/**
	 * 
	 */
	private static final long serialVersionUID = -6872689283286800861L;

	private GridPanel grid;

	private int stepCount;

	//private JButton backButton;

	private long startTime;

	private SimulationRecorder gridRec;

	private JComboBox agentComboBox;

	private JComboBox layerComboBox;

	private String[] entities;

	private JPanel panel1;

	private JPanel panel2;

	public ViewSimScreen(final ScreenManager sm) {
		super(sm);
		entities = new String[0];
		this.setLayout(new BorderLayout());
		this.sm = sm;
		gridRec = new SimulationRecorder(sm.getStatManager());
		stepCount = 0;
		JLabel label = new JLabel("View Simulation", SwingConstants.CENTER);
		JPanel layerPanel = GuiUtility.makeBoxPanel(BoxLayout.Y_AXIS);
		panel1 = GuiUtility.makeBoxPanel(BoxLayout.X_AXIS);
		JLabel agents = new JLabel("Agents", SwingConstants.CENTER);
		agentComboBox = GuiUtility.makeComboBox(null,new MaxSize(200,50));
		panel1.add(agents);
		panel1.add(agentComboBox);
		panel2 = GuiUtility.makeBoxPanel(BoxLayout.X_AXIS);
		JLabel layers = new JLabel("Layers", SwingConstants.CENTER);
		JPanel mainPanel = GuiUtility.makeBoxPanel(BoxLayout.X_AXIS);
		layerComboBox = GuiUtility.makeComboBox(null, new MaxSize(200,50));
		panel2.add(layers);
		panel2.add(layerComboBox);
		JPanel panel3 = GuiUtility.makeBoxPanel(BoxLayout.X_AXIS);
		final JColorChooser colorTool = new JColorChooser();
		colorTool.setMaximumSize(new Dimension(250, 500));
		JButton apply = GuiUtility.makeButton("Apply",
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent ae) {
						sm.getFacade();
						GUIToAgentFacade.newLayer(layerComboBox.getSelectedItem().toString(), colorTool.getColor());
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
		panel3.add(apply);
		panel3.add(clear);
		
		//layerPanel.setSize(new Dimension(600, 1000));

		//TODO add layer elements
		//set Layout
		//objects for layers:
		// - combobox(es) for choosing field, colorchooser to pick primary filter color, 
		//   labels for these, "apply" button, "clear" button

		//gridPanel = new JPanel();
		grid = new GridPanel(sm);
		this.add(layerPanel, BorderLayout.WEST);
		layerPanel.add(panel1);
		layerPanel.add(panel2);
		layerPanel.add(panel3);
		layerPanel.add(colorTool);
		mainPanel.add(layerPanel);
		mainPanel.add(grid);
		this.add(label, BorderLayout.NORTH);
		this.add(makeButtonPanel(), BorderLayout.SOUTH);
		this.add(mainPanel, BorderLayout.CENTER);
		this.setVisible(true);	
	}

	private JPanel makeButtonPanel(){
		JPanel buttonPanel = new JPanel();
		buttonPanel.setMaximumSize(new Dimension(500, 50));
		buttonPanel.add(makeStartButton());
		buttonPanel.add(makePauseButton());
		//TODO most of these will become tabs, adding temporarily for navigation purposes
		buttonPanel.add(makeEntitiesButton());
		buttonPanel.add(makeGlobalFieldsButton());
		buttonPanel.add(makeSetupButton());
		buttonPanel.add(makeStatsButton());
		//buttonPanel.add(makeBackButton());
		return buttonPanel;
	}


	private JButton makeEntitiesButton(){
		return GuiUtility.makeButton("Entities", new GeneralButtonListener("Entities", sm));
	}
	
	private JButton makeGlobalFieldsButton(){
		return GuiUtility.makeButton("Global Fields", new GeneralButtonListener("Fields", sm));
	}
	
	private JButton makeSetupButton(){
		return GuiUtility.makeButton("Setup options", new GeneralButtonListener("Grid Setup", sm));
	}
	
	private JButton makeStatsButton(){
		return GuiUtility.makeButton("Statistics", new GeneralButtonListener("Statistics", sm));
	}
	
	private JButton makeBackButton(){
		JButton b = GuiUtility.makeButton("Back",new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sm.update(sm.getScreen("Edit Simulation")); 
			} 
		});
		//backButton = b;
		return b;
	}

	private JButton makePauseButton(){
		JButton b = GuiUtility.makeButton("Pause",
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						sm.setRunning(false);
						//backButton.setEnabled(true);
					}
				}
				);
		return b;
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
						//backButton.setEnabled(false);
						sm.setRunning(true);
						sm.setStarted(true);
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
						//backButton.setEnabled(true);
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
		entities = GUIToAgentFacade.prototypeNames().toArray(entities);
		agentComboBox = new JComboBox(entities);
		agentComboBox.addItemListener(
				new ItemListener() {
					@Override
					public void itemStateChanged(ItemEvent e) {
						sm.getFacade();
						layerComboBox = new JComboBox(GUIToAgentFacade.getPrototype
								(agentComboBox.getSelectedItem().toString())
								.getCustomFieldMap().keySet().toArray());
						layerComboBox.setMaximumSize(new Dimension(200, 50));
						panel2.remove(1);
						panel2.add(layerComboBox);
						validate();
						repaint();
					}
				}
				);
		if(entities.length != 0){
			sm.getFacade();
			layerComboBox = new JComboBox(GUIToAgentFacade.getPrototype
					(agentComboBox.getItemAt(0).toString())
					.getCustomFieldMap().keySet().toArray());
			layerComboBox.setMaximumSize(new Dimension(200, 50));
			panel2.remove(1);
			panel2.add(layerComboBox);
		}
		agentComboBox.setMaximumSize(new Dimension(200, 50));
		panel1.remove(1);
		panel1.add(agentComboBox);
		validate();
		grid.repaint();

	}
}
