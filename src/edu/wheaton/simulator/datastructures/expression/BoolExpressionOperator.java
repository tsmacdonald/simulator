/**
 * BoolExpressionOperator Class
 * 
 * Contains a boolean operator (&&, || or !) and 1 or 2 arguments (other BoolExpression's). Main function is evaluating this expression and return the result. 
 * This is a subclass that is used to create the BoolExpression tree in the Trigger class  	 
 * 
 * @author Daniel Davenport, Grant Hensel, Elliot Penson, and Simon Swenson
 * Wheaton College, CSCI 335, Spring 2013
 */

package edu.wheaton.simulator.datastructures.expression;

import net.sourceforge.jeval.EvaluationException;
import edu.wheaton.simulator.gridentities.Agent;

public class BoolExpressionOperator extends BoolExpression {

	/**
	 * The operator contained in this class. Represented as a string. Can be "&&", "||" or "!" (Note: If using "!", supply the same boolean expression as the right and leave the left null)
	 */
	private Operator op;
	private Boolean isBinary;
	
	/**
	 * The two boolean expressions that are evaluated using the stored boolean operator
	 */
	private BoolExpression left, right;
	
	/**
	 * Constructor
	 * @param op The operator contained in this class. Represented as a string. Can be "&&", "||" or "!" (Note: If using "!", supply the same boolean expression as the right and leave the left null)
	 * @param right The first of two boolean expressions that are evaluated using the stored boolean operator
	 * @param left The second of two boolean expressions that are evaluated using the stored boolean operator
	 */
	public BoolExpressionOperator(BinaryOperator op, BoolExpression right, BoolExpression left){
		this.op = op;
		this.isBinary = true;
		this.left = left; 
		this.right = right;
	}
	
	public BoolExpressionOperator(UnaryOperator op, BoolExpression right, BoolExpression left){
		this.op = op;
		this.isBinary = false;
		this.left = left; 
		this.right = right;
	}
	
	/**
	 * Evaluate this boolean expression
	 * @return the evaluation
	 * @throws Exception if the expression is incorrectly constructed
	 */
	@Override
	public Boolean evaluate(Agent me, Agent other) throws EvaluationException{
		Boolean rightEval = right.evaluate(me, other);
		Boolean leftEval = left.evaluate(me, other);
		
		if(isBinary){
			Expression expr = new Expression( (BinaryOperator) op,leftEval.toString(),rightEval.toString());
			return expr.getBool();
		}
		Expression expr = new Expression( (UnaryOperator) op,rightEval.toString());
		return expr.getBool();
	}
}
