package edu.wheaton.simulator.statistics;

import edu.wheaton.simulator.entity.AgentID;
import edu.wheaton.simulator.expression.Expression;


/**
 * A class representing all the information to track a specified trigger
 * 
 * @author Chris Anderson
 * 
 */
public class TriggerSnapshot {

	/*
	 * The unique id of the agent for this snapshot
	 */
	public final AgentID id;
	
	/**
	 * The name of the specified trigger.
	 */
	public final String triggerName;
	
	/*
	 * The priority for the execution of the trigger
	 */
	public final Integer priority;
	
	/*
	 * The step in which this was executed
	 */
	public final Integer step;
	
	/*
	 * A way to store the string value of the conditionExpression
	 */
	public final Expression conditionExpression;
	
	/*
	 * A string to store the behaviorExpression value
	 */
	public final Expression behaviorExpression;

	/**
	 * Constructor
	 * 
	 * @param id
	 *            The ID of the Agent associated with this trigger.
	 * @param triggerName
	 *            The name of the trigger
	 * @param priority
	 *            A priority setting for the trigger
	 * @param conditionExpression
	 *            A string version of the condition
	 * @param behaviorExpression
	 * 			  A string version of the behavior
	 */
	public TriggerSnapshot(AgentID id, String triggerName, int priority, Expression conditionExpression, 
			Expression behaviorExpression, int step) {
		this.id = id;
		this.step = step;
		this.triggerName = triggerName;
		this.priority = priority;
		this.conditionExpression = conditionExpression;
		this.behaviorExpression = behaviorExpression;
	}

	/**
	 * Produce a string serializing this object
	 * @return a String containing all of the data in this snapshot
	 * 
	 * TODO
	 * 
	 * Format: (Stuff in parentheses is just notes - not actually there)
	 * -----------------------------------------------------------------
	 * TriggerSnapshot
	 */
	public String serialize(){
		//TODO: Take the content and serialize it.
		return "";
	}
}
