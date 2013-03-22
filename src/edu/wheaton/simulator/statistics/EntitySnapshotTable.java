package edu.wheaton.simulator.statistics;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.TreeBasedTable;

/**
 * This class is an abstraction of a database which is represented by a Table,
 * provided by the Guava libraries
 * 
 * @author Akonwi, Daniel Gill
 */

public class EntitySnapshotTable {

	/**
	 * This table will be used to put the snapshots into
	 */
	private TreeBasedTable<Integer, Integer, EntitySnapshot> table;

	/**
	 * Constructor.
	 */
	public EntitySnapshotTable() {
		table = TreeBasedTable.create();
	}

	/**
	 * Put a snapshot of the given entity into the Grid.
	 * 
	 * @param entity
	 *            The entity to be captured.
	 * @param step
	 *            The current step.
	 */
	public void putEntity(EntitySnapshot entity, int step) {
		table.put(entity.entityID, entity.step, entity);
	}

	/**
	 * Get the EntitySnapshot with given id at given step
	 * 
	 * @param id
	 *            EntityID of gridEntity or row to query
	 * @param step
	 *            the turn in the game or column to query
	 * @return EntitySnapshot at given row(id) and column(step)
	 * @throws Exception
	 *             if the database hasn't been finalized or if the query fails
	 */
	public EntitySnapshot getSnapshot(Integer entityID, int step) throws Exception {
		return table.get(entityID, step);
	}

	/**
	 * Get all the Snapshots from a current step returned in a map using the
	 * EntityID as the key and Snapshot as the value. Will be immutable in
	 * order to prevent altering the contents.
	 * 
	 * @param step
	 *            to query for
	 * @return an ImmutableMap<EntityID, EntitySnapshot>
	 */
	public ImmutableMap<Integer, EntitySnapshot> getSnapshotsAtStep(int step) {
		ImmutableMap.Builder<Integer, EntitySnapshot> builder = new ImmutableMap.Builder<Integer, EntitySnapshot>();
		builder.putAll(table.column(step));
		return builder.build();
	}

	/**
	 * Get all the Snapshots of a specific GridEntity throughout the game
	 * returned in a map using the step number as the key and Snapshot as the
	 * value. Will be immutable in order to prevent altering the contents.
	 * 
	 * @param id
	 *            the EntityID of GridEntity to query for
	 * @return an ImmutableMap<Integer, EntitySnapshot>
	 */
	public ImmutableMap<Integer, EntitySnapshot> getSnapshotsOfEntity(
			Integer entityID) {
		ImmutableMap.Builder<Integer, EntitySnapshot> builder = new ImmutableMap.Builder<Integer, EntitySnapshot>();
		builder.putAll(table.row(entityID));
		return builder.build();
	}

	/**
	 * Does the table have records for the given Entity?
	 * 
	 * @param entityID
	 *            GridEntity to query for
	 * @return true or false depending on whether the table has a row for the
	 *         given id
	 */
	public boolean containsEntity(Integer entityID) {
		return table.containsRow(entityID);
	}

	/**
	 * Does the table have records at the given step?
	 * 
	 * @param step
	 *            to query for
	 * @return true or false depending on whether the table has a column for
	 *         the given step
	 */
	public boolean containsStep(int step) {
		return table.containsColumn(step);
	}

	/**
	 * Get the number of values(Snapshots) in the table
	 * 
	 * @return size of database
	 */
	public int getSize() {
		return table.size();
	}
}
