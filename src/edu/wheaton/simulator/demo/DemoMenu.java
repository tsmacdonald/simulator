package edu.wheaton.simulator.demo;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import edu.wheaton.simulator.entity.Prototype;
import edu.wheaton.simulator.entity.Trigger;
import edu.wheaton.simulator.expression.Expression;
import edu.wheaton.simulator.simulation.GUIToAgentFacade;
import edu.wheaton.simulator.simulation.SimulationPauseException;
import edu.wheaton.simulator.statistics.GridObserver;
import edu.wheaton.simulator.statistics.StatisticsManager;

public class DemoMenu {

	private JFrame frame;
	private JPanel startScreen;
	private JPanel simulationScreen;
	private JPanel statsScreen;
	private JTextField nameField;
	private JColorChooser colorTool;
	private DemoGridPanel grid;
	private GUIToAgentFacade facade;
	private StatisticsManager statsManager;
	private GridObserver observer;
	private boolean isRunning;
	private int turnCount;
	private HashSet<Prototype> prototypes;
	private JButton finishButton;
	private long startTime;
	private long endTime;
	private JLabel movesLabel;
	private JLabel timesLabel;
	private JLabel durationLabel;

	public DemoMenu() {
		//initialize instance variables
		facade = new GUIToAgentFacade(10, 10);
		statsManager = new StatisticsManager();
		observer = new GridObserver(statsManager);
		isRunning = false;
		turnCount = 0;
		startTime = 0;
		prototypes = new HashSet<Prototype>();
		frame = new JFrame("Simulator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 800);
		frame.setLocation(300, 200);

		//create panel for opening screen
		startScreen = new JPanel();
		startScreen.setLayout(new BorderLayout());
		JLabel startLabel = new JLabel("Starting Information");
		startLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		startLabel.setHorizontalAlignment(SwingConstants.CENTER);
		startScreen.add(startLabel, BorderLayout.NORTH);
		JPanel startMainPanel = new JPanel();
		startMainPanel.setLayout(new BoxLayout(startMainPanel, BoxLayout.Y_AXIS));
		JPanel startNamePanel = new JPanel();
		JLabel startNameLabel = new JLabel("Agent name");
		nameField = new JTextField(25);
		startNamePanel.add(startNameLabel);
		startNamePanel.add(nameField);
		startMainPanel.add(startNamePanel);
		colorTool = new JColorChooser();
		startMainPanel.add(colorTool);
		startScreen.add(startMainPanel, BorderLayout.CENTER);
		JButton startNextButton = new JButton("Next");
		//addActionListener to move to next screen, create the agent, add it to the grid
		startNextButton.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						facade.createPrototype(nameField.getText(), facade.getGrid(), colorTool.getColor());
						//add behavior to that prototype
						facade.getPrototype(nameField.getText()).addTrigger(
								new Trigger("move right", 
										1, 
										new Expression("1==1"), 
										new Expression("move('this', this.x + 1, this.y)"))
								);
						prototypes.add(facade.getPrototype(nameField.getText()));
						facade.spiralSpawn(nameField.getText(), 0, 4);
						frame.setContentPane(simulationScreen);
						frame.setVisible(true);
						grid.repaint();
					}
				}
				);
		startScreen.add(startNextButton, BorderLayout.SOUTH);

		//create panel for simulation screen
		simulationScreen = new JPanel();
		simulationScreen.setLayout(new BorderLayout());
		JLabel simLabel = new JLabel("View Simulation");
		simLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		simulationScreen.add(simLabel, BorderLayout.NORTH);
		grid = new DemoGridPanel(facade);
		simulationScreen.add(grid, BorderLayout.CENTER);
		JPanel simButtonPanel = new JPanel();
		JButton startButton = new JButton("Start");
		startButton.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						isRunning = true;
						startTime = System.currentTimeMillis();
						if (turnCount == 0) {
							statsManager.setStartTime(startTime);
						}
						runSim();
					}
				}
				);
		JButton pauseButton = new JButton("Pause");
		pauseButton.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						isRunning = false;
					}
				}
				);
		finishButton = new JButton("Finish");
		finishButton.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						frame.setContentPane(statsScreen);
						frame.setVisible(true);
						Date dStart = new Date(statsManager.getSimulationStartTime());
						Date dEnd = new Date(endTime);
						SimpleDateFormat ft = new SimpleDateFormat ("hh:mm:ss.SSS a");

						movesLabel.setText("Number of steps taken:  " + 
								statsManager.getLastStep()
								);
						//TODO set text for start/end times
						timesLabel.setText("Simulation start time:  " + 
								ft.format(dStart) +
								"      Simulation end Time:  " + 
								ft.format(dEnd)
								);
						//TODO set text for duration
						durationLabel.setText("Simulation duration:  " + 
								statsManager.getSimulationDuration()/1000 + 
								" seconds");
					}
				}
				);
		finishButton.setEnabled(false);
		simButtonPanel.add(pauseButton);
		simButtonPanel.add(startButton);
		simButtonPanel.add(finishButton);
		simulationScreen.add(simButtonPanel, BorderLayout.SOUTH);

		//create panel for stats screen
		statsScreen = new JPanel();
		statsScreen.setLayout(new BorderLayout());
		JLabel statsLabel = new JLabel("Statistics");
		statsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		statsLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		statsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		statsScreen.add(statsLabel, BorderLayout.NORTH);
		JPanel statsMainPanel = new JPanel();
		statsMainPanel.setLayout(new BoxLayout(statsMainPanel, BoxLayout.Y_AXIS));
		statsMainPanel.add(Box.createVerticalGlue());
		movesLabel = new JLabel();
		statsMainPanel.add(movesLabel);
		movesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		movesLabel.setMinimumSize(new Dimension(800, 500));
		timesLabel = new JLabel();
		statsMainPanel.add(timesLabel);
		timesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		timesLabel.setMinimumSize(new Dimension(800, 500));
		durationLabel = new JLabel();
		statsMainPanel.add(durationLabel);
		durationLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		durationLabel.setMinimumSize(new Dimension(800, 500));
		statsMainPanel.add(Box.createVerticalGlue());
		statsScreen.add(statsMainPanel, BorderLayout.CENTER);
		JButton quitButton = new JButton("Quit");
		quitButton.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						//close simulation
						frame.setVisible(false);
						frame.dispose();
					}
				}
				);
		statsScreen.add(quitButton, BorderLayout.SOUTH);

		frame.setContentPane(startScreen);
		frame.setVisible(true);
	}

	private void runSim() {

		new Thread(new Runnable() {
			@Override
			public void run() {
				while(isRunning) {
					try {
						facade.updateEntities();
					} catch (SimulationPauseException e) {
						isRunning = false;
						JOptionPane.showMessageDialog(null, e.getMessage());
					}

					SwingUtilities.invokeLater(
							new Thread (new Runnable() {
								@Override
								public void run() {
									grid.repaint();
								}
							}));
					turnCount++;
					long currentTime = System.currentTimeMillis();
					observer.recordSimulationStep(facade.getGrid(), turnCount, prototypes);
					observer.updateTime(currentTime, currentTime - startTime);
					startTime = currentTime;
					System.out.println(turnCount);
					if (turnCount >= 9) {
						isRunning = false;
						endTime = System.currentTimeMillis();
						finishButton.setEnabled(true);
					}
					try {
						System.out.println("Sleep!");
						Thread.sleep(500);
					} catch (InterruptedException e) {
						System.err.println("DemoMenu.java: 'Thread.sleep(500)' was interrupted");
						e.printStackTrace();
					}
				}
			}
		}).start();

	}
}
