/**
 * TimeElapsedCondition.java
 *
 * Encapsulates an ending condition based on the number of simulation steps completed
 *
 * @author Grant Hensel
 */

package edu.wheaton.simulator.simulation;

public class TimeElapsedCondition extends EndCondition {

	/**
	 * Int representing the number of steps that will be simulated before the simulation halts
	 */
	private int duration; 
	
	/**
	 * Constructor 
	 * @param grid Reference to the simulation Grid
	 * @param duration The number of steps to simulate before stopping the simulation
	 */
	public TimeElapsedCondition(Grid grid, int duration) {
		super(grid);
		this.duration = duration; 
	}

	/**
	 * Determine if the simulation ending condition has been met
	 * @return true/false
	 */
	@Override
	public boolean evaluate() {
		return duration > Simulator.getStepNum(); 
	}

}
