/**
 * PriorityUpdater.java
 * 
 * The class used for PriorityUpdate
 */

package edu.wheaton.simulator.datastructure;

import java.util.HashSet;

import edu.wheaton.simulator.entity.Agent;
import edu.wheaton.simulator.entity.AgentID;
import edu.wheaton.simulator.simulation.SimulationPauseException;

public class PriorityUpdater implements Updater {

	/**
	 * The minimum and maximum priorities for priorityUpdateEntities(). Should
	 * be changed so that the user can define these values Or that they are
	 * defined by checking minimum and maximum priorities of all triggers of all
	 * agents in a simulation
	 */
	private int minPriority = 0;
	private int maxPriority = 50;

	/**
	 * The grid object
	 */
	private Grid grid;

	/**
	 * Constructor
	 * 
	 * @param g
	 * @param minPriority
	 * @param maxPriority
	 */
	public PriorityUpdater(Grid g, int minPriority, int maxPriority) {
		grid = g;
		this.minPriority = minPriority;
		this.maxPriority = maxPriority;
	}

	/**
	 * Makes the entities in the grid perform their triggers in ascending
	 * priority order; that is, priority takes precedence over Agent order for
	 * when triggers are evaluated.
	 * 
	 * @throws SimulationPauseException
	 */
	@Override
	public void update() throws SimulationPauseException {
		for (int priority = minPriority; priority <= maxPriority; priority++) {
			HashSet<AgentID> processedIDs = new HashSet<AgentID>();

			for (Agent current : grid) {
				if (current != null)
					if (!processedIDs.contains(current.getID())) {
						current.priorityAct(priority);
						processedIDs.add(current.getID());
					}
			}
		}
	}

	@Override
	public String toString() {
		return "Priority";
	}
}