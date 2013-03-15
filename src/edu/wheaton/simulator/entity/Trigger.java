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

import net.sourceforge.jeval.EvaluationException;
import edu.wheaton.simulator.expression.Expression;
import edu.wheaton.simulator.simulation.Grid;

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
	 * Evaluates the boolean expression represented by this object.
	 * 
	 * @return Boolean
	 * @throws EvaluationException
	 *             if the expression was invalid
	 */
	public Agent evaluate(Entity xThis, Grid grid, Entity xLocal,
			Entity xGlobal) throws EvaluationException {
		// TODO not sure how to go about implementing this function
		Entity xOther = null;
		Boolean passedConditions = conditions.evaluate(xThis, xOther, xLocal,
				xGlobal).toBool();
		if (!passedConditions)
			return null;
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
	public void fire(Entity xThis, Entity xOther, Entity xLocal, Entity xGlobal) {
		behavior.execute(xThis, xOther, xLocal, xGlobal);
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
