package edu.wheaton.simulator.datastructures;

import edu.wheaton.simulator.gridentities.GridEntity;
import edu.wheaton.simulator.simulation.Grid;

public class PrimitiveBooleanOperatorNode implements BooleanEvaluatable {

	public enum Sign { EQUALS, NOT_EQUALS, GREATER, LESS, GREATER_OR_EQUAL, LESS_OR_EQUAL;
		
	}
	
	private Sign sign;
	private PrimitiveEvaluatable left;
	private PrimitiveEvaluatable right;
	
	public PrimitiveBooleanOperatorNode(Sign sign, PrimitiveEvaluatable left, PrimitiveEvaluatable right) {
		this.sign = sign;
		this.left = left;
		this.right = right;
	}
	
	@Override
	public boolean evaluate(GridEntity me, GridEntity other, GridEntity local,
			Grid global) {
		Primitive leftEval = left.evaluate(me, other, local, global);
		Primitive rightEval = right.evaluate(me, other, local, global);
		if(sign.equals(Sign.EQUALS)) {
			return leftEval.stringValue().equals(rightEval.stringValue());
		}
		else if(sign.equals(Sign.NOT_EQUALS)) {
			return !leftEval.stringValue().equals(rightEval.stringValue());
		}
		else if(sign.equals(Sign.GREATER)) {
			try {
				return leftEval.intValue() > rightEval.intValue();
			}
			catch(StringFormatMismatchException e) {
				try {
					return leftEval.doubleValue() > rightEval.doubleValue();
				} catch (StringFormatMismatchException e1) {
					return false;
				}
			}
		}
		else if(sign.equals(Sign.LESS)) {
			try {
				return leftEval.intValue() < rightEval.intValue();
			}
			catch(StringFormatMismatchException e) {
				try {
					return leftEval.doubleValue() < rightEval.doubleValue();
				} catch (StringFormatMismatchException e1) {
					return false;
				}
			}
		}
		else if(sign.equals(Sign.GREATER_OR_EQUAL)) {
			try {
				return leftEval.intValue() >= rightEval.intValue();
			}
			catch(StringFormatMismatchException e) {
				try {
					return leftEval.doubleValue() >= rightEval.doubleValue();
				} catch (StringFormatMismatchException e1) {
					return false;
				}
			}
		}
		else if(sign.equals(Sign.LESS_OR_EQUAL)) {
			try {
				return leftEval.intValue() <= rightEval.intValue();
			}
			catch(StringFormatMismatchException e) {
				try {
					return leftEval.doubleValue() <= rightEval.doubleValue();
				} catch (StringFormatMismatchException e1) {
					return false;
				}
			}
		}
		return false;
	}

}
