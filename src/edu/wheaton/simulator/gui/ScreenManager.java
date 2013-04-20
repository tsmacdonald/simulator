package edu.wheaton.simulator.gui;

import java.util.HashMap;

import edu.wheaton.simulator.gui.screen.Screen;

public class ScreenManager {

	private static ScreenManager sm;
	
	private Display display;
	
	private ScreenManager() {
		this.display = Gui.getDisplay();
		screens = new HashMap<String, Screen>();
	}
	
	public static ScreenManager getInstance(){
		if(sm==null)
			sm = new ScreenManager();
		return sm;
	}
	
	public void putScreen(String name, Screen screen){
		screens.put(name, screen);
	}
	
	public Screen getScreen(String screenName) {
		return screens.get(screenName);
	}
	 
	public void update(Screen update) {
		display.updateDisplay(update);
	}
	
	public static void loadScreen(Screen s){
		s.load();
	}
	
	public Display getDisplay(){
		return display;
	}
	
	private HashMap<String, Screen> screens;
}
