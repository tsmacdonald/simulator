package edu.wheaton.simulator.gridentities;

import edu.wheaton.simulator.simulation.Grid;

/**
 * Slot.java
 * 
 * Class that defines properties for a specific point in the overall grid.
 *
 * @author Daniel Davenport, Grant Hensel, Elliot Penson, and Simon Swenson
 * Wheaton College, CSCI 335, Spring 2013
 */

public class Slot extends GridEntity {

	/**
	 * The GridEntity currently in this slot
	 */
	private GridEntity entity;

	/**
	 * Constructor
	 * Sets up the Grid and makes the color object null, since slots will not be represented
	 * on the simulation with a color. 
	 * @param g The Grid object
	 */
	public Slot(Grid g) {
		super(g, null);
	}

	/**
	 * Adds a different GridEntity to this slot
	 * @param a The new entity
	 */
	public void setEntity(GridEntity ge) {
		entity = ge;
	}

	/**
	 * Returns the agent currently in this slot
	 * @return The Agent reference
	 */
	public GridEntity getEntity() {
		return entity;
	}
	
	/**
	 * Empty act method, since slots do not act
	 * 
	 * TODO Possible refactoring opportunity: empty method inherited from super class
	 */
	@Override
	public void act() {}

}