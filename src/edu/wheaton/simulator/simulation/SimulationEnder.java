/**
 * SimulationEnder.java
 *
 * A class that handles the conclusion of a simulation
 *
 * @author Grant Hensel
 */

package edu.wheaton.simulator.simulation;

public class SimulationEnder {

	/**
	 * A BoolExpression object containing the conditions under which the simulation ends
	 */
	private EndCondition endCondition; 
	
	/**
	 * Constructor
	 * @param endCondition The conditions under which the simulation is to finish
	 */
	public SimulationEnder(EndCondition endCondition){
		this.endCondition = endCondition; 
	}
	
	/**
	 * Check if the ending conditions for the simulation have been met
	 * @return true or false
	 */
	public boolean simulationFinished(){
		return endCondition.evaluate(); 
	}
	
	
	
	
}
