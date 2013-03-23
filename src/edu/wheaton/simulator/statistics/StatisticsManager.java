package edu.wheaton.simulator.statistics;

import edu.wheaton.simulator.entity.EntityID;

public class StatisticsManager {

	/**
	 * The single instance of the StatisticsManager. 
	 */
	private static StatisticsManager instance; 

	/**
	 * Get the StatisticsManager. 
	 * Insures there is at most only ever one StatisticsManager. 
	 * @return The StatisticsManager
	 */
	public static StatisticsManager getSnapShotManager() { 
		if (instance == null)
			instance = new StatisticsManager(); 
		return instance; 
	}
	
	/**
	 * The table on which all entity snapshots will be stored. 
	 */
	private EntitySnapshotTable table; 
	
	//TODO: Some sort of behavior queue mapping AgentID's to behavior representations. 
	
	/**
	 * Private constructor to prevent wanton instantiation. 
	 */
	private StatisticsManager() { 
		table = new EntitySnapshotTable(); 
	}
	
	
	
}
