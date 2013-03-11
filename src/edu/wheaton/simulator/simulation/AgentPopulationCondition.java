/**
 * AgentPopulationCondition.java
 *
 * Encapsulates an ending condition based on the population of agents of a certain type
 * Example: if the fox population reaches 100, end the simulation
 *
 * @author Grant Hensel
 */

package edu.wheaton.simulator.simulation;

import java.util.Iterator;
import edu.wheaton.simulator.gridentities.Agent;
import edu.wheaton.simulator.gridentities.Slot;

public class AgentPopulationCondition extends EndCondition {

	/**
	 * The name of the Agent who's population we are monitoring to determine if the simulation should end
	 */
	private String agentName; 
	
	/**
	 * The population limit for the 'target' agent. If reached, the simulation will end.
	 * Note that this limit only has to be reached, not exceeded.  
	 */
	private int populationLimit; 
	
	/**
	 * Constructor
	 * @param grid Reference to the simulation Grid
	 * @param target The name of the Agent who's population we are monitoring to determine if the simulation should end
	 * @param populationLimit The population limit for the 'target' agent. If reached, the simulation will end
	 */
	public AgentPopulationCondition(Grid grid, String agentName, int populationLimit) {
		super(grid);
		this.agentName = agentName; 
		this.populationLimit = populationLimit; 
	}

	/**
	 * Determine if the simulation ending condition has been met
	 * @return true/false
	 */
	@Override
	public boolean evaluate() {
		int count = 0; 
		
		Iterator<Slot> it = this.grid.iterator(); 
		
		//TODO: Get the Agent team to add names to Agents
		while(it.hasNext()){	
			if(it.next().getEntity().getName().equals(agentName))
				count++;
			
			if(count >= populationLimit)
				return true; 
		}
		
		return false;
	}

}
