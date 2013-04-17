package edu.wheaton.simulator.test.statistics;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.wheaton.simulator.datastructure.ElementAlreadyContainedException;
import edu.wheaton.simulator.datastructure.Grid;
import edu.wheaton.simulator.entity.Agent;
import edu.wheaton.simulator.entity.Prototype;
import edu.wheaton.simulator.statistics.AgentSnapshot;
import edu.wheaton.simulator.statistics.AgentSnapshotTable;
import edu.wheaton.simulator.statistics.PrototypeSnapshot;
import edu.wheaton.simulator.statistics.Saver;
import edu.wheaton.simulator.statistics.SnapshotFactory;

public class SaverTest {

	String s;
	Agent agent;
	Agent agentOther;
	Grid grid;
	Prototype prototypeOne;
	Prototype prototypeTwo;
	Integer step;
	
	@Before
	public void setUp() {
		grid = new Grid(10, 10);
		prototypeOne = new Prototype(grid, "Prototype 1");
		agent = prototypeOne.createAgent();
		try {
			agent.addField("Pig", "Tom");
			agent.addField("Monkey", "Olly");
			agent.addField("Cat", "Joomba");
		} catch (ElementAlreadyContainedException e) {
			e.printStackTrace();
		}
		
		prototypeTwo = new Prototype(grid, "Prototype 2");
		step = new Integer(23);
	}

	@After
	public void tearDown() {
		agent = null;
		grid = null;
		prototypeOne = null;
		step = null;
	}

	@Test
	public void testSave() {
		//Create two AgentSnapshots
		AgentSnapshot agentSnap1 = new AgentSnapshot(agent.getID(), 
				SnapshotFactory.makeFieldSnapshots(agent.getCustomFieldMap()), 
				step, prototypeOne.getName(), null, 0, 0);  
		
		AgentSnapshot agentSnap2 = new AgentSnapshot(prototypeOne.createAgent().getID(), 
				SnapshotFactory.makeFieldSnapshots(agent.getCustomFieldMap()), 
				step, prototypeOne.getName(), null, 0, 0);  
		
		//Create the table, add two AgentSnapshots
		AgentSnapshotTable table = new AgentSnapshotTable();
		table.putEntity(agentSnap1); 
		table.putEntity(agentSnap2); 
		
		Saver s = new Saver(table, new HashMap<String, PrototypeSnapshot>());
		s.save();
	}
}
