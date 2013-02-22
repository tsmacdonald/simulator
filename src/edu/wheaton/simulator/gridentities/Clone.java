	/**
	 * Behavior class
	 * 
	 * Behavior subclass. Clone the Actor. 
	 * 
	 * @author Grant Hensel
	 * Wheaton College, CSCI 335, Spring 2013
	 */

package edu.wheaton.simulator.gridentities;

public class Clone extends Behavior {

	public Clone(Agent target, String name) {
		super(target, name);
	}

	/**
	 * Clone this Actor
	 */
	public void execute() {
		//Clone this Actor, add a new copy to the Grid
	}
}
