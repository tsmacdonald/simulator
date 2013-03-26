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
	
	//constants for returning boolean results
	protected static final String TRUE = "1.0";
	protected static final String FALSE = "0.0";
	
	/*
	 * A wrapper constructed from a JEval Evaluator instance that is not known until the point at which the
	 * function is called by JEval. For now it is null.
	 */
	private Expression evaluator=null;
	
	/**
	 * The name of the function as it is to appear in an Expression (ex: "add")
	 */
	@Override
	public abstract String getName();

	/**
	 * Returns one of the three constants defined at the top of this class
	 */
	@Override
	public abstract int getResultType();

	/**
	 * When this method is called from within an Expression, it's parameters are evaluated
	 * down to either a string ("'i'm a string'"), a boolean ("true"), or a double ("1.0" or "1")
	 * and then passed to this function which then performs the intended logic
	 */
	@Override
	public abstract String execute(String[] args) throws EvaluationException;

	/**
	 * returns the wrapper for the JEval Evaluator that is null until JEval calls the function
	 * and provides a means to construct an ExpressionEvaluator
	 * @return
	 */
	private Expression getExprEval(){
		return evaluator;
	}
	
	/**
	 * Returns the Entity mapped to the given 'aliasName'
	 * 
	 * @param aliasName
	 * 		the name used to refer to the entity in the expression
	 */
	@Override
	public Entity resolveEntity(String aliasName) {
		return getExprEval().getEntity(aliasName.replaceAll("'", ""));
	}
	
	/**
	 * Same as resolveEntity except that the return value is casted
	 * to Agent
	 */
	@Override
	public Agent resolveAgent(String aliasName){
		return (Agent)resolveEntity(aliasName);
	}

	/**
	 * Returns the variable value (doubleStr,stringStr, or boolStr) mapped to the given 'aliasName'
	 * 
	 * @param aliasName
	 * 		the name used to refer to the variable in the expression
	 */
	@Override
	public String resolveVariable(String variableName)
			throws EvaluationException {
		return getExprEval().getVariableValue(variableName);
	}

	/**
	 * Formats this object into a form that is usable by JEval such that
	 * the layer of abstraction is not lost.
	 */
	@Override
	public Function toJEvalFunction() {
		final AbstractExpressionFunction xEnclosingWrapper = this;
		return new Function() {

			/*
			 * used to differentiate between ExpressionFunction.execute() and Function.execute()
			 */
			protected final AbstractExpressionFunction enclosingWrapper = xEnclosingWrapper;

			@Override
			public FunctionResult execute(Evaluator evaluator, String arguments)
					throws FunctionException {
				
				/*
				 * wraps the passed 'Evaluator' into an ExpressionEvaluator to provide a higher level
				 * of abstraction away from the internals of JEval
				 */
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

	/**
	 * Used to set the ExpressionEvaluator once constructed
	 * @param evaluator
	 */
	private void setExprEval(Expression evaluator) {
		this.evaluator = evaluator;
	}
}
