/**
 * 
 */
package edu.wheaton.simulator.test.statistics;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.google.common.collect.ImmutableMap;

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

class EntitySnapshotTableTest {

	EntitySnapshot entitySnap;
	Entity entity;
	Integer step;
	HashMap<String, String> fields;

	/**
	 * A JUnit test case for testing EntitySnapshotTable.java.
	 * 
	 * @author Grant Hensel
	 * Wheaton College, CSCI 335
	 * Spring 2013
	 * 25 Mar 2013
	 */
	public class EntitySnapshotTableCase {

		EntitySnapshotTable t;
		EntityID id; 

		/**
		 * @throws java.lang.Exception
		 */
		@Before
		public void setUp() throws Exception {
			t = new EntitySnapshotTable(); 

			//Test data for an EntitySnapshot to insert
			Entity entity = new Entity();

			Integer step = new Integer(1);
			HashMap<String, String> fields = new HashMap<String, String>();
			fields.put("Weight", "2.0");
			fields.put("Name", "Ted");
			id = entity.getEntityID(); 
			EntitySnapshot entSnap = new EntitySnapshot(id,
					SnapshotFactory.makeFieldSnapshots(fields), step);

			t.putEntity(entSnap); 
		}

		/**
		 * @throws java.lang.Exception
		 */
		@After
		public void tearDown() throws Exception {
		}

		/**
		 * Test method for {@link edu.wheaton.simulator.statistics.EntitySnapshotTable#EntitySnapshotTable()}.
		 */
		@Test
		public void testEntitySnapshotTable() {
			Assert.assertNotNull(t); 
		}

		/**
		 * Test method for {@link edu.wheaton.simulator.statistics.EntitySnapshotTable#putEntity(edu.wheaton.simulator.statistics.EntitySnapshot)}.
		 */
		@Test
		public void testPutEntity() {
			//Test data for an EntitySnapshot to insert
			Entity entity = new Entity();
			Integer step = new Integer(2);
			HashMap<String, String> fields = new HashMap<String, String>();
			fields.put("Weight", "3.0");
			fields.put("Name", "Frank");
			EntitySnapshot entSnap = new EntitySnapshot(entity.getEntityID(),
					SnapshotFactory.makeFieldSnapshots(fields), step);

			t.putEntity(entSnap); 
		}

		/**
		 * Test method for {@link edu.wheaton.simulator.statistics.EntitySnapshotTable#getSnapshotsAtStep(int)}.
		 */
		@Test
		public void testGetSnapshotsAtStep() {
			ImmutableMap<EntityID, EntitySnapshot> result = t.getSnapshotsAtStep(1);
			Assert.assertNotNull(result.get(id)); 
		}

		/**
		 * Test method for {@link edu.wheaton.simulator.statistics.EntitySnapshotTable#getSnapshotsOfEntity(edu.wheaton.simulator.entity.EntityID)}.
		 */
		@Test
		public void testGetSnapshotsOfEntity() {
			ImmutableMap<Integer, EntitySnapshot> result = t.getSnapshotsOfEntity(id);

			EntitySnapshot e = result.get(id.getInt()); 
			Assert.assertNotNull(e); 
		}

		/**
		 * Test method for {@link edu.wheaton.simulator.statistics.EntitySnapshotTable#containsEntity(edu.wheaton.simulator.entity.EntityID)}.
		 */
		@Test
		public void testContainsEntity() {
			Assert.assertTrue(t.containsEntity(id)); 
		}

		/**
		 * Test method for {@link edu.wheaton.simulator.statistics.EntitySnapshotTable#containsStep(int)}.
		 */
		@Test
		public void testContainsStep() {
			Assert.assertTrue(t.containsStep(1)); 
		}

		/**
		 * Test method for {@link edu.wheaton.simulator.statistics.EntitySnapshotTable#getSize()}.
		 */
		@Test
		public void testGetSize() {
			Assert.assertEquals(1, t.getSize()); 
		}

		/**
		 * Test method for {@link edu.wheaton.simulator.statistics.EntitySnapshotTable#getAllSteps()}.
		 */
		@Test
		public void testGetAllSteps() {
			Set<Integer> i = t.getAllSteps(); 
			Assert.assertTrue(i.contains(1)); 
		}

	}
}
