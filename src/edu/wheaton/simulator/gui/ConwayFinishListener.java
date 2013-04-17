package edu.wheaton.simulator.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;

import edu.wheaton.simulator.gui.screen.Screen;

public class ConwayFinishListener implements ActionListener {
	
	private JTextField name;
	private JTextField width;
	private JTextField height;
	private SimulatorGuiManager gm;
	
	public ConwayFinishListener(JTextField name, JTextField width, JTextField height, 
			SimulatorGuiManager gm){
		this.name = name;
		this.width = width;
		this.height = height;
		this.gm = gm;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			int heightInt = getHeight();
			int widthInt = getWidth();
			gm.setSim(widthInt, heightInt);
			gm.updateGUIManager(getName(), widthInt, heightInt);
		} catch(java.lang.NumberFormatException nfe) { 
			System.err.println("Invalid input passed to ConwayFinishListener");
		}

		Screen upload = gm.getScreenManager().getScreen("View Simulation");
		gm.getSim().initGameOfLife();
		gm.getEnder().setStepLimit(1000);
		gm.setStarted(false);
		gm.getScreenManager().update(upload);
		ScreenManager.loadScreen(upload);
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

