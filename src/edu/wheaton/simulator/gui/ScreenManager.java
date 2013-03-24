package edu.wheaton.simulator.gui;

import java.util.HashMap;
import javax.swing.JPanel;

public class ScreenManager {

	private HashMap<String, Screen> screens;

	private DisplayManager dm;
	
	private GUIManager gm;

	public ScreenManager(DisplayManager dm, GUIManager gm) {
		screens = new HashMap<String, Screen>();
		this.dm = dm;
		this.gm = gm;
		screens.put("Title", new TitleScreen(this));
		screens.put("New Simulation", new NewSimulationScreen(this));
		screens.put("Edit Simulation", new EditSimScreen(this));
		screens.put("Fields", new FieldScreen(this));
		screens.put("Edit Fields", new EditFieldScreen(this));
		screens.put("Entities", new EntityScreen(this));
		screens.put("Edit Entities", new EditEntityScreen(this));
		screens.put("Spawning", new SpawningScreen(this));
		screens.put("View Simulation", new ViewSimScreen(this));
		screens.put("Statistics", new StatisticsScreen(this));
		screens.put("Grid Setup", new SetupScreen(this));
	}

	public Screen getScreen(String screenName) {
		return screens.get(screenName);
	}

	public void update(Screen update) {
		dm.updateScreen(update);
	}
		
	public String getGUIname(){
		return GUIManager.getNameOfSim();
	}
	public int getGUIheight(){
		return GUIManager.getGridHeight();
	}
	public int getGUIwidth(){
		return GUIManager.getGridWidth();
	}
	public void updateGUIManager(String nos, int width, int height){
		GUIManager.setNameOfSim(nos);
		GUIManager.setGridWidth(width);
		GUIManager.setGridHeight(height);
	}
}
