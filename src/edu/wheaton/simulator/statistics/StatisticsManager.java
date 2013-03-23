package edu.wheaton.simulator.statistics;

import java.util.TreeSet;

public class StatisticsManager {

	/**
	 * The table on which all entity snapshots will be stored.
	 */
	private EntitySnapshotTable table;

	/**
	 * The number of steps the simulation has taken.
	 * Effectively it is the largest step it has encountered in
	 * a Snapshot given to it. 
	 */
	private int numSteps;

	/**
	 * The GridOberserver keeps track of changes in the grid.
	 */
	private GridObserver gridObserver;

	// TODO: Appropriate datastructure for storing sets of prototype snapshots
	// over time.

	// TODO: Some sort of behavior queue mapping AgentID's to behavior
	// representations.

	/**
	 * Private constructor to prevent wanton instantiation.
	 */
	public StatisticsManager() {
		table = new EntitySnapshotTable();
		gridObserver = new GridObserver(this);
		numSteps = 0;
	}

	/**
	 * @return The total number of steps the simulation has taken.
	 */
	public int getTotalSteps() {
		return numSteps;
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
	 * @param gridEntity
	 *            The Snapshot to be stored.
	 */
	public void addGridEntity(EntitySnapshot gridEntity) {
		table.putEntity(gridEntity);
		if (gridEntity.step > numSteps)
			numSteps = gridEntity.step; 
	}
}
