package edu.wheaton.simulator.datastructure;

import edu.wheaton.simulator.entity.Agent;
import edu.wheaton.simulator.entity.GridEntity;

/**
 * Slot.java
 * 
 * Class that defines properties for a specific point in the overall grid.
 * 
 * @author Daniel Davenport, Grant Hensel, Elliot Penson, and Simon Swenson
 *         Wheaton College, CSCI 335, Spring 2013
 */

public class Slot extends GridEntity {

	/**
	 * The Agent currently in this slot
	 */
	private Agent agent;

	/**
	 * Constructor
	 * 
	 * @param g
	 *            The Grid object
	 */
	public Slot(Grid g) {
		super(g);
	}

	/**
	 * Adds a different Agent to this slot
	 * 
	 * @param a
	 *            The new agent
	 */
	public boolean setAgent(Agent a) {
		agent = a;
		return true;
	}

	/**
	 * Returns the agent currently in this slot
	 * 
	 * @return The Agent reference
	 */
	public Agent getAgent() {
		return agent;
	}

}
