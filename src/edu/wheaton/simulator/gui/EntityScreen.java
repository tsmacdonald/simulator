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
		JLabel label = makeLabelPreferredSize("Entities",300, 100);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		this.setLayout(new BorderLayout());
		JPanel mainPanel = makeBoxPanel(BoxLayout.Y_AXIS);
		mainPanel.setAlignmentX(CENTER_ALIGNMENT);
		JPanel listPanel = new JPanel();
		//listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
		listPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		listPanel.setAlignmentX(CENTER_ALIGNMENT);
		listPanel.setPreferredSize(new Dimension(450, 550));
		listModel = new DefaultListModel();
		entityList = new JList(listModel);
		entityList.setBackground(Color.white);
		entityList.setPreferredSize(new Dimension(400, 500));
		entityList.setLayoutOrientation(JList.VERTICAL_WRAP);
		entityList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		entityList.setFixedCellWidth(400);
		entityList.setVisibleRowCount(20);
		entityList.setBorder(BorderFactory.createLineBorder(Color.red));
		entityList.addListSelectionListener( new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent le){
				if(!edit.isEnabled())
					edit.setEnabled(true);
			}
		});
		listPanel.add(entityList);
		listPanel.setAlignmentX(CENTER_ALIGNMENT);
		delete = makeButton("Delete",new DeleteListener());
		JButton add = makeButton("Add",new AddListener(sm));
		edit = makeButton("Edit",new EditListener(sm));
		edit.setEnabled(false);
		entityList.addListSelectionListener(
				new ListSelectionListener() {
					@Override
					public void valueChanged(ListSelectionEvent e) {
						edit.setEnabled(sm.hasStarted() ? false : true);
					}
				}
				);
		JButton back = makeButton("Back",new BackListener(sm));
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
			sm.update(sm.getScreen("Edit Simulation"));
		}
	}

}
