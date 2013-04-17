package edu.wheaton.simulator.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;

public class NewSimScreenFinishListener implements ActionListener {
	
	private JTextField name;
	private JTextField width;
	private JTextField height;
	private ScreenManager sm;
	
	public NewSimScreenFinishListener(JTextField name, JTextField width, JTextField height, 
									  ScreenManager sm){
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
			String nameStr = getName();
			sm.setFacade(nameStr, widthInt, heightInt);
			sm.updateGUIManager(getName(), widthInt, heightInt);
			sm.getEnder().setStepLimit(1000);
		} catch(java.lang.NumberFormatException nfe) { 
			System.err.println("Invalid input passed to NewSimScreenFinishListener");
		}
		Screen upload = sm.getScreen("View Simulation");
		sm.setStarted(false);
		sm.getFacade().initSamples();
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
