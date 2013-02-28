/**
 * BoolExpressionOperator Class
 * 
 * Contains a boolean comparison operator (==, !=, >, <, =>, =<) 
 * and 2 arguments (Primitive Expressions). Main function is evaluating this 
 * expression and return the result. This is a subclass that is used to 
 * create the BoolExpression tree in the Trigger class  	 
 * 
 * @author Daniel Davenport, Grant Hensel, Elliot Penson, and Simon Swenson
 * Wheaton College, CSCI 335, Spring 2013
 */

package edu.wheaton.simulator.gridentities;

public class BoolExpressionComparison extends BoolExpression {

	/**
	 * The operator contained in this class (==, !=, >, <, =>, =<) 
	 */
	private ComparisonOp op;

	public enum ComparisonOp {
		EQUAL,
		NOT_EQUAL,
		GREATER_THAN,
		LESS_THAN,
		GREATER_THAN_OR_EQUAL,
		LESS_THAN_OR_EQUAL
	}

	/**
	 * The two PrimitiveExpression's that are evaluated using the stored 
     * boolean comparison operator
	 */
	private PrimitiveExpression left;
	
	// TODO Unused field: BoolExpressionComparison.right
	private PrimitiveExpression right; 
	
	/**
	 * Default constructor
	 */
	public BoolExpressionComparison() {
		op = null; 
		left = null; 
		right = null; 
	}
	
	/**
	 * Constructor
	 * @param op The operator contained in this class (==, !=, >, <, =>, =<). 
     * Represented as a string. 
	 * @param left The first of two PrimitiveExpression's that are evaluated 
     * using the stored boolean comparison operator
	 * @param right The second of two PrimitiveExpression's that are evaluated 
     * using the stored boolean comparison operator
	 */
	public BoolExpressionComparison(ComparisonOp op, PrimitiveExpression left, 
            PrimitiveExpression right) {
		this.op = op; 
		this.left = left; 
		this.right = right; 
	}
	
	/**
	 * Evaluate this boolean expression
	 * @return the evaluation
	 * @throws Exception if the expression is incorrectly constructed
	 */
	@Override
	public boolean evaluate(Agent me, Agent other) throws Exception {
		PrimitiveExpression leftEval = left.evaluate(me, other);
		PrimitiveExpression rightEval = left.evaluate(me, other); 
		
		switch (op) {
		case EQUAL: 
			return leftEval.toString().equals(rightEval.toString()); 
		case NOT_EQUAL: 
			return !leftEval.toString().equals(rightEval.toString()); 
		case GREATER_THAN: 
			return leftEval.intValue() > rightEval.intValue();
		case LESS_THAN: 
			return leftEval.intValue() < rightEval.intValue();
		case GREATER_THAN_OR_EQUAL: 
			return leftEval.intValue() >= rightEval.intValue();
		case LESS_THAN_OR_EQUAL: 
			return leftEval.intValue() <= rightEval.intValue();
		default:
			System.out.println("BooleanExpressionComparison evaluate()"
                   + " error: op not recognized");
			throw new Exception(); 
		}
	}

}
