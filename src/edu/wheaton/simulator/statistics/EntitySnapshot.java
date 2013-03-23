package edu.wheaton.simulator.statistics;

import java.util.HashMap;

import com.google.common.collect.ImmutableMap;

import edu.wheaton.simulator.entity.EntityID;


/**
 * A class representing all the information to track from each Entity in the
 * game.
 * 
 * @author Daniel Gill, Akon Ngoh
 */
public abstract class EntitySnapshot {

	public final EntityID entityID;

	/**
	 * The saved fields of this entity.
	 */
	public final ImmutableMap<String, FieldSnapshot> fields;

	/**
	 * The point in the simulation at which this snapshot was taken.
	 */
	public final Integer step;

	/**
	 * The present prototype for the category of this Entity.
	 */
	public final PrototypeSnapshot prototype;

	/**
	 * Constructor.
	 * 
	 * @param id
	 *            The ID of the Entity associated with this snapshot.
	 * @param fields
	 *            The current values of the fields of the Entity.
	 * @param step
	 *            The step in the simulation associated with this snapshot.
	 * @param prototype
	 *            The prototype for this category of Entity.
	 */
	public EntitySnapshot(EntityID entityID, HashMap<String, FieldSnapshot> fields,
			Integer step, PrototypeSnapshot prototype) {
		this.entityID = entityID;
		this.step = step;
		this.prototype = prototype;

		ImmutableMap.Builder<String, FieldSnapshot> builder = new ImmutableMap.Builder<String, FieldSnapshot>();
		builder.putAll(fields);
		this.fields = builder.build();
	}
}
