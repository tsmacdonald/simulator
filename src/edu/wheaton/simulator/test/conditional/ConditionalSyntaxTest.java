package edu.wheaton.simulator.test.conditional;

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
 * Simple tests to verify the syntax of important hard coded conditional functions
 * 
 * @author David Emmanuel
 *
 */

public class ConditionalSyntaxTest {

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
	}
	
	@Test
	public void testIsSlotOpenConditional() throws SimulationPauseException, ElementAlreadyContainedException {
		proto.addField("slotIsOpen", "0");
		testGrid.addAgent(proto.createAgent(testGrid), 5, 5);
		// there is an agent at 5, 5
		Assert.assertFalse(testGrid.emptyPos(5, 5));
		testGrid.addAgent(proto.createAgent(testGrid), 6, 6);
		// assert that there is an agent at slot 6, 6
		Assert.assertFalse(testGrid.emptyPos(6, 6));
		
		Expression setSlotIsOpen = new Expression("setField('slotIsOpen', 1.0)");
		
		// check to see if 6, 6 has an agent there (there should be)
		Expression slotIsNotOpen = new Expression("!isSlotOpen(6, 6)");
		proto.addTrigger(new Trigger("isSlotOpen", 1, slotIsNotOpen, setSlotIsOpen));
		testGrid.updateEntities();
		Assert.assertEquals("1.0", testGrid.getAgent(5, 5).getFieldValue("slotIsOpen"));
	}
	
	@Test
	public void testIsValidCoord() throws SimulationPauseException, ElementAlreadyContainedException{
		proto.addField("isValidCoord", "0");
		testGrid.addAgent(proto.createAgent(testGrid), 5, 5);
		
		// there is an agent at 5, 5
		Assert.assertFalse(testGrid.emptyPos(5, 5));
		Expression setValidCoord = new Expression("setField('isValidCoord', 1)");
		
		// check to see if -1, -1 is a valid coordinate
		Expression isNotValidCoord = new Expression("!isValidCoord(-1, -1)");
		proto.addTrigger(new Trigger("isValidCoord", 1, isNotValidCoord, setValidCoord));
		testGrid.updateEntities();
		Assert.assertEquals("1.0", testGrid.getAgent(5, 5).getFieldValue("isValidCoord"));
	}
	
	@Test
	public void testGetFieldOfAgentAt() throws SimulationPauseException, ElementAlreadyContainedException{
		
		// create a field to store the field of another agent
		proto.addField("storedField", "0");
		testGrid.addAgent(proto.createAgent(testGrid), 5, 5);
		// there is an agent at 5, 5
		Assert.assertFalse(testGrid.emptyPos(5, 5));
		
		// create an agent at the position 6, 6
		testGrid.addAgent(proto.createAgent(testGrid), 6, 6);
		// assert that there is an agent at slot 6, 6
		Assert.assertFalse(testGrid.emptyPos(6, 6));
		
		Expression getFieldOfAgentAt = new Expression("getFieldOfAgentAt(6, 6, 'health') == 100");
		Expression setStoredField = new Expression("setField('storedField', 1)");
		
		proto.addTrigger(new Trigger("isValidCoord", 1, getFieldOfAgentAt, setStoredField));
		testGrid.updateEntities();
		Assert.assertEquals("1.0", testGrid.getAgent(5, 5).getFieldValue("storedField"));
	}
}
