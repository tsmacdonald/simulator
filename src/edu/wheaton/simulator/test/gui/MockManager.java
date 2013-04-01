package edu.wheaton.simulator.test.gui;

import java.util.HashMap;
import edu.wheaton.simulator.gui.Display;
import edu.wheaton.simulator.gui.Screen;
import edu.wheaton.simulator.gui.ScreenManager;

public class MockManager extends ScreenManager {
	private Display mockDisplay;
	private HashMap<String, MockScreen> screens;
	
	public MockManager(Display display, String msName, MockScreen ms){
		mockDisplay = display;
		screens = new HashMap<String, MockScreen>();
		screens.put(msName, ms);
	}
	@Override
	public void update(Screen s){
		mockDisplay.updateDisplay(s);
	}
	@Override
	public Screen getScreen(String screen){
		return screens.get(screen);
	}
}
