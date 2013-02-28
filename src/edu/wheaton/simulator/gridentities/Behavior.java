/**
 * Behavior class
 * 
 * Abstract parent class for Behaviors, which are executed when Trigger 
 * conditions are met
 * 
 * @author Grant Hensel
 * Wheaton College, CSCI 335, Spring 2013
 */

package edu.wheaton.simulator.gridentities;

public abstract class Behavior {

	// TODO Unused field: Behavior.target
	private Agent target;
	
	// TODO Unused field: Behavior.name
	private String name;
	
	public Behavior(Agent owner, String name) {
		this.target = owner; 
		this.name = name; 
	}
	
	public abstract void execute();
	
	/**
	 * Use an input string to create a new Behavior object
	 * @param s String containing the specifications for the behavior
	 * @return The created Behavior object
	 */
	public static Behavior parseBehavior(String s) {
		//Generate a Behavior object based on the input string
		// TODO Method stub
		return null; 
	}
	
}
