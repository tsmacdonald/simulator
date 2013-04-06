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
import javax.swing.colorchooser.AbstractColorChooserPanel;

import edu.wheaton.simulator.entity.Prototype;
import edu.wheaton.simulator.simulation.SimulationPauseException;
import edu.wheaton.simulator.statistics.GridRecorder;
import edu.wheaton.simulator.statistics.StatisticsManager;

public class ViewSimScreen extends Screen {

	private JPanel gridPanel;

	private int height;

	private int width;

	private ScreenManager sm;
	/**
	 * 
	 */
	private static final long serialVersionUID = -6872689283286800861L;

	private GridPanel grid;

	private int stepCount;
	
	private JButton backButton;
	
	private long startTime;
	
	private long endTime;
	
	private GridRecorder gridRec;

	//TODO figure out best way to have agents drawn before pressing start, without creating issues
	//with later changing spawn conditions
	public ViewSimScreen(final ScreenManager sm) {
		super(sm);
		this.setLayout(new BorderLayout());
		this.sm = sm;
		gridRec = new GridRecorder(sm.getStatManager());
		stepCount = 0;
		JLabel label = new JLabel("View Simulation", SwingConstants.CENTER);
		JPanel layerPanel = new JPanel();
		layerPanel.setLayout(new BoxLayout(layerPanel, BoxLayout.Y_AXIS));
		JPanel panel1 = new JPanel();
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));
		JLabel agents = new JLabel("Agents", SwingConstants.CENTER);
		JComboBox agentComboBox = new JComboBox();
		panel1.add(agents);
		panel1.add(agentComboBox);
		JPanel panel2 = new JPanel();
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS));
		JLabel layers = new JLabel("Layers", SwingConstants.CENTER);
		JComboBox layerComboBox = new JComboBox();
		panel2.add(layers);
		panel2.add(layerComboBox);
		JColorChooser color = new JColorChooser();
		//Commented out because of type error
		//		AbstractColorChooserPanel panels[] = { new DefaultSwatchChoserPanel() };
		//		color.setChooserPanels(panels);
		//AbstractColorChooserPanel panels[] = { new DefaultSwatchChoserPanel() };
		//color.setChooserPanels(panels);
		agentComboBox.setMinimumSize(new Dimension(500, 50));
		layerComboBox.setMinimumSize(new Dimension(500, 50));
		layerPanel.setMinimumSize(new Dimension(600, 1000));

		//TODO add layer elements
		//set Layout
		//objects for layers:
		// - combobox(es) for choosing field, colorchooser to pick primary filter color, 
		//   labels for these, "apply" button, "clear" button

		gridPanel = new JPanel();
		grid = new GridPanel(sm);
		this.add(layerPanel, BorderLayout.WEST);
		layerPanel.add(panel1);
		layerPanel.add(panel2);
		this.add(label, BorderLayout.NORTH);
		this.add(makeButtonPanel(), BorderLayout.SOUTH);
		this.add(grid, BorderLayout.CENTER);
		this.setVisible(true);	
	}

	private JPanel makeButtonPanel(){
		JPanel buttonPanel = new JPanel();
		buttonPanel.setMaximumSize(new Dimension(500, 50));
		buttonPanel.add(makeStartButton());
		buttonPanel.add(makePauseButton());
		buttonPanel.add(makeBackButton());
		return buttonPanel;
	}

	private JButton makeBackButton(){
		JButton b = new JButton("Back");
		b.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						sm.update(sm.getScreen("Edit Simulation")); 
					} 
				}
				);
		backButton = b;
		return b;
	}

	private JButton makePauseButton(){
		JButton b = new JButton("Pause");
		b.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						sm.setRunning(false);
						backButton.setEnabled(true);
						endTime = System.currentTimeMillis();
					}
				}
				);
		return b;
	}

	private JButton makeStartButton(){
		JButton b = new JButton("Start/Resume");
		b.addActionListener(
				new ActionListener() {
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
						backButton.setEnabled(false);
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
		System.out.println(sm.getEnder().getStepLimit());
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
					sm.setRunning(!(sm.getEnder().evaluate(stepCount, 
							sm.getFacade().getGrid())));
					//TODO setEndTime if it ends; should that be handled in statistics code?

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
		validate();
		grid.repaint();
	}
}
