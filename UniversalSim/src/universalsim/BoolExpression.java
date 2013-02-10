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

package universalsim;

public class BoolExpression {

	/**
	 * Value contained in this boolean expression
	 */
	private boolean value; 
	
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
	}
	
	/**
	 * Evaluate this boolean expression
	 * @return the evaluation
	 * @throws Exception if the expression is incorrectly constructed
	 */
	public boolean evaluate() throws Exception{
		return value; 
	}
}
