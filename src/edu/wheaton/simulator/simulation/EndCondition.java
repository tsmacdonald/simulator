/**
 * EndCondition.java
 *
 * Encapsulates an ending condition for the simulation
 * This is a superclass - extended by classes implementing specific conditions
 *
 * @author Grant Hensel
 */

package edu.wheaton.simulator.simulation;

public abstract class EndCondition {
	
	/**
	 * Reference to the Grid. Used to check the state of the simulation and determine if the ending condition has been met
	 */
	protected Grid grid; 
	
	/**
	 * Constructor
	 * @param grid The main simulation grid
	 */
	public EndCondition(Grid grid){
		this.grid = grid; 
		
	}
	
	/**
	 * Determine if the simulation ending condition has been met
	 * @return true/false
	 */
	public abstract boolean evaluate(); 
}
