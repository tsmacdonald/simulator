package edu.wheaton.simulator.expression;

import net.sourceforge.jeval.EvaluationException;
import net.sourceforge.jeval.function.Function;
import edu.wheaton.simulator.entity.Agent;
import edu.wheaton.simulator.entity.Entity;

public interface ExpressionFunction {
	
	public String getName();

	public int getResultType();

	public String execute(String[] args) throws EvaluationException;

	public Entity resolveEntity(String aliasName);
	
	public Agent resolveAgent(String aliasName);

	public String resolveVariable(String variableName)
			throws EvaluationException;

	public Function toJEvalFunction();

}
