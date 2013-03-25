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
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class EntityScreen extends Screen {

	private static final long serialVersionUID = 8471925846048875713L;

	private JList entities;

	private DefaultListModel listModel;
	
	private JButton delete;
	
	private JButton edit;
	
	public EntityScreen(final ScreenManager sm) {
		super(sm);
		JLabel label = new JLabel("Entities");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setPreferredSize(new Dimension(300, 100));
		this.setLayout(new BorderLayout());
		JPanel mainPanel = new JPanel(new GridLayout(2, 1));
		mainPanel.setAlignmentX(CENTER_ALIGNMENT);
		JPanel listPanel = new JPanel();
		listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
		listPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		listPanel.setAlignmentX(CENTER_ALIGNMENT);
		listModel = new DefaultListModel();
		listModel.addElement("Entity 1");
		listModel.addElement("Entity 2");
		listModel.addElement("Entity 3");
		entities = new JList(listModel);
		entities.setSize(new Dimension(400, 800));
		entities.setLayoutOrientation(JList.VERTICAL_WRAP);
		entities.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		entities.setVisibleRowCount(20);
		entities.setBorder(BorderFactory.createLineBorder(Color.red));
		listPanel.add(entities);
		entities.setAlignmentX(CENTER_ALIGNMENT);
		delete = new JButton("Delete");
		delete.addActionListener(new DeleteListener());
		JButton add = new JButton("Add");
		add.addActionListener(new AddListener());
		edit = new JButton("Edit");
		edit.addActionListener(new EditListener(sm));
		edit.setEnabled(false);
		entities.addListSelectionListener(
				new ListSelectionListener() {
					@Override
					public void valueChanged(ListSelectionEvent e) {
						edit.setEnabled(true);
					}
				}
				);
		JButton back = new JButton("Back");
		back.addActionListener(new BackListener(sm));
		JPanel buttonPanel = new JPanel(new FlowLayout());
		buttonPanel.add(add);
		buttonPanel.add(Box.createHorizontalStrut(5));
		buttonPanel.add(edit);
		buttonPanel.add(Box.createHorizontalStrut(5));
		buttonPanel.add(delete);
		buttonPanel.add(Box.createHorizontalStrut(5));
		buttonPanel.add(back);
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		mainPanel.add(listPanel);
		mainPanel.add(buttonPanel);
		this.add(label, BorderLayout.NORTH);
		this.add(mainPanel, BorderLayout.CENTER);
	}
	
	public void reset() {
		listModel.clear();
	}
	
	@Override
	public void load() {
		reset();
		Set<String> entities = sm.getFacade().prototypeNames();
		for (String s : entities) {
			listModel.addElement(s);
		}
	}
	
	class DeleteListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e){
			int index = entities.getSelectedIndex();
			listModel.remove(index);
			int size = listModel.getSize();
			if(size == 0)
				delete.setEnabled(false);
			if(index == size)
				index--;
			entities.setSelectedIndex(index);
			entities.ensureIndexIsVisible(index);
		}
	}
	
	class AddListener implements ActionListener {
		
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
					(String)entities.getSelectedValue());
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
			sm.update(sm.getScreen("Edit Simulation"));
		}
	}

}
