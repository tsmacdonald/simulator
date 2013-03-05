/**
 * SimulationEnder.java
 *
 * A class that handles the conclusion of a simulation
 *
 * @author Grant Hensel
 */

package edu.wheaton.simulator.simulation;

import edu.wheaton.simulator.gridentities.BoolExpression;

public class SimulationEnder {

	/**
	 * A BoolExpression object containing the conditions under which the simulation ends
	 */
	private BoolExpression endCondition; 
	
	/**
	 * Constructor
	 * @param endCondition The conditions under which the simulation is to finish
	 */
	public SimulationEnder(BoolExpression endCondition){
		this.endCondition = endCondition; 
	}
	
	/**
	 * Check if the ending conditions for the simulation have been met
	 * @return true or false
	 */
	public boolean simulationFinished(){
		//TODO: Determine the arguments for evaluate(), if any
		return endCondition.evaluate(); 
	}
	
	
	
	
}
