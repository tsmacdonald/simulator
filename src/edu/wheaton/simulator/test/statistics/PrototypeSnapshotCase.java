package edu.wheaton.simulator.test.statistics;

/**
 * A JUnit test case for testing PrototypeSnapshot.java.
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

import com.google.common.collect.ImmutableSet;

import edu.wheaton.simulator.datastructure.Grid;
import edu.wheaton.simulator.entity.AgentID;
import edu.wheaton.simulator.entity.EntityID;
import edu.wheaton.simulator.entity.Prototype;
import edu.wheaton.simulator.statistics.PrototypeSnapshot;
import edu.wheaton.simulator.statistics.SnapshotFactory;

public class PrototypeSnapshotCase {
	
	String categoryName;
	Grid grid;
	Prototype prototype;
	HashMap<String, String> fields;
	int population;
	ImmutableSet<EntityID> children;
	Integer step;

	/**
	 * Initialize variables.
	 */
	@Before
	public void setUp() {
		categoryName = "testing";
		grid = new Grid(10, 10);
		prototype = new Prototype(grid, "tester");
		fields = new HashMap<String, String>();
		population = 50;
		children = prototype.childIDs();
		step = new Integer(23);
	}

	/**
	 * Give variables null pointers.
	 */
	@After
	public void tearDown() {
	}

	/**
	 * Auto-generated method stub.
	 */
	@Test
	public void test() {
		//fail("Not yet implemented");
	}
	
	/**
	 * Tests to make sure a PrototypeSnapshot object was successfully created.
	 */
	public void prototypeSnapshotTest() {
		PrototypeSnapshot protoSnap = new PrototypeSnapshot(categoryName, prototype.getPrototypeID(),
				SnapshotFactory.makeFieldSnapshots(fields), population,
				children, step);
		Assert.assertNotNull("PrototypeSnapshot not created.", protoSnap);
	}
}
