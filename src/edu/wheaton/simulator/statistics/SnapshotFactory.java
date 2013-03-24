package edu.wheaton.simulator.statistics;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import edu.wheaton.simulator.entity.Agent;
import edu.wheaton.simulator.entity.EntityID;
import edu.wheaton.simulator.entity.GridEntity;
import edu.wheaton.simulator.entity.Prototype;
import edu.wheaton.simulator.entity.PrototypeID;
import edu.wheaton.simulator.entity.Slot;

/**
 * This class will create the Snapshots to be put into the Database
 * 
 * @author akonwi and Daniel Gill
 */

public class SnapshotFactory {

	// TODO Please check these methods and see if they're all okay.

	public static EntitySnapshot makeSlotSnapshot(GridEntity entity,
			Integer step) {
		return new EntitySnapshot(entity.getEntityID(),
				makeFieldSnapshots(entity.getFieldMap()), step);
	}

	public static AgentSnapshot makeAgentSnapshot(GridEntity entity,
			Integer step) {
		// Sort out with the Agent guys just wtf is up with fields.
		return new AgentSnapshot(entity.getEntityID(), makeFieldSnapshots(entity.getFieldMap()), step, 
				entity.getPrototypeName(), );
	}

	/**
	 * Make a FieldSnapshot from the associated name and value. 
	 * @param name The name of the field.
	 * @param value The value of the field. 
	 * @return A FieldSnapshot corresponding to the pair of Strings. 
	 */
	public static FieldSnapshot makeFieldSnapshot(String name, String value) {
		return new FieldSnapshot(name, value);
	}

	public static ImmutableMap<String, FieldSnapshot> makeFieldSnapshots(
			Map<String, String> fields) {
		ImmutableMap.Builder<String, FieldSnapshot> builder = new ImmutableMap.Builder<String, FieldSnapshot>();
		for (String name : fields.keySet()) {
			String value = fields.get(name);
			builder.put(name, makeFieldSnapshot(name, value));
		}
		return builder.build();
	}

	public static PrototypeSnapshot makePrototypeSnapshot(Prototype prototype,
			Integer step) {
		return null; // TODO:
	}

	private SnapshotFactory() {
	}
}
