package edu.wheaton.simulator.datastructures;

import edu.wheaton.simulator.gridentities.GridEntity;
import edu.wheaton.simulator.simulation.Grid;

public class BooleanBooleanOperatorNode implements BooleanEvaluatable {

	public enum Sign { 
		AND, OR, NOT;
	}
	
	private Sign sign;
	private BooleanEvaluatable left;
	private BooleanEvaluatable right;
	
	public BooleanBooleanOperatorNode(Sign sign, BooleanEvaluatable left, BooleanEvaluatable right) {
		this.sign = sign;
		this.left = left;
		this.right = right;
	}

	@Override
	public boolean evaluate(GridEntity me, GridEntity other, GridEntity local,
			Grid global) {
		boolean leftEval = left.evaluate(me, other, local, global);
		boolean rightEval = right.evaluate(me, other, local, global);
		if(sign.equals(Sign.AND))
			return leftEval && rightEval;
		else if(sign.equals(Sign.OR))
			return leftEval || rightEval;
		else //not. ASSUMING RIGHT SUBTREE IS THE OPERAND.
			return !rightEval;
	}
}
