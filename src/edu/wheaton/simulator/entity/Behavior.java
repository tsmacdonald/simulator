/**
 * Behavior class
 * 
 * Abstract parent class for Behaviors, which are executed when Trigger conditions are met
 * 
 * @author Grant Hensel
 * Wheaton College, CSCI 335, Spring 2013
 */

package edu.wheaton.simulator.entity;

public abstract class Behavior {

	private String name;
	private String target;

	public Behavior(String strRepresentation) {
		String[] params = strRepresentation.split(":");
		this.name = params[1].trim();
		this.target = params[2].trim();
	}

	public Behavior(String name, String target) {
		this.name = name;
		this.target = target;
	}

	public String name() {
		return name;
	}

	public String target() {
		return target;
	}

	@Override
	public String toString() {
		return "Behavior:" + name() + ":" + target();
	}

	public abstract void execute(Entity xThis, Entity xOther, Entity xLocal, Entity xGlobal);

	public static Behavior constructBehavior(String behavior) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}
}
