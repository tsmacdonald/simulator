package edu.wheaton.simulator.datastructures.expression;

import edu.wheaton.simulator.datastructures.Primitive;
import net.sourceforge.jeval.EvaluationException;
import net.sourceforge.jeval.Evaluator;

public final class Expression {
	private String mStr;
	
	public Expression(Object str){
		setString(str.toString());
	}
	
	public Expression(BinaryOperator op, Object lhs, Object rhs){
		setString("(" + lhs + " " + op + " " + rhs + ")");
	}
	
	public Expression(UnaryOperator op, Object expr){
		setString("(" + op + expr +")");
	}
	
	@Override
	public String toString(){
		return mStr;
	}
	
	private void setString(String str){
		if(str==null || str.isEmpty())
			throw new IllegalArgumentException();
		mStr = str;
	}
	
	public Boolean getBool() throws EvaluationException{
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
