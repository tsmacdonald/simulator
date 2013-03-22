package edu.wheaton.simulator.statistics;

import java.util.HashMap;

/**
 * A class representing all the information to track from agents in the game.
 * 
 * @author Akon
 * 
 */
public class AgentSnapshot extends EntitySnapshot {

	/**
	 * This Class and variable should provide details of this agent's most
	 * recent interaction caused by a trigger.
	 */
	public final InteractionDescription interaction;

	/**
	 * Constructor
	 * 
	 * @param id
	 *            The ID of the Agent associated with this snapshot.
	 * @param fields
	 *            The current values of the fields of the Entity
	 * @param step
	 *            The step in the simulation associated with this snapshot.
	 * @param prototype
	 *            The prototype for this category of Agent.
	 * @param interaction
	 *            the interaction details for this snapshot
	 */
	public AgentSnapshot(Integer entityID, HashMap<String, FieldSnapshot> fields,
			Integer step, EntityPrototypeSnapshot prototype,
			InteractionDescription interaction) {
		super(entityID, fields, step, prototype);
		this.interaction = interaction;
	}

}
