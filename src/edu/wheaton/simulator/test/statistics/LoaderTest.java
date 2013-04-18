package edu.wheaton.simulator.test.statistics;

import java.io.File;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Test;

import edu.wheaton.simulator.datastructure.Grid;
import edu.wheaton.simulator.entity.Prototype;
import edu.wheaton.simulator.statistics.Loader;

public class LoaderTest {

	Grid grid; 
	Set<Prototype> prototypes;
	String name; 

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
}
