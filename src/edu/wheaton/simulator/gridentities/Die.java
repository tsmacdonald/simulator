/**
 * Behavior class
 * 
 * Behavior subclass. Kill the Actor. 
 * 
 * @author Grant Hensel
 * Wheaton College, CSCI 335, Spring 2013
 */

package edu.wheaton.simulator.gridentities;

public class Die extends Behavior {

	public Die(Agent target, String name) {
		super(target, name);
	}

	/**
	 * Remove this Actor (the owner) from the Grid
	 * 
	 * TODO Method stub
	 */
	@Override
	public void execute() {
		// Remove this Actor (owner) from the Grid
	}
	
}
