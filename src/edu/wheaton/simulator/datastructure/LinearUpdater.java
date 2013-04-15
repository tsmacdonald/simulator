/**
 * LinearUpdater.java
 * 
 * The class used for LinearUpdate
 */

package edu.wheaton.simulator.datastructure;

import java.util.HashSet;

import edu.wheaton.simulator.entity.Agent;
import edu.wheaton.simulator.entity.AgentID;
import edu.wheaton.simulator.simulation.SimulationPauseException;

public class LinearUpdater implements Updater {

	/**
	 * The grid object
	 */
	private Grid grid;

	/**
	 * Constructor
	 * 
	 * @param g
	 */
	public LinearUpdater(Grid g) {
		grid = g;
	}

	/**
	 * Causes all entities in the grid to act(). Checks to make sure each Agent
	 * has only acted once this iteration.
	 * 
	 * @throws SimulationPauseException
	 */
	@Override
	public void update() throws SimulationPauseException {
		HashSet<AgentID> processedIDs = new HashSet<AgentID>();

		for (Agent current : grid) {
			if (current != null)
				if (!processedIDs.contains(current.getID())) {
					current.act();
					processedIDs.add(current.getID());
				}
		}
	}

	@Override
	public String toString() {
		return "Linear";
	}
}