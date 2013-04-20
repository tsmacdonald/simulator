/**
 * Simulation.java
 * 
 * A single directional class for simulation. Once the simulation 
 * is completed, a new simulation must be made.
 *
 * @author Agent Team
 */

package edu.wheaton.simulator.simulation;

import edu.wheaton.simulator.datastructure.Grid;
import edu.wheaton.simulator.simulation.end.SimulationEnder;
import edu.wheaton.simulator.statistics.StatisticsManager;

public class Simulation {
	
	/**
	 * Name of the simulation
	 */
	private String name;
	
	/**
	 * The Grid to hold all the Agents
	 */
	private Grid grid;
	
	/**
	 * 
	 */
	private SimulationEnder ender;
	
	/**
	 * Constructor
	 * 
	 * @param name
	 * @param gridX
	 * @param gridY
	 * @param se
	 */
	public Simulation(String name, int gridX, int gridY, SimulationEnder ender) {
		this.name = name;
		grid = new Grid(gridX, gridY);
		this.ender = ender;
		StatisticsManager.getInstance().initialize(grid, ender);
	}
	
	/**
	 * Constructor
	 * 
	 * @param name
	 * @param grid
	 * @param se
	 */
	public Simulation(String name, Grid grid, SimulationEnder ender) {
		this.name = name;
		this.grid = grid;
		this.ender = ender;
		StatisticsManager.getInstance().initialize(grid, ender);
	}
	
	/**
	 * Causes all the triggers of all the entities in the simulator's grid to be fired
	 * 
	 * @throws SimulationPauseException
	 */
	public void updateEntities() throws SimulationPauseException {
		grid.updateEntities();
	}
	
	/**
	 * Tells the grid to stop on the next iteration if the ender evaluates to
	 * true
	 * 
	 * @return
	 */
	public boolean shouldEnd() {
		return ender.evaluate(grid);
	}
	
	/**
	 * Notifies all the observers following this simulation's grid
	 */
	public void notifyObservers() {
		grid.notifyObservers();
	}
	
	/**
	 * Provides this simulator's name
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Provides the Grid the Facade is using
	 * 
	 * @return Grid object
	 */
	public Grid getGrid() {
		return grid;
	}
}
