package edu.wheaton.simulator.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;

public abstract class SimScreenListener implements ActionListener {

	protected JList entities;
	protected DefaultListModel listModel;
	protected JButton button;
	protected ScreenManager sm;
	
	public SimScreenListener(JList entities, DefaultListModel listModel, 
							 JButton button, ScreenManager sm){
		this.entities = entities;
		this.listModel = listModel;
		this.button = button;
		this.sm = sm;
	}
	
	public abstract void actioPerformed(ActionEvent e);
}
