/**
 * BoolExpressionOperator Class
 * 
 * Contains a boolean operator (&&, || or !) and 1 or 2 arguments (other BoolExpression's). Main function is evaluating this expression and return the result. 
 * This is a subclass that is used to create the BoolExpression tree in the Trigger class  	 
 * 
 * @author Daniel Davenport, Grant Hensel, Elliot Penson, and Simon Swenson
 * Wheaton College, CSCI 335, Spring 2013
 */

package universalsim;

public class BoolExpressionOperator extends BoolExpression {

	/**
	 * The operator contained in this class. Represented as a string. Can be "&&", "||" or "!" (Note: If using "!", supply the same boolean expression as the right and leave the left null)
	 */
	private String op; 
	
	/**
	 * Hold references to this agent and an arbitary agent "other", as this will be required for some trigger conditions
	 */
	private Agent me, other; 
	
	/**
	 * The two boolean expressions that are evaluated using the stored boolean operator
	 */
	private BoolExpression right, left; 
	
	/**
	 * Default constructor
	 */
	public BoolExpressionOperator(){
		op = null; 
		right = null; 
		left = null; 
	}
	
	/**
	 * Constructor
	 * @param op The operator contained in this class. Represented as a string. Can be "&&", "||" or "!" (Note: If using "!", supply the same boolean expression as the right and leave the left null)
	 * @param right The first of two boolean expressions that are evaluated using the stored boolean operator
	 * @param left The second of two boolean expressions that are evaluated using the stored boolean operator
	 */
	public BoolExpressionOperator(String op, BoolExpression right, BoolExpression left){
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
		boolean rightEval = right.evaluate();
		boolean leftEval = left.evaluate(); 
		
		switch(op){
		case "&&": 
			return rightEval && leftEval; 
		case "||": 
			return rightEval || leftEval; 
		case "!": 
			return !rightEval; 
		default: 
			System.out.println("Boolean Expression evaluate() error: op not recognized");
			throw new Exception(); 
		}
	}
}
