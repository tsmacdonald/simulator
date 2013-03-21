package edu.wheaton.simulator.expression;

import net.sourceforge.jeval.EvaluationException;
import net.sourceforge.jeval.Evaluator;
import net.sourceforge.jeval.function.Function;
import net.sourceforge.jeval.function.FunctionConstants;
import net.sourceforge.jeval.function.FunctionException;
import net.sourceforge.jeval.function.FunctionResult;
import edu.wheaton.simulator.entity.Entity;

public abstract class AbstractExpressionFunction implements ExpressionFunction {

	// RETURN_TYPE_NUMERIC and RETURN_TYPE_BOOL are supposed to have the same
	// value.
	// The distinction here is for clarity.
	protected static final int RESULT_TYPE_NUMERIC = FunctionConstants.FUNCTION_RESULT_TYPE_NUMERIC;
	protected static final int RESULT_TYPE_BOOL = FunctionConstants.FUNCTION_RESULT_TYPE_NUMERIC;
	protected static final int RESULT_TYPE_STRING = FunctionConstants.FUNCTION_RESULT_TYPE_STRING;

	@Override
	public abstract String getName();

	@Override
	public Entity getEntity(ExpressionEvaluator expr, String aliasName) {
		return expr.getEntity(aliasName);
	}

	@Override
	public String getVariableValue(ExpressionEvaluator expr, String variableName)
			throws EvaluationException {
		return expr.getVariableValue(variableName);
	}

	/**
	 * May only return one of the three static constants defined in this class
	 * 
	 */
	@Override
	public abstract int getResultType();

	@Override
	public Function toJEvalFunction() {
		final AbstractExpressionFunction xEnclosingWrapper = this;
		return new Function() {

			protected final AbstractExpressionFunction enclosingWrapper = xEnclosingWrapper;

			@Override
			public FunctionResult execute(Evaluator evaluator, String arguments)
					throws FunctionException {
				String[] args = arguments.split(",");
				try {
					return new FunctionResult(enclosingWrapper.execute(args),
							enclosingWrapper.getResultType());
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

	@Override
	public abstract String execute(String[] args)
			throws EvaluationException;
}
