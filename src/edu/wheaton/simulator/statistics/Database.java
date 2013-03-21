package edu.wheaton.simulator.statistics;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observer;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.TreeBasedTable;

import edu.wheaton.simulator.datastructure.Field;
import edu.wheaton.simulator.entity.Agent;
import edu.wheaton.simulator.entity.EntityID;
import edu.wheaton.simulator.entity.GridEntity;

/**
 * This class is an abstraction of a database which is represented by a Table,
 * provided by the Guava libraries
 * 
 * @author akonwi
 */

public class Database {

	/**
	 * This table will be used to put the snapshots into
	 */
	private TreeBasedTable<EntityID, Integer, EntitySnapshot> treeTable;

	/**
	 * This table will be an immutable copy of treeTable in order to ensure
	 * that nothing gets removed from the table. All get() requests will be
	 * responded to by this table
	 */
	private ImmutableTable<EntityID, Integer, EntitySnapshot> database;

	/**
	 * CONSTRUCTOR An editable database will be created and the immutable copy
	 * will be set to null and all get() requests will fail until the client
	 * calls the finalize() method.
	 */
	public Database() {
		treeTable = TreeBasedTable.create();
		database = null;
	}

	public void putEntity(EntitySnapshot entity, int step) {
		treeTable.put(entity.id, entity.step, entity); 
	}

	/**
	 * Once the caller is finished adding Snapshots to the database, they are
	 * responsible for calling this method to initialize the ImmutableTable
	 * which will be publicly available via get methods
	 */
	@Override
	public void finalize() {
		database = new ImmutableTable.Builder<EntityID, Integer, EntitySnapshot>()
				.putAll(treeTable).build();
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
	public EntitySnapshot getSnapshot(EntityID id, int step) throws Exception {
		if (database == null)
			throw new Exception("The database has not been finalized");
		try {
			return database.get(id, step);
		} catch (Exception e) {
			System.out.println(e);
			throw e;
		}
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
	public ImmutableMap<EntityID, EntitySnapshot> getSnapshotsAt(int step) {
		Map<EntityID, EntitySnapshot> results = database.column(step);
		return new ImmutableMap.Builder<EntityID, EntitySnapshot>().putAll(
				results).build();
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
	public ImmutableMap<Integer, EntitySnapshot> getAllSnapshotsOf(EntityID id) {
		Map<Integer, EntitySnapshot> results = database.row(id);
		return new ImmutableMap.Builder<Integer, EntitySnapshot>().putAll(
				results).build();
	}

	/**
	 * Does the table have records for the given Entity?
	 * 
	 * @param id
	 *            GridEntity to query for
	 * @return true or false depending on whether the table has a row for the
	 *         given id
	 */
	public boolean containsEntity(EntityID id) {
		return database.containsRow(id);
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
		return database.containsColumn(step);
	}

	/**
	 * Get the number of values(Snapshots) in the table
	 * 
	 * @return size of database
	 */
	public int getSize() {
		return database.size();
	}
}

/**
 * the following is a foobar EntitySnapshot and details
 */
/*
 * EntityID id = new EntityID("akon", 1); HashMap<String, FieldSnapshot>
 * defaults = new HashMap<String, FieldSnapshot>(); HashMap<String,
 * FieldSnapshot> fields = new HashMap<String, FieldSnapshot>();
 * EntityPrototype prototype = new EntityPrototype("agent", defaults);
 * InteractionDescription interaction = new InteractionDescription();
 * EntitySnapshot snap = new AgentSnapshot(id, fields, 1, prototype,
 * interaction);
 * 
 * treeTable.put(id, 1, snap); // put snapshot into table
 *//**
 * once finished adding all snapshots, build Immutable table
 */
/*
 * database = new ImmutableTable.Builder<EntityID, Integer, EntitySnapshot>()
 * .putAll(treeTable).build();
 * 
 * database.get(id, 1).id(); // Test database
 */
