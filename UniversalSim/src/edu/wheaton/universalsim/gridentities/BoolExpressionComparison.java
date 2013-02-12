/**
 * BoolExpressionOperator Class
 * 
 * Contains a boolean comparison operator (==, !=, >, <, =>, =<) and 2 arguments (Primitive Expressions). Main function is evaluating this expression and return the result. 
 * This is a subclass that is used to create the BoolExpression tree in the Trigger class  	 
 * 
 * @author Daniel Davenport, Grant Hensel, Elliot Penson, and Simon Swenson
 * Wheaton College, CSCI 335, Spring 2013
 */

package edu.wheaton.universalsim.gridentities;

public class BoolExpressionComparison extends BoolExpression {

	/**
	 * The operator contained in this class (==, !=, >, <, =>, =<). Represented as a string. 
	 */
	private String op; 
	
	/**
	 * The two PrimitiveExpression's that are evaluated using the stored boolean comparison operator
	 */
	private PrimitiveExpression left, right; 
	
	/**
	 * Default constructor
	 */
	public BoolExpressionComparison(){
		op = null; 
		left = null; 
		right = null; 
	}
	
	/**
	 * Constructor
	 * @param op The operator contained in this class (==, !=, >, <, =>, =<). Represented as a string. 
	 * @param left The first of two PrimitiveExpression's that are evaluated using the stored boolean comparison operator
	 * @param left The second of two PrimitiveExpression's that are evaluated using the stored boolean comparison operator
	 */
	public BoolExpressionComparison(String op, PrimitiveExpression left, PrimitiveExpression right){
		this.op = op; 
		this.left = left; 
		this.right = right; 
	}
	
	/**
	 * Evaluate this boolean expression
	 * @return the evaluation
	 * @throws Exception if the expression is incorrectly constructed
	 */
	public boolean evaluate() throws Exception{
		PrimitiveExpression leftEval = left.evaluate();
		PrimitiveExpression rightEval = left.evaluate(); 
		
		switch(op){
		case "==": 
			return leftEval.toString().equals(rightEval.toString()); 
		case "!=": 
			return !leftEval.toString().equals(rightEval.toString()); 
		case ">": 
			return leftEval.toInt() > rightEval.toInt();
		case "<": 
			return leftEval.toInt() < rightEval.toInt();
		case ">=": 
			return leftEval.toInt() >= rightEval.toInt();
		case "<=": 
			return leftEval.toInt() <= rightEval.toInt();
		default: 
			System.out.println("BooleanExpressionComparison evaluate() error: op not recognized");
			throw new Exception(); 
		}
	}
}
