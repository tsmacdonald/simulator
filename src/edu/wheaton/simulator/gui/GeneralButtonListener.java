package edu.wheaton.simulator.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wheaton.simulator.gui.screen.Screen;

public class GeneralButtonListener implements ActionListener {

	private String screenName;
	private ScreenManager sm;

	public GeneralButtonListener(String screenName, ScreenManager screenManager) {
		this.screenName = screenName;
		this.sm = screenManager;
	}

	@Override
	public void actionPerformed(ActionEvent e){
		Screen toUpload = sm.getScreen(screenName);
		sm.update(toUpload);
		ScreenManager.loadScreen(toUpload);
	}
	
}
