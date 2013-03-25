/**
 * 
 */
package edu.wheaton.simulator.statistics;

import static org.junit.Assert.fail;

import java.util.HashMap;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;

import edu.wheaton.simulator.entity.Entity;
import edu.wheaton.simulator.entity.EntityID;

/**
 * A JUnit test case for testing EntitySnapshotTable.java.
 * 
 * @author Grant Hensel
 * Wheaton College, CSCI 335
 * Spring 2013
 * 25 Mar 2013
 */
public class EntitySnapshotTableTest {

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
		Assert.assertNotNull(result.get(id.getInt())); 
	}

	/**
	 * Test method for {@link edu.wheaton.simulator.statistics.EntitySnapshotTable#containsEntity(edu.wheaton.simulator.entity.EntityID)}.
	 */
	@Test
	public void testContainsEntity() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link edu.wheaton.simulator.statistics.EntitySnapshotTable#containsStep(int)}.
	 */
	@Test
	public void testContainsStep() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link edu.wheaton.simulator.statistics.EntitySnapshotTable#getSize()}.
	 */
	@Test
	public void testGetSize() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link edu.wheaton.simulator.statistics.EntitySnapshotTable#getAllSteps()}.
	 */
	@Test
	public void testGetAllSteps() {
		fail("Not yet implemented");
	}

}
