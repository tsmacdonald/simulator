package edu.wheaton.simulator.test.datastructure;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import edu.wheaton.simulator.datastructure.Grid;
import edu.wheaton.simulator.entity.Prototype;

public class GridTest {

	private Grid grid;

	@Before
	public void setUp() {
		grid = new Grid(20, 30);
	}

	@Test
	public void sizeTest() {
		Assert.assertTrue(grid.getWidth() == 20);
		Assert.assertTrue(grid.getHeight() == 30);
	}

	@Test
	public void positionTest() {
		// in bounds
		Assert.assertTrue(grid.emptyPos(0, 0));
		Assert.assertTrue(grid.emptyPos(10, 10));
		Assert.assertTrue(grid.emptyPos(19, 29));
		
		// out of bounds
		Assert.assertFalse(grid.emptyPos(-10, -7));
		Assert.assertFalse(grid.emptyPos(98, 100));
	}

	@Test
	public void addAgentTest() {
		// TODO
	}

	/**
	 * Helper method that provides the integer ID of the Agent at the given x/y
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	private int IDOfAgentAt(int x, int y) {
		return grid.getAgent(x, y).getID().getInt();
	}
	
	@Test
	public void removeTest() {
		Prototype p = new Prototype(grid, "Test");
		grid.addAgent(p.createAgent(), 5, 5);
		grid.removeAgent(5, 5);
		Assert.assertNull(grid.getAgent(5, 5));
	}
}
