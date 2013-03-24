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
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
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

public class EntityScreen extends Screen {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8471925846048875713L;

	private JList entities;

	private DefaultListModel listModel;
	
	private JButton delete;
	
	public EntityScreen(final ScreenManager sm) {
		super(sm);
		JLabel label = new JLabel("Entities");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setPreferredSize(new Dimension(300, 100));
		this.setLayout(new BorderLayout());
		JPanel mainPanel = new JPanel(new GridLayout(2, 1));
		mainPanel.setAlignmentX(CENTER_ALIGNMENT);
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		listModel = new DefaultListModel();
		listModel.addElement("Entity 1");
		listModel.addElement("Entity 2");
		listModel.addElement("Entity 3");
		entities = new JList(listModel);
		entities.setMaximumSize(new Dimension(400, 800));
		entities.setLayoutOrientation(JList.VERTICAL_WRAP);
		entities.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		entities.setVisibleRowCount(20);
		panel.add(entities);
		entities.setAlignmentX(CENTER_ALIGNMENT);
		delete = new JButton("Delete");
		delete.addActionListener(this);
		JButton add = new JButton("Add");
		add.addActionListener(this);
		JButton edit = new JButton("Edit");
		edit.addActionListener(this);
		JButton back = new JButton("Back");
		back.addActionListener(this);
		JPanel buttonPanel = new JPanel(new FlowLayout());
		buttonPanel.add(add);
		buttonPanel.add(Box.createHorizontalStrut(5));
		buttonPanel.add(edit);
		buttonPanel.add(Box.createHorizontalStrut(5));
		buttonPanel.add(delete);
		buttonPanel.add(Box.createHorizontalStrut(5));
		buttonPanel.add(back);
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		mainPanel.add(panel);
		mainPanel.add(buttonPanel);
		this.add(label, BorderLayout.NORTH);
		this.add(mainPanel, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals("Delete")) {
			int index = entities.getSelectedIndex();
			listModel.remove(index);
			int size = listModel.getSize();
			if(size == 0)
				delete.setEnabled(false);
			if(index == size)
				index--;
			entities.setSelectedIndex(index);
			entities.ensureIndexIsVisible(index);
		} else if (action.equals("Add")) {
			sm.update(sm.getScreen("Edit Entities"));
		} else if (action.equals("Edit")) {
			//TODO call EditEntitiesScreen's load on the selected entity
			sm.update(sm.getScreen("Edit Entities"));
		} else if (action.equals("Back")) {
			sm.update(sm.getScreen("Edit Simulation"));
		} else
			System.out.println("Error with EntityListener");
	}

	//TODO need load method, which should just update listModel
	
	@Override
	public void sendInfo() {
		// TODO Auto-generated method stub

	}

}
