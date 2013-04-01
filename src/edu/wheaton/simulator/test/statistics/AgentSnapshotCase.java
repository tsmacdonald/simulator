package edu.wheaton.simulator.test.statistics;

/**
 * A JUnit test case for testing AgentSnapshot.java.
 * 
 * @author Nico Lasta
 * Wheaton College, CSCI 335
 * Spring 2013
 * 25 Mar 2013
 */

import java.util.HashMap;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.wheaton.simulator.datastructure.Grid;
import edu.wheaton.simulator.entity.Entity;
import edu.wheaton.simulator.entity.Prototype;
import edu.wheaton.simulator.statistics.AgentSnapshot;
import edu.wheaton.simulator.statistics.SnapshotFactory;

public class AgentSnapshotCase {

	Entity entity;
	Grid grid;
	Prototype prototype;
	Integer step;
	HashMap<String, String> fields;

	/**
	 * Initialize variables.
	 */
	@Before
	public void setUp() {
		entity = new Entity();
		grid = new Grid(10, 10);
		prototype = new Prototype(grid, "tester");
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
		grid = null;
		prototype = null;
		step = null;
		fields.remove("Pig");
		fields.remove("Monkey");
		fields.remove("Cat");
		fields = null;
	}

	/**
	 * Tests to make sure an AgentSnapshot object was successfully created.
	 */
	@Test
	public void agentSnapshotTest() {
		AgentSnapshot agentSnap = new AgentSnapshot(entity.getEntityID(),
				SnapshotFactory.makeFieldSnapshots(fields), step,
				prototype.getPrototypeID());
		Assert.assertNotNull("AgentSnapshot not created.", agentSnap);
	}
	
	/**
	 * Tests the serialize() method 
	 */
	@Test
	public void serializeTest(){
		AgentSnapshot agentSnap = new AgentSnapshot(entity.getEntityID(),
				SnapshotFactory.makeFieldSnapshots(fields), step,
				prototype.getPrototypeID());
		
		String expected = "AgentSnapshot\n102\nFields: Cat FieldSnapshot Cat Joomba" +
				"\nFields: Pig FieldSnapshot Pig Tom\nFields: Monkey FieldSnapshot Monkey Olly\n23\n1"; 
		System.out.println(agentSnap.serialize()); 
		
		Assert.assertEquals(expected, agentSnap.serialize()); 	
	}
}
