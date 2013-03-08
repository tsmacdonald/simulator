package edu.wheaton.simulator.datastructures.expression;

import net.sourceforge.jeval.EvaluationException;
import net.sourceforge.jeval.Evaluator;

public final class Expression {
	private String mStr;
	
	protected Expression(String str){
		if(str==null || str.isEmpty())
			throw new IllegalArgumentException();
		
		mStr = str;
	}
	
	public Expression(Primitive val){
		mStr = val.toString();
	}
	
	@Override
	public String toString(){
		return mStr;
	}
	
	public boolean getBool() throws EvaluationException{
		Evaluator evaluator = new Evaluator();
		return evaluator.getBooleanResult(this.toString());
	}
	
	public Double getDouble() throws EvaluationException{
		Evaluator evaluator = new Evaluator();
		return evaluator.getNumberResult(this.toString());
	}
	
	public Expression evaluate() throws EvaluationException{
		Evaluator evaluator = new Evaluator();
		return new Expression(evaluator.evaluate(this.toString()));
	}
}
