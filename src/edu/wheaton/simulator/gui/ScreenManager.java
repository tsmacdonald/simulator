package edu.wheaton.simulator.gui;

import java.util.HashMap;
import javax.swing.JPanel;

import edu.wheaton.simulator.simulation.GUIToAgentFacade;
import edu.wheaton.simulator.simulation.end.SimulationEnder;

public class ScreenManager {

	private HashMap<String, Screen> screens;

	private DisplayManager dm;
	
	private JPanel[][] grid;
	
	private SimulationEnder se;
	
	private GUIToAgentFacade facade;

	public ScreenManager(DisplayManager dm) {
		screens = new HashMap<String, Screen>();
		this.dm = dm;
		se = new SimulationEnder();
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
	
	public void setGrid(JPanel[][] grid){
		this.grid = grid;
	}
	
	public JPanel[][] getGrid(){
		return grid;
	}
	
	public void setFacade(int x, int y) {
		facade = new GUIToAgentFacade(x, y);
	}
}
