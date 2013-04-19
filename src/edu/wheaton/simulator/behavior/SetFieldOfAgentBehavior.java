package edu.wheaton.simulator.behavior;

import java.util.NoSuchElementException;

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
	public Integer numArgs() {
		return 4;
	}

	@Override
	public String execute(String[] args) throws EvaluationException {
		Agent target = resolveAgent("this");
		int x = Double.valueOf(args[0]).intValue();
		int y = Double.valueOf(args[1]).intValue();
		String fieldName = args[2].replaceAll("'", "");
		String newFieldValue = args[3];
		
		Grid grid = target.getGrid();
		try {
			if(grid.isValidCoord(x, y) && grid.getAgent(x,y)!=null){
				grid.getAgent(x, y).updateField(fieldName, newFieldValue);
				return Expression.TRUE;
			}
			return Expression.FALSE;
		}
		catch (NoSuchElementException e) {
			return Expression.FALSE;
		}
	}
	

}
