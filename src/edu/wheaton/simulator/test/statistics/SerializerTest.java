package edu.wheaton.simulator.test.statistics;

import static org.junit.Assert.fail;

import java.util.HashMap;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.wheaton.simulator.entity.Entity;
import edu.wheaton.simulator.entity.Prototype;
import edu.wheaton.simulator.simulation.Grid;
import edu.wheaton.simulator.statistics.AgentSnapshot;
import edu.wheaton.simulator.statistics.Serializer;
import edu.wheaton.simulator.statistics.SnapshotFactory;

public class SerializerTest {
	
	String s;
	Entity entity;
	Grid grid;
	Prototype prototype;
	Integer step;
	HashMap<String, String> fields;

	@Before
	public void setUp() throws Exception {
		entity = new Entity();
		grid = new Grid(10, 10);
		prototype = new Prototype(grid, "tester");
		step = new Integer(23);
		fields = new HashMap<String, String>();
		fields.put("Pig", "Tom");
		fields.put("Monkey", "Olly");
		fields.put("Cat", "Joomba");
	}

	@After
	public void tearDown() throws Exception {
		entity = null;
		grid = null;
		prototype = null;
		step = null;
		fields.remove("Pig");
		fields.remove("Monkey");
		fields.remove("Cat");
		fields = null;
	}

	@Test
	public void test() {
		//fail("Not yet implemented");
	}

	@Test
	public void makeNewAgentSerializerObject() {
		AgentSnapshot agentSnap = new AgentSnapshot(entity.getEntityID(),
				SnapshotFactory.makeFieldSnapshots(fields), step,
				prototype.getPrototypeID());
		Assert.assertNotNull("AgentSnapshot not created.", agentSnap);
		
		s = agentSnap.serialize();
		System.out.println(s);
		
		Serializer.serializer(s);
	}
}
