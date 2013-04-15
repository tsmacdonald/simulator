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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import edu.wheaton.simulator.simulation.GUIToAgentFacade;

public class EntityScreen extends Screen {

	private static final long serialVersionUID = 8471925846048875713L;

	private JList entityList;

	private DefaultListModel listModel;
	
	private JButton delete;
	
	private JButton edit;
	
	public EntityScreen(final ScreenManager sm) {
		super(sm);
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
				if(!edit.isEnabled())
					edit.setEnabled(true);
			}
		});
		delete = GuiUtility.makeButton("Delete",new DeleteListener());
		JButton add = GuiUtility.makeButton("Add",new AddListener(sm));
		edit = GuiUtility.makeButton("Edit",new EditListener(sm));
		edit.setEnabled(false);
		entityList.addListSelectionListener(
				new ListSelectionListener() {
					@Override
					public void valueChanged(ListSelectionEvent e) {
						edit.setEnabled(sm.hasStarted() ? false : true);
					}
				}
				);
		JButton back = GuiUtility.makeButton("Back",new BackListener(sm));

		//formatting needs a little work but this is now in GridBagLayout 
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 0;
		c.gridy = 2;
		this.add(add, c); 
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 0;
		c.gridy = 2;
		this.add(edit, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 0;
		c.gridy = 2;
		this.add(delete, c);
		//The code between this comment and the next
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 0;
		c.gridy = 2;
		this.add(back, c);
		//will be removed once added to tabbed pane
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridy = 0;
		this.add(label, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridy = 1;
		this.add(entityList, c);
		
	}
	
	public void reset() {
		listModel.clear();
	}
	
	@Override
	public void load() {
		reset();
		edit.setEnabled(false);
		delete.setEnabled(sm.hasStarted() ? false : true); 
		sm.getFacade();
		Set<String> entities = GUIToAgentFacade.prototypeNames();
		for (String s : entities) {
			listModel.addElement(s);
		}
	}
	
	class DeleteListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e){
			int index = entityList.getSelectedIndex();
			listModel.remove(index);
			//need to delete prototype here -- Caleb
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
		
		public AddListener(ScreenManager sm){
			this.sm = sm;
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
		
		public EditListener(ScreenManager sm) {
			this.sm = sm;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			//TODO replace this with the load() method on the selected entity
			((EditEntityScreen)sm.getScreen("Edit Entities")).load(
					(String)entityList.getSelectedValue());
			((EditEntityScreen)sm.getScreen("Edit Entities")).setEditing(true);
			sm.update(sm.getScreen("Edit Entities"));
		}
	}
	
	class BackListener implements ActionListener {
		
		private ScreenManager sm;
		
		public BackListener(ScreenManager sm) {
			this.sm = sm;
		}
		
		@Override
		public void actionPerformed(ActionEvent e){
			sm.update(sm.getScreen("View Simulation"));
		}
	}

}
