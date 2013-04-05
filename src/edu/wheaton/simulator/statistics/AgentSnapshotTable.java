package edu.wheaton.simulator.statistics;

import java.util.Set;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.TreeBasedTable;

import edu.wheaton.simulator.entity.AgentID;

/**
 * This class is an abstraction of a database which is represented by a Table,
 * provided by the Guava libraries
 * 
 * @author Akonwi, Daniel Gill
 */
public class AgentSnapshotTable {

	/**
	 * This table will be used to put the snapshots into
	 */
	private TreeBasedTable<AgentID, Integer, AgentSnapshot> table;

	/**
	 * Constructor.
	 */
	public AgentSnapshotTable() {
		table = TreeBasedTable.create();
	}

	/**
	 * Put a snapshot of the given entity into the Grid.
	 * 
	 * @param AgentSnapshot
	 *            The Snapshot to be stored. The entity to be captured.
	 */
	public void putEntity(AgentSnapshot AgentSnapshot) {
		table.put(AgentSnapshot.entityID, AgentSnapshot.step, AgentSnapshot);
	}

	/**
	 * Get all the Snapshots from a current step returned in a map using the
	 * EntityID as the key and Snapshot as the value. Will be immutable in
	 * order to prevent altering the contents.
	 * 
	 * @param step
	 *            The point in time at which to examine the simulation.
	 * @return an ImmutableMap from EntityID's to AgentSnapshots.
	 */
	public ImmutableMap<AgentID, AgentSnapshot> getSnapshotsAtStep(int step) {
		return new ImmutableMap.Builder<AgentID, AgentSnapshot>().putAll(
				table.column(step)).build();
	}

	/**
	 * Get all the Snapshots of a specific GridEntity throughout the game
	 * returned in a map using the step number as the key and Snapshot as the
	 * value. Will be immutable in order to prevent altering the contents.
	 * 
	 * @param id
	 *            the EntityID of GridEntity to query for
	 * @return an ImmutableMap<Integer, AgentSnapshot>
	 */
	public ImmutableMap<Integer, AgentSnapshot> getSnapshotsOfEntity(
			AgentID entityID) {
		ImmutableMap.Builder<Integer, AgentSnapshot> builder = new ImmutableMap.Builder<Integer, AgentSnapshot>();
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
	public boolean containsEntity(AgentID entityID) {
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
	
	/**
	 * Get all the steps in the table
	 * 
	 * @return a set of integers
	 */
	public Set<Integer> getAllSteps() {
		return table.columnKeySet();
	}
	
	/**
	 * Get the snapshot at given row, column
	 * @param id of snapshot
	 * @param step of the game
	 * @return an AgentSnapshot
	 */
	public AgentSnapshot get(AgentID id, int step) {
		return table.get(id, step);
	}

}
