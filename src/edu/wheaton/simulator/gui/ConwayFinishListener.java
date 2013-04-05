package edu.wheaton.simulator.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;

public class ConwayFinishListener implements ActionListener {
	
	private JTextField name;
	private JTextField width;
	private JTextField height;
	private Manager sm;
	
	public ConwayFinishListener(JTextField name, JTextField width, JTextField height, 
									  Manager sm){
		this.name = name;
		this.width = width;
		this.height = height;
		this.sm = sm;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			int heightInt = getHeight();
			int widthInt = getWidth();
			sm.setFacade(widthInt, heightInt);
			sm.updateGUIManager(getName(), widthInt, heightInt);
		} catch(java.lang.NumberFormatException nfe) { 
			System.err.println("Invalid input passed to ConwayFinishListener");
		}
		Screen upload = sm.getScreen("Edit Simulation");
		//TODO Is there anything else that needs to happen here?
		sm.getFacade().initGameOfLife();
		sm.update(upload);
		sm.loadScreen(upload);
	}
	public String getName(){
		return name.getText();
	}
	public int getHeight(){
		return Integer.parseInt(height.getText());
	}
	public int getWidth(){
		return Integer.parseInt(width.getText());
	}
}

