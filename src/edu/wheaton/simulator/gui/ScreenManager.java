package edu.wheaton.simulator.gui;

import java.util.HashMap;

public class ScreenManager {

	private Display display;
	
	public ScreenManager(Display d) {
		this.display = d;
		screens = new HashMap<String, Screen>();
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
