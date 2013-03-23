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
	
	/**
	 * The GridOberserver keeps track of changes in the grid. 
	 */
	private GridObserver gridObserver; 
	
	//TODO: Some sort of behavior queue mapping AgentID's to behavior representations. 
	
	/**
	 * Private constructor to prevent wanton instantiation. 
	 */
	private StatisticsManager() { 
		table = new EntitySnapshotTable(); 
		gridObserver = new GridObserver(this); 
	}
	
	/**
	 * Get the grid entity Observer. 
	 * @return The GridEntityObserver associated with this StatisticsManager. 
	 */
	public GridObserver getGridObserver() { 
		return gridObserver;
	}
	
	/**
	 * Store a snapshot of a gridEntity. 
	 * @param gridEntity The Snapshot to be stored. 
	 */
	public void addGridEntity(EntitySnapshot gridEntity) { 
		table.putEntity(gridEntity); 
	}
	
	
}
