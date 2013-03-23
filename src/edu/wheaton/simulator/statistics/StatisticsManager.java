package edu.wheaton.simulator.statistics;

import java.util.List;
import java.util.Map;

import edu.wheaton.simulator.entity.PrototypeID;


public class StatisticsManager {

	/**
	 * The single instance of the StatisticsManager.
	 */
	private static StatisticsManager instance;

	/**
	 * Get the StatisticsManager. Insures there is at most only ever one
	 * StatisticsManager.
	 * 
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
	
	/**
	 * Each index in the List stores the prototype snapshot associated with that step in the simulation
	 */
	private List<Map<PrototypeID, PrototypeSnapshot>> prototypes; 

	// TODO: Some sort of behavior queue mapping AgentID's to behavior
	// representations.

	/**
	 * Private constructor to prevent wanton instantiation.
	 */
	private StatisticsManager() {
		table = new EntitySnapshotTable();
		gridObserver = new GridObserver(this);
		prototypes = null;
	}

	/**
	 * Get the grid entity Observer.
	 * 
	 * @return The GridEntityObserver associated with this StatisticsManager.
	 */
	public GridObserver getGridObserver() {
		return gridObserver;
	}

	/**
	 * Store a snapshot of a gridEntity.
	 * 
	 * @param gridEntity The Snapshot to be stored.
	 */
	public void addGridEntity(EntitySnapshot gridEntity) {
		table.putEntity(gridEntity);
	}
	
	/**
	 * Get data for a graph of the population of a certain GridEntity over time
	 * @param id The PrototypeID of the GridEntity to be tracked
	 * @return An array where indexes refer to the step in the simulation and the value refers to the population of the targeted entity at that time
	 */
	public int[] getPopVsTime(PrototypeID id){
		return null; 
	}
	
	/**
	 * Get data for a graph of the average value of a field over time
	 * @param id The PrototypeID of the GridEntity to be tracked
	 * @param FieldName The name of the field to be tracked
	 * @return An array where indexes refer to the step in the simulation and the value refers to average field value at that time
	 */
	public double[] getAvgFieldValue(PrototypeID id, String FieldName){
		return null; 
	}
	
	/**
	 * Get the average lifespan of a given GridEntity
	 * @param id The PrototypeID of the GridEntity to be tracked
	 * @return The average lifespan of the specified GridEntity
	 */
	public double getAvgLifespan(PrototypeID id){
		return 0.0; 
	}
}
