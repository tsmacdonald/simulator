/**
 * IsValidCoord.java
 * 
 * @author Agent Team
 */

package edu.wheaton.simulator.expression;

import net.sourceforge.jeval.EvaluationException;

public class IsValidCoord extends AbstractExpressionFunction {

	@Override
	public String getName() {
		return "isValidCoord";
	}

	@Override
	public int getResultType() {
		return AbstractExpressionFunction.RESULT_TYPE_BOOL;
	}

	@Override
	public Integer numArgs() {
		return 2;
	}
	
	/**
	 * Takes the form: isValidCoord(x, y).
	 * 
	 * @param args
	 * @return
	 */
	@Override
	public String execute(String[] args) throws EvaluationException {
		Double x = Double.valueOf(args[0]);
		Double y = Double.valueOf(args[1]);
		Boolean isValid = resolveAgent("this").getGrid().isValidCoord(x.intValue(),y.intValue());
		if(isValid==true)
			return Expression.TRUE;
		return Expression.FALSE;
	}
}
