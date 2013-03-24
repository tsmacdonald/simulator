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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

public class FieldScreen extends Screen {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4286820591194407735L;

	private JList fields;

	private DefaultListModel listModel;

	private JButton delete;

	//TODO I think this page may need to be reworked a bit based on how 
	//     slot fields actually work. I'll do that. -Willy

	public FieldScreen(ScreenManager sm) {
		super(sm);
		JLabel label = new JLabel("Fields");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setPreferredSize(new Dimension(300, 100));
		this.setLayout(new BorderLayout());
		JPanel mainPanel = new JPanel(new GridLayout(2, 1));
		mainPanel.setAlignmentX(CENTER_ALIGNMENT);
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		listModel = new DefaultListModel();
		listModel.addElement("Field 1");
		listModel.addElement("Field 2");
		listModel.addElement("Field 3");
		fields = new JList(listModel);
		fields.setMaximumSize(new Dimension(400, 800));
		fields.setLayoutOrientation(JList.VERTICAL_WRAP);
		fields.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		fields.setVisibleRowCount(20);
		panel.add(fields);
		fields.setAlignmentX(CENTER_ALIGNMENT);
		delete = new JButton("Delete");
		delete.addActionListener(new DeleteListener(listModel, fields, delete));
		JButton add = new JButton("Add");
		add.addActionListener(new GeneralButtonListener("Edit Fields", sm));
		JButton edit = new JButton("Edit");
		edit.addActionListener(new GeneralButtonListener("Edit Fields", sm));
		JButton back = new JButton("Back");
		back.addActionListener(new GeneralButtonListener("Edit Simulation", sm));
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

	//TODO need load and reset methods

	//TODO can this be an anonymous inner class when adding the listener? 
	//     or would that look bad? Is this class even necessary?

	private class DeleteListener implements ActionListener {
		
		private DefaultListModel listModel;
		private JList fields;
		private JButton delete;

		public DeleteListener(DefaultListModel listModel, JList fields, JButton delete){
			this.listModel = listModel;
			this.fields = fields;
			this.delete = delete;
		}
		
		public void actionPerformed(ActionEvent e){
			int index = fields.getSelectedIndex();
			listModel.remove(index);
			int size = listModel.getSize();
			if(size == 0)
				delete.setEnabled(false);
			if(index == size)
				index--;
			fields.setSelectedIndex(index);
			fields.ensureIndexIsVisible(index);
		}	
	}

}