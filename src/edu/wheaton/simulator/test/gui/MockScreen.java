package edu.wheaton.simulator.test.gui;

import edu.wheaton.simulator.gui.Manager;
import edu.wheaton.simulator.gui.Screen;

public class MockScreen extends Screen {
	
	private static final long serialVersionUID = 1L;
	
	private boolean load = false;

	public MockScreen(Manager sm) {
		super(sm);
	}

	public void load() {
		load = true;
		return;
	}
	
	public boolean getLoad(){
		return load;
	}
	
}