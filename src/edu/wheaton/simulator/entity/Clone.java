/**
 * Behavior class
 * 
 * Behavior subclass. Clone the Actor. 
 * 
 * @author Grant Hensel
 * Wheaton College, CSCI 335, Spring 2013
 */

package edu.wheaton.simulator.entity;

public class Clone extends Behavior {

	public Clone(String name, String target) {
		super(name, target);
	}

	/**
	 * Clone this Actor
	 * 
	 * TODO Method stub
	 */
	@Override
	public void execute(GridEntity xThis, GridEntity xOther,
			GridEntity xLocal, GridEntity xGlobal) {
		// Clone this Actor, add a new copy to the Grid
	}
}
