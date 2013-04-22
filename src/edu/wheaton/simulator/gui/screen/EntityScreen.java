/**
 * EntityScreen
 * 
 * Class representing the screen that manages user interactions 
 * pertaining to grid entities, including triggers and appearance.
 * 
 * @author Willy McHie
 * Wheaton College, CSCI 335, Spring 2013
 */

package edu.wheaton.simulator.gui.screen;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import edu.wheaton.simulator.entity.Prototype;
import edu.wheaton.simulator.gui.Gui;
import edu.wheaton.simulator.gui.GuiList;
import edu.wheaton.simulator.gui.HorizontalAlignment;
import edu.wheaton.simulator.gui.PrefSize;
import edu.wheaton.simulator.gui.ScreenManager;
import edu.wheaton.simulator.gui.SimulatorFacade;

public class EntityScreen extends Screen {

	private static final long serialVersionUID = 8471925846048875713L;

	private GuiList entityList;

	private JButton delete;

	private JButton edit;
	
	private JButton save;
	
	private JButton clear;
	
	private JButton fill;

	private JButton random;
	
	public EntityScreen(final SimulatorFacade gm) {
		super(gm);
		this.setLayout(new GridBagLayout());

		entityList = new GuiList();
		entityList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent le) {
				edit.setEnabled(!gm.hasStarted());
			}
		});
		delete = Gui.makeButton("Delete", null, new DeleteListener());
		edit = Gui.makeButton("Edit", null, new EditListener());
		edit.setEnabled(false);
		JButton load = Gui.makeButton("Load", null,  
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						//TODO load agents
					}
		});
		
		JButton importButton = Gui.makeButton("Import", null, 
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						gm.importAgent();
					}
		});
		save = Gui.makeButton("Save Agent", null, 
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						gm.saveAgent((String)entityList.getSelectedValue());
					}
		});
		
		clear = Gui.makeButton("Clear Agents", null,
				new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gm.clearGrid();
			}
		});
		
		fill = Gui.makeButton("Fill Grid", null,
				new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String agentName = getList().getSelectedValue().toString();
				if (agentName != null) {
					gm.fillGrid(agentName);
				}
			}
		});
		
		random = Gui.makeButton("Random Spawn", null,
				new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String agentName = getList().getSelectedValue().toString();
				if (agentName != null) {
					gm.addAgentRandom(agentName);
				}
			}
		});
		
		entityList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				edit.setEnabled(!gm.hasStarted());
				delete.setEnabled(!gm.hasStarted());
				save.setEnabled(!gm.isRunning());
				clear.setEnabled(!gm.isRunning());
				fill.setEnabled(!gm.isRunning());
				((ViewSimScreen) Gui.getScreenManager().getScreen(
						"View Simulation")).setSpawn(true);
			}
		});

		GridBagConstraints c = new GridBagConstraints();

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		this.add(Gui.makeButton("Add", null, new AddListener()), c);

		c.gridx = 1;
		this.add(edit, c);

		c.gridx = 2;
		this.add(delete, c);
		
		c.gridx = 3;
		this.add(clear,c);
		
		c.gridx = 4;
		this.add(fill,c);
		
		c.gridx = 0;
		c.gridy = 3;
		this.add(load, c);
		
		c.gridx = 1;
		this.add(importButton, c);
		
		c.gridx = 2;
		this.add(save, c);
		
		c.gridx = 4;
		this.add(random, c);
		
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 5;
		c.insets = new Insets(5,0,10,0);
		JLabel header = Gui.makeLabel("Agents", PrefSize.NULL,HorizontalAlignment.CENTER);
		this.add(header, c);

		c.gridy = 1;
		c.insets = new Insets(0,0,0,0);
		this.add(entityList, c);
	}

	public void reset() {
		entityList.clearItems();
	}

	@Override
	public void load() {
		reset();
		delete.setEnabled(getGuiManager().hasStarted() ? false : true);
		Set<String> entities = gm.getPrototypeNames();
		for (String s : entities)
			entityList.addItem(s);
		edit.setEnabled(false);
		delete.setEnabled(false);
		save.setEnabled(false);
		entityList.setSelectedIndex(0);
		validate();
	}

	public GuiList getList() {
		return entityList;
	}

	class DeleteListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			int index = entityList.getSelectedIndex();
			Prototype.removePrototype((String) entityList.getSelectedValue());
			entityList.removeItem(index);
			int size = entityList.getNumItems();
			if (size == 0) {
				delete.setEnabled(false);
				edit.setEnabled(false);
				save.setEnabled(false);
			}
			if (index == size)
				index--;
			entityList.setSelectedIndex(index);
			entityList.ensureIndexIsVisible(index);
		}
	}

	class AddListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			ScreenManager sm = getScreenManager();
			EditEntityScreen screen = (EditEntityScreen) sm
					.getScreen("Edit Entities");
			sm.load(screen);
			screen.setEditing(false);
		}
	}

	class EditListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			ScreenManager sm = getScreenManager();
			EditEntityScreen screen = (EditEntityScreen) sm
					.getScreen("Edit Entities");
			screen.load((String) entityList.getSelectedValue());
			screen.setEditing(true);
			sm.load(screen);
		}
	}
}
