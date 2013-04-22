package edu.wheaton.simulator.test.statistics;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
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
	public void setUp() {
		
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
		l.loadSimulation(new File("simulations/SimulationState.txt"));
		System.out.println("Loaded"); 
		grid = l.getGrid(); 
		prototypes = l.getPrototypes(); 
		name = l.getName(); 	
		
		Assert.assertNotNull(grid); 
		Assert.assertNotNull(prototypes); 
		Assert.assertEquals("SimulationState.txt", name); 
	}
	
	@Test
	public void testLoadPrototype() throws FileNotFoundException{
		Loader l = new Loader(); 
		Prototype proto = null; 
		File loadFile = new File("prototypes/Prototype 1.txt"); 
		
		//Print loadFile contents for debugging 
		System.out.println("\nLoad File Contents:"); 
		Scanner s = new Scanner(loadFile); 
		while(s.hasNext())
			System.out.println(s.nextLine()); 
		
		proto = l.loadPrototype(loadFile);
		Assert.assertNotNull(proto); 
		Assert.assertEquals("Prototype 1", proto.getName()); 
		s.close();
	}	
}