package edu.wheaton.simulator.test.gui;

import edu.wheaton.simulator.gui.Display;
import edu.wheaton.simulator.gui.Manager;
import edu.wheaton.simulator.gui.Screen;

public class MockManager implements Manager {
	private Display mockDisplay;
	public MockManager(Display display){
		mockDisplay = display;
	}
	public void update(Screen s){
		mockDisplay.updateDisplay(s);
	}
}
