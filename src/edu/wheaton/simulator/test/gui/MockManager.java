package edu.wheaton.simulator.test.gui;

import java.util.HashMap;
import edu.wheaton.simulator.gui.Display;
import edu.wheaton.simulator.gui.Screen;
import edu.wheaton.simulator.gui.ScreenManager;

public class MockManager extends ScreenManager {
	private Display mockDisplay;
	private HashMap<String, MockScreen> screens;
	private boolean setFacadeCalled;
	private boolean setGUIManagerCalled;
	
	public MockManager(Display display, String msName, MockScreen ms){
		mockDisplay = display;
		screens = new HashMap<String, MockScreen>();
		screens.put(msName, ms);
		setFacadeCalled = false;
		setGUIManagerCalled = false;
	}
	@Override
	public void update(Screen s){
		mockDisplay.updateDisplay(s);
	}
	@Override
	public Screen getScreen(String screen){
		return screens.get(screen);
	}
	@Override
	public void setFacade(int width, int height) {
		setFacadeCalled = true;
	}
	
	public boolean getFacadeCalled(){
		return setFacadeCalled;
	}
	
	@Override 
	public void updateGUIManager(String name, int width, int height){
		setGUIManagerCalled = true;
	}
	
	public boolean getUpdateGUIManagerCalled(){
		return setGUIManagerCalled;
	}
	
	public void addScreen(String screenName, MockScreen toAdd){
		screens.put(screenName, toAdd);
	}
	
}
