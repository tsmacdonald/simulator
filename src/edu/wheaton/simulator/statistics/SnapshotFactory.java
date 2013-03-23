package edu.wheaton.simulator.statistics;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

import edu.wheaton.simulator.entity.Agent;
import edu.wheaton.simulator.entity.Prototype;
import edu.wheaton.simulator.entity.Slot;

/**
 * This class will create the Snapshots to be put into the Database
 * 
 * @author akonwi and Daniel Gill
 */

public class SnapshotFactory {


	public static AgentSnapshot makeAgentSnapshot(Agent agent, Integer step) {
//		Sort out with the Agent guys just wtf is up with fields. 
		return null; // TODO 
	}

	public static SlotSnapshot makeSlotSnapshot(Slot slot, Integer step) {
		return null; // TODO
	}

	public static FieldSnapshot makeFieldSnapshot(String name, String value) {
		return new FieldSnapshot(name, value);
	}
	
	public static ImmutableMap<String, FieldSnapshot> makeFieldSnapshots(Map<String, String> fields) { 
		ImmutableMap.Builder<String, FieldSnapshot> builder = 
				new ImmutableMap.Builder<String, FieldSnapshot>(); 
		for (String name : fields.keySet()) { 
			String value = fields.get(name); 
			builder.put(name, makeFieldSnapshot(name, value)); 
		}
		return builder.build(); 
	}

	public static PrototypeSnapshot makePrototypeSnapshot(Prototype prototype,
			Integer step) {
		return null; // TODO
	}
	
	private SnapshotFactory() {
	}
}
