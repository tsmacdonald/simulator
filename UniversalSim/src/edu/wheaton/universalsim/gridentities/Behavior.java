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

	private Agent owner; 
	private String name;
	
	public Behavior(Agent owner, String name){
		this.owner = owner; 
		this.name = name; 
	}
	
	public abstract void execute();
	
}
