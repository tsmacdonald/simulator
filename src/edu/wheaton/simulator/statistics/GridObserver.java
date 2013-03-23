package edu.wheaton.simulator.statistics;

import java.util.HashMap;

import edu.wheaton.simulator.entity.Agent;
import edu.wheaton.simulator.entity.Prototype;
import edu.wheaton.simulator.entity.Slot;
import edu.wheaton.simulator.simulation.Grid;

/**
 * @author Daniel Gill, Nico Lasta
 *
 */
public class GridObserver {

	private StatisticsManager statManager;
	private SnapshotFactory snapFactory;
	
	/**
	 * Constructor.
	 */
	public GridObserver(StatisticsManager statManager) {
		this.statManager = statManager;
	}
	
	public void observe(Grid grid, Integer step, HashMap<String, Prototype> prototypes) { 
		for (Slot s : grid) { 
			snapFactory.makeSlotSnapshot(s);
			Agent agent = s.getAgent();
			snapFactory.makeAgentSnapshot(agent);
			Field field = s.getField(Object);
			snapFactory.makeFieldSnapshot(field);
			
		}
	}
}
