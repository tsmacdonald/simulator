package edu.wheaton.simulator.statistics;

import java.util.HashMap;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import edu.wheaton.simulator.entity.EntityID;

/**
 * A class representing all the information to track from slots in the game.
 * 
 * @author Akon, Daniel Gill
 * 
 */
public class SlotSnapshot extends EntitySnapshot {

	/**
	 * How many agents are occupying this slot
	 */
	public final ImmutableSet<EntityID> agents;

	/**
	 * Constructor
	 * 
	 * @param id
	 *            The ID of the Slot associated with this snapshot.
	 * @param fields
	 *            The current values of the fields of the Entity
	 * @param step
	 *            The step in the simulation associated with this snapshot.
	 * @param prototype
	 *            The prototype for this category of Slot.
	 * @param agents
	 *            the number of agents occupying this slot
	 */
	public SlotSnapshot(EntityID id, HashMap<String, FieldSnapshot> fields,
			Integer step, EntityPrototypeSnapshot prototype,
			Set<EntityID> agents) {
		super(id, fields, step, prototype);

		ImmutableSet.Builder<EntityID> builder = new ImmutableSet.Builder<EntityID>();

		builder.addAll(agents);
		this.agents = builder.build();
	}
}
