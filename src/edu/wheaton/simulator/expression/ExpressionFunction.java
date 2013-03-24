package edu.wheaton.simulator.expression;

import net.sourceforge.jeval.EvaluationException;
import net.sourceforge.jeval.function.Function;
import edu.wheaton.simulator.entity.Entity;

public interface ExpressionFunction {
	
	public String getName();

	public int getResultType();

	public String execute(String[] args) throws EvaluationException;

	public Entity resolveEntity(ExpressionEvaluator expr, String aliasName);

	public String resolveVariable(ExpressionEvaluator expr, String variableName)
			throws EvaluationException;

	public Function toJEvalFunction();

}
