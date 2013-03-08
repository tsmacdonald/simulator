/**
 * BoolExpressionOperator Class
 * 
 * Contains a primitive operator (+, -, *, /) and 2 arguments (Primitive Expressions). Main function is evaluating this expression and return the result. 
 * This is a subclass that is used to create the BoolExpression tree in the Trigger class  	 
 * 
 * @author Daniel Davenport, Grant Hensel, Elliot Penson, and Simon Swenson
 * Wheaton College, CSCI 335, Spring 2013
 */

package edu.wheaton.simulator.datastructures.expression;

import net.sourceforge.jeval.EvaluationException;
import edu.wheaton.simulator.datastructures.Primitive;
import edu.wheaton.simulator.datastructures.StringFormatMismatchException;
import edu.wheaton.simulator.datastructures.Type;
import edu.wheaton.simulator.gridentities.Agent;

public class PrimitiveExpressionOperator extends Primitive {
	
	/**
	 * The operator stored in this object (+, -, /, *)
	 */
	private BinaryOperator op; 
	
	/**
	 * The two PrimitiveExpression's that are evaluated using the stored operator
	 */
	private Primitive right, left; 
	
	/**
	 * Constructor
	 * @param op The operator contained in this class (+, -, /, *). Represented as a string. 
	 * @param right The first of two PrimitiveExpression's that are evaluated using the stored boolean comparison operator
	 * @param left The second of two PrimitiveExpression's that are evaluated using the stored boolean comparison operator
	 */
	public PrimitiveExpressionOperator(BinaryOperator op, Primitive right, Primitive left){
		this.op = op; 
		this.right = right; 
		this.left = left; 
	}
	
	/**
	 * Evaluate this boolean expression
	 * @return the evaluation
	 * @throws EvaluationException 
	 *
	 */
	public Primitive evaluate(Agent me, Agent other) throws EvaluationException{
		Expression expr = new Expression(op,left.toString(),right.toString());
		return new Primitive(Type.DOUBLE,expr.getDouble().toString());
	}
}
