package edu.wheaton.simulator.expression;

import net.sourceforge.jeval.EvaluationException;
import net.sourceforge.jeval.function.Function;
import edu.wheaton.simulator.entity.Entity;

public interface ExpressionFunction {

	public String getName();

	public Entity getEntity(ExpressionEvaluator expr, String aliasName);

	public String getVariableValue(ExpressionEvaluator expr, String variableName)
			throws EvaluationException;

	public int getResultType();

	public Function toJEvalFunction();

	public String execute(String[] args) throws EvaluationException;

}
