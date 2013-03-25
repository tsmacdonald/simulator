package edu.wheaton.simulator.test.statistics;


import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.google.common.collect.TreeBasedTable;

import edu.wheaton.simulator.entity.Entity;
import edu.wheaton.simulator.entity.EntityID;
import edu.wheaton.simulator.statistics.EntitySnapshot;
import edu.wheaton.simulator.statistics.EntitySnapshotTable;
import edu.wheaton.simulator.statistics.SnapshotFactory;

@RunWith(JUnit4.class)
public class EntitySnapshotTableCase {

	EntitySnapshotTable table;
	ArrayList<EntitySnapshot> snaps;
	HashSet<EntityID> ids;
	String[] names = new String[] {"bear", "tom", "john", "piglet", "reese"};
	
	/**
	 * Make entities and then snapshots to put into the table
	 */
	@Before
	public void setUp() {
		snaps = new ArrayList<EntitySnapshot>();
		ids = new HashSet<EntityID>();
		for(int i = 0; i < 5; i++) {
			Entity entity = new Entity();
			Map<String, String> fields = new HashMap<String, String>();
			fields.put("name", names[i]);
			fields.put("weight", "50");
			ids.add(entity.getEntityID());
			for(int s = 1; s < 3; s++) {
				snaps.add(new EntitySnapshot(entity.getEntityID(), SnapshotFactory.makeFieldSnapshots(fields), s));
			}
		}
		table = null;
		fillTable();
	}
	
	/**
	 * Helper method to fill the table for tests to use
	 */
	public void fillTable() {
		table = new EntitySnapshotTable();
		for(EntitySnapshot snap: snaps) {
			table.putEntity(snap);
		}
	}
	
	@After
	public void tearDown() {
		table = null;
		snaps = null;
		ids = null;
	}
	
	@Test
	public void test() {
		//fail("Not yet implemented");
	}

	/**
	 * Test whether the table actually saves records
	 */
	@Test
	public void tableStoresStuff() {
		assertNotNull("failure-table should not be null", table);
		for(EntitySnapshot snap: snaps) {
			table.putEntity(snap);
		}
		org.junit.Assert.assertEquals("failure-table.size() should equal 10. it equals " + table.getSize(), table.getSize(), 10);
	}
	
	/**
	 * Test whether the table stores records correctly
	 */
	@Test
	public void tableStoresCorrectly() {
		for(EntityID id : ids) {
			int s = 1;
			while(s < 3) {
				EntitySnapshot snap = table.get(id, s);
				org.junit.Assert.assertNotNull("failure-Snap should not be null(" + snap+")", snap);
				s++;
			}
		}
	}
	
	@Test
	public void tableChecksForStep() {
		org.junit.Assert.assertTrue(table.containsStep(1));
		org.junit.Assert.assertTrue(table.containsStep(2));
	}
}
