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

	public String getName() {
		return name;
	}

	public String getTarget() {
		return target;
	}

	@Override
	public String toString() {
		return "Behavior:" + getName() + ":" + getTarget();
	}

	public abstract void execute(GridEntity xThis, GridEntity xOther,
			GridEntity xLocal, GridEntity xGlobal);

	public static Behavior constructBehavior(String behavior) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}
}
