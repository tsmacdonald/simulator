package edu.wheaton.simulator.statistics;

import java.util.HashMap;

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

	public static EntitySnapshot makeSlotSnapshot(Slot slot, Integer step) {
		return null; // TODO
	}

	public static FieldSnapshot makeFieldSnapshot(String name, String value) {
		return new FieldSnapshot(name, value);
	}
	
	public static ImmutableMap<String, FieldSnapshot> makeFieldSnapshots(HashMap<String, String> fields) { 
//		for (String name : fields.keySet()) { 
//			
//		}
		return null;
	}

	public static PrototypeSnapshot makePrototypeSnapshot(Prototype prototype,
			Integer step) {
		return null; // TODO
	}
	
	private SnapshotFactory() {
	}
}
