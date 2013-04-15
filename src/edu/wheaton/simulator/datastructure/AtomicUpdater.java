/**
 * AtomicUpdater
 * 
 * The class used for AtomicUpdate
 */

package edu.wheaton.simulator.datastructure;

import java.util.HashSet;

import edu.wheaton.simulator.entity.Agent;
import edu.wheaton.simulator.entity.AgentID;
import edu.wheaton.simulator.simulation.SimulationPauseException;

public class AtomicUpdater implements Updater {

	/**
	 * The grid object
	 */
	private Grid grid;

	/**
	 * Constructor
	 * @param g
	 */
	public AtomicUpdater(Grid g) {
		grid = g;
	}

	/**
	 * Evaluates all the conditionals of the Triggers first, then fires the
	 * behaviors later depending on whether or not those conditionals fired.
	 */
	@Override
	public void update() throws SimulationPauseException {
		HashSet<AgentID> processedIDs = new HashSet<AgentID>();

		for(Agent current : grid) {
			if (current != null)
				if (!processedIDs.contains(current.getID())) {
					current.atomicCondEval();
					processedIDs.add(current.getID());
				}
		}
		processedIDs = new HashSet<AgentID>();

		for(Agent current : grid)
			if (current != null)
				if (!processedIDs.contains(current.getID())) {
					current.atomicFire();
					processedIDs.add(current.getID());
				}
	}


	@Override
	public String toString() {
		return "Atomic";
	}
}