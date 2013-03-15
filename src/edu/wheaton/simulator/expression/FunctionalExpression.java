package edu.wheaton.simulator.expression;

import net.sourceforge.jeval.Evaluator;
import net.sourceforge.jeval.function.Function;
import net.sourceforge.jeval.function.FunctionException;
import net.sourceforge.jeval.function.FunctionResult;

public class FunctionalExpression implements Function {

	private String name;
	private Expression expression;
	
	public FunctionalExpression(String name, Expression expression){
		this.name = name;
		this.expression = expression;
	}
	
	@Override
	public FunctionResult execute(Evaluator evaluator, String args)
			throws FunctionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		return name;
	}

}
