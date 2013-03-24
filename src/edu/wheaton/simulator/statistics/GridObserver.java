package edu.wheaton.simulator.statistics;

import java.util.Collection;

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

	public void observe(Grid grid, Integer step, Collection<Prototype> prototypes) { 
		for (Prototype prototype : prototypes) { 
			
		}
		
		for (Slot s : grid) { 
			EntitySnapshot slotSnap = SnapshotFactory.makeSlotSnapshot(s, step);
			statManager.addGridEntity(slotSnap);
			Agent agent = s.getAgent();
			if (agent != null) {
				AgentSnapshot agentSnap = SnapshotFactory.makeAgentSnapshot(agent, step);
				statManager.addGridEntity(agentSnap);
			}
			// TODO Make sure this method is completely implemented! (for the most part)
		}
	}
}
