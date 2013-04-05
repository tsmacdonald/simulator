package edu.wheaton.simulator.statistics;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

import edu.wheaton.simulator.datastructure.Grid;
import edu.wheaton.simulator.entity.Agent;
import edu.wheaton.simulator.entity.GridEntity;
import edu.wheaton.simulator.entity.Prototype;

/**
 * This class will create the Snapshots to be put into the Database
 * 
 * @author akonwi and Daniel Gill
 */

public class SnapshotFactory {

	/**
	 * Make a snapshot of an Agent at a particular point in time. 
	 * @param agent The agent being recorded. 
	 * @param step The point at which the capture was taken. 
	 * @return
	 */
	public static AgentSnapshot makeAgentSnapshot(Agent agent,
			Integer step) {
		return new AgentSnapshot(agent.getEntityID(), 
				makeFieldSnapshots(agent.getCustomFieldMap()), step, 
				agent.getPrototype().getPrototypeID());
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

	/**
	 * Build FieldSnapshots out of all the string pairs in a map. 
	 * @param fields A series of name-value string pairs. 
	 * @return An ImmutableMap of Strings to FieldSnapshots. 
	 */
	public static ImmutableMap<String, FieldSnapshot> makeFieldSnapshots(
			Map<String, String> fields) {
		ImmutableMap.Builder<String, FieldSnapshot> builder = new ImmutableMap.Builder<String, FieldSnapshot>();
		for (String name : fields.keySet()) {
			String value = fields.get(name);
			builder.put(name, makeFieldSnapshot(name, value));
		}
		return builder.build();
	}

	/**
	 * Build a new snapshot of a Prototype at a given point in time. 
	 * @param prototype The prototype being recorded. 
	 * @param step The point in the simulation being captured. 
	 * @return A PrototypeSnapshot corresponding to the provided Prototype. 
	 */
	public static PrototypeSnapshot makePrototypeSnapshot(Prototype prototype,
			Integer step) {
		return new PrototypeSnapshot(prototype.getName(), prototype.getPrototypeID(),
				makeFieldSnapshots(prototype.getCustomFieldMap()),
				prototype.childPopulation(), prototype.childIDs(), step);
	}

	/**
	 * Hidden constructor to prevent instantiation. 
	 */
	private SnapshotFactory() {
	}
}
