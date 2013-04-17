package edu.wheaton.simulator.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GeneralButtonListener implements ActionListener {

	private String screenName;
	private ScreenManager sm;

	public GeneralButtonListener(String screenName, ScreenManager sm) {
		this.screenName = screenName;
		this.sm = sm;
	}

	@Override
	public void actionPerformed(ActionEvent e){
		Screen toUpload = sm.getScreen(screenName);
		sm.update(toUpload);
		sm.loadScreen(toUpload);
	}
	
}
