package edu.wheaton.simulator.statistics;

import edu.wheaton.simulator.entity.Slot;
import edu.wheaton.simulator.simulation.DatabaseHandler;
import edu.wheaton.simulator.simulation.Grid;

/**
 * This class will observe GridEntities
 * 
 * @author akonwi
 */
public class EntityObserver {

	/*
	 * Singleton that will handle all input to Database
	 */
	private DatabaseHandler dbHandler;

	/**
	 * CONSTRUCTOR
	 */
	public EntityObserver() {
		dbHandler = DatabaseHandler.instance();
	}

	/**
	 * Method to be called upon at each step of the game record Snapshots
	 * 
	 * @param grid
	 *            the instance of grid being played with
	 * @param step
	 *            the current step of the game
	 */
	public void observe(Grid grid, int step) {
		for (Slot s : grid) {
			dbHandler.addSnapshot(SnapshotFactory.createEntity(s, step), step);
			if (s.getEntity() != null) {
				dbHandler.addSnapshot(
						SnapshotFactory.createEntity(s.getEntity(), step),
						step);
			}
		}
	}
}
