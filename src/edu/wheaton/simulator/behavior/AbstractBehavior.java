package edu.wheaton.simulator.behavior;

import edu.wheaton.simulator.expression.AbstractExpressionFunction;
import edu.wheaton.simulator.expression.ExpressionEvaluator;

public abstract class AbstractBehavior extends AbstractExpressionFunction {
	@Override
	public int getResultType(){
		return AbstractExpressionFunction.RESULT_TYPE_BOOL;
	}
}
