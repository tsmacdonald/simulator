/**
 * Agent.java
 *
 * Agents model actors in the simulation's Grid.
 * 
 * @author Daniel Davenport, Grant Hensel, Elliot Penson, and Simon Swenson
 * Wheaton College, CSCI 335, Spring 2013
 */

package edu.wheaton.simulator.entity;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.wheaton.simulator.datastructure.Field;
import edu.wheaton.simulator.simulation.Grid;

public class Agent extends GridEntity {

	/**
	 * The list of all triggers/events associated with this agent.
	 */
	private List<Trigger> triggers;

	/**
	 * The list of all children of this Agent if it's a prototype agent. Will
	 * always be null, otherwise.
	 */
	private List<Agent> children;

	/**
	 * The x position of this Agent
	 */
	private int x;

	/**
	 * The y position of this Agent
	 */
	private int y;

	/**
	 * Constructor.
	 * 
	 * @param g
	 *            The grid (passed to super constructor)
	 * @param c
	 *            The color of this agent (passed to super constructor)
	 * @param isPrototype
	 *            Is this a prototype agent from which all other agents of this
	 *            type are made?
	 */
	public Agent(Grid g, Color c, boolean isPrototype) {
		super(g, c);

		triggers = new ArrayList<Trigger>();

		if (isPrototype) {
			children = new ArrayList<Agent>();
			getFields().add(new Field("spawnCondition", "random"));
		} else {
			children = null;
		}
	}

	/**
	 * Clone constructor. Will create a deep clone with every instance variable
	 * copied, not just references.
	 * 
	 * @param parent
	 *            The parent from which to clone.
	 */
	public Agent(Agent parent, boolean isPrototype) {
		super(parent.getGrid(), parent.getColor(), parent.getDesign());

		triggers = new ArrayList<Trigger>();

		for (Field f : parent.getFields()) {
			getFields().add(new Field(f)); // copy all fields
		}

		for (Trigger t : parent.triggers) {
			triggers.add(new Trigger(t)); // copy all triggers
		}

		if (isPrototype) {
			children = new ArrayList<Agent>();
		} else {
			children = null;
		}
	}

	/**
	 * Causes this Agent to perform 1 action. The first trigger with valid
	 * conditions will fire.
	 * 
	 * @throws Exception
	 */
	@Override
	public void act(GridEntity local, GridEntity global) {
		try {
			for (Trigger t : triggers) {
				Agent triggerer = t.evaluate(this, getGrid(), local, global);
				if (triggerer != null) {
					if (triggerer == this)
						t.fire(this, null, local, global);
					else
						t.fire(this, triggerer, local, global);

					break;
				}
			}
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	/**
	 * Clones this agent and puts it in the Grid's list of agents.
	 */
	public void cloneAgent() {
		getGrid().spawnEntity(new Agent(this, false));
	}

	/**
	 * Clones this agent and puts it in the environment's list of agents, then
	 * prepares it to be a prototype agent.
	 */
	public void cloneAgentPrototype() {
		getGrid().spawnEntity(new Agent(this, true));
	}

	/**
	 * Removes this Agent from the environment's list.
	 */
	public void die() {
		getGrid().removeEntity(this);
	}

	public void addTrigger(Trigger trigger) {
		triggers.add(trigger);
		Collections.sort(triggers);
	}

	/**
	 * Removes a trigger with the given priority (index in array list). If this
	 * is the prototype Agent, will also remove the trigger from all children.
	 * 
	 * @param priority
	 *            The priority of the given trigger to remove.
	 */
	public void removeTrigger(int priority) {
		triggers.remove(triggers.get(priority));
		Collections.sort(triggers);
		if (children != null) {
			for (Agent a : children) {
				a.removeTrigger(priority);
			}
		}
	}

	/**
	 * Gets the current x position of this agent
	 * 
	 * @return x
	 */
	public int getPosX() {
		return x;
	}

	/**
	 * Gets the current y position of this agent
	 * 
	 * @return y
	 */
	public int getPosY() {
		return y;
	}
}
