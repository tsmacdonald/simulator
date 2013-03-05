package edu.wheaton.simulator.statistics;

import java.util.HashMap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;

/**
 * A class representing all the information to track from each GridEntity in
 * the game.
 * 
 * @author Daniel Gill, Akon Ngoh
 */
public abstract class EntitySnapshot {

	// TODO: Implement an EntityID class or interface.
	public final EntityID id;

	public final Builder<String, Object> fields;

	public final Integer step;

	public final EntityPrototype prototype;

	/**
	 * Constructor
	 * 
	 * @param id
	 *            The ID of the GridEntity associated with this snapshot.
	 * @param fields
	 *            The current values of the fields of the GridEntity.
	 * @param step
	 *            The step in the simulation associated with this snapshot.
	 * @param prototype
	 *            The prototype for this category of Entity.
	 */
	public EntitySnapshot(EntityID id, HashMap<String, Object> fields,
			Integer step, EntityPrototype prototype) {
		this.id = id;
		this.fields = new ImmutableMap.Builder<String, Object>()
				.putAll(fields);
		this.step = step;
		this.prototype = prototype;
	}
}
