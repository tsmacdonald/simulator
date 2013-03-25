package edu.wheaton.simulator.expression;

import net.sourceforge.jeval.EvaluationException;

public class GetFieldOfAgentAt extends AbstractExpressionFunction {

	@Override
	public String getName() {
		return "getFieldOfAgentAt";
	}

	@Override
	public int getResultType() {
		return AbstractExpressionFunction.RESULT_TYPE_NUMERIC;
	}

	@Override
	public String execute(String[] args) throws EvaluationException {
		Double x = Double.valueOf(args[0]);
		Double y = Double.valueOf(args[1]);
		String fieldName = args[2].replaceAll("'", "");
		Object fieldValue = resolveAgent("this").getGrid().getAgent(x.intValue(), y.intValue()).getFieldValue(fieldName);
		return fieldValue.toString();
		}
}
