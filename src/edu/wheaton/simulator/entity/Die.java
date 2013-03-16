/**
 * Behavior class
 * 
 * Behavior subclass. Kill the Actor. 
 * 
 * @author Grant Hensel
 * Wheaton College, CSCI 335, Spring 2013
 */

package edu.wheaton.simulator.entity;

public class Die extends Behavior {

	public Die(String name, String target) {
		super(name, target);
	}

	/**
	 * Remove this Actor (the owner) from the Grid
	 * 
	 * TODO Method stub
	 */
	@Override
	public void execute(GridEntity xThis, GridEntity xOther,
			GridEntity xLocal, GridEntity xGlobal) {
		// Remove this Actor (owner) from the Grid
	}

}
