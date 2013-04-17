package edu.wheaton.simulator.statistics;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import edu.wheaton.simulator.datastructure.Grid;
import edu.wheaton.simulator.entity.Agent;
import edu.wheaton.simulator.entity.AgentID;
import edu.wheaton.simulator.entity.Prototype;
import edu.wheaton.simulator.entity.Trigger;
import edu.wheaton.simulator.expression.Expression;

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
	public static AgentSnapshot makeAgentSnapshot(Agent agent, ArrayList<TriggerSnapshot> behaviors,
			Integer step) {
		return new AgentSnapshot(agent.getID(), 
				makeFieldSnapshots(agent.getCustomFieldMap()), step, 
				agent.getPrototype().getName(), behaviors, agent.getPosX(), agent.getPosY());
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
		
		ArrayList<Trigger> triggers = (ArrayList<Trigger>) prototype.getTriggers();
		
		List<TriggerSnapshot> trigSnaps = new ArrayList<TriggerSnapshot>();
		for(Trigger t : triggers)
			trigSnaps.add(makeTriggerSnapshot(null, t.getName(), t.getPriority(), t.getConditions(), t.getBehavior(), (Integer) null));
		
		ImmutableMap<String, FieldSnapshot> fields = makeFieldSnapshots(prototype.getCustomFieldMap()); 
		int population = prototype.childPopulation();
		ImmutableSet<AgentID> childIDs = prototype.childIDs();
		Color color = prototype.getColor(); 
		byte[] design = prototype.getDesign(); 
		
		return new PrototypeSnapshot(name, fields, population, childIDs, step, color, design);	
	}
	
	// TODO Add documentation
	public static TriggerSnapshot makeTriggerSnapshot(AgentID id, String triggerName, int priority, Expression condition, 
			Expression behavior, int step) {
		return new TriggerSnapshot(id, triggerName, priority, condition, behavior, step);
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
		//TODO: Note that AgentSnapshots now require an xPos and yPos in the constructor
		return new AgentSnapshot(Grid.getID(), makeFieldSnapshots(grid.getCustomFieldMap()), step, prototype.getName(), null, 0, 0);
	}
	
	/**
	 * Hidden constructor to prevent instantiation. 
	 */
	private SnapshotFactory() {
	}

}
