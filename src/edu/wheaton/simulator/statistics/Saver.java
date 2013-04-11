package edu.wheaton.simulator.statistics;

import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableMap;

import edu.wheaton.simulator.entity.AgentID;

public class Saver {

	private StringBuilder sb;
	
	/**
	 * The table on which all entity snapshots are be stored.
	 */
	private AgentSnapshotTable table;
	
	/**
	 * Map of all PrototypeSnapshots for the simulation
	 * Since PrototypeSnapshots are immutable, this collection is the same for each step
	 */
	private Map<String, PrototypeSnapshot> prototypes; 
	
	/**
	 * Constructor
	 * @param table An AgentSnapshotTable of all AgentSnapshots at every step of the simulation
	 * @param prototypes A Map of PrototypeSnapshots
	 */
	public Saver(AgentSnapshotTable table, Map<String, PrototypeSnapshot> prototypes){
		this.sb = new StringBuilder(); 
		this.table = table; 
		this.prototypes = prototypes; 
	}
	
	/**
	 * Write data serializing the simulation's current state to a file
	 * Saves the state of the most recent completed step only
	 */
	public void save(){
		int currentStep = getCurrentStep();  
		ImmutableMap<AgentID, AgentSnapshot> snaps = table.getSnapshotsAtStep(currentStep); 
		
		//Serialize and write all AgentSnapshots to file
		for(AgentSnapshot snap : snaps.values()){
			sb.append(snap.serialize()); 
		}
		
		//Serialize and write all PrototypeSnapshots to file
		for(PrototypeSnapshot proto : prototypes.values()){
			sb.append(proto.serialize()); 
		}
	}
	
	/**
	 * Get the current step in the simulation
	 * This is assumed to be the highest numbered step stored in the table
	 * @return The current simulation step
	 */
	private int getCurrentStep(){
		Set<Integer> steps = table.getAllSteps();
		int max = 0; 
		
		for(Integer i : steps)
			if(i > max) 
				max = i; 
		
		return max; 
	}
	
}
