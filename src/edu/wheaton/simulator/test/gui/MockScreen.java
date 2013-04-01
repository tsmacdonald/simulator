package edu.wheaton.simulator.test.gui;

import edu.wheaton.simulator.gui.Screen;
import edu.wheaton.simulator.gui.ScreenManager;

public class MockScreen extends Screen {
	
	private static final long serialVersionUID = 1L;

	public MockScreen(ScreenManager sm) {
		super(sm);
	}

	public void load() {
		return;
	}
}