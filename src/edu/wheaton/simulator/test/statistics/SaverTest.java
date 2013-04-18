package edu.wheaton.simulator.test.statistics;

import java.awt.Color;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.wheaton.simulator.datastructure.ElementAlreadyContainedException;
import edu.wheaton.simulator.datastructure.Grid;
import edu.wheaton.simulator.entity.Agent;
import edu.wheaton.simulator.entity.Prototype;
import edu.wheaton.simulator.simulation.end.SimulationEnder;
import edu.wheaton.simulator.statistics.AgentSnapshot;
import edu.wheaton.simulator.statistics.AgentSnapshotTable;
import edu.wheaton.simulator.statistics.PrototypeSnapshot;
import edu.wheaton.simulator.statistics.Saver;
import edu.wheaton.simulator.statistics.SnapshotFactory;
import edu.wheaton.simulator.statistics.TriggerSnapshot;

public class SaverTest {

	String s;
	Agent agent;
	Agent agentOther;
	Grid grid;
	Prototype prototypeOne;
	Prototype prototypeTwo;
	Integer step;
	Set<TriggerSnapshot> triggers;
	SimulationEnder simEnder; 
	
	@Before
	public void setUp() throws ElementAlreadyContainedException {
		grid = new Grid(10, 10);
		grid.addField("Depth", "100"); 
		
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
		agentOther = prototypeTwo.createAgent();
		try {
			agentOther.addField("Crayfish", "Paul");
			agentOther.addField("Meerkat", "Timon");
			agentOther.addField("Person", "John Charles");
		} catch (ElementAlreadyContainedException e) {
			e.printStackTrace();
		}
		
		step = new Integer(23);
		
		//Create the ending conditions
		simEnder = new SimulationEnder(); 
		simEnder.setPopLimit(prototypeOne.getName(), 100); 
		simEnder.setPopLimit(prototypeTwo.getName(), 100); 
		simEnder.setStepLimit(20); 
		
		//Create the list of TriggerSnapshots
		triggers = new HashSet<TriggerSnapshot>();
		triggers.add(new TriggerSnapshot("trigger1", 1, "conditionExpression", "behaviorExpression"));
	}

	@After
	public void tearDown() {
		agent = null;
		agentOther = null;
		grid = null;
		prototypeOne = null;
		prototypeTwo = null;
		step = null;
	}

	@Test
	public void testSave() {
		//Create two AgentSnapshots
		AgentSnapshot agentSnap1 = new AgentSnapshot(agent.getID(), 
				SnapshotFactory.makeFieldSnapshots(agent.getCustomFieldMap()), 
				step, prototypeOne.getName(), null, 0, 0);  
		
		AgentSnapshot agentSnap2 = new AgentSnapshot(agentOther.getID(), 
				SnapshotFactory.makeFieldSnapshots(agentOther.getCustomFieldMap()), 
				step, prototypeTwo.getName(), null, 0, 0);
		
		//Create a global variable snapshot
		AgentSnapshot agentSnap3 = SnapshotFactory.makeGlobalVarSnapshot(grid, new Prototype(grid, "GRID"), step);
		
		//Create the table, add the AgentSnapshots				
		AgentSnapshotTable table = new AgentSnapshotTable();
		table.putEntity(agentSnap1); 
		table.putEntity(agentSnap2);
		table.putEntity(agentSnap3); 
		
		// Create two PrototypeSnapshots
		PrototypeSnapshot protoSnapAlpha = new PrototypeSnapshot(prototypeOne.getName(), 
				SnapshotFactory.makeFieldSnapshots(agent.getCustomFieldMap()), prototypeOne.childPopulation(),
				prototypeOne.childIDs(), triggers, step, new Color(10, 10, 10), agent.getDesign());
		Assert.assertNotNull("PrototypeSnapshot not created.", protoSnapAlpha);
		
		PrototypeSnapshot protoSnapBeta = new PrototypeSnapshot(prototypeTwo.getName(), 
				SnapshotFactory.makeFieldSnapshots(agentOther.getCustomFieldMap()), prototypeTwo.childPopulation(),
				prototypeTwo.childIDs(), triggers, step, new Color(10, 10, 10), agentOther.getDesign());
		Assert.assertNotNull("PrototypeSnapshot not created.", protoSnapAlpha);
		
		// Creating a HashMap of PrototypeSnapshots
		HashMap<String, PrototypeSnapshot> protoMap = new HashMap<String, PrototypeSnapshot>();
		protoMap.put("PrototypeSnapshot Alpha", protoSnapAlpha);
		protoMap.put("PrototypeSnapshot Beta", protoSnapBeta);
		Assert.assertTrue("protoMap has values", !protoMap.isEmpty());
		
		Saver s = new Saver(table, protoMap, grid.getWidth(), grid.getHeight(), simEnder);
		s.saveSimulation("SimulationState");
		
		System.out.println("File creation complete.");
	}
}
