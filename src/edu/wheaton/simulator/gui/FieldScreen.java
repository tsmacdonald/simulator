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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

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

public class FieldScreen extends Screen {

	private static final long serialVersionUID = -4286820591194407735L;

	private JList fields;

	private DefaultListModel listModel;

	private JButton delete;

	private JButton add;

	private JButton edit;

	private static boolean editing;

	//TODO prevent clicking edit when no object is selected 

	public FieldScreen(final SimulatorGuiManager gm) {
		super(gm);
		editing = false;
		JLabel label = GuiUtility.makeLabel("Fields",new PrefSize(300, 100),HorizontalAlignment.CENTER);
		
		this.setLayout(new BorderLayout());
		JPanel mainPanel = GuiUtility.makePanel(BoxLayoutAxis.Y_AXIS,null,null);
		mainPanel.setAlignmentX(CENTER_ALIGNMENT);
		JPanel panel = GuiUtility.makePanel((LayoutManager)null , MaxSize.NULL, new PrefSize(450,550));
		listModel = new DefaultListModel();
		fields = new JList(listModel);
		fields.setBackground(Color.white);
		fields.setPreferredSize(new Dimension(400, 500));
		fields.setFixedCellWidth(400);
		fields.setLayoutOrientation(JList.VERTICAL_WRAP);
		fields.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		fields.setVisibleRowCount(20);
		fields.addListSelectionListener(new ListListener());
		panel.add(fields);
		fields.setAlignmentX(CENTER_ALIGNMENT);
		delete = GuiUtility.makeButton("Delete",new DeleteListener(listModel, fields));
		add = GuiUtility.makeButton("Add",new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae){
				FieldScreen.setEditing(false);
				gm.getScreenManager().getScreen("Edit Fields").load();
				gm.getScreenManager().update(gm.getScreenManager().getScreen("Edit Fields"));
			}
		});
		edit = GuiUtility.makeButton("Edit",new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				FieldScreen.setEditing(true);
				((EditFieldScreen) gm.getScreenManager().getScreen("Edit Fields")).load((String) fields.getSelectedValue());
				gm.getScreenManager().update(gm.getScreenManager().getScreen("Edit Fields"));
			}
		});
		JButton back = GuiUtility.makeButton("Back",
				new GeneralButtonListener("View Simulation", gm.getScreenManager()));
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

	public void reset() {
		listModel.clear();
	}
	@Override
	public void load() {
		reset();
		Map<String, String> map = getGuiManager().getFacade().getGrid().getCustomFieldMap();
		Object[] fieldsA = map.keySet().toArray();
		for(Object s: fieldsA){
			System.out.println((String) s);
			listModel.addElement(s);
		}
		edit.setEnabled(false);
		delete.setEnabled(false);

	}

	private class ListListener implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			edit.setEnabled(getGuiManager().hasStarted() ? false : true); 
			delete.setEnabled(getGuiManager().hasStarted() ? false : true); 
		}
	}

	private class DeleteListener implements ActionListener {

		private DefaultListModel listModel;
		private JList fields;

		public DeleteListener(DefaultListModel listModel, JList fields){
			this.listModel = listModel;
			this.fields = fields;
		}

		@Override
		public void actionPerformed(ActionEvent e){
			int index = fields.getSelectedIndex();
			getGuiManager().getFacade().removeGlobalField((String)fields.getSelectedValue());
			listModel.remove(index);
			int size = listModel.getSize();
			if(size == 0) {
				edit.setEnabled(false);
				delete.setEnabled(false);
				
			}
			if(index == size)
				index--;
			fields.setSelectedIndex(index);
			fields.ensureIndexIsVisible(index);
		}	
	}

	public static boolean getEditing(){
		return editing;
	}
	public static void setEditing(boolean e){
		editing = e;
	}

}