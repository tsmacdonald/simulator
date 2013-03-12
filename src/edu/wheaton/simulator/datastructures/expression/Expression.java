package edu.wheaton.simulator.datastructures.expression;

import java.security.InvalidParameterException;

import edu.wheaton.simulator.datastructures.Primitive;
import edu.wheaton.simulator.datastructures.Value;
import edu.wheaton.simulator.gridentities.GridEntity;
import net.sourceforge.jeval.EvaluationException;
import net.sourceforge.jeval.Evaluator;
import net.sourceforge.jeval.VariableResolver;

public final class Expression {
	private Value mVal;
	
	public Expression(Object fx){
		setValue(fx);
	}
	
	@Override
	public String toString(){
		return mVal.toString();
	}
	
	private void setValue(Object fx){
		String str = fx.toString();
		if(str==null || str.isEmpty())
			throw new IllegalArgumentException();
		mVal = new Value(fx);
	}
	
	public Boolean getBoolean() throws EvaluationException{
		return getValue().getBoolean();
	}
	
	public Value getValue() throws EvaluationException{
		return new Value(getString());
	}
	
	public String getString() throws EvaluationException{
		Evaluator evaluator = new Evaluator();
		return evaluator.evaluate(this.toString());
	}
	
	public Expression evaluate(GridEntity xThis, GridEntity xOther, GridEntity xLocal, GridEntity xGlobal) throws EvaluationException{
		Evaluator evaluator = new Evaluator();
		
		VariableResolver varRes = new ExpressionParameterResolver(xThis,xOther,xLocal,xGlobal);
		evaluator.setVariableResolver(varRes);
		
		return new Expression(evaluator.evaluate(this.toString()));
	}
}
