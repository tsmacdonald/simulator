package edu.wheaton.simulator.statistics;

import java.util.HashMap;

/**
 * A class representing all the information to track from slots in the game.
 * 
 * @author akonwi
 * 
 */
public class SlotSnapshot extends EntitySnapshot {

	/**
	 * How many agents are occupying this slot
	 */
	public final int agents;

	/**
	 * Constructor
	 * 
	 * @param id
	 *            The ID of the Slot associated with this snapshot.
	 * @param fields
	 *            The current values of the fields of the GridEntity
	 * @param step
	 *            The step in the simulation associated with this snapshot.
	 * @param prototype
	 *            The prototype for this category of Slot.
	 * @param agents
	 *            the number of agents occupying this slot
	 */
	public SlotSnapshot(EntityID id, HashMap<String, Object> fields,
			Integer step, EntityPrototype prototype, int agents) {
		super(id, fields, step, prototype);
		this.agents = agents;
	}
}
