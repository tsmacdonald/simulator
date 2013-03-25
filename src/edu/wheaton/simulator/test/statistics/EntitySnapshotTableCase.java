package edu.wheaton.simulator.test.statistics;

import java.util.HashMap;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.wheaton.simulator.entity.Entity;
import edu.wheaton.simulator.statistics.EntitySnapshot;
import edu.wheaton.simulator.statistics.SnapshotFactory;

public class EntitySnapshotTableCase {

	EntitySnapshot entitySnap;
	Entity entity;
	Integer step;
	HashMap<String, String> fields;

	@Before
	public void setUp() throws Exception {
		entity = new Entity();
		step = new Integer(23);
		fields = new HashMap<String, String>();
		fields.put("Pig", "Tom");
		fields.put("Monkey", "Olly");
		fields.put("Cat", "Joomba");
		entitySnap = new EntitySnapshot(entity.getEntityID(),
				SnapshotFactory.makeFieldSnapshots(fields), step);
	}

	@After
	public void tearDown() throws Exception {
		entity = null;
		step = null;
		fields.remove("Pig");
		fields.remove("Monkey");
		fields.remove("Cat");
		fields = null;
		entitySnap = null;
	}

	@Test
	public void test() {
		// fail("Not yet implemented");
	}

	@Test
	public void entitySnapshotTableTest() {

	}
}
