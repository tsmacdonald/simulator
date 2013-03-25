package edu.wheaton.simulator.expression;

import net.sourceforge.jeval.EvaluationException;
import net.sourceforge.jeval.Evaluator;
import net.sourceforge.jeval.function.Function;
import net.sourceforge.jeval.function.FunctionConstants;
import net.sourceforge.jeval.function.FunctionException;
import net.sourceforge.jeval.function.FunctionResult;
import edu.wheaton.simulator.entity.Agent;
import edu.wheaton.simulator.entity.Entity;

public abstract class AbstractExpressionFunction implements ExpressionFunction {

	// RETURN_TYPE_NUMERIC and RETURN_TYPE_BOOL are supposed to have the same
	// value.
	// The distinction here is for clarity.
	protected static final int RESULT_TYPE_NUMERIC = FunctionConstants.FUNCTION_RESULT_TYPE_NUMERIC;
	protected static final int RESULT_TYPE_BOOL = FunctionConstants.FUNCTION_RESULT_TYPE_NUMERIC;
	protected static final int RESULT_TYPE_STRING = FunctionConstants.FUNCTION_RESULT_TYPE_STRING;
	
	private ExpressionEvaluator evaluator=null;
	
	@Override
	public abstract String getName();

	@Override
	public abstract int getResultType();

	@Override
	public abstract String execute(String[] args) throws EvaluationException;

	private ExpressionEvaluator getExprEval(){
		return evaluator;
	}
	
	@Override
	public Entity resolveEntity(String aliasName) {
		return getExprEval().getEntity(aliasName);
	}
	
	@Override
	public Agent resolveAgent(String aliasName){
		return (Agent)resolveEntity(aliasName);
	}

	@Override
	public String resolveVariable(String variableName)
			throws EvaluationException {
		return getExprEval().getVariableValue(variableName);
	}

	@Override
	public Function toJEvalFunction() {
		final AbstractExpressionFunction xEnclosingWrapper = this;
		return new Function() {

			protected final AbstractExpressionFunction enclosingWrapper = xEnclosingWrapper;

			@Override
			public FunctionResult execute(Evaluator evaluator, String arguments)
					throws FunctionException {
				
				setExprEval(new Expression(evaluator,(Expression.EntityFieldResolver)evaluator.getVariableResolver()));
				
				String[] args = arguments.split(",");
				try {
					return new FunctionResult(enclosingWrapper.execute(args),
							getResultType());
				} catch (EvaluationException e) {
					throw new FunctionException(arguments);
				}
			}

			@Override
			public String getName() {
				return enclosingWrapper.getName();
			}

		};
	}

	private void setExprEval(ExpressionEvaluator evaluator) {
		this.evaluator = evaluator;
	}
}
