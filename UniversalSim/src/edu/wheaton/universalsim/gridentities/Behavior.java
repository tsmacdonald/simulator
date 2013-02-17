/**
 * Behavior class
 * 
 * Abstract parent class for Behaviors, which are executed when Trigger conditions are met
 * 
 * @author Grant Hensel
 * Wheaton College, CSCI 335, Spring 2013
 */

package edu.wheaton.universalsim.gridentities;

public abstract class Behavior {

	private Agent target; 
	private String name;
	
	public Behavior(Agent owner, String name){
		this.target = owner; 
		this.name = name; 
	}
	
	public abstract void execute();
	
	/**
	 * Use an input string to create a new Behavior object
	 * @param s String containing the specificiations for the behavior
	 * @return The created Behavior object
	 */
	public static Behavior parseBehavior(String s){
		throw new UnsupportedOperationException();
	}
	
}
