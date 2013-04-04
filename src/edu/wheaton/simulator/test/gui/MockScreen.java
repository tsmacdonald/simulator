package edu.wheaton.simulator.test.gui;

import edu.wheaton.simulator.gui.Manager;
import edu.wheaton.simulator.gui.Screen;

public class MockScreen extends Screen implements Comparable {
	
	private static final long serialVersionUID = 1L;
	
	private boolean load = false;
	private String name;

	public MockScreen(String name, Manager sm) {
		super(sm);
		this.name = name;
	}

	@Override
	public void load() {
		load = true;
		return;
	}
	
	public boolean getLoad(){
		return load;
	}
	
	@Override
	public String getName(){
		return name;
	}

	@Override
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}