package edu.wheaton.simulator.test.statistics;

/**
 * A JUnit test case for testing EntitySnapshot.java.
 * 
 * @author Nico Lasta
 * Wheaton College, CSCI 335
 * Spring 2013
 * 24 Mar 2013
 */

import java.util.HashMap;

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
	HashMap<String, String> fields;

	/**
	 * Initialize variables.
	 */
	@Before
	public void setUp() {
		entity = new Entity();
		step = new Integer(23);
		fields = new HashMap<String, String>();
		fields.put("Pig", "Tom");
		fields.put("Monkey", "Olly");
		fields.put("Cat", "Joomba");
	}

	/**
	 * Give variables null pointers.
	 */
	@After
	public void tearDown() {
		entity = null;
		step = null;
		fields.remove("Pig");
		fields.remove("Monkey");
		fields.remove("Cat");
		fields = null;
	}

	/**
	 * Tests to make sure an EntitySnapshot object was successfully created.
	 */
	@Test
	public void entitySnapshotTest() {
		EntitySnapshot entSnap = new EntitySnapshot(entity.getEntityID(),
				SnapshotFactory.makeFieldSnapshots(fields), step);
		Assert.assertNotNull("EntitySnapshot not created.", entSnap);
	}
	
	/**
	 * Tests the serialize() method 
	 */
	@Test
	public void serializeTest(){
		EntitySnapshot entSnap = new EntitySnapshot(entity.getEntityID(),
				SnapshotFactory.makeFieldSnapshots(fields), step);
		
		String expected = "EntitySnapshot\n2\nFields: Cat FieldSnapshot Cat Joomba" +
				"\nFields: Pig FieldSnapshot Pig Tom\nFields: Monkey FieldSnapshot Monkey Olly\n23"; 
		System.out.println(entSnap.serialize()); 
		Assert.assertEquals(expected, entSnap.serialize()); 	
	}
}
