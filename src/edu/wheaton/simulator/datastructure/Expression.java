package edu.wheaton.simulator.datastructure;

import edu.wheaton.simulator.entity.Entity;
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
	
	public Value evaluate(Entity xThis, Entity xOther, Entity xLocal, Entity xGlobal) throws EvaluationException{
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
