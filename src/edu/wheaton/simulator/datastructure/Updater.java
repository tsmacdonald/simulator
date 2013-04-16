/**
 * Updater.java
 * 
 * The interface for the state pattern that allows for switching update
 * modes.
 */

package edu.wheaton.simulator.datastructure;

import edu.wheaton.simulator.simulation.SimulationPauseException;

public interface Updater {

	/**
	 * Updates the Agents in the simulation by evaluating their triggers
	 * 
	 * @throws SimulationPauseException
	 */
	public void update() throws SimulationPauseException;

}