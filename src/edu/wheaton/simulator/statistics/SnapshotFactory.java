package edu.wheaton.simulator.statistics;

import java.util.Map;
import java.util.TreeMap;

import edu.wheaton.simulator.datastructure.Field;
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

	public static FieldSnapshot makeFieldSnapshot(Field field) {
		return null; // TODO
	}

	public static PrototypeSnapshot makePrototypeSnapshot(String prototypeName, Prototype prototype,
			Integer step) {
		return null; // TODO
	}
	
	private SnapshotFactory() {
	}
}
