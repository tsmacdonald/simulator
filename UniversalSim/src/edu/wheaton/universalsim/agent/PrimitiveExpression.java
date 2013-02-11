/**
 * PrimitiveExpression Class
 * 
 * Contains a primitive value (String or number)  
 * 
 * @author Daniel Davenport, Grant Hensel, Elliot Penson, and Simon Swenson
 * Wheaton College, CSCI 335, Spring 2013
 */

package edu.wheaton.universalsim.agent;

public class PrimitiveExpression {
	
	/**
	 * The value contained in this object
	 */
	private String value; 
	
	/**
	 * Default constructor
	 */
	public PrimitiveExpression(){}
	
	/**
	 * Constructor
	 * @param val The val this object will represent (Stored as a string by calling .toString())
	 */
	public PrimitiveExpression(Object val){
		value = val.toString(); 
	}
	
	/**
	 * Get this object's value as a String
	 */
	public String toString(){
		return value; 
	}
	
	/**
	 * Get this object's value as an int
	 */
	public int toInt(){
		return Integer.parseInt(value); 
	}
	
	/**
	 * Evaluate this object and return a PrimitiveExpression
	 * In this case, that is simply this object
	 * @throws Exception if the expression is invalid
	 */
	public PrimitiveExpression evaluate() throws Exception{
		return this; 
	}
}
