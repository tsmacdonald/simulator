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

	//	private JComboBox xPos;
	//
	//	private JComboBox yPos;

	private JList fields;

	private DefaultListModel listModel;

	private JButton delete;

	private JButton add;

	private JButton edit;

	private static boolean editing;

	//TODO prevent clicking edit when no object is selected 

	public FieldScreen(final ScreenManager sm) {
		super(sm);
		editing = false;
		JLabel label = GuiUtility.makeLabel("Fields",new PrefSize(300, 100),HorizontalAlignment.CENTER);
		
		this.setLayout(new BorderLayout());
		JPanel mainPanel = GuiUtility.makePanel(BoxLayoutAxis.Y_AXIS,null,null);
		mainPanel.setAlignmentX(CENTER_ALIGNMENT);
		JPanel panel = GuiUtility.makePanel((LayoutManager)null , MaxSize.NULL, new PrefSize(450,550));
		//panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		//		JLabel xLabel = new JLabel("X Pos: ");
		//		xPos = new JComboBox();
		//		xPos.setMaximumSize(new Dimension(150, 40));
		//		xPos.addItem(0);
		//		xPos.addActionListener(new BoxListener());
		//		JLabel yLabel = new JLabel("Y Pos: ");
		//		yPos = new JComboBox();
		//		yPos.setMaximumSize(new Dimension(150, 40));
		//		yPos.addItem(0);
		//		yPos.addActionListener(new BoxListener());
		//		JPanel posPanel = new JPanel();
		//		posPanel.setLayout(new BoxLayout(posPanel, BoxLayout.X_AXIS));
		//		posPanel.add(xLabel);
		//		posPanel.add(xPos);
		//		posPanel.add(yLabel);
		//		posPanel.add(yPos);
		//		posPanel.setAlignmentX(CENTER_ALIGNMENT);
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
		add = GuiUtility.makeButton("Add",new FieldAddListener(sm));
		edit = GuiUtility.makeButton("Edit",new FieldEditListener(sm, fields));
		JButton back = GuiUtility.makeButton("Back",
				new GeneralButtonListener("View Simulation", sm));
		JPanel buttonPanel = new JPanel(new FlowLayout());
		buttonPanel.add(add);
		buttonPanel.add(Box.createHorizontalStrut(5));
		buttonPanel.add(edit);
		buttonPanel.add(Box.createHorizontalStrut(5));
		buttonPanel.add(delete);
		buttonPanel.add(Box.createHorizontalStrut(5));
		buttonPanel.add(back);
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		//mainPanel.add(posPanel);
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

		//		if (xPos.getItemCount() != GUI.getGridWidth()) {
		//			xPos.removeAllItems();
		//			for (int i = 0; i < GUI.getGridWidth(); i++) {
		//				xPos.addItem(i + "");
		//			}
		//		}
		//		if (yPos.getItemCount() != GUI.getGridHeight()) {
		//			yPos.removeAllItems();
		//			for (int j = 0; j < GUI.getGridWidth(); j++) {
		//				yPos.addItem(j + "");
		//			}
		//		}
		Map<String, String> map = sm.getFacade().getGrid()
				//				.getSlot(Integer.parseInt(xPos.getSelectedItem().toString()),Integer.parseInt(yPos.getSelectedItem().toString()))
				.getCustomFieldMap();
		Object[] fieldsA = map.keySet().toArray();
		for(Object s: fieldsA){
			System.out.println((String) s);
			listModel.addElement(s);
		}
		edit.setEnabled(false);
		delete.setEnabled(false);

	}

	//	private class BoxListener implements ActionListener {
	//		@Override
	//		public void actionPerformed(ActionEvent arg0) {
	//			if (xPos.getSelectedIndex() >= 0 && yPos.getSelectedIndex() >= 0) {
	//				Map<String, String> fieldNames = sm.getFacade().getGrid().getSlot(
	//						xPos.getSelectedIndex(), yPos.getSelectedIndex()
	//						).getCustomFieldMap();
	//				listModel.clear();
	//				if (fieldNames != null) {
	//					for (String s : fieldNames.keySet()) {
	//						listModel.addElement(s);
	//					}
	//				}
	//			}
	//			edit.setEnabled(false);
	//		}
	//	}

	private class ListListener implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			edit.setEnabled(sm.hasStarted() ? false : true); 
			delete.setEnabled(sm.hasStarted() ? false : true); 
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
			sm.getFacade().removeGlobalField((String)fields.getSelectedValue());
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