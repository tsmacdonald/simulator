package edu.wheaton.simulator.datastructures.expression;

import edu.wheaton.simulator.datastructures.Primitive;
import edu.wheaton.simulator.datastructures.Value;
import edu.wheaton.simulator.gridentities.GridEntity;
import net.sourceforge.jeval.EvaluationException;
import net.sourceforge.jeval.Evaluator;

public final class Expression {
	private Value mVal;
	
	public Expression(Object fx){
		setString(fx);
	}
	
	@Override
	public String toString(){
		return mVal.toString();
	}
	
	private void setString(Object fx){
		String str = fx.toString();
		if(str==null || str.isEmpty())
			throw new IllegalArgumentException();
		mVal = new Value(fx);
	}
	
	public Boolean getBoolean() throws EvaluationException{
		Evaluator evaluator = new Evaluator();
		return evaluator.getBooleanResult(this.toString());
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
		return new Expression(evaluator.evaluate(this.toString()));
	}
}
