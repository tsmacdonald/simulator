package edu.wheaton.simulator.behavior;

import edu.wheaton.simulator.expression.AbstractExpressionFunction;
import edu.wheaton.simulator.expression.ExpressionEvaluator;

public abstract class AbstractBehavior extends AbstractExpressionFunction {

	private ExpressionEvaluator eval;
	
	protected AbstractBehavior(ExpressionEvaluator eval){
		this.eval = eval;
	}
	
	public ExpressionEvaluator getExprEval(){
		return eval;
	}
	
	@Override
	public int getResultType(){
		return AbstractExpressionFunction.RESULT_TYPE_BOOL;
	}
}
