package edu.wheaton.simulator.statistics;

/**
 * This class will save PrototypeSnapshots and AgentSnapshots to a file.
 * To be used by other classes that will load and place the appropriate values.
 * 
 * @author Grant Hensel, Nico Lasta
 */
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableMap;

import edu.wheaton.simulator.entity.AgentID;
import edu.wheaton.simulator.entity.Prototype;
import edu.wheaton.simulator.simulation.end.SimulationEnder;


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
	 * The width of the grid we're saving
	 */
	private int width; 

	/**
	 * The height of the grid we're saving
	 */
	private int height; 

	/**
	 * Handles the ending conditions for the simulation
	 */
	private SimulationEnder simEnder; 

	/**
	 * Constructor
	 * @param table An AgentSnapshotTable of all AgentSnapshots at every step of the simulation
	 * @param prototypes A Map of PrototypeSnapshots
	 */
	public Saver(AgentSnapshotTable table, Map<String, PrototypeSnapshot> prototypes, 
			int width, int height, SimulationEnder simEnder){
		this.table = table; 
		this.prototypes = prototypes; 
		this.width = width; 
		this.height = height; 
		this.simEnder = simEnder; 
	}

	/**
	 * Write data serializing the simulation's current state to a file
	 * Saves the state of the most recent completed step only
	 * FileWriter code taken from: http://www.javapractices.com/topic/TopicAction.do?Id=42
	 * 
	 * @param filename The name of the file that's going to be saved
	 */
	public void saveSimulation(String filename){
		sb = new StringBuilder(); 

		//Name the file, first
		filename = filename + ".txt";

		int currentStep = getCurrentStep();  
		ImmutableMap<AgentID, AgentSnapshot> snaps = table.getSnapshotsAtStep(currentStep); 

		//Save the Grid dimensions
		sb.append(width + "\n"); 
		sb.append(height + "\n");  

		//Serialize and write all PrototypeSnapshots to file
		for(PrototypeSnapshot proto : prototypes.values()){
			sb.append(proto.serialize() + "\n"); 
		}

		//Serialize and write all AgentSnapshots to file
		for(AgentSnapshot snap : snaps.values()){
			sb.append(snap.serialize() + "\n"); 
		}

		//Save the Ending Conditions
		sb.append(simEnder.serialize()); 

		//Create BufferedWriter and BufferedReader
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
			writer.write(sb.toString());
			writer.close();
		} catch (IOException e) {
			System.err.println("Saver.java: IOException");
			e.printStackTrace();
		}

		//What just got saved to file?
		System.out.println("The following text was just saved to SimulationState.txt: \n" + sb); // TODO Delete
	}

	/**
	 * Create a save file for an individual prototype
	 * @param proto
	 */
	public void savePrototype(Prototype proto){
		sb = new StringBuilder(); 
		PrototypeSnapshot protoSnap = SnapshotFactory.makePrototypeSnapshot(proto);
		sb.append(protoSnap.serialize()); 

		String filename = proto.getName() + ".txt"; 
		//Create BufferedWriter and BufferedReader
		try {	
			BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
			writer.write(sb.toString());
			writer.close();
		} catch (IOException e) {
			System.err.println("Saver.java: IOException");
			e.printStackTrace();
		}
		
		//What just got saved to file?
		System.out.println("The following text was just saved to " + filename + ": \n" + sb);
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
