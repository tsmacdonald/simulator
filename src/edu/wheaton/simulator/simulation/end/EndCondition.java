package edu.wheaton.simulator.simulation.end;

import edu.wheaton.simulator.simulation.Grid;

/**
 * EndCondition.java
 *
 * Encapsulates an ending condition for the simulation
 * This is a superclass - extended by classes implementing specific conditions
 *
 * @author Grant Hensel, Daniel Gill
 */
public interface EndCondition {

	/**
	 * Determine if the simulation ending condition has been met.
	 * @return true/false
	 */
	public boolean evaluate(int step, Grid grid);

}
