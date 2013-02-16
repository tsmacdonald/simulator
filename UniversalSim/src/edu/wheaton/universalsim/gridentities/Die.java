	/**
	 * Behavior class
	 * 
	 * Behavior subclass. Kill the Actor. 
	 * 
	 * @author Grant Hensel
	 * Wheaton College, CSCI 335, Spring 2013
	 */

package edu.wheaton.universalsim.gridentities;

public class Die extends Behavior {

	public Die(Agent owner, String name) {
		super(owner, name);
	}

	/**
	 * Remove this Actor (the owner) from the Grid
	 */
	public void execute() {
		//Remove this Actor (owner) from the Grid
	}
	
}
