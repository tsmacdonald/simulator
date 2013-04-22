/**
 * IsSlotOpen.java
 * 
 * @author Agent Team
 */

package edu.wheaton.simulator.expression;

import net.sourceforge.jeval.EvaluationException;

public class IsSlotOpen extends AbstractExpressionFunction {

	@Override
	public String getName() {
		return "isSlotOpen";
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
	 * Takes the form isSlotOpen(x, y). Returns true if the position is open.
	 * 
	 * @param args
	 * @return
	 */
	@Override
	public String execute(String[] args) throws EvaluationException {
		Double x = Double.valueOf(args[0]);
		Double y = Double.valueOf(args[1]);
		Boolean isOpen = resolveAgent("this").getGrid().emptyPos(x.intValue(),y.intValue());
		
		if(isOpen==true)
			return Expression.TRUE;
		return Expression.FALSE;
	}


}
