package edu.wheaton.simulator.statistics;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import edu.wheaton.simulator.entity.EntityID;
import edu.wheaton.simulator.entity.PrototypeID;


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
	public final ImmutableSet<InteractionSnapshot> interactions;
	
	/**
	 * The present prototype for the category of this Entity.
	 */
	public final PrototypeID prototype;

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
	public AgentSnapshot(EntityID entityID, ImmutableMap<String, FieldSnapshot> fields,
			Integer step, PrototypeID prototype, 
			ImmutableSet<InteractionSnapshot> interactions) {
		super(entityID, fields, step);
		this.prototype = prototype;
		this.interactions = interactions;
	}

}
