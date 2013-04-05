package edu.wheaton.simulator.test.statistics;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import edu.wheaton.simulator.datastructure.ElementAlreadyContainedException;
import edu.wheaton.simulator.datastructure.Grid;
import edu.wheaton.simulator.entity.Agent;
import edu.wheaton.simulator.entity.Prototype;
import edu.wheaton.simulator.statistics.AgentSnapshot;
import edu.wheaton.simulator.statistics.Serializer;
import edu.wheaton.simulator.statistics.SnapshotFactory;

@RunWith(JUnit4.class)
public class SerializerTest {
	
	String s;
	Agent agent;
	Grid grid;
	Prototype prototype;
	Integer step;

	@Before
	public void setUp() {
		grid = new Grid(10, 10);
		prototype = new Prototype(grid, "tester");
		agent = prototype.createAgent();
		try {
			agent.addField("Pig", "Tom");
			agent.addField("Monkey", "Olly");
			agent.addField("Cat", "Joomba");
		} catch (ElementAlreadyContainedException e) {
			e.printStackTrace();
		}
		step = new Integer(23);
	}

	@After
	public void tearDown() {
		agent = null;
		grid = null;
		prototype = null;
		step = null;
	}

	/*
	 * Test that Serializer properly writes and reads what it should 
	 */
	@Test
	public void serializeAgentSnapshot() {
		AgentSnapshot agentSnap = new AgentSnapshot(agent.getID(),
				SnapshotFactory.makeFieldSnapshots(agent.getCustomFieldMap()), step,
				prototype.getPrototypeID());
		Assert.assertNotNull("AgentSnapshot not created.", agentSnap);
		
		s = agentSnap.serialize();
		System.out.println(s); // Test and see output is as expected
		System.out.println("\nNow this is what is read from the saved file:\n");
		StringBuilder sb = Serializer.serializer(s);
		org.junit.Assert.assertNotNull("StringBuilder(" + sb + ") shouldn't be null", sb);
	}
}
