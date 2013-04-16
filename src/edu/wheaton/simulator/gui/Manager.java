package edu.wheaton.simulator.gui;

import java.util.ArrayList;

import edu.wheaton.simulator.simulation.Simulator;
import edu.wheaton.simulator.simulation.end.SimulationEnder;

public interface Manager {
	/**
	 * Updates the screen with the given screen
	 * @param s The screen to update to.
	 */
	public void update(Screen s);
	/**
	 * Gets the given screen
	 * @param screen The screen name
	 * @return The screen associated with that screen name
	 */
	public Screen getScreen(String screen);
	/**
	 * Access to the GUI Facade
	 * @return The Facade
	 */
	public Simulator getFacade();
	/**
	 * Tells whether Entities has been added or not
	 * @return
	 */
	public boolean hasStarted();
	/**
	 * Returns new facade with the passed in parameters
	 * @param widthInt The new width
	 * @param heightInt The new height
	 */
	public void setFacade(int widthInt, int heightInt);
	/**
	 * Sets the GUI name, width, and height to the given parameters respectively
	 * @param nameString The new name
	 * @param widthInt The new width
	 * @param heightInt The new height
	 */
	public void updateGUIManager(String nameString, int widthInt, int heightInt);
	/**
	 * Returns a SimultionEnder
	 * @return A SimulationEnder
	 */
	public SimulationEnder getEnder();
	/**
	 * Access to the Spawn Conditions
	 * @return The spawn conditions
	 */
	public ArrayList<SpawnCondition> getSpawnConditions();
	/**
	 * Calls the load method on the given screen
	 * @param toUpload The given screen
	 */
	public void loadScreen(Screen toUpload);
}
