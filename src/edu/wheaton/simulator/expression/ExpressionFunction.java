package edu.wheaton.simulator.expression;

import net.sourceforge.jeval.Evaluator;
import net.sourceforge.jeval.function.Function;
import net.sourceforge.jeval.function.FunctionConstants;
import net.sourceforge.jeval.function.FunctionException;
import net.sourceforge.jeval.function.FunctionResult;

public abstract class ExpressionFunction {
	
	//RETURN_TYPE_NUMERIC and RETURN_TYPE_BOOL are supposed to have the same value.
	//The distinction here is for clarity.
	protected static final int RESULT_TYPE_NUMERIC = FunctionConstants.FUNCTION_RESULT_TYPE_NUMERIC;
	protected static final int RESULT_TYPE_BOOL = FunctionConstants.FUNCTION_RESULT_TYPE_NUMERIC;
	protected static final int RESULT_TYPE_STRING = FunctionConstants.FUNCTION_RESULT_TYPE_STRING;
	
	public abstract String getName();
	
	/**
	 * May only return one of the three static constants defined in this class
	 * 
	 */
	protected abstract int getResultType();
	
	protected Function makeJEvalFunction(){
		final ExpressionFunction xEnclosingWrapper = this;
		return new Function(){

			private final ExpressionFunction enclosingWrapper = xEnclosingWrapper;
			
			@Override
			public FunctionResult execute(Evaluator evaluator, String arguments)
					throws FunctionException {
				String[] args = arguments.split(",");
				return new FunctionResult(enclosingWrapper.execute(args),enclosingWrapper.getResultType());
			}

			@Override
			public String getName() {
				return enclosingWrapper.getName();
			}
			
		};
	}

	protected abstract String execute(String[] args);
}
