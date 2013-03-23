package edu.wheaton.simulator.statistics;

import java.util.HashMap;

import edu.wheaton.simulator.entity.Prototype;
import edu.wheaton.simulator.entity.Slot;
import edu.wheaton.simulator.simulation.Grid;

/**
 * @author Daniel Gill, Nico Lasta
 *
 */
class GridObserver {

	private StatisticsManager statManager;
	
	/**
	 * Constructor.
	 */
	public GridObserver(StatisticsManager statManager) {
		this.statManager = statManager;
	}
	
	public void observe(Grid grid, Integer step, HashMap<String, Prototype> prototypes) { 
		for (Slot s : grid) { 
			// store snapshot of Slot
			// store snapshot of Agent (if != null)
			
		}
	}
}
