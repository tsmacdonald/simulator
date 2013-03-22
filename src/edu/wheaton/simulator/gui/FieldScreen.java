/**
 * FieldScreen
 * 
 * Class representing the screen that manages all user interactions 
 * pertaining to local and global fields.
 * 
 * @author Willy McHie
 * Wheaton College, CSCI 335, Spring 2013
 */

package edu.wheaton.simulator.gui;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class FieldScreen extends Screen {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4286820591194407735L;

	private JList fields;
	
	private DefaultListModel listModel;

	private JButton delete;
	
	private JButton add;
	
	private JButton edit;
	
	private JButton back;

	private JPanel buttonPanel;
	
	public FieldScreen(ScreenManager sm) {
		super(sm);
		label = new JLabel("Fields");
		label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		label.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		layout = new GridLayout(3, 1);
		listModel = new DefaultListModel();
		listModel.addElement("Field 1");
		listModel.addElement("Field 2");
		listModel.addElement("Field 3");
		fields = new JList(listModel);
		fields.setLayoutOrientation(JList.VERTICAL_WRAP);
		fields.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		fields.setVisibleRowCount(3);
		delete = new JButton("Delete");
		delete.addActionListener(this);
		add = new JButton("Add");
		add.addActionListener(this);
		edit = new JButton("Edit");
		edit.addActionListener(this);
		back = new JButton("Back");
		back.addActionListener(this);
		buttonPanel = new JPanel(new FlowLayout());
		buttonPanel.add(add);
		buttonPanel.add(Box.createHorizontalStrut(5));
		buttonPanel.add(edit);
		buttonPanel.add(Box.createHorizontalStrut(5));
		buttonPanel.add(delete);
		buttonPanel.add(Box.createHorizontalStrut(5));
		buttonPanel.add(back);
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		components = new JComponent[3];
		components[0] = label;
		components[1] = fields;
		components[2] = buttonPanel;
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals("Delete")) {
			int index = fields.getSelectedIndex();
			listModel.remove(index);
			int size = listModel.getSize();
			if(size == 0)
				delete.setEnabled(false);
			if(index == size)
				index--;
			fields.setSelectedIndex(index);
			fields.ensureIndexIsVisible(index);
		} else if (action.equals("Add")) {
			sm.update(sm.getScreen("Edit Fields"));
		} else if (action.equals("Edit")) {
			sm.update(sm.getScreen("Edit Fields"));
		} else if (action.equals("Back")) {
			sm.update(sm.getScreen("Edit Simulation"));
		} else
			System.out.println("Error with FieldListener");
	}

	@Override
	public void sendInfo() {
		// TODO Auto-generated method stub

	}
	
	private class FieldListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			//TODO
			
		}
	}

}
