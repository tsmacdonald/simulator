/**
 * SpawningScreen
 * 
 * Class representing the screen that allows to determine spawning conditions,
 * or spawn new entities during a simulation.
 * 
 * @author Willy McHie
 * Wheaton College, CSCI 335, Spring 2013
 */

package edu.wheaton.simulator.gui.screen;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.wheaton.simulator.gui.BoxLayoutAxis;
import edu.wheaton.simulator.gui.Gui;
import edu.wheaton.simulator.gui.HorizontalAlignment;
import edu.wheaton.simulator.gui.MaxSize;
import edu.wheaton.simulator.gui.MinSize;
import edu.wheaton.simulator.gui.PrefSize;
import edu.wheaton.simulator.gui.ScreenManager;
import edu.wheaton.simulator.gui.SimulatorGuiManager;
import edu.wheaton.simulator.gui.SpawnCondition;
import edu.wheaton.simulator.simulation.Simulator;

public class SpawningScreen extends Screen {
	// TODO how do we handle if spawn are set, and then the grid is made
	// smaller,
	// and some of the spawns are now out of bounds? delete those fields?


	// TODO temporary placeholder
	private String[] spawnOptions = { "Clustered", "Horizontal", "Vertical",
			"Random" };
	private String[] entities;

	private ArrayList<JComboBox> entityTypes;
	private ArrayList<JComboBox> spawnPatterns;
	private ArrayList<JTextField> xLocs;
	private ArrayList<JTextField> yLocs;
	private ArrayList<JTextField> numbers;
	private ArrayList<JButton> deleteButtons;
	private ArrayList<JPanel> subPanels;

	private JButton addSpawnButton;
	private JPanel listPanel;

	private static final long serialVersionUID = 6312784326472662829L;

	public SpawningScreen(final SimulatorGuiManager gm) {
		super(gm);
		this.setLayout(new BorderLayout());
		entities = new String[0];
		deleteButtons = new ArrayList<JButton>();
		subPanels = new ArrayList<JPanel>();

		addSpawnButton = Gui.makeButton("Add Spawn",null,
			new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					addSpawn();
				}
		});
		addSpawnButton.setAlignmentX(CENTER_ALIGNMENT);
		
		JButton cancelButton = Gui.makeButton("Cancel",null,
			new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					ScreenManager sm = gm.getScreenManager();
					sm.update(sm.getScreen("Edit Simulation"));
				}
		});
		
		entityTypes = new ArrayList<JComboBox>();
		spawnPatterns = new ArrayList<JComboBox>();
		xLocs = new ArrayList<JTextField>();
		yLocs = new ArrayList<JTextField>();
		numbers = new ArrayList<JTextField>();
		JButton finishButton = Gui.makeButton("Finish",null,
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							for (int i = 0; i < xLocs.size(); i++) {
								if (Integer.parseInt(xLocs.get(i).getText()) < 0
										|| Integer.parseInt(yLocs.get(i)
												.getText()) < 0
										|| Integer.parseInt(numbers.get(i)
												.getText()) < 0) {
									throw new Exception(
											"Coordinates and numbers must be integers greater at least 0");
								}
							}
							ArrayList<SpawnCondition> conditions = gm
									.getSpawnConditions();
							conditions.clear();
							for (int i = 0; i < entityTypes.size(); i++) {
								gm.getSim();
								SpawnCondition condition = new SpawnCondition(
										Simulator
												.getPrototype(((String) entityTypes
														.get(i)
														.getSelectedItem())),
										Integer.parseInt(xLocs.get(i)
												.getText()), Integer
												.parseInt(yLocs.get(i)
														.getText()), Integer
												.parseInt(numbers.get(i)
														.getText()),
										(String) spawnPatterns.get(i)
												.getSelectedItem());
								gm.getSpawnConditions().add(condition);
							}
							gm.getScreenManager().update(
									gm.getScreenManager().getScreen(
											"Edit Simulation"));
						} catch (NumberFormatException excep) {
							JOptionPane
									.showMessageDialog(null,
											"Coordinates and numbers must be integers greater than 0");
							excep.printStackTrace();
						} catch (Exception excep) {
							JOptionPane.showMessageDialog(null,
									excep.getMessage());
						}
					}
				});
		
		JLabel label = Gui.makeLabel("Spawning",
				new PrefSize(300, 150), HorizontalAlignment.CENTER);
		this.add(label, BorderLayout.NORTH);
		
		JPanel labelsPanel = Gui.makePanel(BoxLayoutAxis.X_AXIS, null,null,
			Box.createHorizontalGlue(),
			Box.createHorizontalGlue(),
			Box.createHorizontalGlue(),
			Gui.makeLabel("Entity Type", new PrefSize(
					200, 30), HorizontalAlignment.CENTER),
			Box.createHorizontalGlue(),
			Gui.makeLabel("Spawn Pattern",
					new PrefSize(270, 30), HorizontalAlignment.CENTER),
			Box.createHorizontalGlue(),
			Gui.makeLabel("x Loc.", new PrefSize(100, 30),
					null),
			Gui.makeLabel("Y Loc.", new PrefSize(100, 30),
					null),
			Gui.makeLabel("Number", new PrefSize(290,
					30), null),
			Box.createHorizontalGlue()
		);
		labelsPanel.setAlignmentX(CENTER_ALIGNMENT);
		
		listPanel = Gui.makePanel(BoxLayoutAxis.Y_AXIS, null, null,
			addSpawnButton,Box.createVerticalGlue());

		this.add(Gui.makePanel(BoxLayoutAxis.Y_AXIS, null,
				null,labelsPanel,listPanel), BorderLayout.CENTER
		);
		this.add(Gui.makePanel(cancelButton,finishButton), BorderLayout.SOUTH);
	}

	public void reset() {
		entityTypes.clear();
		spawnPatterns.clear();
		xLocs.clear();
		yLocs.clear();
		numbers.clear();
		subPanels.clear();
		deleteButtons.clear();
		listPanel.removeAll();
	}

	@Override
	public void load() {
		reset();
		entities = Simulator.prototypeNames().toArray(entities);
		ArrayList<SpawnCondition> spawnConditions = getGuiManager()
				.getSpawnConditions();

		for (int i = 0; i < spawnConditions.size(); i++) {
			addSpawn();
			entityTypes.get(i).setSelectedItem(
					spawnConditions.get(i).prototype.getName());
			spawnPatterns.get(i).setSelectedItem(
					spawnConditions.get(i).pattern);
			xLocs.get(i).setText(spawnConditions.get(i).x + "");
			yLocs.get(i).setText(spawnConditions.get(i).y + "");
			numbers.get(i).setText(spawnConditions.get(i).number + "");
		}
		if (spawnConditions.size() == 0)
			addSpawn();
		validate();
	}

	private void addSpawn() {
		JComboBox newBox = Gui.makeComboBox(entities, new MaxSize(250,
				30));
		entityTypes.add(newBox);
		
		JComboBox newSpawnType = Gui.makeComboBox(spawnOptions,
				new MaxSize(250, 30));
		newSpawnType.addItemListener(new PatternListener(spawnPatterns
				.indexOf(newSpawnType)));
		spawnPatterns.add(newSpawnType);
		
		JTextField newXLoc = Gui.makeTextField("0", 10, new MaxSize(
				100, 30), MinSize.NULL);
		xLocs.add(newXLoc);
		
		JTextField newYLoc = Gui.makeTextField("0", 10, new MaxSize(
				100, 30), null);
		yLocs.add(newYLoc);

		JTextField newNumber = Gui.makeTextField("1", 10, new MaxSize(
				100, 30), MinSize.NULL);
		numbers.add(newNumber);
		
		JButton newButton = Gui.makeButton("Delete",null,
				new DeleteListener());
		deleteButtons.add(newButton);
		newButton.setActionCommand(deleteButtons.indexOf(newButton) + "");
		
		JPanel newPanel = Gui.makePanel(BoxLayoutAxis.X_AXIS, null,
				null,newBox,newSpawnType,newXLoc,newYLoc,newNumber,newButton);
		subPanels.add(newPanel);
		listPanel.add(newPanel);
		listPanel.add(addSpawnButton);
		listPanel.add(Box.createVerticalGlue());
		listPanel.validate();
		repaint();
	}

	private void deleteSpawn(int n) {
		entityTypes.remove(n);
		((PatternListener) spawnPatterns.get(n).getItemListeners()[0])
				.setNum(n);
		spawnPatterns.remove(n);
		xLocs.remove(n);
		yLocs.remove(n);
		numbers.remove(n);
		deleteButtons.remove(n);
		for (int i = n; i < deleteButtons.size(); i++) {
			deleteButtons.get(i).setActionCommand(i + "");
			((PatternListener) spawnPatterns.get(i).getItemListeners()[0])
					.setNum(i);
		}
		listPanel.remove(subPanels.get(n));
		subPanels.remove(n);
		listPanel.validate();
		repaint();
	}

	private class DeleteListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String action = e.getActionCommand();
			deleteSpawn(Integer.parseInt(action));
		}
	}

	private class PatternListener implements ItemListener {

		private int n;

		public PatternListener(int n) {
			this.n = n;
		}

		public void setNum(int n) {
			this.n = n;
		}

		@Override
		public void itemStateChanged(ItemEvent e) {
			if (((String) e.getItem()).equals("Random")) {
				xLocs.get(n).setEnabled(false);
				yLocs.get(n).setEnabled(false);
			} else {
				xLocs.get(n).setEnabled(true);
				yLocs.get(n).setEnabled(true);
			}
			repaint();
		}
	}
}
