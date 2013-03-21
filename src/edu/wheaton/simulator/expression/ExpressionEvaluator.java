package edu.wheaton.simulator.expression;

import net.sourceforge.jeval.EvaluationException;
import edu.wheaton.simulator.entity.Entity;

public interface ExpressionEvaluator {
	
	public ExpressionEvaluator clone();

	public void setString(Object exprStr);

	public void importVariable(String name, String value);

	public void importEntity(String aliasName, Entity entity);

	public void importFunction(AbstractExpressionFunction function);

	public Entity getEntity(String aliasName);

	public String getVariableValue(String variableName) throws EvaluationException;

	public void clearVariables();

	public void clearFunctions();

	public Boolean evaluateBool() throws EvaluationException;

	public Double evaluateDouble() throws EvaluationException;

	public String evaluateString() throws EvaluationException;
}
