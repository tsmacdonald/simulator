package edu.wheaton.simulator.test.statistics;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.wheaton.simulator.entity.Entity;
import edu.wheaton.simulator.statistics.EntitySnapshot;
import edu.wheaton.simulator.statistics.SnapshotFactory;

public class EntitySnapshotCase {

	Entity entity;
	Integer step;
	Map<String, String> fields;

	@Before
	public void setUp() throws Exception {
		entity = new Entity();
		step = new Integer(23);
		fields = new HashMap<String, String>();
		fields.put("Pig", "Tom");
		fields.put("Monkey", "Olly");
		
	}

	@After
	public void tearDown() throws Exception {
		entity = null;
		step = null;
		fields = null;
	}

	@Test
	public void test() {
		// fail("Not yet implemented");
	}

	@Test
	public void entitySnapshotTest() {
		EntitySnapshot entSnap = new EntitySnapshot(entity.getEntityID(), 
				SnapshotFactory.makeFieldSnapshots(fields), step);
		Assert.assertNotNull("EntitySnapshot not created.", entSnap);
	}
}
