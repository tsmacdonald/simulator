package edu.wheaton.simulator.test.statistics;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.wheaton.simulator.datastructure.ElementAlreadyContainedException;
import edu.wheaton.simulator.datastructure.Grid;
import edu.wheaton.simulator.entity.Prototype;
import edu.wheaton.simulator.entity.Trigger;
import edu.wheaton.simulator.expression.Expression;

public class SavePrototypeTest {

	Grid grid;
	Prototype prototype;

	@Before
	public void setUp() throws ElementAlreadyContainedException {
		grid = new Grid(1, 1);
		prototype = new Prototype(grid, "NewPrototypeThing");
		prototype.addField("Age", Integer.toString(2));
		prototype.addField("Height", Integer.toString(5));
		prototype.addTrigger(new Trigger("trigger", 1, new Expression("hello"), new Expression("goodbye")));
		prototype.addTrigger(new Trigger("trigger2", 2, new Expression("bad"), new Expression("die")));
	}
	
	
	
	@Test
	public void test() {
		Saver.savePrototype(prototype);
		org.junit.Assert.assertTrue(true);
	}

}
