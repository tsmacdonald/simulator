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

import edu.wheaton.simulator.datastructures.Primitive;
import edu.wheaton.simulator.datastructures.StringFormatMismatchException;
import edu.wheaton.simulator.datastructures.Type;
import edu.wheaton.simulator.gridentities.Agent;

public class PrimitiveExpressionOperator extends Primitive {
	
	/**
	 * The operator stored in this object (+, -, /, *)
	 */
	private String op; 
	
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
	public PrimitiveExpressionOperator(String op, Primitive right, Primitive left){
		this.op = op; 
		this.right = right; 
		this.left = left; 
	}
	
	/**
	 * Evaluate this boolean expression
	 * @return the evaluation
	 * @throws Exception if the expression is incorrectly constructed
	 */
	public Primitive evaluate(Agent me, Agent other) throws Exception{
		//TODO Method "PrimitiveExpressionOperator.evaluate" only handles one value type
		try{
			if (op.equals("+"))
				return new Primitive(Type.INT, String.valueOf(right.intValue() + left.intValue()));
			else if (op.equals("-"))
				return new Primitive(Type.INT, String.valueOf(right.intValue() - left.intValue()));
			else if (op.equals("*"))
				return new Primitive(Type.INT, String.valueOf(right.intValue() * left.intValue()));
			else if (op.equals("/"))
				return new Primitive(Type.INT, String.valueOf(right.intValue() / left.intValue()));
			else{
				System.out.println("PrimitiveExpressionOperator evaluate() error: op not recognized");
				throw new Exception(); 
			}
		}
		catch(StringFormatMismatchException e){
			throw new Exception();
		}
	}
}
