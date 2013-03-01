package edu.wheaton.simulator.statistics;

import java.util.HashMap;

/**
 * A class representing all the information to track from the agent in
 * the game. 
 * 
 * @author akonwi
 *
 */
public class AgentSnapshot extends EntitySnapshot {

	/**
	 * This Class and variable should provide details of this agent's
	 * most recent interaction caused by a trigger. 
	 */
	public final InteractionDescription interaction;
	
	/**
	 * Constructor
	 * 
	 * @param id The ID of the Agent associated with this snapshot.
	 * @param fields The current values of the fields of the GridEntity. 
	 * @param step The step in the simulation associated with this snapshot.
	 * @param prototype The prototype for this category of Agent.
	 */
	public AgentSnapshot(EntityID id, HashMap<String, Object> fields,
			Integer step, EntityPrototype prototype) {
		super(id, fields, step, prototype);
	}
}
