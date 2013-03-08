package edu.wheaton.simulator.datastructures.expression;

import edu.wheaton.simulator.datastructures.Primitive;
import net.sourceforge.jeval.EvaluationException;
import net.sourceforge.jeval.Evaluator;

public final class Expression {
	private String mStr;
	
	public Expression(String str){
		setString(str);
	}
	
	public Expression(Primitive val){
		setString(val.toString());
	}
	
	public Expression(BinaryOperator op, Expression lhs, Expression rhs){
		setString("(" + lhs + " " + op + " " + rhs + ")");
	}
	
	public Expression(UnaryOperator op, Expression expr){
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
