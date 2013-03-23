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

import java.util.ArrayList;

import net.sourceforge.jeval.EvaluationException;
import edu.wheaton.simulator.behavior.AbstractBehavior;
import edu.wheaton.simulator.expression.ExpressionEvaluator;
import edu.wheaton.simulator.simulation.Grid;

public class Trigger {
	
	/**
	 * A name to reference a trigger by
	 */
	private String name;

	/**
	 * Represents the conditions of whether or not the trigger fires.
	 */
	private ExpressionEvaluator conditionExpression;

	/**
	 * The behavior that is executed when the trigger condition is met
	 */
	private ExpressionEvaluator behavior;

	/**
	 * Constructor
	 * 
	 * @param priority
	 *            Triggers are checked in order of priority, with lower numbers
	 *            coming first
	 * @param conditions
	 *            boolean expression this trigger represents
	 */
	public Trigger(String name, ExpressionEvaluator conditionExpression,
			ExpressionEvaluator behavior) {
		this.name = name;
		this.conditionExpression = conditionExpression;
		this.behavior = behavior;		
	}

	/**
	 * Clone Constructor. Deep copy is not necessary at this point.
	 * 
	 * @param parent
	 *            The trigger from which to clone.
	 */
	public Trigger(Trigger parent) {
		name = parent.name;
		conditionExpression = parent.conditionExpression;
		behavior = parent.behavior;
	}

	/**
	 * Evaluates the boolean expression represented by this object and fires if
	 * all conditions evaluate to true.
	 * 
	 * If someone wants to evaluate an expression to something other than boolean,
	 * they will need to change this method or fire.
	 * 
	 * @return Boolean
	 * @throws EvaluationException
	 *             if the expression was invalid
	 */
	public void evaluate(Agent xThis) throws EvaluationException {
		
		ExpressionEvaluator expr = conditionExpression.clone();

		expr.importEntity("this", xThis);

		if (expr.evaluateBool()){
			fire();
		
		}
		throw new UnsupportedOperationException();
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
	public void fire() {
		//Needs to be updated to work with new behaviors
	}

	/**
	 * Sets the behavior of the trigger.
	 * 
	 * @param behavior
	 *            Behavior to be added to list
	 */
	public void setBehavior(ExpressionEvaluator behavior) {
		this.behavior = behavior;
	}
}
