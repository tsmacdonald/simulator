package edu.wheaton.simulator.statistics;

import edu.wheaton.simulator.entity.AgentID;

/**
 * A class representing all the information to track a specified trigger
 * 
 * @author Chris Anderson
 * 
 */
public class TriggerSnapshot {
	
	/**
	 * The name of the specified trigger.
	 */
	public final String triggerName;
	
	/**
	 * The priority for the execution of the trigger
	 */
	public final Integer priority;
	
	/**
	 * A way to store the string value of the conditionExpression
	 */
	public final String conditionExpression;
	
	/**
	 * A string to store the behaviorExpression value
	 */
	public final String behaviorExpression;

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
	public TriggerSnapshot(String triggerName, int priority, String conditionExpression, 
			String behaviorExpression) {
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
		String ret = "Trigger";
		ret += "~" + triggerName; 
		ret += "~" + priority;  
		ret += "~" + conditionExpression; 
		ret += "~" + behaviorExpression; 
		return ret;
	}
}
