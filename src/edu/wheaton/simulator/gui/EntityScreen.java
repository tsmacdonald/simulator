/**
 * EntityScreen
 * 
 * Class representing the screen that manages user interactions 
 * pertaining to grid entities, including triggers and appearance.
 * 
 * @author Willy McHie
 * Wheaton College, CSCI 335, Spring 2013
 */

package edu.wheaton.simulator.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import edu.wheaton.simulator.entity.Prototype;
import edu.wheaton.simulator.simulation.Simulator;

public class EntityScreen extends Screen {

	private static final long serialVersionUID = 8471925846048875713L;

	private JList entityList;

	private DefaultListModel listModel;
	
	private JButton delete;
	
	private JButton edit;
	
	public EntityScreen(final SimulatorGuiManager gm) {
		super(gm);
		JLabel label = GuiUtility.makeLabel("Entities",new PrefSize(300, 100),HorizontalAlignment.CENTER);
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		listModel = new DefaultListModel();
		entityList = new JList(listModel);
		entityList.setBackground(Color.white);
		entityList.setPreferredSize(new Dimension(400, 500));
		entityList.setLayoutOrientation(JList.VERTICAL_WRAP);
		entityList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		entityList.setFixedCellWidth(400);
		entityList.setVisibleRowCount(20);
		entityList.setBorder(BorderFactory.createLineBorder(Color.RED));
		entityList.addListSelectionListener( new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent le){
					edit.setEnabled(!gm.hasStarted());
			}
		});
		delete = GuiUtility.makeButton("Delete",new DeleteListener());
		JButton add = GuiUtility.makeButton("Add",new AddListener(gm.getScreenManager()));
		edit = GuiUtility.makeButton("Edit",new EditListener(gm.getScreenManager()));
		edit.setEnabled(false);
		entityList.addListSelectionListener(
				new ListSelectionListener() {
					@Override
					public void valueChanged(ListSelectionEvent e) {
						edit.setEnabled(!gm.hasStarted());
					}
				}
				);
		JButton back = GuiUtility.makeButton("Back",new BackListener(gm.getScreenManager()));

		//formatting needs a little work but this is now in GridBagLayout 
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		this.add(add, c); 
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 2;
		this.add(edit, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 2;
		this.add(delete, c);
		//The code between this comment and the next
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 3;
		c.gridy = 2;
		this.add(back, c);
		//will be removed once added to tabbed pane
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 4;
		this.add(label, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 4;
		this.add(entityList, c);
		
	}
	
	public void reset() {
		listModel.clear();
	}
	
	@Override
	public void load() {
		reset();
		delete.setEnabled(getGuiManager().hasStarted() ? false : true); 
		getGuiManager().getFacade();
		Set<String> entities = Simulator.prototypeNames();
		for (String s : entities) {
			listModel.addElement(s);
		}
		edit.setEnabled(false);
	}
	
	class DeleteListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e){
			int index = entityList.getSelectedIndex();
			String toRemove = (String)entityList.getSelectedValue();
			Prototype.removePrototype(toRemove);
			listModel.remove(index);
			int size = listModel.getSize();
			if(size == 0){
				delete.setEnabled(false);
				edit.setEnabled(false);
			}
			if(index == size)
				index--;
			entityList.setSelectedIndex(index);
			entityList.ensureIndexIsVisible(index);
		}
	}
	
	class AddListener implements ActionListener {
		
		private ScreenManager sm;
		
		public AddListener(ScreenManager screenManager){
			this.sm = screenManager;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			((EditEntityScreen)sm.getScreen("Edit Entities")).load();
			((EditEntityScreen)sm.getScreen("Edit Entities")).setEditing(false);
			sm.update(sm.getScreen("Edit Entities"));
		}
	}
	
	class EditListener implements ActionListener {
		
		private ScreenManager sm;
		
		public EditListener(ScreenManager screenManager) {
			this.sm = screenManager;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			((EditEntityScreen)sm.getScreen("Edit Entities")).load(
					(String)entityList.getSelectedValue());
			((EditEntityScreen)sm.getScreen("Edit Entities")).setEditing(true);
			sm.update(sm.getScreen("Edit Entities"));
		}
	}
	
	class BackListener implements ActionListener {
		
		private ScreenManager sm;
		
		public BackListener(ScreenManager screenManager) {
			this.sm = screenManager;
		}
		
		@Override
		public void actionPerformed(ActionEvent e){
			sm.update(sm.getScreen("View Simulation"));
		}
	}

}
