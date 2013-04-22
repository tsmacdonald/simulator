package edu.wheaton.simulator.expression;

import edu.wheaton.simulator.datastructure.Grid;
import net.sourceforge.jeval.EvaluationException;

public class GetGlobalField extends AbstractExpressionFunction{

	@Override
	public String getName() {
		return "getGlobalField";
	}

	@Override
	public Integer numArgs() {
		return 1;
	}

	@Override
	public int getResultType() {
		return AbstractExpressionFunction.RESULT_TYPE_BOOL;
	}

	@Override
	public String execute(String[] args) throws EvaluationException {
		String fieldName = args[0].replaceAll("'", "");
		Grid grid = resolveAgent("this").getGrid();
		return grid.getFieldValue(fieldName).toString();
	}

}
