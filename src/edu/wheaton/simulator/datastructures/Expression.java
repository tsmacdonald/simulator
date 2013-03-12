package edu.wheaton.simulator.datastructures;

import edu.wheaton.simulator.gridentities.GridEntity;
import net.sourceforge.jeval.EvaluationException;
import net.sourceforge.jeval.Evaluator;
import net.sourceforge.jeval.VariableResolver;

public final class Expression {
	private Value mVal;
	
	public Expression(Object fxVal){
		setValue(fxVal);
	}
	
	@Override
	public String toString(){
		return mVal.toString();
	}
	
	private void setValue(Object fx){
		mVal = new Value(fx);
	}
	
	public Value evaluate(GridEntity xThis, GridEntity xOther, GridEntity xLocal, GridEntity xGlobal) throws EvaluationException{
		Evaluator evaluator = new Evaluator();
		
		VariableResolver varRes = new ExpressionParameterResolver(xThis,xOther,xLocal,xGlobal);
		evaluator.setVariableResolver(varRes);
		
		return new Value(evaluator.evaluate(this.toString()));
	}
	
	public static Value evaluate(String expr) throws EvaluationException{
		Evaluator evaluator = new Evaluator();
		return new Value(evaluator.evaluate(expr));
	}
}
