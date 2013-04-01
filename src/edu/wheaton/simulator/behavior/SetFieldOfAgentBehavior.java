package edu.wheaton.simulator.behavior;

import net.sourceforge.jeval.EvaluationException;
import edu.wheaton.simulator.datastructure.Grid;
import edu.wheaton.simulator.entity.Agent;
import edu.wheaton.simulator.expression.AbstractExpressionFunction;
import edu.wheaton.simulator.expression.Expression;

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
		int x = Double.valueOf(args[1]).intValue();
		int y = Double.valueOf(args[2]).intValue();
		String fieldName = args[3].replaceAll("'", "");
		String newFieldValue = args[4];
		
		Grid grid = target.getGrid();
		if(grid.isValidCoord(x, y) && grid.getAgent(x,y)!=null){
			grid.getAgent(x, y).updateField(fieldName, newFieldValue);
			return Expression.TRUE;
		}
		return Expression.FALSE;
	}
	

}
