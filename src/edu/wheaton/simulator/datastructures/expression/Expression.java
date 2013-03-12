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
		return new Expression(evaluator.evaluate(this.toString()));
	}
	
	private static String resolveVariable(String _varStr, GridEntity xThis, GridEntity xOther, GridEntity xLocal, GridEntity xGlobal){
		String varStr = removeWhiteSpace(_varStr);
		String targetName = extractTargetName(varStr);
		String fieldName = extractFieldName(varStr);
		GridEntity target = resolveTarget(targetName,xThis,xOther,xLocal,xGlobal);
		return target.getField(fieldName).getValue();
	}

	private static GridEntity resolveTarget(String targetName,
			GridEntity xThis, GridEntity xOther, GridEntity xLocal,
			GridEntity xGlobal) {
		// TODO Auto-generated method stub
		return null;
	}

	private static String extractFieldName(String varStr) {
		// TODO Auto-generated method stub
		return null;
	}

	private static String extractTargetName(String varStr) {
		// TODO Auto-generated method stub
		return null;
	}

	private static String removeWhiteSpace(String str) {
		//TODO not implemented (does nothing)
		return str;
	}
}
