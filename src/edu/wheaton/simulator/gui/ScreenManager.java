package edu.wheaton.simulator.gui;

import java.util.HashMap;

public class ScreenManager {

	private HashMap<String, Screen> screens;

	private DisplayManager dm;

	public ScreenManager(DisplayManager dm) {
		screens = new HashMap<String, Screen>();
		this.dm = dm;
		/**
		 * screens.put("editSim", new EditSimScreen(menu));
		 * screens.put("fields", new FieldScreen(menu));
		 * screens.put("editField", new EditFieldScreen(menu));
		 * screens.put("entities", new EntityScreen(menu));
		 * screens.put("editEntity", new EditEntityScreen(menu));
		 * screens.put("spawning", new SpawningScreen(menu));
		 * screens.put("viewSim", new ViewSimScreen(menu));
		 * screens.put("statistics", new StatisticsScreen(menu));
		 */
		screens.put("Title", new TitleScreen(this));
		screens.put("New Simulation", new NewSimulationScreen(this));
		screens.put("Edit Simulation", new EditSimScreen(this));
		screens.put("Fields", new FieldScreen(this));
	}

	public Screen getScreen(String screenName) {
		return screens.get(screenName);
	}

	public void update(Screen update) {
		dm.updateScreen(update.getComponents(), update.getLayout());
	}
}
