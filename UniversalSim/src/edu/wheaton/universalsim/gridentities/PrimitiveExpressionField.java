	/**
	 * PrimitiveExpressionField class
	 * 
	 * Primitive expression for the value of a Field within an Agent
	 * 
	 * @author Grant Hensel
	 * Wheaton College, CSCI 335, Spring 2013
	 */

package edu.wheaton.universalsim.gridentities;

import edu.wheaton.universalsim.gridentities.PrimitiveExpression.Type;

public class PrimitiveExpressionField  extends PrimitiveExpression {

	/**
	 * The field whose value we are storing
	 */
	private Field field; 
	
	/**
	 * Is this field in the Agent that owns this Trigger (true) or an arbitrary other Agent (false)?
	 */
	private boolean internalCondition; 
	
	/**
	 * Constructor
	 * @param field The field whose value we are storing
	 * @param internalCondition Is this field in the Agent that owns this Trigger (true) or an arbitrary other Agent (false)?
	 */
	public PrimitiveExpressionField(Field field, boolean internalCondition){
		this.field = field; 
		this.internalCondition = internalCondition; 
		this.type = field.type; 
	}
	
	/**
	 * Evaluate the value of the field in the specified Agent (me or other)
	 * @param me The Agent processing this trigger
	 * @param other The other Agent being processed
	 * @return A primitive expression containing the value in the field specified in the constructor in this Agent or an arbitrary other Agent
	 */
	public PrimitiveExpression evaluate(Agent me, Agent other) throws Exception{
		String val;
		if(internalCondition)
			val = String.valueOf(me.getField(String.valueOf(field))); 
		else
			val = String.valueOf(other.getField(String.valueOf(field)));
		
		return new PrimitiveExpression(String.valueOf(this.type), val);
	}
}
