package edu.wheaton.simulator.test.simulation;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.wheaton.simulator.simulation.Simulation;
import edu.wheaton.simulator.simulation.SimulationPauseException;

public class SimulationTest {

	private Simulation sim;
	
	@Before
	public void setUp() {
		sim = new Simulation("SimTest", 16, 16);
	}
	
	@After
	public void tearDown() {
		sim = null;
	}
	
	@Test
	public void test() {
		Assert.assertNotSame(sim.getGrid(), null);
		Assert.assertEquals("SimTest", sim.getName());
		sim.setSleepPeriod(200);
		Assert.assertEquals(200, sim.getSleepPeriod());
		try {
			sim.notifyDisplayObservers();
			sim.play();
			sim.pause();
			sim.resetLayer();
			sim.setLayerExtremes();
			sim.runLayer();
			sim.stopLayer();
			sim.setName("SimTest2");
			Assert.assertEquals("SimTest2", sim.getName());
		}
		catch (Exception e) {
			fail("This exception probably shouldn't happen with an empty grid");
		}
		try {
			sim.updateEntities();
		} catch (SimulationPauseException e) {
			fail("SimulationPauseException problemo.");
		}
	}

}
