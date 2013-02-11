/**
 * BoolExpressionOperator Class
 * 
 * Contains a boolean comparison operator (==, !=, >, <, =>, =<) and 2 arguments (Primitive Expressions). Main function is evaluating this expression and return the result. 
 * This is a subclass that is used to create the BoolExpression tree in the Trigger class  	 
 * 
 * @author Daniel Davenport, Grant Hensel, Elliot Penson, and Simon Swenson
 * Wheaton College, CSCI 335, Spring 2013
 */

package edu.wheaton.universalsim.agent;

public class BoolExpressionComparison extends BoolExpression {

	/**
	 * The operator contained in this class (==, !=, >, <, =>, =<). Represented as a string. 
	 */
	private String op; 
	
	/**
	 * The two PrimitiveExpression's that are evaluated using the stored boolean comparison operator
	 */
	private PrimitiveExpression right, left; 
	
	/**
	 * Default constructor
	 */
	public BoolExpressionComparison(){
		op = null; 
		right = null; 
		left = null; 
	}
	
	/**
	 * Constructor
	 * @param op The operator contained in this class (==, !=, >, <, =>, =<). Represented as a string. 
	 * @param right The first of two PrimitiveExpression's that are evaluated using the stored boolean comparison operator
	 * @param left The second of two PrimitiveExpression's that are evaluated using the stored boolean comparison operator
	 */
	public BoolExpressionComparison(String op, PrimitiveExpression right, PrimitiveExpression left){
		this.op = op; 
		this.right = right; 
		this.left = left; 
	}
	
	/**
	 * Evaluate this boolean expression
	 * @return the evaluation
	 * @throws Exception if the expression is incorrectly constructed
	 */
	public boolean evaluate() throws Exception{
		PrimitiveExpression rightEval = right.evaluate();
		PrimitiveExpression leftEval = left.evaluate(); 
		
		switch(op){
		case "==": 
			return rightEval.toString().equals(leftEval.toString()); 
		case "!=": 
			return !rightEval.toString().equals(leftEval.toString()); 
		case ">": 
			return rightEval.toInt() > leftEval.toInt();
		case "<": 
			return rightEval.toInt() < leftEval.toInt();
		case ">=": 
			return rightEval.toInt() >= leftEval.toInt();
		case "<=": 
			return rightEval.toInt() <= leftEval.toInt();
		default: 
			System.out.println("BooleanExpressionComparison evaluate() error: op not recognized");
			throw new Exception(); 
		}
	}
}
