package edu.wheaton.simulator.gui;

import java.util.HashMap;

import edu.wheaton.simulator.gui.screen.EditEntityScreen;
import edu.wheaton.simulator.gui.screen.EditFieldScreen;
import edu.wheaton.simulator.gui.screen.NewSimulationScreen;
import edu.wheaton.simulator.gui.screen.Screen;
import edu.wheaton.simulator.gui.screen.SetupScreen;
import edu.wheaton.simulator.gui.screen.StatDisplayScreen;
import edu.wheaton.simulator.gui.screen.TitleScreen;
import edu.wheaton.simulator.gui.screen.ViewSimScreen;

public class ScreenManager {

	private static ScreenManager sm;
	
	private Display display;
	
	private ScreenManager() {
		this.display = Gui.getDisplay();
		screens = new HashMap<String, Screen>();
		SimulatorGuiManager gm = SimulatorGuiManager.getInstance();
		putScreen("Title", new TitleScreen(gm));
		putScreen("New Simulation", new NewSimulationScreen(gm));
		putScreen("Edit Fields", new EditFieldScreen(gm));
		putScreen("Edit Entities", new EditEntityScreen(gm));
		putScreen("View Simulation", new ViewSimScreen(gm));
		putScreen("Statistics", new StatDisplayScreen(gm));
		putScreen("Grid Setup", new SetupScreen(gm));
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
