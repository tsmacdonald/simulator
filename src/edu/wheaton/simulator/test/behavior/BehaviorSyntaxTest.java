package edu.wheaton.simulator.test.behavior;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.wheaton.simulator.datastructure.ElementAlreadyContainedException;
import edu.wheaton.simulator.datastructure.Grid;
import edu.wheaton.simulator.entity.Prototype;
import edu.wheaton.simulator.entity.Trigger;
import edu.wheaton.simulator.expression.Expression;
import edu.wheaton.simulator.simulation.SimulationPauseException;

/**
 * Simple tests to verify the syntax of important hard coded behaviors functions
 * 
 * @author David Emmanuel
 *
 */

public class BehaviorSyntaxTest {

	Grid testGrid;
	Prototype proto;
	/**
	 * Makes a cat at position 5, 5 with the field of health set to 100
	 * 
	 * @throws ElementAlreadyContainedException
	 */
	@Before
	public void setUp() throws ElementAlreadyContainedException {
		testGrid = new Grid(20, 20);
		proto = new Prototype("cat");
		Prototype.addPrototype(proto);
		proto.addField("health", 100 + "");
		testGrid.addAgent(proto.createAgent(testGrid), 5, 5);
		// there is an agent at 5, 5
		Assert.assertFalse(testGrid.emptyPos(5, 5));
	}
	
	@Test
	public void testCloneAgentAtPositionBehavior() throws SimulationPauseException {
		Expression cloneAgentAtPosition = new Expression("cloneAgentAtPosition( 5, 5, 6, 6)");
		Expression alwaysTrue = new Expression("1 < 2");
		proto.addTrigger(new Trigger("cloneAgentAtPosition", 1, alwaysTrue, cloneAgentAtPosition));
		testGrid.updateEntities();
		Assert.assertFalse(testGrid.emptyPos(6, 6));
	}
	
	@Test
	public void testCloneBehavior() throws SimulationPauseException {
		Expression clone = new Expression("clone( 6 , 6)");
		Expression alwaysTrue = new Expression("1 < 2");
		proto.addTrigger(new Trigger("clone", 1, alwaysTrue, clone));
		testGrid.updateEntities();
		Assert.assertFalse(testGrid.emptyPos(6, 6));
	}

	@Test
	public void testDieBehavior() throws SimulationPauseException {
		Expression die = new Expression("die()");
		Expression alwaysTrue = new Expression("1 < 2");
		proto.addTrigger(new Trigger("clone", 1, alwaysTrue, die));
		testGrid.updateEntities();
		// agent is removed from the grid
		Assert.assertTrue(testGrid.emptyPos(5, 5));	
		}

	@Test
	public void testMoveBehavior() throws SimulationPauseException {
		// 6, 6 is open for an agent to move there
		Assert.assertTrue(testGrid.emptyPos(6, 6));
		Expression move = new Expression("move( 6 , 6)");
		Expression alwaysTrue = new Expression("1 < 2");
		proto.addTrigger(new Trigger("move", 1, alwaysTrue, move));
		testGrid.updateEntities();
		Assert.assertTrue(testGrid.emptyPos(5, 5));
		Assert.assertFalse(testGrid.emptyPos(6, 6));	
		}
	
	@Test
	public void testMoveRightBehavior() throws SimulationPauseException {
		// 6, 5 is open for an agent to move there
		Assert.assertTrue(testGrid.emptyPos(6, 5));
		Expression moveRight = new Expression("moveRight(1)");
		Expression alwaysTrue = new Expression("1 < 2");
		proto.addTrigger(new Trigger("moveRight", 1, alwaysTrue, moveRight));
		testGrid.updateEntities();
		Assert.assertTrue(testGrid.emptyPos(5, 5));
		Assert.assertFalse(testGrid.emptyPos(6, 5));	
		}
	
	@Test
	public void testMoveUpBehavior() throws SimulationPauseException {
		// 5, 6 is open for an agent to move there
		Assert.assertTrue(testGrid.emptyPos(5, 6));
		Expression moveUp = new Expression("moveUp(1)");
		Expression alwaysTrue = new Expression("1 < 2");
		proto.addTrigger(new Trigger("moveUp", 1, alwaysTrue, moveUp));
		testGrid.updateEntities();
		Assert.assertTrue(testGrid.emptyPos(5, 5));
		Assert.assertFalse(testGrid.emptyPos(5, 6));	
		}
	
	@Test
	public void testMoveLeftBehavior() throws SimulationPauseException {
		// 4, 5 is open for an agent to move there
		Assert.assertTrue(testGrid.emptyPos(4, 5));
		Expression moveLeft = new Expression("moveLeft(1)");
		Expression alwaysTrue = new Expression("1 < 2");
		proto.addTrigger(new Trigger("moveLeft", 1, alwaysTrue, moveLeft));
		testGrid.updateEntities();
		Assert.assertTrue(testGrid.emptyPos(5, 5));
		Assert.assertFalse(testGrid.emptyPos(4, 5));	
		}
	
	@Test
	public void testMoveDownBehavior() throws SimulationPauseException {
		// 5, 4 is open for an agent to move there
		Assert.assertTrue(testGrid.emptyPos(5, 4));
		Expression moveDown = new Expression("moveDown(1)");
		Expression alwaysTrue = new Expression("1 < 2");
		proto.addTrigger(new Trigger("moveDown", 1, alwaysTrue, moveDown));
		testGrid.updateEntities();
		Assert.assertTrue(testGrid.emptyPos(5, 5));
		Assert.assertFalse(testGrid.emptyPos(5, 4));	
		}
	
	@Test
	public void testMoveRightMultipleBehavior() throws SimulationPauseException {
		// 7, 5 is open for an agent to move there
		Assert.assertTrue(testGrid.emptyPos(7, 5));
		Expression moveRight = new Expression("moveRight(2)");
		Expression alwaysTrue = new Expression("1 < 2");
		proto.addTrigger(new Trigger("moveRight", 1, alwaysTrue, moveRight));
		testGrid.updateEntities();
		Assert.assertTrue(testGrid.emptyPos(5, 5));
		Assert.assertFalse(testGrid.emptyPos(7, 5));	
		}
	
	@Test
	public void testSetFieldBehavior() throws SimulationPauseException {
		// health starts at 100
		Assert.assertEquals(testGrid.getAgent(5, 5).getFieldValue("health") + "", "100");
		Expression setField = new Expression("setField( 'health' , 50)");
		Expression alwaysTrue = new Expression("1 < 2");
		proto.addTrigger(new Trigger("setAgent", 1, alwaysTrue, setField));
		testGrid.updateEntities();
		Assert.assertEquals(testGrid.getAgent(5, 5).getFieldValue("health"), "50.0");
	}
	
	@Test
	public void testSetFieldOfAgentBehavior() throws SimulationPauseException {
		// spawn another agent at 4, 4 and make sure that it spawned
		Assert.assertTrue(testGrid.emptyPos(4, 4));
		testGrid.addAgent(proto.createAgent(testGrid), 4, 4);
		Assert.assertFalse(testGrid.emptyPos(4, 4));
		// health starts at 100
		Assert.assertEquals(testGrid.getAgent(4, 4).getFieldValue("health") + "", "100");
		Expression setFieldOfAgent = new Expression("setFieldOfAgent( 4, 4, 'health', 50)");
		Expression alwaysTrue = new Expression("1 < 2");
		proto.addTrigger(new Trigger("setFieldOfAgent", 1, alwaysTrue, setFieldOfAgent));
		testGrid.updateEntities();
		Assert.assertEquals(testGrid.getAgent(4, 4).getFieldValue("health"), "50.0");	
		
	}
	@Test
	public void testClonePrototype() throws SimulationPauseException{
		Expression clonePrototype = new Expression("clonePrototype(4, 4, 'cat')");
		Expression alwaysTrue = new Expression("true");
		proto.addTrigger(new Trigger("cloneCat", 1, alwaysTrue, clonePrototype));
		testGrid.updateEntities();
		Assert.assertEquals( "cat", testGrid.getAgent(4, 4).getPrototypeName());
	}
}
