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
	private Agent target;

	public Behavior(String strRepresentation) {
		String[] params = strRepresentation.split(":");
		this.name = params[1].trim();
		this.target = resolveTarget(params[2].trim());
	}

	public Behavior(String name, Agent target) {
		this.name = name;
		this.target = target;
	}

	public String name() {
		return name;
	}

	public Agent target() {
		return target;
	}

	@Override
	public String toString() {
		return "Behavior:" + name() + ":" + target();
	}

	public abstract void execute();

	private Agent resolveTarget(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	public static Behavior constructType(String type) {
		// TODO Auto-generated method stub
		return null;
	}

}
