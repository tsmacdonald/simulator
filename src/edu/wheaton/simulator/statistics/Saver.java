package edu.wheaton.simulator.statistics;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
	 * The name of the file this class will generate. 
	 */
	private String filename;
	
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
	 * FileWriter code taken from: http://www.javapractices.com/topic/TopicAction.do?Id=42
	 */
	public void save(){
		// name the file, first
		filename = "SimulationState.txt"; // TODO Change if necessary
		
		int currentStep = getCurrentStep();  
		ImmutableMap<AgentID, AgentSnapshot> snaps = table.getSnapshotsAtStep(currentStep); 
		
		//TODO: Write the dimensions of the grid and other global variables
		sb.append("Globals"); 
		
		//Serialize and write all PrototypeSnapshots to file
		for(PrototypeSnapshot proto : prototypes.values()){
			System.out.println(proto.serialize()); // TESTER
			sb.append(proto.serialize()); 
		}
		
		//Serialize and write all AgentSnapshots to file
		for(AgentSnapshot snap : snaps.values()){
			System.out.println(snap.serialize());  // TESTER
			sb.append(snap.serialize()); 
		}
		
		// create BufferedWriter and BufferedReader
				try {
					BufferedWriter writer = new BufferedWriter(
							new FileWriter(filename));
					writer.write(sb.toString());
					writer.close();
				} catch (IOException e) {
					System.err.println("Saver.java: IOException");
					e.printStackTrace();
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
