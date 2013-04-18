package edu.wheaton.simulator.gui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

public class GuiList extends JList {

	private static final long serialVersionUID = -7184082010065265787L;
	
	private DefaultListModel listModel;

	public GuiList() {
		super();
		listModel = new DefaultListModel();
		setModel(listModel);
		setBackground(Color.white);
		setBorder(BorderFactory.createLineBorder(Color.black));
		setPreferredSize(new Dimension(400, 500));
		setFixedCellWidth(400);
		setVisibleRowCount(20);
		setLayoutOrientation(JList.VERTICAL_WRAP);
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

	public void addItem(String s) {
		listModel.addElement(s);
	}

	public void clearItems() {
		listModel.clear();
	}

	public int getNumItems() {
		return listModel.getSize();
	}

	public void removeItem(int index) {
		listModel.remove(index);
	}
}
