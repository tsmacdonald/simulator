package edu.wheaton.simulator.gui;

import java.util.HashMap;
import javax.swing.JPanel;

import edu.wheaton.simulator.simulation.GUIToAgentFacade;
import edu.wheaton.simulator.simulation.end.SimulationEnder;
import edu.wheaton.simulator.statistics.StatisticsManager;

public class ScreenManager {

	private HashMap<String, Screen> screens;

	private DisplayManager dm;
	
	private JPanel[][] grid;
	
	private SimulationEnder se;
	
	private StatisticsManager statMan;
	
	private GUIToAgentFacade facade;
	
	private boolean simulationIsRunning;
	
	//for determining when components should be disabled while running a sim.
	private boolean hasStarted;

	public ScreenManager(DisplayManager dm, GUIManager gm) {
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
	
	public GUIToAgentFacade getFacade() {
		return facade;
	}
	
	public SimulationEnder getEnder() {
		return se;
	}
	
	public StatisticsManager getStatManager(){
		return statMan;
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
	
	public boolean isRunning() {
		return simulationIsRunning;
	}
	
	public void setRunning(boolean b) {
		simulationIsRunning = b;
	}
	
	public void setStarted(boolean b) {
		hasStarted = b;
	}
	
	public boolean hasStarted() {
		return hasStarted;
	}
}
