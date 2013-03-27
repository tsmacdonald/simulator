package edu.wheaton.simulator.behavior;

import net.sourceforge.jeval.EvaluationException;
import edu.wheaton.simulator.entity.Agent;
import edu.wheaton.simulator.expression.AbstractExpressionFunction;
import edu.wheaton.simulator.expression.Expression;
import edu.wheaton.simulator.simulation.Grid;

public class SetFieldOfAgentBehavior extends AbstractExpressionFunction {

	@Override
	public String getName() {
		return "setFieldOfAgent";
	}

	@Override
	public int getResultType() {
		return AbstractExpressionFunction.RESULT_TYPE_BOOL;
	}

	@Override
	public String execute(String[] args) throws EvaluationException {
		Agent target = resolveAgent(args[0]);
		int x = Integer.valueOf(args[1]);
		int y = Integer.valueOf(args[2]);
		String fieldName = args[3];
		String newFieldValue = args[4];
		
		Grid grid = target.getGrid();
		if(grid.isValidCoord(x, y) && grid.getAgent(x,y)==null){
			target.getGrid().getAgent(x, y).updateField(fieldName, newFieldValue);
			return Expression.TRUE;
		}
		return Expression.FALSE;
	}
	

}
