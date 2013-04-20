/**
 * Prototype.java
 *
 * A template for an Agent
 * 
 * @author Elliot Penson
 * Wheaton College, CSCI 335, Spring 2013
 */

package edu.wheaton.simulator.entity;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import edu.wheaton.simulator.datastructure.Grid;

public class Prototype extends GridEntity {

	/**
	 * The list of all Agent children of this Prototype
	 */
	private List<Agent> children;

	/**
	 * HashMap of Prototypes with associated names
	 */
	private static Map<String, Prototype> prototypes = new HashMap<String, Prototype>();

	/**
	 * The list of all triggers/events associated with this prototype.
	 */
	private List<Trigger> triggers;

	private String name;

	/**
	 * Constructor.
	 * 
	 * @param g
	 *            The grid (passed to super constructor)
	 * @param n
	 *            The name of this prototype
	 */
	public Prototype(Grid g, String n) {
		super(g);
		name = n;
		children = new ArrayList<Agent>();
		triggers = new ArrayList<Trigger>();
	}

	/**
	 * Constructor.
	 * 
	 * @param g
	 *            The grid (passed to super constructor)
	 * @param c
	 *            The color of this prototype (passed to super constructor)
	 * @param n
	 *            The name of this prototype
	 */
	public Prototype(Grid g, Color c, String n) {
		super(g, c);
		name = n;
		children = new ArrayList<Agent>();
		triggers = new ArrayList<Trigger>();
	}

	/**
	 * Constructor.
	 * 
	 * @param g
	 *            The grid (passed to super constructor)
	 * @param c
	 *            The color of this prototype (passed to super constructor)
	 * @param d
	 *            The bitmask of this prototype (passed to super constructor)
	 * @param n
	 *            The name of this prototype
	 */
	public Prototype(Grid g, Color c, byte[] d, String n) {
		super(g, c, d);
		name = n;
		triggers = new ArrayList<Trigger>();
		children = new ArrayList<Agent>();
	}

	/**
	 * Adds a Prototype to the HashMap
	 * 
	 * @param n
	 * @param g
	 * @param c
	 */
	public static void addPrototype(Prototype p) {
		prototypes.put(p.getName(), p);
	}

	/**
	 * Returns the Prototype that corresponds to the given string.
	 * 
	 * @param n
	 * @return
	 */
	public static Prototype getPrototype(String n) {
		return prototypes.get(n);
	}

	/**
	 * Returns and removes a Prototype from the HashMap
	 * 
	 * @param n
	 *            name of the Prototype to remove
	 * @return The deleted Prototype
	 */
	public static Prototype removePrototype(String n) {
		return prototypes.remove(n);
	}

	/**
	 * Removes a Prototype templates from the HashMap
	 */
	public static void clearPrototypes() {
		prototypes.clear();
	}

	/**
	 * Gets a Set of the prototype names
	 * 
	 * @return
	 */
	public static Set<String> prototypeNames() {
		return prototypes.keySet();
	}

	/**
	 * Provides all of the Prototypes in the HashMap
	 * 
	 * @return Immutable set of Prototypes
	 */
	public static ImmutableSet<Prototype> getPrototypes() {
		ImmutableSet.Builder<Prototype> toReturn = new ImmutableSet.Builder<Prototype>();
		for (String current : prototypes.keySet()) {
			toReturn.add(getPrototype(current));
		}
		return toReturn.build();
	}

	/**
	 * Changes the name of a prototype without resetting its children.
	 * 
	 * @param oldName
	 * @param newName
	 */
	public void setPrototypeName(String oldName, String newName) {
		Prototype p = getPrototype(oldName);
		name = newName;
		addPrototype(p);
		removePrototype(oldName);
	}

	/**
	 * Does a deep clone of this prototype and returns it as an Agent.
	 * 
	 * @return An Agent following this prototype
	 */
	public Agent createAgent() {
		Agent clone = new Agent(getGrid(), this, getColor(), getDesign());

		// copy all fields
		clone.getFieldMap().putAll(this.getFieldMap());

		// copy all triggers
		for (Trigger t : triggers)
			clone.addTrigger(new Trigger(t));

		children.add(clone);
		return clone;
	}

	/**
	 * Adds the given trigger to this prototype as well as all of its children.
	 * 
	 * @param trigger
	 */
	public void addTrigger(Trigger trigger) {
		triggers.add(trigger);
		Collections.sort(triggers);
		for (Agent a : children) {
			a.addTrigger(trigger);
		}
	}

	/**
	 * Removes a trigger with the given priority all children.
	 * 
	 * @param priority
	 *            The priority of the given trigger to remove.
	 */
	public void removeTrigger(int priority) {
		triggers.remove(triggers.get(priority));
		Collections.sort(triggers);
		for (Agent a : children) {
			a.removeTrigger(priority);
		}
	}

	/**
	 * Removes a trigger with the given name from both this Prototype and its
	 * children.
	 * 
	 * @param name
	 */
	public void removeTrigger(String name) {
		for (int i = 0; i < triggers.size(); i++)
			if (triggers.get(i).getName().equals(name))
				triggers.remove(i);
		Collections.sort(triggers);
		for (Agent a : children) {
			a.removeTrigger(name);
		}
	}

	/**
	 * Updates the trigger(s) with the given name
	 * 
	 * @param name
	 */
	public void updateTrigger(String name, Trigger newT) {
		for (int i = 0; i < triggers.size(); i++)
			if (triggers.get(i).getName().equals(name))
				triggers.set(i, newT);
		Collections.sort(triggers);
		for (Agent a : children) {
			a.updateTrigger(name, newT);
		}
	}

	/**
	 * Tells if this prototype has a Trigger corresponding to the name given.
	 * 
	 * @param name
	 * @return
	 */
	public boolean hasTrigger(String name) {
		for (Trigger current : triggers)
			if (current.getName().equals(name))
				return true;
		return false;
	}

	/**
	 * Returns the list of triggers for this Prototype
	 */
	public List<Trigger> getTriggers() {
		return triggers;
	}

	/**
	 * Provides a number of this children this prototype currently has.
	 * 
	 * @return The size of the children List
	 */
	public int childPopulation() {
		return children.size();
	}

	/**
	 * Gives the AgentID objects for all of the Agents that use this specific
	 * prototype.
	 * 
	 * @return An ImmutableSet of AgentIDs.
	 */
	public ImmutableSet<AgentID> childIDs() {
		ImmutableSet.Builder<AgentID> builder = new ImmutableSet.Builder<AgentID>();
		for (Agent current : children) {
			builder.add(current.getID());
		}
		return builder.build();
	}

	/**
	 * Provides the name of this Prototype
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

}
