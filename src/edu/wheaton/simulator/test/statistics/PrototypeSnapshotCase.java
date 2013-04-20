package edu.wheaton.simulator.test.statistics;

/**
 * A JUnit test case for testing PrototypeSnapshot.java.
 * 
 * @author Nico Lasta
 * Wheaton College, CSCI 335
 * Spring 2013
 * 25 Mar 2013
 */
import java.awt.Color;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableSet;

import edu.wheaton.simulator.datastructure.Grid;
import edu.wheaton.simulator.entity.AgentID;
import edu.wheaton.simulator.entity.Prototype;
import edu.wheaton.simulator.statistics.PrototypeSnapshot;
import edu.wheaton.simulator.statistics.SnapshotFactory;
import edu.wheaton.simulator.statistics.TriggerSnapshot;

public class PrototypeSnapshotCase {
	
	String categoryName;
	Grid grid;
	Prototype prototype;
	HashMap<String, String> fields;
	Set<TriggerSnapshot> triggers;
	int population;
	ImmutableSet<AgentID> children;
	Integer step; 
	byte[] design; 

	/**
	 * Initialize variables.
	 */
	@Before
	public void setUp() {
		categoryName = "testing";
		grid = new Grid(10, 10);
		prototype = new Prototype("tester");
		fields = new HashMap<String, String>();
		triggers = new HashSet<TriggerSnapshot>();
		fields.put("Age", "1"); 
		fields.put("Height", "5"); 
		fields.put("Smell", "4");
		
		triggers.add(new TriggerSnapshot("trigger1", 1, "conditionExpression", "behaviorExpression"));
		population = 50;
		children = prototype.childIDs();
		step = new Integer(23);
		design = new byte[10]; 
	}
	
	/**
	 * Tests to make sure a PrototypeSnapshot object was successfully created.
	 */
	@Test
	public void prototypeSnapshotTest() {		
		PrototypeSnapshot protoSnap = new PrototypeSnapshot(categoryName, 
				SnapshotFactory.makeFieldSnapshots(fields), population,
				children, triggers, new Color(10, 10, 10), design);
		Assert.assertNotNull("PrototypeSnapshot not created.", protoSnap);
	}
	
	/**
	 * Tests the serialize() method 
	 */
	@Test
	public void serializeTest(){
		PrototypeSnapshot protoSnap = new PrototypeSnapshot(categoryName, 
				SnapshotFactory.makeFieldSnapshots(fields), population,
				children, triggers, new Color(10, 10, 10), design);
		
		String expected = "PrototypeSnapshot\ntesting\n-16119286\n0000000000\nFieldSnapshot Age 1";
		expected += "\nFieldSnapshot Height 5\nFieldSnapshot Smell 4";
		expected += "\nTrigger~trigger1~1~conditionExpression~behaviorExpression";
		System.out.println(protoSnap.serialize()); 
		Assert.assertEquals(expected, protoSnap.serialize()); 	
	}
}
