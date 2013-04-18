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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multiset.Entry;

import edu.wheaton.simulator.entity.Agent;
import edu.wheaton.simulator.entity.Prototype;
import edu.wheaton.simulator.simulation.end.SimulationEnder;


public class Saver {

	/**
	 * Write data serializing the simulation's current state to a file
	 * Saves the state of the most recent completed step only
	 * FileWriter code taken from: http://www.javapractices.com/topic/TopicAction.do?Id=42
	 * 
	 * @param filename The name of the file that's going to be saved
	 * @param table An AgentSnapshotTable of all AgentSnapshots at every step of the simulation
	 * @param prototypes A Map of PrototypeSnapshots
	 * @param width The width of the grid
	 * @param height The height of the grid
	 * @param simEnder The class that handles simulation ending conditions
	 */
	public void saveSimulation(String filename, Set<Agent> agents, ImmutableSet<Prototype> prototypes, 
			Map<String, String> globalFields, int width, int height, SimulationEnder simEnder){		
		StringBuilder sb = new StringBuilder(); 

		//Name the file, first
		filename = filename + ".txt";
		
		//Create AgentSnapshots  
		HashSet<AgentSnapshot> agentSnaps = new HashSet<AgentSnapshot>(); 
		for(Agent a : agents)
			agentSnaps.add(SnapshotFactory.makeAgentSnapshot(a, null, 0));
		
		//Create PrototypeSnapshots
		HashSet<PrototypeSnapshot> protoSnaps = new HashSet<PrototypeSnapshot>(); 
		for(Prototype p : prototypes)
			protoSnaps.add(SnapshotFactory.makePrototypeSnapshot(p)); 

		//Save the Grid dimensions
		sb.append(width + "\n"); 
		sb.append(height + "\n");  

		//Serialize and write all PrototypeSnapshots to file
		for(PrototypeSnapshot proto : protoSnaps){
			sb.append(proto.serialize() + "\n"); 
		}

		//Serialize and write all AgentSnapshots to file
		for(AgentSnapshot snap : agentSnaps){
			sb.append(snap.serialize() + "\n"); 
		}
		
		for(int i = 0; i < globalFields.size(); i++){
			//TODO: Make global variables save/load
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

		//Debugging: What just got saved to file?
		System.out.println("The following text was just saved to SimulationState.txt: \n" + sb);
	}

	/**
	 * Create a save file for an individual prototype
	 * @param proto
	 */
	public void savePrototype(Prototype proto){
		StringBuilder sb = new StringBuilder(); 
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
		
		//Debugging: What just got saved to file?
		System.out.println("The following text was just saved to " + filename + ": \n" + sb);
	}

	/**
	 * Get the current step in the simulation
	 * This is assumed to be the highest numbered step stored in the table
	 * @return The current simulation step
	 */
	private int getCurrentStep(AgentSnapshotTable table){
		Set<Integer> steps = table.getAllSteps();
		int max = 0; 

		for(Integer i : steps)
			if(i > max) 
				max = i; 

		return max; 
	}
}
