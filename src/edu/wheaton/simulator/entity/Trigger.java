/**
 * Trigger Class
 * 
 * "Triggers" are used to give agents certain behaviors. They represent a boolean expression as created by the user that, when met, causes the agent to perform a certain behavior.
 * Note: Triggers should have unique priorities within an agent; problems will be had if there are multiple triggers with the same priority values within an agent. 	 
 * 
 * @author Daniel Davenport, Grant Hensel, Elliot Penson, Emmanuel Pederson, Chris Anderson and Simon Swenson
 * Wheaton College, CSCI 335, Spring 2013
 */

package edu.wheaton.simulator.entity;

import net.sourceforge.jeval.EvaluationException;
import edu.wheaton.simulator.expression.ExpressionEvaluator;

public class Trigger implements Comparable<Trigger> {

	/**
	 * A name to reference a trigger by
	 */
	private String name;

	private int priority;

	/**
	 * Represents the conditions of whether or not the trigger fires.
	 */
	private ExpressionEvaluator conditionExpression;

	/**
	 * The behavior that is executed when the trigger condition is met
	 */
	private ExpressionEvaluator behaviorExpression;

	/**
	 * Constructor
	 * 
	 * @param priority
	 *            Triggers are checked in order of priority, with lower numbers
	 *            coming first
	 * @param conditions
	 *            boolean expression this trigger represents
	 */
	public Trigger(String name, int priority,
			ExpressionEvaluator conditionExpression,
			ExpressionEvaluator behavior) {
		this.name = name;
		this.priority = priority;
		this.conditionExpression = conditionExpression;
		this.behaviorExpression = behavior;
	}

	/**
	 * Clone Constructor. Deep copy is not necessary at this point.
	 * 
	 * @param parent
	 *            The trigger from which to clone.
	 */
	public Trigger(Trigger parent) {
		name = parent.name;
		priority = parent.priority;
		conditionExpression = parent.conditionExpression;
		behaviorExpression = parent.behaviorExpression;
	}

	/**
	 * Evaluates the boolean expression represented by this object and fires if
	 * all conditions evaluate to true.
	 * 
	 * If someone wants to evaluate an expression to something other than
	 * boolean, they will need to change this method or fire.
	 * 
	 * @return Boolean
	 * @throws EvaluationException
	 *             if the expression was invalid
	 */
	public void evaluate(Agent xThis) throws EvaluationException {
		ExpressionEvaluator condition = conditionExpression.clone();
		ExpressionEvaluator behavior = behaviorExpression.clone();
		
		condition.importEntity("this", xThis);
		behavior.importEntity("this", xThis);

		boolean conditionResult = false;
		try {
			conditionResult = condition.evaluateBool();
		} catch (EvaluationException e) {
			System.out.println("condition threw exception");
			e.printStackTrace();
		}
		
		if (conditionResult) {
			fire(behavior);
		}
	}

	/**
	 * Get the String representation of this trigger's firing condition
	 * 
	 * @return the firing condition
	 */
	public ExpressionEvaluator getConditions() {
		return conditionExpression;
	}

	/**
	 * Fires the trigger. Will depend on the Behavior object for this trigger.
	 */
	private static void fire(ExpressionEvaluator behavior) {
		try {
			if(behavior.evaluateBool() == false){
				System.err.println("behavior '" + behavior.toString() + "' failed");
			}
		} catch (EvaluationException e) {
			System.err.println("malformed expression: " + e.getMessage());
			e.printStackTrace();
		}
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

	/**
	 * Sets the behavior of the trigger.
	 * 
	 * @param behavior
	 *            Behavior to be added to list
	 */
	public void setBehavior(ExpressionEvaluator behavior) {
		this.behaviorExpression = behavior;
	}

	/**
	 * Gets the name of this Trigger
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}
}
