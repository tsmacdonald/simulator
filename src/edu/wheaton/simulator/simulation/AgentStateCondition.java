/**
 * AgentStateCondition.java
 *
 * Variation on AgentPopulation condition. Represents an ending condition based on the population of a certain type of agents that have a certain state
 * Example: if the number of foxes *with [Field Fur] = "Black"* reaches 100, end the simulation
 *
 * @author Grant Hensel
 */

package edu.wheaton.simulator.simulation;

import java.util.Iterator;

import edu.wheaton.simulator.entity.Slot;

public class AgentStateCondition extends EndCondition {

	/**
	 * The name of the Agent who's population we are monitoring to determine if
	 * the simulation should end
	 */
	private String agentName;

	/**
	 * The population limit for the 'target' agent. If reached, the simulation
	 * will end. Note that this limit only has to be reached, not exceeded.
	 */
	private int populationLimit;

	/**
	 * The name of the field to check within the 'target' type of agent
	 */
	private String fieldName;

	/**
	 * The specified value of fieldName that triggers the ending condition
	 */
	private String targetVal;

	/**
	 * Constructor
	 * 
	 * @param grid
	 *            Reference to the simulation Grid
	 * @param target
	 *            The name of the Agent who's population we are monitoring to
	 *            determine if the simulation should end
	 * @param populationLimit
	 *            The population limit for the 'target' agent. If reached, the
	 *            simulation will end
	 * @param fieldName
	 *            The name of the field to check within the 'target' type of
	 *            agent
	 * @param fieldVal
	 *            The specified value of fieldName that triggers the ending
	 *            condition
	 */
	public AgentStateCondition(Grid grid, String agentName,
			int populationLimit, String fieldName, String targetVal) {
		super(grid);
		this.agentName = agentName;
		this.populationLimit = populationLimit;
		this.fieldName = fieldName;
		this.targetVal = targetVal;
	}

	/**
	 * Determine if the simulation ending condition has been met
	 * 
	 * @return true/false
	 */
	@Override
	public boolean evaluate() {
		int count = 0;

		Iterator<Slot> it = this.getGrid().iterator();

		// TODO: Get the Agent team to add names to Agents
		while (it.hasNext()) {
			Slot s = it.next();

			if (s.getAgent().getName().equals(agentName)) {
				if (s.getAgent().getFieldValue(fieldName).equals(targetVal)) {
					count++;
				}

				if (count >= populationLimit)
					return true;
			}
		}

		return false;
	}
}
