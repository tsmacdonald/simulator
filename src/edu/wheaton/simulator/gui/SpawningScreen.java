/**
 * SpawningScreen
 * 
 * Class representing the screen that allows to determine spawning conditions,
 * or spawn new entities during a simulation.
 * 
 * @author Willy McHie
 * Wheaton College, CSCI 335, Spring 2013
 */

package edu.wheaton.simulator.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class SpawningScreen extends Screen {

	//temporary placeholder
	private String[] exampleEntities = {"Fox", "Rabbit", "Clover", "Bear"};

	private ArrayList<JComboBox> entityTypes;

	private ArrayList<JComboBox> spawnPatterns;

	//temporary placeholder
	private String[] spawnOptions = {"Random", "Clustered", "Edge"};

	private ArrayList<JTextField> xLocs;

	private ArrayList<JTextField> yLocs;

	private ArrayList<JTextField> numbers;
	
	private ArrayList<JButton> deleteButtons;

	private ArrayList<JPanel> subPanels;

	private JButton addSpawnButton;
	
	private JPanel listPanel;

	private Component glue;
	/**
	 * 
	 */
	private static final long serialVersionUID = 6312784326472662829L;

	public SpawningScreen(final ScreenManager sm) {
		super(sm);
		this.setLayout(new BorderLayout());
		JLabel label = new JLabel("Spawning");
		label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		label.setPreferredSize(new Dimension(300, 150));
		listPanel = new JPanel();
		listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
		JPanel labelsPanel = new JPanel();
		labelsPanel.setLayout(new BoxLayout(labelsPanel, BoxLayout.X_AXIS));

		//TODO mess with sizes of labels to line up with components
		JLabel entityLabel = new JLabel("Entity Type");
		entityLabel.setPreferredSize(new Dimension(200, 30));
		JLabel patternLabel = new JLabel("Spawn Pattern");
		patternLabel.setPreferredSize(new Dimension(270, 30));
		JLabel xLabel = new JLabel("x Loc.");
		xLabel.setPreferredSize(new Dimension(100, 30));
		JLabel yLabel = new JLabel("Y Loc.");
		yLabel.setPreferredSize(new Dimension(100, 30));
		JLabel numberLabel = new JLabel("Number");
		numberLabel.setPreferredSize(new Dimension(330, 30));
		labelsPanel.add(Box.createHorizontalGlue());
		labelsPanel.add(entityLabel);
		entityLabel.setHorizontalAlignment(SwingConstants.CENTER);
		labelsPanel.add(Box.createHorizontalGlue());
		labelsPanel.add(patternLabel);
		patternLabel.setHorizontalAlignment(SwingConstants.CENTER);
		labelsPanel.add(Box.createHorizontalGlue());
		labelsPanel.add(xLabel);
		labelsPanel.add(yLabel);
		labelsPanel.add(numberLabel);
		labelsPanel.add(Box.createHorizontalGlue());
		listPanel.add(labelsPanel);
		labelsPanel.setAlignmentX(CENTER_ALIGNMENT);

		entityTypes = new ArrayList<JComboBox>();
		entityTypes.add(new JComboBox(exampleEntities));
		entityTypes.get(0).setMaximumSize(new Dimension(250, 30));
		spawnPatterns = new ArrayList<JComboBox>();
		spawnPatterns.add(new JComboBox(spawnOptions));
		spawnPatterns.get(0).setMaximumSize(new Dimension(250, 30));
		xLocs = new ArrayList<JTextField>();
		xLocs.add(new JTextField(10));
		xLocs.get(0).setMaximumSize(new Dimension(100, 30));
		yLocs = new ArrayList<JTextField>();
		yLocs.add(new JTextField(10));
		yLocs.get(0).setMaximumSize(new Dimension(100, 30));
		numbers = new ArrayList<JTextField>();
		numbers.add(new JTextField(10));
		numbers.get(0).setMaximumSize(new Dimension(100, 30));
		deleteButtons = new ArrayList<JButton>();
		deleteButtons.add(new JButton("Delete"));
		deleteButtons.get(0).setActionCommand("Delete 0");
		deleteButtons.get(0).addActionListener(this);
		subPanels = new ArrayList<JPanel>();
		subPanels.add(new JPanel());
		subPanels.get(0).setLayout(
				new BoxLayout(subPanels.get(0), BoxLayout.X_AXIS)
				);
		subPanels.get(0).add(entityTypes.get(0));
		subPanels.get(0).add(spawnPatterns.get(0));
		subPanels.get(0).add(xLocs.get(0));
		subPanels.get(0).add(yLocs.get(0));
		subPanels.get(0).add(numbers.get(0));
		subPanels.get(0).add(deleteButtons.get(0));
		listPanel.add(subPanels.get(0));
		addSpawnButton = new JButton("Add Spawn");
		addSpawnButton.addActionListener(this);
		listPanel.add(addSpawnButton);
		glue = Box.createVerticalGlue();
		listPanel.add(glue);
		
		JPanel buttonPanel = new JPanel();
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						sm.update(sm.getScreen("Edit Simulation")); 
					} 
				});
		JButton finishButton = new JButton("Finish");
		finishButton.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						sm.update(sm.getScreen("Edit Simulation")); 
					} 
				});
		buttonPanel.add(cancelButton);
		buttonPanel.add(finishButton);
		this.add(label, BorderLayout.NORTH);
		this.add(listPanel, BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.SOUTH);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String action = e.getActionCommand();
		Screen update = this;
		if (action.equals("Add Spawn")) {
			addSpawn();
		}
		else if (action.substring(0, 6).equals("Delete")) {
			deleteSpawn(Integer.parseInt(action.substring(7)));
		}
	}

	@Override
	public void sendInfo() {
		// TODO Auto-generated method stub

	}
	
	private void addSpawn() {
		//TODO figure out why repaint() doesn't update the screen
		JPanel newPanel = new JPanel();
		newPanel.setLayout(
				new BoxLayout(newPanel, 
						BoxLayout.X_AXIS)
				);
		JComboBox newBox = new JComboBox(exampleEntities);
		newBox.setMaximumSize(new Dimension(250, 30));
		entityTypes.add(newBox);
		JComboBox newSpawnType = new JComboBox(spawnOptions);
		newSpawnType.setMaximumSize(new Dimension(250, 30));
		spawnPatterns.add(newSpawnType);
		JTextField newXLoc = new JTextField(10);
		newXLoc.setMaximumSize(new Dimension(100, 30));
		xLocs.add(newXLoc);
		JTextField newYLoc = new JTextField(10);
		newYLoc.setMaximumSize(new Dimension(100, 30));
		yLocs.add(newYLoc);
		JTextField newNumber = new JTextField(10);
		newNumber.setMaximumSize(new Dimension(100, 30));
		numbers.add(newNumber);
		JButton newButton = new JButton("Delete");
		newButton.addActionListener(this);
		deleteButtons.add(newButton);
		newButton.setActionCommand(
				"Delete " + deleteButtons.indexOf(newButton)
				);
		newPanel.add(newBox);
		newPanel.add(newSpawnType);
		newPanel.add(newXLoc);
		newPanel.add(newYLoc);
		newPanel.add(newNumber);
		newPanel.add(newButton);
		subPanels.add(newPanel);
		listPanel.add(newPanel);
		listPanel.add(addSpawnButton);
		listPanel.add(glue);
		repaint();	
	}

	private void deleteSpawn(int n) {
		//TODO figure out why repaint() removes the deleted panel, 
		//     but doesn't shift others up to fill the space.
		entityTypes.remove(n);
		spawnPatterns.remove(n);
		xLocs.remove(n);
		yLocs.remove(n);
		numbers.remove(n);
		deleteButtons.remove(n);
		for (int i = n; i < deleteButtons.size(); i++) {
			deleteButtons.get(i).setActionCommand("Delete " + i);
		}
		listPanel.remove(subPanels.get(n));
		subPanels.remove(n);
		repaint();
	}
}
