package edu.wheaton.simulator.test.statistics;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import edu.wheaton.simulator.datastructure.ElementAlreadyContainedException;
import edu.wheaton.simulator.datastructure.Grid;
import edu.wheaton.simulator.entity.Agent;
import edu.wheaton.simulator.entity.Prototype;
import edu.wheaton.simulator.statistics.Loader;
import edu.wheaton.simulator.statistics.TriggerSnapshot;

public class LoaderTest {

	Grid grid; 
	Set<Prototype> prototypes;
	Prototype prototypeOne;
	Agent agent;
	String name; 
	Set<TriggerSnapshot> triggers;

	@Before
	public void setUp() throws ElementAlreadyContainedException {
		
		prototypeOne = new Prototype("Prototype 1");
		agent = prototypeOne.createAgent(grid);
		try {
			agent.addField("Pig", "Tom");
			agent.addField("Monkey", "Olly");
			agent.addField("Cat", "Joomba");
		} catch (ElementAlreadyContainedException e) {
			e.printStackTrace();
		}

		//Create the list of TriggerSnapshots
		triggers = new HashSet<TriggerSnapshot>();
		triggers.add(new TriggerSnapshot("trigger1", 1, "conditionExpression", "behaviorExpression"));
	}
	
	@Test
	public void testLoadSimulation() throws Exception {
		Loader l = new Loader();
		l.loadSimulation(new File("SimulationState.txt"));
		System.out.println("Loaded"); 
		grid = l.getGrid(); 
		prototypes = l.getPrototypes(); 
		name = l.getName(); 	
		
		Assert.assertNotNull(grid); 
		Assert.assertNotNull(prototypes); 
		Assert.assertEquals("SimulationState.txt", name); 
	}
	
	@Test
	public void testLoadPrototype(){
		
	}	
}
