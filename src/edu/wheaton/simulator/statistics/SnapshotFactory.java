package edu.wheaton.simulator.statistics;

import java.util.ArrayList;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import edu.wheaton.simulator.datastructure.Grid;
import edu.wheaton.simulator.behavior.AbstractBehavior;
import edu.wheaton.simulator.entity.Agent;
import edu.wheaton.simulator.entity.AgentID;
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
	public static AgentSnapshot makeAgentSnapshot(Agent agent, ArrayList<BehaviorSnapshot> behaviors,
			Integer step) {
		return new AgentSnapshot(agent.getID(), 
				makeFieldSnapshots(agent.getCustomFieldMap()), step, 
				agent.getPrototype().getName(), behaviors);
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
		String name = prototype.getName();
		ImmutableMap<String, FieldSnapshot> fields = makeFieldSnapshots(prototype.getCustomFieldMap()); 
		int population = prototype.childPopulation();
		ImmutableSet<AgentID> childIDs = prototype.childIDs(); 
		
		return new PrototypeSnapshot(name, fields, population, childIDs, step);	
	}
	
	// TODO Add documentation
	public static BehaviorSnapshot makeBehaviorSnapshot(AgentID actor, AbstractBehavior behavior,
			AgentID recipient, Integer step) {
		return new BehaviorSnapshot(actor, behavior, recipient, step);
	}
 
	/**
	 * Make a new snapshot of the global fields of the grid.
	 * This is an AgentSnapshot made out of the grid.
	 * @param grid The instance of grid being played with.
	 * @param prototye A custom prototype for the grid.
	 * @param step The point in the simulation being captured.
	 * @return
	 */
	public static AgentSnapshot makeGlobalVarSnapshot(Grid grid,
			Prototype prototype, Integer step) {
		return new AgentSnapshot(null, makeFieldSnapshots(grid.getCustomFieldMap()), step, prototype.getName(), null);
	}
	
	/**
	 * Hidden constructor to prevent instantiation. 
	 */
	private SnapshotFactory() {
	}

}
