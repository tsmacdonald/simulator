package edu.wheaton.simulator.statistics;

import java.util.HashMap;
import com.google.common.collect.ImmutableMap;

/**
 * A class representing all the information to track from each GridEntity in
 * the game.
 * 
 * @author Daniel Gill, Akon Ngoh
 */
public abstract class EntitySnapshot {

	// TODO: Implement an EntityID class or interface.
	public final EntityID id;

	/**
	 * The saved fields of this entity.
	 */
	public final ImmutableMap<String, FieldSnapshot> fields;

	/**
	 * The point in the simulation at which this snapshot was taken. 
	 */
	public final Integer step;

	/**
	 * The present prototype for the category of this GridEntity. 
	 */
	public final EntityPrototype prototype;

	/**
	 * Constructor.
	 * @param id The ID of the GridEntity associated with this snapshot.
	 * @param fields The current values of the fields of the GridEntity.
	 * @param step The step in the simulation associated with this snapshot.
	 * @param prototype The prototype for this category of Entity.
	 */
	public EntitySnapshot(EntityID id, HashMap<String, FieldSnapshot> fields,
			Integer step, EntityPrototype prototype) {
		this.id = id;
		this.step = step;
		this.prototype = prototype;

		ImmutableMap.Builder<String, FieldSnapshot> builder = 
				new ImmutableMap.Builder<String, FieldSnapshot>();
		builder.putAll(fields);
		this.fields = builder.build();
	}
	
	public void id() {
		System.out.print(id.name + " " + id.id);
	}
}
