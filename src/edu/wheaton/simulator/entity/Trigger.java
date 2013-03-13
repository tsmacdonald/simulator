/**
 * Trigger Class
 * 
 * "Triggers" are used to give agents certain behaviors. They represent a boolean expression as created by the user that, when met, causes the agent to perform a certain behavior.
 * Note: Triggers should have unique priorities within an agent; problems will be had if there are multiple triggers with the same priority values within an agent. 	 
 * 
 * @author Daniel Davenport, Grant Hensel, Elliot Penson, and Simon Swenson
 * Wheaton College, CSCI 335, Spring 2013
 */

package edu.wheaton.simulator.entity;

import edu.wheaton.simulator.datastructure.Expression;

public class Trigger implements Comparable<Trigger> {

	/**
	 * Triggers are checked in order of priority, with lower numbers coming
	 * first
	 */
	private int priority;

	/**
	 * Represents the conditions of whether or not the trigger fires.
	 */
	private Expression conditions;

	/**
	 * The Agent for which this Trigger will fire.
	 * 
	 * TODO Unused field: Trigger.owner
	 */
	private Agent owner;

	/**
	 * The "other" agent which caused this trigger to evaluate true. Needed so
	 * that we may pass it to the result.
	 * 
	 * TODO Unused field: Trigger.other
	 */
	private Agent other;

	/**
	 * The behavior that is executed when the trigger condition is met
	 */
	private Behavior behavior;

	/**
	 * Constructor
	 * 
	 * @param priority
	 *            Triggers are checked in order of priority, with lower numbers
	 *            coming first
	 * @param conditions
	 *            boolean expression this trigger represents
	 */
	public Trigger(int priority, Expression conditions, Behavior behavior) {
		this.priority = priority;
		this.conditions = conditions;
		this.behavior = behavior;
		owner = null;
		other = null;
	}

	/**
	 * Constructor
	 * 
	 * @param priority
	 *            Priority for this trigger.
	 * @param be
	 *            Tree representing this trigger's fire conditions.
	 * @param result
	 *            Models the outcome of the trigger being fired.
	 * @param owner
	 *            The agent who owns this Trigger.
	 */
	public Trigger(int priority, Expression conditions, Behavior behavior,
			Agent owner) {
		this.priority = priority;
		this.conditions = conditions;
		this.behavior = behavior;
		this.owner = owner;
		other = null;
	}

	/**
	 * Clone Constructor. Deep copy is not necessary at this point.
	 * 
	 * @param parent
	 *            The trigger from which to clone.
	 */
	public Trigger(Trigger parent) {
		priority = parent.priority;
		conditions = parent.conditions;
		behavior = parent.behavior;
	}

	/**
	 * Clone Constructor required for referring to new agent.
	 * 
	 * @param parent
	 *            The trigger from which to clone.
	 * @param owner
	 *            A reference to the Agent to which this trigger belongs.
	 */
	public Trigger(Trigger parent, Agent owner) {
		this(parent);
		this.owner = owner;
	}

	/**
	 * Evaluates the boolean expression represented by this object. If the
	 * expression is true, will return the "other" agent it's true for. If no
	 * "other" was used for the expression, will result the "owner" agent of
	 * this trigger.
	 * 
	 * @return The agent that caused this trigger to be true.
	 * @throws Exception
	 *             if the expression was invalid
	 */
	public Agent evaluate() throws Exception {
		// TODO Method stub
		throw new UnsupportedOperationException();
	}

	/**
	 * Get this trigger's priority
	 * 
	 * @return the priority
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * Get the String representation of this trigger's firing condition
	 * 
	 * @return the firing condition
	 */
	public Expression getConditions() {
		return conditions;
	}

	/**
	 * Fires the trigger. Will depend on the Behavior object for this trigger.
	 */
	public void fire() {
		behavior.execute();
	}

	/**
	 * Compares this trigger to another trigger based on priority
	 * 
	 * @param other
	 *            The other trigger to compare to.
	 * @return -1 if less, 0 if same, 1 if greater.
	 */
	@Override
	public int compareTo(Trigger other) {
		if (priority == other.priority) {
			return 0;
		} else if (priority > other.priority) {
			return 1;
		} else {
			return -1;
		}
	}
}
