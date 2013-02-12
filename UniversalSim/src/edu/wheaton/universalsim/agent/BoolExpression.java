/**
 * BoolExpression Class
 * 
 * Building block for the BoolExpression tree in the Trigger class. This class will be extended into
 * BoolExpressionOperator and BoolExpressionComparison. This version simply contains an internal boolean
 * value, which is returned when evaluate() is called.  	 
 * 
 * @author Daniel Davenport, Grant Hensel, Elliot Penson, and Simon Swenson
 * Wheaton College, CSCI 335, Spring 2013
 */

package edu.wheaton.universalsim.agent;

public class BoolExpression {

	/**
	 * Value contained in this boolean expression
	 */
	private boolean value; 
	
	/**
	 * Hold references to this agent and an arbitary agent "other", as this will be required for some trigger conditions
	 */
	private Agent me, other;
	
	/**
	 * Default constructor
	 */
	public BoolExpression(){}
	
	/**
	 * Constructor
	 * @param value The value this boolean expression will represent
	 */
	public BoolExpression(boolean value){
		this.value = value;
		me = null; 
		other = null; 
	}
	
	public void setMe(Agent me){
		this.me = me; 
	}
	
	public void setOther(Agent other){
		this.other = other; 
	}
	
	/**
	 * Evaluate this boolean expression
	 * @return the evaluation
	 * @throws Exception if the expression is incorrectly constructed
	 */
	public boolean evaluate() throws Exception{
		return value; 
	}

	/**
	 * Parses string input and turns it into the tree representation of the boolean expression.
	 * @param s The string to parse.
	 * @return The boolean expression tree.
	 */
	public static BoolExpression parseExpression(String s) {
		throw new UnsupportedOperationException();
	}
}
