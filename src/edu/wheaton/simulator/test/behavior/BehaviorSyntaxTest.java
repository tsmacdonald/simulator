package edu.wheaton.simulator.test.behavior;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.wheaton.simulator.datastructure.ElementAlreadyContainedException;
import edu.wheaton.simulator.datastructure.Grid;
import edu.wheaton.simulator.entity.Agent;
import edu.wheaton.simulator.entity.Prototype;
import edu.wheaton.simulator.entity.Trigger;
import edu.wheaton.simulator.expression.Expression;
import edu.wheaton.simulator.simulation.SimulationPauseException;

public class BehaviorSyntaxTest {

	@Before
	public void setUp() {
	}
	
	@Test
	public void testCloneAgentAtPositionBehavior() throws ElementAlreadyContainedException, SimulationPauseException {
		Grid testGrid = new Grid(20, 20);
		Prototype proto = new Prototype(testGrid, "cat");
		Assert.assertTrue(testGrid.isValidCoord(5, 5));
		Assert.assertTrue(testGrid.isValidCoord(6, 6));

		testGrid.spawnAgent(proto.createAgent(), 5, 5);
		Assert.assertFalse(testGrid.isValidCoord(5, 5));

		
		Expression cloneAgentAtPosition = new Expression("cloneAgentAtPosition(5, 5, 6, 6)");
		Expression alwaysTrue = new Expression("1 < 2");
		proto.addTrigger(new Trigger("cloneAgentAtPosition", 1, alwaysTrue, cloneAgentAtPosition));
		testGrid.updateEntities();
		Assert.assertFalse(testGrid.isValidCoord(6, 6));
	}
	
	@Test
	public void testCloneBehavior() {
		fail("Not yet implemented");
	}

	@Test
	public void testDieBehavior() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testFullSlotException() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testMoveBehavior() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testSetFieldBehavior() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testSetFieldOfAgentBehavior() {
		fail("Not yet implemented");
	}
}
