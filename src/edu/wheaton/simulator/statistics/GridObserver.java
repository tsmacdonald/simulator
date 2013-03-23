package edu.wheaton.simulator.statistics;

import java.util.HashMap;

import edu.wheaton.simulator.entity.Agent;
import edu.wheaton.simulator.entity.EntityID;
import edu.wheaton.simulator.entity.Prototype;
import edu.wheaton.simulator.entity.Slot;
import edu.wheaton.simulator.simulation.Grid;

/**
 * @author Daniel Gill, Nico Lasta
 *
 */
public class GridObserver {

	private StatisticsManager statManager;

	/**
	 * Constructor.
	 */
	public GridObserver(StatisticsManager statManager) {
		this.statManager = statManager;
	}

	public void observe(Grid grid, Integer step, HashMap<String, Prototype> prototypes) { 
		for (Slot s : grid) { 
			EntitySnapshot slotSnap = SnapshotFactory.makeSlotSnapshot(s, step);
			statManager.addGridEntity(slotSnap);
			Agent agent = s.getAgent();
			if (agent == null) 
				continue;
			AgentSnapshot agentSnap = SnapshotFactory.makeAgentSnapshot(agent, step);
			statManager.addGridEntity(agentSnap);
			for (String currentPrototypeName : prototypes.keySet()) { 
				PrototypeSnapshot currentSnapshot;
				currentSnapshot = SnapshotFactory.makePrototypeSnapshot(
						prototypes.get(currentPrototypeName), step);
			}
			// TODO Make sure this method is completely implemented! (for the most part)
		}
	}
}
